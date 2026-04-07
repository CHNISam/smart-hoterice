import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: () => import('../views/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/dashboard/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'goods',
        name: 'Goods',
        component: () => import('../views/goods/index.vue'),
        meta: { title: '商品管理' }
      },
      {
        path: 'service-points',
        name: 'ServicePoints',
        component: () => import('../views/service-point/index.vue'),
        meta: { title: '服务点管理' }
      },
      {
        path: 'delivery-regions',
        name: 'DeliveryRegions',
        component: () => import('../views/delivery-region/index.vue'),
        meta: { title: '配送片区管理' }
      },
      {
        path: 'service-point-goods',
        name: 'ServicePointGoods',
        component: () => import('../views/service-point-goods/index.vue'),
        meta: { title: '服务点商品' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
