<template>
    <div>
        <strong style="font-size: larger">601大卖部在线商城后台管理系统</strong>
        <div class="header-avatar">
            <el-dropdown>
                <el-link href="/admin/login" v-if="username == ''">登录</el-link>
                <span class="el-dropdown-link" v-else>
                    {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                        <router-link to="/admin/self/personalCenter">个人中心</router-link>
                    </el-dropdown-item>
                    <el-dropdown-item @click.native="adminLogout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-link href="" >返回前台</el-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HeaderMenu",
        data() {
            return {
                username: "",
            }
        },
        methods: {
            getUserName() {
                this.username = JSON.parse(sessionStorage.getItem("adminUser")).username;
            },
            adminLogout() {
                this.username = "";
                localStorage.clear();
                sessionStorage.clear();
                this.$store.commit("RESET_STATE");
                this.$store.commit("RESET_MENU_STATE");
                // 跳转到后台主页
                this.$router.push("/admin/index");
            },
        },
        created() {
            this.getUserName();
        },
    }
</script>

<style scoped>

    .header-avatar {
        float: right;
        width: 160px;
        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    .el-dropdown-link {
        cursor: pointer;
    }

    a {
        text-decoration: none;
    }

</style>