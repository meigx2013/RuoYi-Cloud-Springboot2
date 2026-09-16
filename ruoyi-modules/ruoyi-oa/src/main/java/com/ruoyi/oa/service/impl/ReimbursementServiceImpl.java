package com.ruoyi.oa.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.oa.mapper.ReimbursementMapper;
import com.ruoyi.oa.domain.Reimbursement;
import com.ruoyi.oa.service.IReimbursementService;
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
 * 员工报销Service业务层处理
 * 
 * @author admin
 * @date 2026-01-23
 */
@Service
public class ReimbursementServiceImpl implements IReimbursementService 
{
    @Autowired
    private ReimbursementMapper reimbursementMapper;
    @Autowired
    private RemoteFlowableService remoteFlowableService;
    /** 流程的KEY */
    private String process_key="Process_1769170860344";

    /**
     * 查询员工报销
     * 
     * @param id 员工报销主键
     * @return 员工报销
     */
    @Override
    public Reimbursement selectReimbursementById(Long id)
    {
        return reimbursementMapper.selectReimbursementById(id);
    }

    /**
     * 查询员工报销列表
     * 
     * @param reimbursement 员工报销
     * @return 员工报销
     */
    @Override
    public List<Reimbursement> selectReimbursementList(Reimbursement reimbursement)
    {
        try {
            if (StringUtils.isNotNull(reimbursement.getParams())&&reimbursement.getParams().get("processTab")!=null&&!"all".equals(reimbursement.getParams().get("processTab"))){
                FlowTaskVo flowTaskVo=new FlowTaskVo();
                flowTaskVo.setType((String) reimbursement.getParams().get("processTab"));
                flowTaskVo.setProcessKey(process_key);
                PageDomain pageDomain = TableSupport.buildPageRequest();
                Integer pageNum = pageDomain.getPageNum();
                Integer pageSize = pageDomain.getPageSize();
                flowTaskVo.setPageNum(pageNum);
                flowTaskVo.setPageSize(pageSize);
                Map<String, Object> resultMap = BeanUtil.beanToMap(reimbursement);
                flowTaskVo.setQueryMap(resultMap);
                R<TableDataInfo> result= remoteFlowableService.commonProcessList(flowTaskVo, SecurityConstants.INNER);
                List<Reimbursement> reimbursementList = new ArrayList<>();
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
                        Reimbursement reimbursementItem = new  Reimbursement();
                        BeanUtil.fillBeanWithMap(processVariables, reimbursementItem, false);
                        // 根据实际情况继续设置其他字段
                        reimbursementItem.setProcessState((String) jsonMap.get("processStatus"));
                        reimbursementItem.setProcessDefinitionId((String) jsonMap.get("procInsId"));
                        reimbursementItem.setTaskId((String) jsonMap.get("taskId"));
                        reimbursementList.add(reimbursementItem);
                    }
                    return reimbursementList;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return reimbursementMapper.selectReimbursementList(reimbursement);
    }

    /**
     * 新增员工报销
     * 
     * @param reimbursement 员工报销
     * @return 结果
     */
    @Override
    public int insertReimbursement(Reimbursement reimbursement)
    {
        reimbursement.setCreateTime(DateUtils.getNowDate());      // 创建时间
        return reimbursementMapper.insertReimbursement(reimbursement);
    }

    /**
     * 修改员工报销
     * 
     * @param reimbursement 员工报销
     * @return 结果
     */
    @Override
    public int updateReimbursement(Reimbursement reimbursement)
    {
        reimbursement.setUpdateTime(DateUtils.getNowDate());
        return reimbursementMapper.updateReimbursement(reimbursement);
    }

    /**
     * 批量删除员工报销
     * 
     * @param ids 需要删除的员工报销主键
     * @return 结果
     */
    @Override
    public int deleteReimbursementByIds(Long[] ids)
    {

        //对应流程先删除
        for (Long id:ids){
            Reimbursement reimbursement=reimbursementMapper.selectReimbursementById(id);
            if(reimbursement!=null&&StringUtils.isNotNull(reimbursement.getProcessDefinitionId())){
                remoteFlowableService.delProcess(reimbursement.getProcessDefinitionId(), SecurityConstants.INNER);
            }
        }
        return reimbursementMapper.deleteReimbursementByIds(ids);
    }

    /**
     * 删除员工报销信息
     * 
     * @param id 员工报销主键
     * @return 结果
     */
    @Override
    public int deleteReimbursementById(Long id)
    {
        return reimbursementMapper.deleteReimbursementById(id);
    }



    /**
    * 启动流程
    *
    */
    @Override
    public int startProcess(Reimbursement reimbursement)
    {
        try {
            Map<String, Object> resultMap = BeanUtil.beanToMap(reimbursement);
            resultMap.put("processKey",process_key);
            resultMap.put("processState","running");
            R<String> processInstanceId= remoteFlowableService.startProcessByKey(resultMap, SecurityConstants.INNER);
            if(processInstanceId!=null&&processInstanceId.getData()!=null){
                reimbursement.setProcessDefinitionId(processInstanceId.getData());
                reimbursement.setProcessState("running");

                /**
                 * 送审成功，更新[提交日期]为当前时间
                 * meigx 20260124
                 *
                 */
                reimbursement.setSubmissionDate(DateUtils.getNowDate());

                return reimbursementMapper.updateReimbursement(reimbursement);
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
    public int stopProcess(Reimbursement reimbursement)
    {
        try {
            R<String> processInstanceId= remoteFlowableService.stopProcess(reimbursement.getProcessDefinitionId(), SecurityConstants.INNER);
            if(processInstanceId!=null&&processInstanceId.getData()!=null){
                reimbursement.setProcessDefinitionId(processInstanceId.getData());
                reimbursement.setProcessState("canceled");
                return reimbursementMapper.updateReimbursement(reimbursement);
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
    public String revokeProcess(Reimbursement reimbursement)
    {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("procInsId",reimbursement.getProcessDefinitionId());
        resultMap.put("taskId",reimbursement.getTaskId());
        R<String> revokeProcess=remoteFlowableService.revokeProcess(resultMap, SecurityConstants.INNER);
        if (revokeProcess.getCode()==500){
            return revokeProcess.getMsg();
        }
        return "撤回成功";
    }
}
