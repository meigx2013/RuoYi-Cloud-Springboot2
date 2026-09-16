package com.ruoyi.oa.service;

import java.util.List;
import com.ruoyi.oa.domain.LeaveRequest;

/**
 * 请假管理Service接口
 * 
 * @author admin
 * @date 2026-01-23
 */
public interface ILeaveRequestService 
{
    /**
     * 查询请假管理
     * 
     * @param id 请假管理主键
     * @return 请假管理
     */
    public LeaveRequest selectLeaveRequestById(Long id);

    /**
     * 查询请假管理列表
     * 
     * @param leaveRequest 请假管理
     * @return 请假管理集合
     */
    public List<LeaveRequest> selectLeaveRequestList(LeaveRequest leaveRequest);

    /**
     * 新增请假管理
     * 
     * @param leaveRequest 请假管理
     * @return 结果
     */
    public int insertLeaveRequest(LeaveRequest leaveRequest);

    /**
     * 修改请假管理
     * 
     * @param leaveRequest 请假管理
     * @return 结果
     */
    public int updateLeaveRequest(LeaveRequest leaveRequest);

    /**
     * 批量删除请假管理
     * 
     * @param ids 需要删除的请假管理主键集合
     * @return 结果
     */
    public int deleteLeaveRequestByIds(Long[] ids);

    /**
     * 删除请假管理信息
     * 
     * @param id 请假管理主键
     * @return 结果
     */
    public int deleteLeaveRequestById(Long id);

    /**
     * 送审
     */
    public int startProcess(LeaveRequest leaveRequest);

    /**
     * 停止流程
     * @param leaveRequest
     * @return
     */
    public int stopProcess(LeaveRequest leaveRequest);

    /**
     * 撤回流程
     * @param leaveRequest
     * @return
     */
    public String revokeProcess(LeaveRequest leaveRequest);
}
