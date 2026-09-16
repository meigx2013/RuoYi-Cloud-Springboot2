package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.RemoteFlowableService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.api.vo.FlowTaskVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteFlowableFallbackFactory implements FallbackFactory<RemoteFlowableService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteFlowableFallbackFactory.class);

    @Override
    public RemoteFlowableService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteFlowableService()
        {

            @Override
            public R<TableDataInfo> commonProcessList(FlowTaskVo flowTaskVo, String source) {
                return R.fail("查询失败:" + throwable.getMessage());
            }

            @Override
            public R<String> startProcessByKey( Map<String, Object> resultMap, String source) {
                return R.fail("启动失败:" + throwable.getMessage());
            }

            @Override
            public R<String> stopProcess(String processDefinitionId, String source) {
                return R.fail("操作失败:" + throwable.getMessage());
            }

            @Override
            public R<String> delProcess(String processDefinitionId, String source) {
                return R.fail("操作失败:" + throwable.getMessage());
            }

            @Override
            public R<String> revokeProcess(Map<String, Object> resultMap, String source) {
                return R.fail("操作失败:" + throwable.getMessage());
            }
        };
    }
}
