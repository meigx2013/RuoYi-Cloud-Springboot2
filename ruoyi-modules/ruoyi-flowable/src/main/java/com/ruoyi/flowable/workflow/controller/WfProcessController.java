package com.ruoyi.flowable.workflow.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.flowable.core.domain.ProcessQuery;
import com.ruoyi.flowable.core.domain.model.PageQuery;
import com.ruoyi.flowable.core.page.TableDataInfo;
import com.ruoyi.flowable.workflow.domain.bo.WfCopyBo;
import com.ruoyi.flowable.workflow.domain.bo.WfTaskBo;
import com.ruoyi.flowable.workflow.domain.vo.*;
import com.ruoyi.flowable.workflow.service.IWfCopyService;
import com.ruoyi.flowable.workflow.service.IWfProcessService;
import com.ruoyi.flowable.workflow.service.IWfTaskService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.api.vo.FlowTaskVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.oer.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工作流流程管理
 *
 * @author KonBAI
 * @createTime 2022/3/24 18:54
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/process")
public class WfProcessController extends BaseController {

    private final IWfProcessService processService;
    private final IWfTaskService taskService;
    private final IWfCopyService copyService;

    /**
     * 查询可发起流程列表
     *
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/list")
    public TableDataInfo<WfDefinitionVo> startProcessList(ProcessQuery processQuery, PageQuery pageQuery) {
        return processService.selectPageStartProcessList(processQuery, pageQuery);
    }

    /**
     * 我拥有的流程
     */
    @GetMapping(value = "/ownList")
    public TableDataInfo<WfTaskVo> ownProcessList(ProcessQuery processQuery, PageQuery pageQuery) {
        return processService.selectPageOwnProcessList(processQuery, pageQuery);
    }

    /**
     * 获取待办列表
     */
    @GetMapping(value = "/todoList")
    public TableDataInfo<WfTaskVo> todoProcessList(ProcessQuery processQuery, PageQuery pageQuery) {
        return processService.selectPageTodoProcessList(processQuery, pageQuery);
    }

    /**
     * 获取待签列表
     *
     * @param processQuery 流程业务对象
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/claimList")
    public TableDataInfo<WfTaskVo> claimProcessList(ProcessQuery processQuery, PageQuery pageQuery) {
        return processService.selectPageClaimProcessList(processQuery, pageQuery);
    }

    /**
     * 获取已办列表
     *
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/finishedList")
    public TableDataInfo<WfTaskVo> finishedProcessList(ProcessQuery processQuery, PageQuery pageQuery) {
        return processService.selectPageFinishedProcessList(processQuery, pageQuery);
    }

    /**
     * 获取抄送列表
     *
     * @param copyBo 流程抄送对象
     * @param pageQuery 分页参数
     */
    @GetMapping(value = "/copyList")
    public TableDataInfo<WfCopyVo> copyProcessList(WfCopyBo copyBo, PageQuery pageQuery) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            copyBo.setUserId(loginUser.getUserid());
        }
        return copyService.selectPageList(copyBo, pageQuery);
    }

    /**
     * 导出可发起流程列表
     */
    @PostMapping("/startExport")
    public void startExport(@Validated ProcessQuery processQuery, HttpServletResponse response) {
        List<WfDefinitionVo> list = processService.selectStartProcessList(processQuery);

        ExcelUtil<WfDefinitionVo> util = new ExcelUtil<WfDefinitionVo>(WfDefinitionVo.class);
        util.exportExcel(response, list, "可发起流程","可发起流程");
    }

    /**
     * 导出我拥有流程列表
     */
    @PostMapping("/ownExport")
    public void ownExport(@Validated ProcessQuery processQuery, HttpServletResponse response) {
        List<WfTaskVo> list = processService.selectOwnProcessList(processQuery);
        List<WfOwnTaskExportVo> listVo = BeanUtil.copyToList(list, WfOwnTaskExportVo.class);
        for (WfOwnTaskExportVo exportVo : listVo) {
            exportVo.setStatus(ObjectUtil.isNull(exportVo.getFinishTime()) ? "进行中" : "已完成");
        }

        ExcelUtil<WfOwnTaskExportVo> util = new ExcelUtil<WfOwnTaskExportVo>(WfOwnTaskExportVo.class);
        util.exportExcel(response, listVo, "我拥有流程","我拥有流程");
    }

    /**
     * 导出待办流程列表
     */
    @PostMapping("/todoExport")
    public void todoExport(@Validated ProcessQuery processQuery, HttpServletResponse response) {
        List<WfTaskVo> list = processService.selectTodoProcessList(processQuery);
        List<WfTodoTaskExportVo> listVo = BeanUtil.copyToList(list, WfTodoTaskExportVo.class);
        ExcelUtil<WfTodoTaskExportVo> util = new ExcelUtil<WfTodoTaskExportVo>(WfTodoTaskExportVo.class);
        util.exportExcel(response, listVo, "待办流程","待办流程");
    }

    /**
     * 导出待签流程列表
     */
    @PostMapping("/claimExport")
    public void claimExport(@Validated ProcessQuery processQuery, HttpServletResponse response) {
        List<WfTaskVo> list = processService.selectClaimProcessList(processQuery);
        List<WfClaimTaskExportVo> listVo = BeanUtil.copyToList(list, WfClaimTaskExportVo.class);
        ExcelUtil<WfClaimTaskExportVo> util = new ExcelUtil<WfClaimTaskExportVo>(WfClaimTaskExportVo.class);
        util.exportExcel(response, listVo, "待签流程","待签流程");
    }

    /**
     * 导出已办流程列表
     */
    @PostMapping("/finishedExport")
    public void finishedExport(@Validated ProcessQuery processQuery, HttpServletResponse response) {
        List<WfTaskVo> list = processService.selectFinishedProcessList(processQuery);
        List<WfFinishedTaskExportVo> listVo = BeanUtil.copyToList(list, WfFinishedTaskExportVo.class);
        ExcelUtil<WfFinishedTaskExportVo> util = new ExcelUtil<WfFinishedTaskExportVo>(WfFinishedTaskExportVo.class);
        util.exportExcel(response, listVo, "已办流程","已办流程");

    }

    /**
     * 导出抄送流程列表
     */
    @PostMapping("/copyExport")
    public void copyExport(WfCopyBo copyBo, HttpServletResponse response) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            copyBo.setUserId(loginUser.getUserid());
        }
        List<WfCopyVo> list = copyService.selectList(copyBo);

        ExcelUtil<WfCopyVo> util = new ExcelUtil<WfCopyVo>(WfCopyVo.class);
        util.exportExcel(response, list, "抄送流程","抄送流程");
    }

    /**
     * 查询流程部署关联表单信息
     *
     * @param definitionId 流程定义id
     * @param deployId 流程部署id
     */
    @GetMapping("/getProcessForm")
    public R<?> getForm(@RequestParam(value = "definitionId") String definitionId,
                        @RequestParam(value = "deployId") String deployId,
                        @RequestParam(value = "procInsId", required = false) String procInsId) {
        return R.ok(processService.selectFormContent(definitionId, deployId, procInsId));
    }

    /**
     * 根据流程定义id启动流程实例
     *
     * @param processDefId 流程定义id
     * @param variables 变量集合,json对象
     */
    @PostMapping("/start/{processDefId}")
    public AjaxResult start(@PathVariable(value = "processDefId") String processDefId, @RequestBody Map<String, Object> variables) {
        return AjaxResult.success("流程启动成功",processService.startProcessByDefId(processDefId, variables));
    }

    /**
     * 删除流程实例
     *
     * @param instanceIds 流程实例ID串
     */
    @DeleteMapping("/instance/{instanceIds}")
    public R<Void> delete(@PathVariable String[] instanceIds) {
        processService.deleteProcessByIds(instanceIds);
         return R.ok(null,"操作成功");
    }

    /**
     * 读取xml文件
     * @param processDefId 流程定义ID
     */
    @GetMapping("/bpmnXml/{processDefId}")
    public R<String> getBpmnXml(@PathVariable(value = "processDefId") String processDefId) {
        return R.ok(processService.queryBpmnXmlById(processDefId),null );
    }

    /**
     * 查询流程详情信息
     *
     * @param procInsId 流程实例ID
     * @param taskId 任务ID
     */
    @GetMapping("/detail")
    public R detail(String procInsId, String taskId) {
        return R.ok(processService.queryProcessDetail(procInsId, taskId));
    }


    /**
     * 查找流程接口
     */
    @InnerAuth
    @PostMapping( value ="/commonProcessList")
    public  R<TableDataInfo> commonProcessList(@RequestBody FlowTaskVo flowTaskVo) {
        ProcessQuery processQuery =new ProcessQuery();
        processQuery.setProcessKey(flowTaskVo.getProcessKey());
        processQuery.setParams(flowTaskVo.getQueryMap());
        PageQuery pageQuery=new PageQuery();
        pageQuery.setPageNum(flowTaskVo.getPageNum());
        pageQuery.setPageSize(flowTaskVo.getPageSize());
        //sw
        TableDataInfo<WfTaskVo> result=null;
        switch (flowTaskVo.getType()) {
            case "wd":
                result=processService.selectPageOwnProcessList(processQuery, pageQuery);
                break;
            case "db":
                result=processService.selectPageTodoProcessList(processQuery, pageQuery);
                break;
            case "dq":
                result=processService.selectPageTodoProcessList(processQuery, pageQuery);
                break;
            case "yb":
                result=processService.selectPageFinishedProcessList(processQuery, pageQuery);
                break;
            case "csw":
                //todo 后续再补充
                break;
            default:
        }

        return R.ok(result);
    }

    /**
     * 启动流程接口
     */
    @InnerAuth
    @PostMapping( value ="/startProcessByKey")
    public  R<String> startProcessByKey(@RequestBody Map<String, Object> variables) {
        if(variables!=null&&variables.get("processKey")!=null){
            String processInstanceId=processService.startProcessByDefKey((String) variables.get("processKey"),variables);
            return R.ok(processInstanceId);
        }
       return null;
    }


    /**
     * 停止流程接口
     */
    @InnerAuth
    @PostMapping( value ="/stopProcess")
    public  R<String> stopProcess(@RequestBody String processInstanceId) {
        if(StringUtils.isNotNull(processInstanceId)){
            taskService.stopProcess(processInstanceId);
            return R.ok(processInstanceId);
        }
       return null;
    }
    /**
     * 删除流程接口
     */
    @InnerAuth
    @PostMapping( value ="/delProcess")
    public  R<String> delProcess(@RequestBody String processInstanceId) {
        if(StringUtils.isNotNull(processInstanceId)){
            taskService.stopProcess(processInstanceId);
            processService.deleteProcessByIds(new String[]{processInstanceId});
            return R.ok(processInstanceId);
        }
       return null;
    }

    /**
     * 撤回流程接口
     */
    @InnerAuth
    @PostMapping( value ="/revokeProcess")
    public  R<String> revokeProcess(@RequestBody Map<String, Object> variables) {
        if(variables!=null&&variables.get("taskId")!=null&&variables.get("procInsId")!=null){
            WfTaskBo bo=new WfTaskBo();
            bo.setTaskId((String) variables.get("taskId"));
            bo.setProcInsId((String)variables.get("procInsId"));
            try {
                taskService.revokeProcess(bo);
            }catch (RuntimeException e){
                return R.fail(e.getMessage());
            }
            return R.ok("撤回成功");
        }
        return R.fail("撤回失败");
    }

}
