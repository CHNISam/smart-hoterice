import request from '../utils/request'

export function listServicePointGoods(servicePointId) {
  return request({
    url: '/service-point-goods/list',
    method: 'get',
    params: servicePointId ? { servicePointId } : undefined
  })
}

export function saveServicePointGoods(data) {
  return request({ url: '/service-point-goods/save', method: 'post', data })
}

export function deleteServicePointGoods(id) {
  return request({ url: '/service-point-goods/delete', method: 'post', data: { id } })
}
