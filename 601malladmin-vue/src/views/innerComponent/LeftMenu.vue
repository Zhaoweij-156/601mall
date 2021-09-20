<template>
    <el-menu
            :default-active="this.$store.state.menus.editableTabsValue"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
        <router-link :to="menuIndex.path">
            <el-menu-item :index="menuIndex.name" @click="selectMenuItem(menuIndex)">
                <template slot="title">
                    <i :class="menuIndex.icon"></i>
                    <span slot="title">{{ menuIndex.title }}</span>
                </template>
            </el-menu-item>
        </router-link>

        <el-submenu :index="menuItem.name" v-for="(menuItem, index) in menuList" :key="index">
            <template slot="title">
                <i :class="menuItem.icon"></i>
                <span>{{ menuItem.title }}</span>
            </template>
            <div v-for="(childItem1, index) in menuItem.children" :key="index">
                <router-link :to="childItem1.path" v-if="childItem1.path != ''">
                    <el-menu-item :index="childItem1.name" @click="selectMenuItem(childItem1)">
                        <template slot="title">
                            <i :class="childItem1.icon"></i>
                            <span>{{ childItem1.title }}</span>
                        </template>
                    </el-menu-item>
                </router-link>
                <el-submenu :index="childItem1.name" v-if="childItem1.children.length > 0">
                    <template slot="title">
                        <i :class="childItem1.icon"></i>
                        <span>{{ childItem1.title }}</span>
                    </template>
                    <router-link :to="childItem2.path" v-for="(childItem2, index) in childItem1.children" :key="index">
                        <el-menu-item :index="childItem2.name" @click="selectMenuItem(childItem2)">
                            <template slot="title">
                                <i :class="childItem2.icon"></i>
                                <span>{{ childItem2.title }}</span>
                            </template>
                        </el-menu-item>
                    </router-link>
                </el-submenu>
            </div>
        </el-submenu>
    </el-menu>
</template>

<script>
    export default {
        name: "LeftMenu",
        data() {
            return {
                menuIndex: {
                    name: "AdminIndex",
                    title: "首页",
                    icon: "el-icon-s-home",
                    path: "/admin/index",
                    children: [],
                },
                menuList: [
                    {
                        name: "SysManage",
                        title: "系统管理",
                        icon: "el-icon-s-operation",
                        path: "",
                        children: [
                            {
                                name: "CategoryManage",
                                title: "商品分类",
                                icon: "el-icon-menu",
                                path: "/admin/sys/categoryManage",
                                children: [],
                            },
                            {
                                name: "GoodsManage",
                                title: "商品管理",
                                icon: "el-icon-s-shop",
                                path: "",
                                children: [
                                    {
                                        name: "SpuManage",
                                        title: "SPU商品管理",
                                        icon: "el-icon-goods",
                                        path: "/admin/sys/spuManage",
                                        children: [],
                                    },
                                    {
                                        name: "SkuManage",
                                        title: "SKU商品管理",
                                        icon: "el-icon-s-goods",
                                        path: "/admin/sys/skuManage",
                                        children: [],
                                    },
                                ],
                            },
                            {
                                name: "SeckillManage",
                                title: "秒杀商品管理",
                                icon: "el-icon-star-on",
                                path: "/admin/sys/seckillManage",
                                children: [],
                            },
                            {
                                name: "OrderManage",
                                title: "订单管理",
                                icon: "el-icon-s-order",
                                path: "",
                                children: [
                                    {
                                        name: "OrderDeliver",
                                        title: "订单发货",
                                        icon: "el-icon-s-check",
                                        path: "/admin/sys/orderDeliver",
                                        children: [],
                                    },
                                    {
                                        name: "OrderReturn",
                                        title: "订单退货",
                                        icon: "el-icon-s-release",
                                        path: "/admin/sys/orderReturn",
                                        children: [],
                                    },
                                ],
                            },
                            {
                                name: "UserManage",
                                title: "用户管理",
                                icon: "el-icon-s-custom",
                                path: "/admin/sys/userManage",
                                children: [],
                            },
                        ],
                    },
                    {
                        name: "SysTools",
                        title: "系统工具",
                        icon: "el-icon-s-tools",
                        path: "",
                        children: [],
                    },
                ],
            }
        },
        // // 动态导航与动态路由绑定
        // computed: {
        //     menuList: {
        //         get() {
        //             return this.$store.state.menus.menuList;
        //         }
        //     },
        // },
        methods: {
            selectMenuItem(item) {
                this.$store.commit("ADD_TAB", item);
            }
        },
    }
</script>

<style scoped>

    .el-menu-vertical-demo {
        height: 100%;
    }

    a {
        text-decoration: none;
    }

</style>