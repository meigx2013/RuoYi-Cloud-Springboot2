import request from '@/utils/request'

// 查询员工报销列表
export function listReimbursement(query) {
  return request({
    url: '/oa/reimbursement/list',
    method: 'get',
    params: query
  })
}

// 查询员工报销详细
export function getReimbursement(id) {
  return request({
    url: '/oa/reimbursement/' + id,
    method: 'get'
  })
}

// 新增员工报销
export function addReimbursement(data) {
  return request({
    url: '/oa/reimbursement',
    method: 'post',
    data: data
  })
}

// 修改员工报销
export function updateReimbursement(data) {
  return request({
    url: '/oa/reimbursement',
    method: 'put',
    data: data
  })
}

// 删除员工报销
export function delReimbursement(id) {
  return request({
    url: '/oa/reimbursement/' + id,
    method: 'delete'
  })
}


// 提交送审
export function startProcess(data) {
  return request({
    url: '/oa/reimbursement/' +'startProcess',
    method: 'post',
    data: data
  })
}
// 停止流程
export function stopProcess(data) {
  return request({
    url: '/oa/reimbursement/' +'stopProcess',
    method: 'post',
    data: data
  })
}

// 撤回流程
export function revokeProcess(data) {
  return request({
    url: '/oa/reimbursement/' +'revokeProcess',
    method: 'post',
    data: data
  })
}