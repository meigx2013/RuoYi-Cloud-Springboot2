package com.ruoyi.ai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 提示词管理对象 t_prompt_management
 * 
 * @author admin
 * @date 2026-02-04
 */
public class PromptManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID，自增 */
    private Long id;

    /** 提示词名称 */
    @Excel(name = "提示词名称")
    private String promptName;

    /** 提示词具体内容 */
    @Excel(name = "提示词具体内容")
    private String promptContent;

    /** 提示词分类 */
    @Excel(name = "提示词分类")
    private String promptCategory;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPromptName(String promptName) 
    {
        this.promptName = promptName;
    }

    public String getPromptName() 
    {
        return promptName;
    }

    public void setPromptContent(String promptContent) 
    {
        this.promptContent = promptContent;
    }

    public String getPromptContent() 
    {
        return promptContent;
    }

    public void setPromptCategory(String promptCategory) 
    {
        this.promptCategory = promptCategory;
    }

    public String getPromptCategory() 
    {
        return promptCategory;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("promptName", getPromptName())
            .append("promptContent", getPromptContent())
            .append("promptCategory", getPromptCategory())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
