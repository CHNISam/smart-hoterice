import request from '../utils/request'

export function listServicePointGoods() {
  return request({ url: '/service-point-goods/list', method: 'get' })
}

export function saveServicePointGoods(data) {
  return request({ url: '/service-point-goods/save', method: 'post', data })
}

export function deleteServicePointGoods(id) {
  return request({ url: '/service-point-goods/delete', method: 'post', data: { id } })
}
