package com.ruoyi.oa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.oa.domain.LeaveRequest;
import com.ruoyi.oa.service.ILeaveRequestService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 请假管理Controller
 * 
 * @author admin
 * @date 2026-01-23
 */
@RestController
@RequestMapping("/leave")
public class LeaveRequestController extends BaseController
{
    @Autowired
    private ILeaveRequestService leaveRequestService;

    /**
     * 查询请假管理列表
     */
    @RequiresPermissions("oa:leave:list")
    @GetMapping("/list")
    public TableDataInfo list(LeaveRequest leaveRequest)
    {
        startPage();
        List<LeaveRequest> list = leaveRequestService.selectLeaveRequestList(leaveRequest);
        return getDataTable(list);
    }

    /**
     * 导出请假管理列表
     */
    @RequiresPermissions("oa:leave:export")
    @Log(title = "请假管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LeaveRequest leaveRequest)
    {
        List<LeaveRequest> list = leaveRequestService.selectLeaveRequestList(leaveRequest);
        ExcelUtil<LeaveRequest> util = new ExcelUtil<LeaveRequest>(LeaveRequest.class);
        util.exportExcel(response, list, "请假管理数据");
    }

    /**
     * 获取请假管理详细信息
     */
    @RequiresPermissions("oa:leave:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(leaveRequestService.selectLeaveRequestById(id));
    }

    /**
     * 新增请假管理
     */
    @RequiresPermissions("oa:leave:add")
    @Log(title = "请假管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LeaveRequest leaveRequest)
    {
        leaveRequest.setEmployeeId(SecurityUtils.getUserId());
        return toAjax(leaveRequestService.insertLeaveRequest(leaveRequest));
    }

    /**
     * 修改请假管理
     */
    @RequiresPermissions("oa:leave:edit")
    @Log(title = "请假管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LeaveRequest leaveRequest)
    {
        return toAjax(leaveRequestService.updateLeaveRequest(leaveRequest));
    }

    /**
     * 删除请假管理
     */
    @RequiresPermissions("oa:leave:remove")
    @Log(title = "请假管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(leaveRequestService.deleteLeaveRequestByIds(ids));
    }

    /**
    * 送审，启动流程
    *
    */
    @PostMapping("/startProcess")
    public AjaxResult startProcess( @RequestBody LeaveRequest leaveRequest) {
        return AjaxResult.success("送审成功",leaveRequestService.startProcess(leaveRequest));
    }

    /**
     * 停止流程
     *
     */
    @PostMapping("/stopProcess")
    public AjaxResult stopProcess( @RequestBody LeaveRequest leaveRequest) {
        return AjaxResult.success("停止成功",leaveRequestService.stopProcess(leaveRequest));
    }
    /**
     * 撤回流程
     *
     */
    @PostMapping("/revokeProcess")
    public AjaxResult revokeProcess( @RequestBody LeaveRequest leaveRequest) {
        String result=leaveRequestService.revokeProcess( leaveRequest);
        return AjaxResult.success(result);
    }
}
