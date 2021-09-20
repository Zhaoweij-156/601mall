<template>
    <el-container>
        <el-aside width="200px">
            <LeftMenu></LeftMenu>
        </el-aside>
        <el-container>
            <el-header>
                <HeaderMenu></HeaderMenu>
            </el-header>
            <el-main>
                <Tabs></Tabs>
                <div style="margin: 0 15px;">
                    <router-view></router-view>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import LeftMenu from "./innerComponent/LeftMenu";
    import HeaderMenu from "./innerComponent/HeaderMenu";
    import Tabs from "./innerComponent/Tabs";

    export default {
        name: "AdminHome",
        components: {
            LeftMenu,
            HeaderMenu,
            Tabs,
        },
    }
</script>

<style scoped>

    .el-container {
        margin: 0;
        padding: 0;
        height: 100%;
    }

    .el-header, .el-footer {
        background-color: #66CDAA;/* 17B3A3 */
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        line-height: 200px;
    }

    .el-main {
        /*height: 100%;*/
        background-color: #F5F5F5;
        color: #333;
        /*text-align: center;*/
        /*line-height: 160px;*/
        padding: 2px;
    }

</style>