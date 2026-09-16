package com.ruoyi.oa.mapper;

import java.util.List;
import com.ruoyi.oa.domain.Reimbursement;

/**
 * 员工报销Mapper接口
 * 
 * @author admin
 * @date 2026-01-23
 */
public interface ReimbursementMapper 
{
    /**
     * 查询员工报销
     * 
     * @param id 员工报销主键
     * @return 员工报销
     */
    public Reimbursement selectReimbursementById(Long id);

    /**
     * 查询员工报销列表
     * 
     * @param reimbursement 员工报销
     * @return 员工报销集合
     */
    public List<Reimbursement> selectReimbursementList(Reimbursement reimbursement);

    /**
     * 新增员工报销
     * 
     * @param reimbursement 员工报销
     * @return 结果
     */
    public int insertReimbursement(Reimbursement reimbursement);

    /**
     * 修改员工报销
     * 
     * @param reimbursement 员工报销
     * @return 结果
     */
    public int updateReimbursement(Reimbursement reimbursement);

    /**
     * 删除员工报销
     * 
     * @param id 员工报销主键
     * @return 结果
     */
    public int deleteReimbursementById(Long id);

    /**
     * 批量删除员工报销
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReimbursementByIds(Long[] ids);
}
