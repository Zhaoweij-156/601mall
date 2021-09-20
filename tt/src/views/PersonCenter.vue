<template>
  <el-container>
    <el-header style="height: 30px">
      <TopBar></TopBar>
    </el-header>
    <SecBar></SecBar>
    <el-container id="mainContain">
      <el-aside width="200px">
        <el-menu
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose">
          <router-link to="/cart">
          <el-menu-item  index="1">
            <i class="el-icon-shopping-cart-2"></i>
            <span slot="title">购物车&nbsp;&nbsp;&nbsp;</span>
          </el-menu-item>
          </router-link>

          <router-link to="/order">
          <el-menu-item  index="2">
            <i class="el-icon-s-order"></i>
            <span slot="title">我的订单</span>
          </el-menu-item>
          </router-link>

          <router-link to="/myInfo">
          <el-menu-item index="3">
            <i class="el-icon-user"></i>
            <span slot="title">个人信息</span>
          </el-menu-item>
          </router-link>

          <router-link to="/address">
          <el-menu-item index="4">
            <i class="el-icon-office-building"></i>
            <span slot="title">地址管理</span>
          </el-menu-item>
          </router-link>
        </el-menu>
      </el-aside>
      <el-main >
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import TopBar from "./inc/TopBar";
import SecBar from "@/views/inc/SecBar";

export default {
  name: "PersonCenter",
  components: {
    TopBar,
    SecBar
  },
  data(){
    return {
      logosrc:'../assets/img/Logo.png',
    }

  }
}
</script>

<style scoped>
#Sec_Bar {
  background-color: #0094ff;
  height: 80px;
  padding: 10px;
}

#mainContain{
  padding-left: 100px;
  padding-right: 200px;
}

.el-container {
  padding: 0;
  margin: 0;
  height: 100%
}

.el-main {
  height: 100%;
}

.el-header{
  padding: 0;
}


/*.el-aside {*/
/*  background-color: #D3DCE6;*/
/*  color: #333;*/
/*  text-align: center;*/
/*  line-height: 200px;*/
/*}*/

.el-main {
  height: 100%;
}

.no_underline {
  text-decoration: none;
}

* {
  text-decoration: none;
}

</style>