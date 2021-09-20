<template>
  <div class="container">
    <div class="login-box">
      <h2>欢迎登陆</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input show-password type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="captchaCode" >
          <el-input v-model="loginForm.captchaCode" placeholder="请输入验证码"></el-input>
        </el-form-item>
        <el-form-item style="text-align: left">
          <img width="90" height="37" :src="captchaImg" alt="验证码" @click="getCaptcha()" style="cursor: pointer;"/>
        </el-form-item>
        <el-form-item>
          <div style="margin-right: 100px">
          <el-button type="primary" @click="submitForm('loginForm')">登陆</el-button>
          <el-button @click="toHome()">回首页</el-button>
          </div>
          <div>
            <div class="inline_block_top" style="width: 50%" >
          <el-link type="primary" :underline="false" @click="toRegister()">没有账号？去注册</el-link>
            </div>
            <div class="inline_block_top" style="width: 40%" >
              <el-link type="info" :underline="false" @click="toResetPassword()">忘记密码</el-link>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>

<script>
import Qs from 'qs'
export default {
  name: "Login",
  data() {
    return {
      myInfoForm: {

      },
      loginForm: {
        username: '',
        password: '',
        captchaCode: "",
        captchaId: ''
      },
      rules: {
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
        captchaCode: [{required: true, message: "请输入验证码", trigger: "blur"}],
      },
      captchaImg: ''
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            username: this.loginForm.username,
            password: this.loginForm.password,
            captchaCode: this.loginForm.captchaCode,
            captchaId: this.loginForm.captchaId
          }
          this.$axios.post('/user/login', Qs.stringify(data),{
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res => {
            console.log(res)
            const jwt = res.data.data;
            // const jwt = res.headers['authorization']
            this.$store.commit('SET_TOKEN', jwt)
            // sessionStorage.setItem('userName', this.loginForm.username);//用户名持久化到sessionStorage
            //TODO:发送获取个人信息的请求
            this.$axios.get('/user/personal/getInfo').then(res => {
              this.myInfoForm = res.data.data;
              sessionStorage.setItem('userNickName', this.myInfoForm.nickname);//持久化到sessionStorage
              sessionStorage.setItem('userAvatar', this.myInfoForm.avatar);//持久化到sessionStorage
            })
            this.$router.push('/index')
          })
          // alert('submit!');
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getPersonInfo(){
      //TODO:发送获取个人信息的请求
      this.$axios.get('/user/personal/getInfo').then(res => {
        this.myInfoForm = res.data.data;
        console.log(res)
      })
    },
    getCaptcha() {

      this.$axios.get('/captcha/get').then(res => {
        console.log(res)
        this.loginForm.captchaId = res.data.data.captchaId
        this.captchaImg = res.data.data.captchaBody;
      })
    },
    toHome() {
      this.$router.push('/index');
    },
    toRegister() {
      this.$router.push('/register');
    },
    toResetPassword(){
      this.$router.push('/resetPassword');
    }
  },
  created() {
    this.getCaptcha()
  }
}
</script>

<style scoped>
.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 450px;
  padding: 40px;
  padding-bottom: 0px;
  background: white;
  /*border-radius: 8px;*/
  box-sizing: border-box;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .5);
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  text-align: center;
  color: #0094ff;
}

.no_underline {
  text-decoration: none;
}

.inline_block_top {
  display: inline-block;
  vertical-align: top;
}
</style>