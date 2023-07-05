import {createRouter,createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'main',
        component: () => import('../App'),
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/RegisterPage'),
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/LoginPage'),
    },
    {
        path: '/chatRoom',
        name: 'chatRoom',
        component: () => import('../views/ChatRoom'),
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
// 导航守卫
// 参数1 : to 目标路由对象
// 参数2 : from 来源路由对象
// 参数3 : next() 下一步
router.beforeEach((to, from, next) => {
    // 1. 判断是不是登录页面
    // 是登录页面
    if(to.path === '/login'||to.path == '/register') {
        next()
    } else {
        // 不是登录页面
        // 2. 判断 是否登录过
        let token = sessionStorage.getItem('Authorization')
        token ? next() : next('/login')
    }
})
export default router
