import request from '../utils/request'

export function listDeliveryRegions() {
  return request({ url: '/delivery-region/list', method: 'get' })
}

export function createDeliveryRegion(data) {
  return request({ url: '/delivery-region/create', method: 'post', data })
}

export function updateDeliveryRegion(data) {
  return request({ url: '/delivery-region/update', method: 'post', data })
}

export function deleteDeliveryRegion(id) {
  return request({ url: '/delivery-region/delete', method: 'post', data: { id } })
}
