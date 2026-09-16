package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.PromptManagement;

/**
 * 提示词管理Service接口
 * 
 * @author admin
 * @date 2026-02-04
 */
public interface IPromptManagementService 
{
    /**
     * 查询提示词管理
     * 
     * @param id 提示词管理主键
     * @return 提示词管理
     */
    public PromptManagement selectPromptManagementById(Long id);

    /**
     * 查询提示词管理列表
     * 
     * @param promptManagement 提示词管理
     * @return 提示词管理集合
     */
    public List<PromptManagement> selectPromptManagementList(PromptManagement promptManagement);

    /**
     * 新增提示词管理
     * 
     * @param promptManagement 提示词管理
     * @return 结果
     */
    public int insertPromptManagement(PromptManagement promptManagement);

    /**
     * 修改提示词管理
     * 
     * @param promptManagement 提示词管理
     * @return 结果
     */
    public int updatePromptManagement(PromptManagement promptManagement);

    /**
     * 批量删除提示词管理
     * 
     * @param ids 需要删除的提示词管理主键集合
     * @return 结果
     */
    public int deletePromptManagementByIds(Long[] ids);

    /**
     * 删除提示词管理信息
     * 
     * @param id 提示词管理主键
     * @return 结果
     */
    public int deletePromptManagementById(Long id);
}
