package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.api.factory.RemoteFlowableFallbackFactory;
import com.ruoyi.system.api.vo.FlowTaskVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 流程服务
 * 
 * @author bdn
 */
@FeignClient(contextId = "remoteFlowableService", value = ServiceNameConstants.FLOWABLE_SERVICE, fallbackFactory = RemoteFlowableFallbackFactory.class)
//@FeignClient(contextId = "remoteFlowableService", value = ServiceNameConstants.FLOWABLE_SERVICE)
public interface RemoteFlowableService
{

    /**
     * 我拥有的流程
     *
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/process/commonProcessList")
    public R<TableDataInfo> commonProcessList(@RequestBody FlowTaskVo flowTaskVo , @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/process/startProcessByKey")
    public R<String> startProcessByKey(@RequestBody Map<String, Object> resultMap , @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/process/stopProcess")
    public R<String> stopProcess(@RequestBody String processDefinitionId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/process/delProcess")
    public R<String> delProcess(@RequestBody String processDefinitionId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @PostMapping("/process/revokeProcess")
    public R<String>  revokeProcess(@RequestBody Map<String, Object> resultMap,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
