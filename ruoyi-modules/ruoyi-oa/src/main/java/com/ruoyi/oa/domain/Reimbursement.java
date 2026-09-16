package com.ruoyi.oa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 员工报销对象 t_reimbursement
 * 
 * @author admin
 * @date 2026-01-23
 */
public class Reimbursement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long employeeId;

    /** 报销金额 */
    @Excel(name = "报销金额")
    private BigDecimal reimbursementAmount;

    /** 报销类型（如：差旅费、办公费、招待费等） */
    @Excel(name = "报销类型", readConverterExp = "如=：差旅费、办公费、招待费等")
    private String reimbursementType;

    /** 费用说明 */
    @Excel(name = "费用说明")
    private String description;

    /** 发票张数 */
    @Excel(name = "发票张数")
    private Long invoiceCount;

    /** 提交日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submissionDate;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String processDefinitionId;

    /** 流程状态 */
    @Excel(name = "流程状态")
    private String processState;

    /** 业务状态 */
    private String status;

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

    public void setReimbursementAmount(BigDecimal reimbursementAmount) 
    {
        this.reimbursementAmount = reimbursementAmount;
    }

    public BigDecimal getReimbursementAmount() 
    {
        return reimbursementAmount;
    }

    public void setReimbursementType(String reimbursementType) 
    {
        this.reimbursementType = reimbursementType;
    }

    public String getReimbursementType() 
    {
        return reimbursementType;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setInvoiceCount(Long invoiceCount) 
    {
        this.invoiceCount = invoiceCount;
    }

    public Long getInvoiceCount() 
    {
        return invoiceCount;
    }

    public void setSubmissionDate(Date submissionDate) 
    {
        this.submissionDate = submissionDate;
    }

    public Date getSubmissionDate() 
    {
        return submissionDate;
    }

    public void setProcessDefinitionId(String processDefinitionId) 
    {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId() 
    {
        return processDefinitionId;
    }

    public void setProcessState(String processState) 
    {
        this.processState = processState;
    }

    public String getProcessState() 
    {
        return processState;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("reimbursementAmount", getReimbursementAmount())
            .append("reimbursementType", getReimbursementType())
            .append("description", getDescription())
            .append("invoiceCount", getInvoiceCount())
            .append("submissionDate", getSubmissionDate())
            .append("processDefinitionId", getProcessDefinitionId())
            .append("processState", getProcessState())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
