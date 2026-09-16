import request from '@/utils/request'

// 查询提示词管理列表
export function listPromptManage(query) {
  return request({
    url: '/ai/promptManage/list',
    method: 'get',
    params: query
  })
}

// 查询提示词管理详细
export function getPromptManage(id) {
  return request({
    url: '/ai/promptManage/' + id,
    method: 'get'
  })
}

// 新增提示词管理
export function addPromptManage(data) {
  return request({
    url: '/ai/promptManage',
    method: 'post',
    data: data
  })
}

// 修改提示词管理
export function updatePromptManage(data) {
  return request({
    url: '/ai/promptManage',
    method: 'put',
    data: data
  })
}

// 删除提示词管理
export function delPromptManage(id) {
  return request({
    url: '/ai/promptManage/' + id,
    method: 'delete'
  })
}
