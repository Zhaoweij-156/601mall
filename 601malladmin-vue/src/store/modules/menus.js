import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
    state: {
        menuList: [],
        permList: [],

        hasRoute: false,

        editableTabsValue: 'AdminIndex',
        editableTabs: [
            {
                title: '首页',
                name: 'AdminIndex',
            },
        ],
    },
    mutations: {

        SET_MENULIST(state, menus) {
            state.menuList = menus;
        },
        SET_PERMLIST(state, perms) {
            state.permsList = perms;
        },
        CHANGE_ROUTE_STATUS(state, hasRoute) {
            state.hasRoute = hasRoute;
        },

        ADD_TAB(state, tab) {
            let inEditableTabs = state.editableTabs.findIndex(t => t.name === tab.name);
            if(inEditableTabs === -1) {
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name,
                });
            }
            state.editableTabsValue = tab.name;
        },

        RESET_MENU_STATE(state) {
            state.menuList = [];
            state.permList = [];
            state.hasRoute = false;
            state.editableTabsValue = 'AdminIndex';
            state.editableTabs = [
                {
                    title: '首页',
                    name: 'AdminIndex',
                },
            ];
        },

    },
    actions: {
    },
    modules: {
    }
}
