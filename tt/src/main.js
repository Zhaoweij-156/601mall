import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import Qs from 'qs'

import axios from './axois'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.prototype.$qs = Qs

Vue.use(Element)

 require("./mock.js")

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
