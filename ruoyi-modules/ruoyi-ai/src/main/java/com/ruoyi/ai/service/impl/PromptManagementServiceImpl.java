package com.ruoyi.ai.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.PromptManagementMapper;
import com.ruoyi.ai.domain.PromptManagement;
import com.ruoyi.ai.service.IPromptManagementService;

/**
 * 提示词管理Service业务层处理
 * 
 * @author admin
 * @date 2026-02-04
 */
@Service
public class PromptManagementServiceImpl implements IPromptManagementService 
{
    @Autowired
    private PromptManagementMapper promptManagementMapper;

    /**
     * 查询提示词管理
     * 
     * @param id 提示词管理主键
     * @return 提示词管理
     */
    @Override
    public PromptManagement selectPromptManagementById(Long id)
    {
        return promptManagementMapper.selectPromptManagementById(id);
    }

    /**
     * 查询提示词管理列表
     * 
     * @param promptManagement 提示词管理
     * @return 提示词管理
     */
    @Override
    public List<PromptManagement> selectPromptManagementList(PromptManagement promptManagement)
    {
        return promptManagementMapper.selectPromptManagementList(promptManagement);
    }

    /**
     * 新增提示词管理
     * 
     * @param promptManagement 提示词管理
     * @return 结果
     */
    @Override
    public int insertPromptManagement(PromptManagement promptManagement)
    {
        promptManagement.setCreateTime(DateUtils.getNowDate());
        return promptManagementMapper.insertPromptManagement(promptManagement);
    }

    /**
     * 修改提示词管理
     * 
     * @param promptManagement 提示词管理
     * @return 结果
     */
    @Override
    public int updatePromptManagement(PromptManagement promptManagement)
    {
        promptManagement.setUpdateTime(DateUtils.getNowDate());
        return promptManagementMapper.updatePromptManagement(promptManagement);
    }

    /**
     * 批量删除提示词管理
     * 
     * @param ids 需要删除的提示词管理主键
     * @return 结果
     */
    @Override
    public int deletePromptManagementByIds(Long[] ids)
    {
        return promptManagementMapper.deletePromptManagementByIds(ids);
    }

    /**
     * 删除提示词管理信息
     * 
     * @param id 提示词管理主键
     * @return 结果
     */
    @Override
    public int deletePromptManagementById(Long id)
    {
        return promptManagementMapper.deletePromptManagementById(id);
    }
}
