<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="请假类型" prop="leaveType">
        <el-select v-model="queryParams.leaveType" placeholder="请选择请假类型" clearable  style="width: 200px">
          <el-option
            v-for="dict in wf_leave_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流程实例状态" prop="processStatus">
        <el-select v-model="queryParams.processStatus" placeholder="请选择流程实例状态" clearable  style="width: 200px">
          <el-option
            v-for="dict in wf_process_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['oa:leave:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-tabs v-model="tableTab" @tab-change="handleClickTableTab" >
      <el-tab-pane label="全部" name="all"></el-tab-pane>
      <el-tab-pane label="我的" name="wd"></el-tab-pane>
      <el-tab-pane label="待办" name="db"></el-tab-pane>
      <el-tab-pane label="待签" name="dq"></el-tab-pane>
      <el-tab-pane label="已办" name="yb"></el-tab-pane>
    </el-tabs>
    <el-table v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" type="index" width="55" align="center" prop="id" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="员工" align="center" prop="employeeId" >
        <template #default="scope">
          <div v-for="item in userList" :key="item.userId">
            <span v-if="item.userId==scope.row.employeeId">
              {{ item.nickName }}
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="请假类型" align="center" prop="leaveType">
        <template #default="scope">
          <dict-tag :options="wf_leave_type" :value="scope.row.leaveType"/>
        </template>
      </el-table-column>
      <el-table-column label="请假开始日期" align="center" prop="startDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假结束日期" align="center" prop="endDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假原因说明" align="center" prop="reason" />
      <el-table-column label="流程定义ID" align="center" prop="processDefinitionId" />
      <el-table-column label="流程实例状态" align="center" prop="processStatus">
        <template #default="scope">
          <dict-tag :options="wf_process_status" :value="scope.row.processStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button v-show="scope.row.processStatus==null&&(tableTab=='all')" link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['oa:leave:edit']">修改</el-button>
          <el-button v-show="scope.row.processStatus==null&&(tableTab=='all')" link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['oa:leave:remove']">删除</el-button>
          <el-button  v-show="scope.row.processStatus==null" link type="primary" icon="Stamp" @click="handleStartProcess(scope.row)" >送审</el-button>
          <el-button v-show="scope.row.processStatus!=null&&(tableTab=='wd')" link type="primary" icon="CircleClose" @click="handleStop(scope.row)">取消</el-button>
          <el-button v-show="scope.row.processStatus!=null&&(tableTab=='db'||tableTab=='dq')" link type="primary" icon="EditPen" @click="handleProcess(scope.row)" >办理</el-button>
          <el-button v-show="scope.row.processStatus!=null&&(tableTab=='yb')" link type="primary" icon="RefreshLeft" @click="handleRevoke(scope.row)">撤回</el-button>
          <el-button v-show="scope.row.processStatus!=null" link type="primary" icon="Stamp" @click="handleView(scope.row)" >详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改请假管理对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="leaveRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="请假类型" prop="leaveType">
          <el-select v-model="form.leaveType" placeholder="请选择请假类型">
            <el-option
              v-for="dict in wf_leave_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择请假开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable
            v-model="form.endDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择请假结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="form.reason" placeholder="请输入请假原因说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>









    <el-dialog :title="handleprocesstitle" v-model="handleprocessopen" width="1200px" append-to-body>
      <ProcessDetailSoltComponent :procInsId="form.processDefinitionId" :taskId="form.taskId" :processed="processed" :open="handleprocessopen" @handleBack="handleBack" >
        <template #form-info>
          <!-- 这里可以是你想要传入的表单信息内容 -->
          <div>
            <el-form ref="processviewRef" :model="form"  label-width="110px" disabled>
                              <el-form-item label="请假类型" prop="leaveType">
                                <el-select v-model="form.leaveType" placeholder="请选择请假类型">
                                  <el-option
                                      v-for="dict in wf_leave_type"
                                      :key="dict.value"
                                      :label="dict.label"
                                      :value="dict.value"
                                  ></el-option>
                                </el-select>
                              </el-form-item>
                              <el-form-item label="请假开始日期" prop="startDate">
                                <el-date-picker clearable
                                                v-model="form.startDate"
                                                type="date"
                                                value-format="YYYY-MM-DD"
                                                placeholder="请选择请假开始日期">
                                </el-date-picker>
                              </el-form-item>
                              <el-form-item label="请假结束日期" prop="endDate">
                                <el-date-picker clearable
                                                v-model="form.endDate"
                                                type="date"
                                                value-format="YYYY-MM-DD"
                                                placeholder="请选择请假结束日期">
                                </el-date-picker>
                              </el-form-item>
                              <el-form-item label="请假原因说明" prop="reason">
                                <el-input v-model="form.reason" placeholder="请输入请假原因说明" />
                              </el-form-item>
            </el-form>
          </div>
        </template>
      </ProcessDetailSoltComponent>
    </el-dialog>
  </div>
</template>

<script setup name="Leave">
import { listLeave, getLeave, delLeave, addLeave, updateLeave ,startProcess,stopProcess,revokeProcess} from "@/api/oa/leave";
import ProcessDetailSoltComponent from '@/components/ProcessDetailSoltComponent/index.vue';
import { loadAllParams } from "@/api/page" ;
import { listUser } from "@/api/system/user";

const router = useRouter();
const { proxy } = getCurrentInstance();
const { wf_leave_type } = proxy.useDict('wf_leave_type');
const { wf_process_status } = proxy.useDict("wf_process_status");

const leaveList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const tableTab=ref("all");
const processed=ref(false);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    employeeId: null,
    leaveType: null,
    startDate: null,
    endDate: null,
    reason: null,
    processDefinitionId: null,
    processStatus: null,
  },
  rules: {
    leaveType: [
      { required: true, message: "请假类型不能为空", trigger: "change" }
    ],
    startDate: [
      { required: true, message: "请假开始日期不能为空", trigger: "blur" }
    ],
    endDate: [
      { required: true, message: "请假结束日期不能为空", trigger: "blur" }
    ],
    reason: [
      { required: true, message: "请假原因说明不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询请假管理列表 */
function getList() {
  loading.value = true;
  listLeave(queryParams.value).then(response => {
    leaveList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    employeeId: null,
    leaveType: null,
    startDate: null,
    endDate: null,
    reason: null,
    status: null,
    processDefinitionId: null,
    processStatus: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null,
    processDefinitionId: null,
    taskId: null
  };
  proxy.resetForm("leaveRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加请假管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getLeave(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改请假管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["leaveRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLeave(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLeave(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除请假管理编号为"' + _ids + '"的数据项？').then(function() {
    return delLeave(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('oa/leave/export', {
    ...queryParams.value
  }, `leave_${new Date().getTime()}.xlsx`)
}

/** 跳转到处理页面 */
const handleprocesstitle=ref("");
const handleprocessopen=ref(false);
const handleProcess = (row) => {
  reset();
  const _id = row.id || ids.value
  getLeave(_id).then(response => {
    processed.value=true;
    form.value = response.data;
    form.value.taskId=row.taskId;
    handleprocesstitle.value="办理流程";
    handleprocessopen.value=true;

  });
}
const handleBack=()=>{
  handleprocessopen.value=false;
  getList();
}
/** 详情按钮操作 */
function handleView(row) {
  reset();
  const _id = row.id || ids.value
  getLeave(_id).then(response => {
    processed.value=false;
    form.value = response.data;
    form.value.taskId=row.taskId;
    handleprocesstitle.value="查看流程详情";
    handleprocessopen.value=true;

  });
}


/** 撤回任务 */
const handleRevoke = async (row) => {


  proxy.$modal.confirm('是否确认撤回该流程数据项？').then(function() {
  const params = {
  processDefinitionId: row.processDefinitionId,
  taskId: row.taskId
  };
  return revokeProcess(params);
  }).then((response) => {
  getList();
  if(response.msg=='撤回成功'){
  proxy.$modal.msgSuccess(response.msg);
  }else{
  proxy.$modal.msgError(response.msg);
  }

  }).catch(() => {
  });
}

/** 取消按钮操作 */
function handleStop(row) {
  proxy.$modal.confirm('是否确认取消该流程数据？').then(function() {
  return stopProcess(row);
  }).then(() => {
  getList();
  proxy.$modal.msgSuccess("取消成功");
  }).catch(() => {});
}






/** 送审按钮操作 */
function handleStartProcess(row) {
  proxy.$modal.confirm('是否确认提交送审该流程').then(function() {
  return startProcess(row).then(response => {
  if(response.code==200){
  proxy.$modal.msgSuccess("送审成功");
  getList();
  }
  }).catch(() => {});
  });
}

/** 查询用户列表 */
const userList = ref([]);
function getUserList() {
  listUser(loadAllParams).then(response => {
    userList.value = response.rows;
  });
}


/** Tab页切换 */
function handleClickTableTab(active){

  try {
    if (!queryParams.value.params) {
      queryParams.value.params = {};
    }
    queryParams.value.params['processTab']=tableTab.value;
    getList();
  } catch (error) {
    console.error('Error in handleClick:', error);
  }

}

getUserList();
getList();
</script>
