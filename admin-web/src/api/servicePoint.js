import request from '../utils/request'

export function listServicePoints() {
  return request({ url: '/service-point/list', method: 'get' })
}

export function detailServicePoint(id) {
  return request({ url: '/service-point/detail', method: 'get', params: { id } })
}

export function createServicePoint(data) {
  return request({ url: '/service-point/create', method: 'post', data })
}

export function updateServicePoint(data) {
  return request({ url: '/service-point/update', method: 'post', data })
}

export function deleteServicePoint(id) {
  return request({ url: '/service-point/delete', method: 'post', data: { id } })
}
