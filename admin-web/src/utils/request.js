import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const service = axios.create({
  baseURL: '/admin',
  timeout: 10000
})

service.interceptors.request.use(config => {
  const token = sessionStorage.getItem('token')
  if (token) {
    config.headers['X-SmartRice-Admin-Token'] = token
  }
  return config
})

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.errno === 501) {
      sessionStorage.removeItem('token')
      router.push('/login')
      return Promise.reject(new Error('登录已过期'))
    }
    if (res.errno !== 0) {
      ElMessage.error(res.errmsg || '请求失败')
      return Promise.reject(new Error(res.errmsg))
    }
    return res
  },
  error => {
    ElMessage.error('网络错误，请稍后重试')
    return Promise.reject(error)
  }
)

export default service
