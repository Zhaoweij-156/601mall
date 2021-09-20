<template>
  <div id="TopBar" style="white-space: nowrap">
    <div style="padding-left: 15%; padding-right: 15%">
      <div class="inline_block_top" style="width: 300px; height:25px;">
        <div class="inline_block_top" style="width: 50px;">
            <span class="f_sm f_bold" @click="goToIndex()" style="cursor: pointer">首页</span>
        </div>
        <div class="inline_block_top" style="width: 250px;text-align: left">
          <div v-if="tokenExistFlag">
            <span class="f_sm">欢迎光临~ </span>
            <span class="f_sm">{{this.userNickName}}</span>
            &nbsp;
            <el-link  :underline="false" style="font-size: 12px; padding-bottom: 1px" @click="goToLogin()">退出</el-link>
          </div>
          <div v-if="!tokenExistFlag">
            <span class="f_sm">尚未登陆 </span>
            &nbsp;
            <el-link  :underline="false" style="font-size: 12px; padding-bottom: 1px" @click="goToLogin()">前往登陆</el-link>
          </div>
        </div>
      </div>
      <div class="inline_block_top" style="width: 1000px; height:25px; text-align: right">
        <div class="inline_block_top" style="width: 60px">
                  <router-link to="/cart">
                  <el-link :underline='false'>
                    <span class="f_sm">购物车</span>
                  </el-link>
                  </router-link>
        </div>
        <div class="inline_block_top" style="width: 60px">
                  <router-link to="/personCenter">
                    <el-link :underline='false'>
                      <span class="f_sm">个人中心</span>
                    </el-link>
                  </router-link>
        </div>
        <div class="inline_block_top" style="width: 60px">
                  <router-link to="/">
                  <el-link :underline='false'>
                    <span class="f_sm f_lighter">后台登陆</span>
                  </el-link>
                  </router-link>
        </div>
      </div>
    </div>
    <!--    <el-row >-->
    <!--      <el-col :span="1">-->
    <!--        <router-link to="/index">-->
    <!--          <span class="f_sm f_bold">首页</span>-->
    <!--        </router-link>-->
    <!--      </el-col>-->
    <!--      <el-col :span="4" style="text-align: left">-->
    <!--        <span class="f_sm">欢迎光临</span>-->
    <!--      </el-col>-->
    <!--      <el-col :span="8" :offset="11" style="text-align: right">-->
    <!--        <router-link to="/">-->
    <!--        <el-link :underline='false'>-->
    <!--          <span class="f_sm">购物车</span>-->
    <!--        </el-link>-->
    <!--        </router-link>-->
    <!--        <span>&nbsp;&nbsp;</span>-->
    <!--        <router-link to="/personcenter">-->
    <!--          <el-link :underline='false'>-->
    <!--            <span class="f_sm">个人中心</span>-->
    <!--          </el-link>-->
    <!--        </router-link>-->
    <!--        <span>&nbsp;&nbsp;</span>-->
    <!--        <router-link to="/">-->
    <!--        <el-link :underline='false'>-->
    <!--          <span class="f_sm f_lighter">后台登陆</span>-->
    <!--        </el-link>-->
    <!--        </router-link>-->
    <!--      </el-col>-->
    <!--    </el-row>-->
  </div>
</template>

<script>
export default {
  name: "TopBar",
  data() {
    return {
      userNickName: '',
      myToken:'',
      tokenExistFlag: false,
    }
  },
  methods: {
    goToIndex(){
      this.$router.push('/index');
    },
    getUserNameNickFromSessionStorage(){
      this.userNickName = sessionStorage.getItem('userNickName');
    },
    getToken(){
      //localStorage键值对不存在的话返回的是null
      this.myToken = localStorage.getItem('token');
      console.log(this.myToken)
      if (null !== this.myToken) this.tokenExistFlag = true;
    },
    goToLogin(){
      if (this.tokenExistFlag)
      {
        localStorage.removeItem('token');
        this.tokenExistFlag = false;
      }
      this.$router.push('/login')
    }
  },
  created() {
    this.getUserNameNickFromSessionStorage();
    this.getToken();
  }
}
</script>

<style scoped>
#TopBar {
  height: 25px;
  background-color: #F5F5F5;
  padding-top: 5px;
}

.f_sm {
  font-size: 12px;
}

.f_bold {
  font-weight: bold;
}

.f_lighter {
  font-weight: lighter;
}

a {
  text-decoration: none;
}

.inline_block_top {
  display: inline-block;
  vertical-align: top;
}


</style>