package com.ruoyi.oa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 请假管理对象 t_leave_request
 * 
 * @author admin
 * @date 2026-01-23
 */
public class LeaveRequest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 员工编号 */
    @Excel(name = "员工编号")
    private Long employeeId;

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String leaveType;

    /** 请假开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请假开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 请假结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请假结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 请假原因说明 */
    @Excel(name = "请假原因说明")
    private String reason;

    /** 业务审批状态（待审批、已批准、已拒绝、已撤销） */
    private String status;

    /** 流程定义ID */
    @Excel(name = "流程定义ID")
    private String processDefinitionId;

    /** 流程实例状态 */
    @Excel(name = "流程实例状态")
    private String processStatus;

    private String taskId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setLeaveType(String leaveType) 
    {
        this.leaveType = leaveType;
    }

    public String getLeaveType() 
    {
        return leaveType;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setProcessDefinitionId(String processDefinitionId) 
    {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId() 
    {
        return processDefinitionId;
    }

    public void setProcessStatus(String processStatus) 
    {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() 
    {
        return processStatus;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("employeeId", getEmployeeId())
            .append("leaveType", getLeaveType())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("reason", getReason())
            .append("status", getStatus())
            .append("processDefinitionId", getProcessDefinitionId())
            .append("processStatus", getProcessStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
