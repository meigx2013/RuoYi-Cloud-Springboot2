import request from '@/utils/request'

// 查询请假管理列表
export function listLeave(query) {
  return request({
    url: '/oa/leave/list',
    method: 'get',
    params: query
  })
}

// 查询请假管理详细
export function getLeave(id) {
  return request({
    url: '/oa/leave/' + id,
    method: 'get'
  })
}

// 新增请假管理
export function addLeave(data) {
  return request({
    url: '/oa/leave',
    method: 'post',
    data: data
  })
}

// 修改请假管理
export function updateLeave(data) {
  return request({
    url: '/oa/leave',
    method: 'put',
    data: data
  })
}

// 删除请假管理
export function delLeave(id) {
  return request({
    url: '/oa/leave/' + id,
    method: 'delete'
  })
}


// 提交送审
export function startProcess(data) {
  return request({
    url: '/oa/leave/' +'startProcess',
    method: 'post',
    data: data
  })
}
// 停止流程
export function stopProcess(data) {
  return request({
    url: '/oa/leave/' +'stopProcess',
    method: 'post',
    data: data
  })
}

// 撤回流程
export function revokeProcess(data) {
  return request({
    url: '/oa/leave/' +'revokeProcess',
    method: 'post',
    data: data
  })
}