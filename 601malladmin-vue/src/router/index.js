import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminHome from "../views/AdminHome";
import AdminLogin from "../views/AdminLogin";
import AdminIndex from "../views/AdminIndex";
// import CategoryManage from "../views/systemComponent/CategoryManage";
// import SpuManage from "../views/systemComponent/SpuManage";
// import SkuManage from "../views/systemComponent/SkuManage";
// import SeckillManage from "../views/systemComponent/SeckillManage";
// import OrderDeliver from "../views/systemComponent/OrderDeliver";
// import OrderReturn from "../views/systemComponent/OrderReturn";
// import UserManage from "../views/systemComponent/UserManage";
// import PersonalCenter from "../views/systemComponent/PersonalCenter";

import axios from "axios";
import store from "../store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    name: 'AdminHome',
    component: AdminHome,
    children: [
      {
        path: '/admin/index',
        name: 'AdminIndex',
        meta: {
          icon: 'el-icon-s-home',
          title: '首页',
        },
        component: AdminIndex,
      },
      {
        path: '/admin/sys/categoryManage',
        name: 'CategoryManage',
        meta: {
          icon: 'el-icon-menu',
          title: '商品分类',
        },
        component: () => import('@/views/systemComponent/CategoryManage.vue'),
      },
      {
        path: '/admin/sys/spuManage',
        name: 'SpuManage',
        meta: {
          icon: 'el-icon-goods',
          title: 'SPU商品管理',
        },
        component: () => import('@/views/systemComponent/SpuManage.vue'),
      },
      {
        path: '/admin/sys/skuManage',
        name: 'SkuManage',
        meta: {
          icon: 'el-icon-s-goods',
          title: 'SKU商品管理',
        },
        component: () => import('@/views/systemComponent/SkuManage.vue'),
      },
      {
        path: '/admin/sys/seckillManage',
        name: 'SeckillManage',
        meta: {
          icon: 'el-icon-star-on',
          title: '秒杀商品管理',
        },
        component: () => import('@/views/systemComponent/SeckillManage.vue'),
      },
      {
        path: '/admin/sys/orderDeliver',
        name: 'OrderDeliver',
        meta: {
          icon: 'el-icon-s-check',
          title: '订单发货',
        },
        component: () => import('@/views/systemComponent/OrderDeliver.vue'),
      },
      {
        path: '/admin/sys/orderReturn',
        name: 'OrderReturn',
        meta: {
          icon: 'el-icon-s-release',
          title: '订单退货',
        },
        component: () => import('@/views/systemComponent/OrderReturn.vue'),
      },
      {
        path: '/admin/sys/userManage',
        name: 'UserManage',
        meta: {
          icon: 'el-icon-s-custom',
          title: '用户管理',
        },
        component: () => import('@/views/systemComponent/UserManage.vue'),
      },
      {
        path: '/admin/self/personalCenter',
        name: 'PersonalCenter',
        meta: {
          icon: '',
          title: '个人中心',
        },
        component: () => import('@/views/systemComponent/PersonalCenter.vue'),
      },
    ],
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: AdminLogin
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// let head = document.getElementsByTagName('head');
// let meta = document.createElement('meta');
// meta.name = 'referrer';
// //根据实际情况修改referrer的值，可选值参考上文
// meta.content = 'no-referrer';
// head[0].appendChild(meta);

// // 动态导航与动态路由绑定
// router.beforeEach((to, from, next) => {
//
//   let hasRoute = store.state.menus.hasRoute;
//   if(!hasRoute) {
//     axios.get("/sys/menu/nav", {
//       headers: {
//         Authorization: localStorage.getItem("token"),
//       }
//     }).then(response => {
//       let result = response.data;
//       // console.log(result);
//       // 获取到menuList
//       store.commit("SET_MENULIST", result.data.nav);
//       // 获取到用户权限
//       store.commit("SET_PERMLIST", result.data.authoritys);
//       // console.log(store.state.menus.menuList);
//
//       // 动态绑定路由
//       let newRoutes = router.options.routes;
//       result.data.nav.forEach(menuItem => {
//         if(menuItem.children.length > 0) {
//           menuItem.children.forEach(childItem1 => {
//             // 转化路由
//             let route1 = menuToRoute(childItem1);
//             // 把路由添加到路由管理中
//             if(route1) {
//               newRoutes[0].children.push(route1);
//             }
//             // 把childItem1的子导航项转化路由并添加到路由管理中
//             if(childItem1.children.length > 0) {
//               childItem1.children.forEach(childItem2 => {
//                 let route2 = menuToRoute(childItem2);
//                 if(route2) {
//                   newRoutes[0].children.push(route2);
//                 }
//               })
//             }
//
//           })
//         }
//       })
//       console.log("newRoutes: ");
//       console.log(newRoutes);
//       router.addRoutes(newRoutes);
//
//       hasRoute = true;
//       store.commit("CHANGE_ROUTE_STATUS", hasRoute);
//     })
//   }
//
//   next()
// })
//
// // 导航转化成路由
// const menuToRoute = (item) => {
//   if(item.component == "") {
//     return null;
//   }
//   let route = {
//     path: item.path,
//     name: item.name,
//     meta: {
//       icon: item.icon,
//       title: item.title,
//     },
//   }
//   route.component = () => import('@/views' + item.component + '.vue');
//   return route;
// }

export default router
