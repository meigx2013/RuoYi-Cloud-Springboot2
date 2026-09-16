package com.ruoyi.oa.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.oa.mapper.LeaveRequestMapper;
import com.ruoyi.oa.domain.LeaveRequest;
import com.ruoyi.oa.service.ILeaveRequestService;
import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteFlowableService;
import com.ruoyi.system.api.vo.FlowTaskVo;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.core.web.page.TableSupport;
import com.ruoyi.common.core.utils.StringUtils;
import java.util.ArrayList;
/**
 * 请假管理Service业务层处理
 * 
 * @author admin
 * @date 2026-01-23
 */
@Service
public class LeaveRequestServiceImpl implements ILeaveRequestService 
{
    @Autowired
    private LeaveRequestMapper leaveRequestMapper;
    @Autowired
    private RemoteFlowableService remoteFlowableService;
    private String process_key="Process_1769096788108";

    /**
     * 查询请假管理
     * 
     * @param id 请假管理主键
     * @return 请假管理
     */
    @Override
    public LeaveRequest selectLeaveRequestById(Long id)
    {
        return leaveRequestMapper.selectLeaveRequestById(id);
    }

    /**
     * 查询请假管理列表
     * 
     * @param leaveRequest 请假管理
     * @return 请假管理
     */
    @Override
    public List<LeaveRequest> selectLeaveRequestList(LeaveRequest leaveRequest)
    {
        try {
            if (StringUtils.isNotNull(leaveRequest.getParams())&&leaveRequest.getParams().get("processTab")!=null&&!"all".equals(leaveRequest.getParams().get("processTab"))){
                FlowTaskVo flowTaskVo=new FlowTaskVo();
                flowTaskVo.setType((String) leaveRequest.getParams().get("processTab"));
                flowTaskVo.setProcessKey(process_key);
                PageDomain pageDomain = TableSupport.buildPageRequest();
                Integer pageNum = pageDomain.getPageNum();
                Integer pageSize = pageDomain.getPageSize();
                flowTaskVo.setPageNum(pageNum);
                flowTaskVo.setPageSize(pageSize);
                Map<String, Object> resultMap = BeanUtil.beanToMap(leaveRequest);
                flowTaskVo.setQueryMap(resultMap);
                R<TableDataInfo> result= remoteFlowableService.commonProcessList(flowTaskVo, SecurityConstants.INNER);
                List<LeaveRequest> leaveRequestList = new ArrayList<>();
                if (result != null && result.getData() != null) {
                    List<Object> list = (List<Object>) result.getData().getRows();
                    for (Object obj : list) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        // 将对象转换为 JSON 字符串
                        String jsonString = null;
                        jsonString = objectMapper.writeValueAsString(obj);
                        // 将 JSON 字符串解析为 Map
                        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
                        // 获取 processVariables
                        Map<String, Object> processVariables = (Map<String, Object>) jsonMap.get("processVariables");
                        // 创建 TestProcess 对象并设置值
                        LeaveRequest leaveRequestItem = new  LeaveRequest();
                        BeanUtil.fillBeanWithMap(processVariables, leaveRequestItem, false);
                        // 根据实际情况继续设置其他字段
                        leaveRequestItem.setProcessStatus((String) jsonMap.get("processStatus"));
                        leaveRequestItem.setProcessDefinitionId((String) jsonMap.get("procInsId"));
                        leaveRequestItem.setTaskId((String) jsonMap.get("taskId"));
                            leaveRequestList.add(leaveRequestItem);
                    }
                    return leaveRequestList;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return leaveRequestMapper.selectLeaveRequestList(leaveRequest);
    }

    /**
     * 新增请假管理
     * 
     * @param leaveRequest 请假管理
     * @return 结果
     */
    @Override
    public int insertLeaveRequest(LeaveRequest leaveRequest)
    {
        leaveRequest.setCreateTime(DateUtils.getNowDate());
        return leaveRequestMapper.insertLeaveRequest(leaveRequest);
    }

    /**
     * 修改请假管理
     * 
     * @param leaveRequest 请假管理
     * @return 结果
     */
    @Override
    public int updateLeaveRequest(LeaveRequest leaveRequest)
    {
        leaveRequest.setUpdateTime(DateUtils.getNowDate());
        return leaveRequestMapper.updateLeaveRequest(leaveRequest);
    }

    /**
     * 批量删除请假管理
     * 
     * @param ids 需要删除的请假管理主键
     * @return 结果
     */
    @Override
    public int deleteLeaveRequestByIds(Long[] ids)
    {

        //对应流程先删除
        for (Long id:ids){
            LeaveRequest leaveRequest=leaveRequestMapper.selectLeaveRequestById(id);
            if(leaveRequest!=null&&StringUtils.isNotNull(leaveRequest.getProcessDefinitionId())){
                remoteFlowableService.delProcess(leaveRequest.getProcessDefinitionId(), SecurityConstants.INNER);
            }
        }
        return leaveRequestMapper.deleteLeaveRequestByIds(ids);
    }

    /**
     * 删除请假管理信息
     * 
     * @param id 请假管理主键
     * @return 结果
     */
    @Override
    public int deleteLeaveRequestById(Long id)
    {
        return leaveRequestMapper.deleteLeaveRequestById(id);
    }



    /**
    * 启动流程
    *
    */
    @Override
    public int startProcess(LeaveRequest leaveRequest)
    {
        try {
            Map<String, Object> resultMap = BeanUtil.beanToMap(leaveRequest);
            resultMap.put("processKey",process_key);
            resultMap.put("processState","running");
            R<String> processInstanceId= remoteFlowableService.startProcessByKey(resultMap, SecurityConstants.INNER);
            if(processInstanceId!=null&&processInstanceId.getData()!=null){
                leaveRequest.setProcessDefinitionId(processInstanceId.getData());
                leaveRequest.setProcessStatus("running");
                return leaveRequestMapper.updateLeaveRequest(leaveRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * 停止流程
     *
     */
    @Override
    public int stopProcess(LeaveRequest leaveRequest)
    {
        try {
            R<String> processInstanceId= remoteFlowableService.stopProcess(leaveRequest.getProcessDefinitionId(), SecurityConstants.INNER);
            if(processInstanceId!=null&&processInstanceId.getData()!=null){
                leaveRequest.setProcessDefinitionId(processInstanceId.getData());
                leaveRequest.setProcessStatus("canceled");
                return leaveRequestMapper.updateLeaveRequest(leaveRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * 撤回流程
     *
     */
    @Override
    public String revokeProcess(LeaveRequest leaveRequest)
    {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("procInsId",leaveRequest.getProcessDefinitionId());
        resultMap.put("taskId",leaveRequest.getTaskId());
        R<String> revokeProcess=remoteFlowableService.revokeProcess(resultMap, SecurityConstants.INNER);
        if (revokeProcess.getCode()==500){
            return revokeProcess.getMsg();
        }
        return "撤回成功";
    }
}
