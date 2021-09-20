import Vue from 'vue'
import Vuex from 'vuex'

// 动态导航与动态路由绑定
import menus from "./modules/menus";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
  },
  mutations: {

    SET_TOKEN: (state, token) => {
      state.token = token;
      localStorage.setItem("token", token);
    },
    RESET_STATE: (state) => {
      state.token = '';
    },

  },
  actions: {
  },
  modules: {
    // 动态导航与动态路由绑定
    menus,
  }
})
