package com.ruoyi.oa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
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
import com.ruoyi.oa.domain.Reimbursement;
import com.ruoyi.oa.service.IReimbursementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

import static com.ruoyi.common.security.utils.SecurityUtils.getLoginUser;
import static com.ruoyi.common.security.utils.SecurityUtils.getUserId;

/**
 * 员工报销Controller
 * 
 * @author admin
 * @date 2026-01-23
 */
@RestController
@RequestMapping("/reimbursement")
public class ReimbursementController extends BaseController
{
    @Autowired
    private IReimbursementService reimbursementService;

    /**
     * 查询员工报销列表
     */
    @RequiresPermissions("oa:reimbursement:list")
    @GetMapping("/list")
    public TableDataInfo list(Reimbursement reimbursement)
    {
        startPage();
        List<Reimbursement> list = reimbursementService.selectReimbursementList(reimbursement);
        return getDataTable(list);
    }

    /**
     * 导出员工报销列表
     */
    @RequiresPermissions("oa:reimbursement:export")
    @Log(title = "员工报销", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Reimbursement reimbursement)
    {
        List<Reimbursement> list = reimbursementService.selectReimbursementList(reimbursement);
        ExcelUtil<Reimbursement> util = new ExcelUtil<Reimbursement>(Reimbursement.class);
        util.exportExcel(response, list, "员工报销数据");
    }

    /**
     * 获取员工报销详细信息
     */
    @RequiresPermissions("oa:reimbursement:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(reimbursementService.selectReimbursementById(id));
    }

    /**
     * 新增员工报销
     */
    @RequiresPermissions("oa:reimbursement:add")
    @Log(title = "员工报销", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Reimbursement reimbursement)
    {
        reimbursement.setEmployeeId(SecurityUtils.getUserId());
        reimbursement.setCreateBy(SecurityUtils.getUsername());
        return toAjax(reimbursementService.insertReimbursement(reimbursement));
    }

    /**
     * 修改员工报销
     */
    @RequiresPermissions("oa:reimbursement:edit")
    @Log(title = "员工报销", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Reimbursement reimbursement)
    {
        return toAjax(reimbursementService.updateReimbursement(reimbursement));
    }

    /**
     * 删除员工报销
     */
    @RequiresPermissions("oa:reimbursement:remove")
    @Log(title = "员工报销", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(reimbursementService.deleteReimbursementByIds(ids));
    }

    /**
    * 送审，启动流程
    *
    */
    @PostMapping("/startProcess")
    public AjaxResult startProcess( @RequestBody Reimbursement reimbursement) {
        return AjaxResult.success("送审成功",reimbursementService.startProcess(reimbursement));
    }

    /**
     * 停止流程
     *
     */
    @PostMapping("/stopProcess")
    public AjaxResult stopProcess( @RequestBody Reimbursement reimbursement) {
        return AjaxResult.success("停止成功",reimbursementService.stopProcess(reimbursement));
    }
    /**
     * 撤回流程
     *
     */
    @PostMapping("/revokeProcess")
    public AjaxResult revokeProcess( @RequestBody Reimbursement reimbursement) {
        String result=reimbursementService.revokeProcess( reimbursement);
        return AjaxResult.success(result);
    }
}
