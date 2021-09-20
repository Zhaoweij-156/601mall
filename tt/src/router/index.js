import Vue from 'vue'
import VueRouter from 'vue-router'

import test from "@/views/test";

import Index from "@/views/Index";
import Home from '../views/Home.vue'
import PersonCenter from "@/views/PersonCenter";
import Order from "@/views/Order";
import Cart from "@/views/Cart";
import Address from "@/views/Address";
import MyInfo from "@/views/MyInfo";
import Register from "@/views/Register";
import SearchList from "@/views/SearchList";
import goodDetail from "@/views/goodDetail";
import OrderConfirm from "@/views/OrderConfirm";
import OrderDetail from "@/views/OrderDetail";
import Pay from "@/views/Pay";
import ReturnGood from "@/views/inc/ReturnGood";
import EmailCheck from "@/views/EmailCheck";
import ResetPassword from "@/views/ResetPassword";
import ChangePassword from "@/views/ChangePassword";

Vue.use(VueRouter)


// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push(location) {
//     return originalPush.call(this, location).catch(err => err)
// }



const routes = [
    {
        path: '/test',
        name: 'test',
        component: test
    },
    {
      path: '/resetPassword',
      name: 'ResetPassword',
      component: ResetPassword
    },
    {
      path: '/emailCheck',
      name: 'EmailCheck',
      component: EmailCheck
    },
    {
        path: '/returnGood',
        name: 'ReturnGood',
        component: ReturnGood
    },
    {
        path: '/pay',
        name: 'Pay',
        component: Pay
    },
    {
        path: '/orderDetail',
        name: 'OrderDetail',
        component: OrderDetail
    },
    {
        path: '/orderConfirm',
        name: 'OrderConfirm',
        component: OrderConfirm
    },
    {
        path: '/search',
        name: 'SearchList',
        component: SearchList
    },
    {
        path: '/goodDetail',
        name: 'goodDetail',
        component: goodDetail,
        // props: {skuID:'as'}
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/index',
        name: 'Index',
        component: Index
    },
    {
        path: '/',
        name: 'index',
        component: Index
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import( '../views/Login.vue')
    },
    // {
    //   path: '/register',
    //   name: 'Register',
    //   component: () => import( '../views/Login.vue')
    // },
    {
        path: '/personCenter',
        name: 'PersonCenter',
        component: PersonCenter,
        children: [
            {
                path: '/personCenter',
                name: 'Order',
                component: Order
            },
            {
                path: '/address',
                name: 'Address',
                component: Address
            },
            {
                path: '/cart',
                name: 'Cart',
                component: Cart
            },
            {
                path: '/order',
                name: 'Order',
                component: Order
            },
            {
                path: '/myInfo',
                name: 'MyInfo',
                component: MyInfo
            },
            {
                path: '/changePassword',
                name: 'ChangePassword',
                component: ChangePassword
            },
        ]
    },



]

const router = new VueRouter({
    // WARNING：我自己打包的时候去掉这个就能用
    // mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
