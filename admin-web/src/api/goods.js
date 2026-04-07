import request from '../utils/request'

export function listGoods() {
  return request({ url: '/goods/list', method: 'get' })
}

export function createGoods(data) {
  return request({ url: '/goods/create', method: 'post', data })
}

export function updateGoods(data) {
  return request({ url: '/goods/update', method: 'post', data })
}

export function deleteGoods(id) {
  return request({ url: '/goods/delete', method: 'post', data: { id } })
}
