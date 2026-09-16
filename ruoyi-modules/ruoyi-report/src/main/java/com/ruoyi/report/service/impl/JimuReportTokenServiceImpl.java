package com.ruoyi.report.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义报表鉴权(如果不进行自定义，则所有请求不做权限控制)
 * @author: meigx
 *
 */
@Component
public class JimuReportTokenServiceImpl implements JmReportTokenServiceI {

    @Autowired
    private TokenService tokenService;

    /**
     * 通过请求获取Token
     */
    @Override
    public String getToken(HttpServletRequest request) {
        //优先从请求头获取，其次从查询参数获取
        String token = request.getHeader("X-Access-Token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }

        // 验证token
        LoginUser loginUser = tokenService.getLoginUser(token);
        if (loginUser != null) {
            return token;
        }
        return "";
    }

    /**
     * 获取登录人用户名
     */
    @Override
    public String getUsername(String token) {
        LoginUser loginUser = tokenService.getLoginUser(token);
        return loginUser != null ? loginUser.getUsername() : "";
    }

    /**
     * Token校验
     */
    @Override
    public Boolean verifyToken(String s) {
        if (s != null && s.length() > 0) {
            LoginUser loginUser = tokenService.getLoginUser(s);
            return loginUser !=null;
        }
        return false;
    }

    /**
     *  自定义请求头
     */
    @Override
    public HttpHeaders customApiHeader() {
        HttpHeaders header = new HttpHeaders();
        header.add("X-Access-Token", SecurityUtils.getToken());
        return header;
    }

    /**
     * 获取多租户id
     * @return tenantId
     */
    public String getTenantId() {
        String token = SecurityUtils.getCurrentRequestInfo().getParameter("token");
        String header = SecurityUtils.getCurrentRequestInfo().getHeader("X-Access-Token");
        /*String token = SecurityUtils.getToken();
        String header = customApiHeader().toString();*/

        LoginUser loginUser = null;
        if(StringUtils.isNotBlank(token)){
            loginUser =  tokenService.getLoginUser(token);
        }else if(StringUtils.isNotBlank(header)){
            loginUser =  tokenService.getLoginUser(header);
        }else {
            //都不具备则不能访问
            return "NO";
        }
        //具备admin或者管理员权限才可访问所有报表
        if(SecurityUtils.isAdmin(loginUser.getUserid())
                || loginUser.getRoles().contains("it")
                || loginUser.getRoles().contains("manger")){
            return "";
        }
        return loginUser.getUsername();
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 将所有信息存放至map 解析sql会根据map的键值解析,可自定义其他值
        Map<String, Object> map = new HashMap<>(20);
        LoginUser loginUser = tokenService.getLoginUser(token);
        map.put("sysUserCode",loginUser.getUsername());
        //设置当前日期（年月日）
        map.put("sysData", DateUtils.getDate());
        //设置昨天日期（年月日）
        map.put("sysYesterDay", DateUtils.getYesterday());
        //设置当前登录用户昵称
        map.put("sysUserName",loginUser.getSysUser().getNickName());
        //设置当前登录用户部门ID
        map.put("deptId",loginUser.getSysUser().getDeptId());
        //设置当前登录用户部门描述
        //map.put("describe",loginUser.getSysUser().getDept().getDescribes());
        return map;
    }
}
