<template>
  <div class="container">
    <div class="login-box">
      <h2>邮箱验证</h2>
      <el-form :model="emailCheckForm" :rules="rules" ref="emailCheckForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="邮箱" prop="email" required>
          <el-input v-model="emailCheckForm.email" placeholder="请输入账户绑定的邮箱"></el-input>
        </el-form-item>
        <el-form-item label="邮箱验证码" prop="emailCaptchaCode">
          <el-input v-model="emailCheckForm.emailCaptchaCode" placeholder="请输入邮箱验证码"></el-input>
        </el-form-item>
        <el-form-item style="text-align: left">
          <el-button @click="sendEmailCaptchaForResetPassword()">发送邮箱验证码</el-button>
        </el-form-item>
        <el-form-item>
          <div style="margin-right: 90px">
            <el-button type="primary" @click="submitForm('emailCheckForm')">下一步</el-button>
            <el-button @click="toLogin()">返回</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmailCheck",
  data() {
    let checkEmail = (rule, value,callback) => {//验证邮箱
      if (value == '') {
        callback(new Error('请输入邮箱'));
      } else {
        const regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (!regEmail.test(this.emailCheckForm.email)) {
          callback(new Error('请输入有效的邮箱'));
        }
      }
    }
    return {
      emailCheckForm: {
        email: '',
        emailCaptchaCode: '',
      },
      rules: {
        email: [{validator: checkEmail, trigger: "blur"}],
        emailCaptchaCode: [{required: true, message: "请输入验证码", trigger: "blur"}],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login', this.emailCheckForm).then(res => {
            // const jwt = res.headers['authorization']
            // this.$store.commit('SET_TOKEN', jwt)
            // this.$router.push('/index')
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

    getCaptcha() {
      this.$axios.get('/captcha').then(res => {
        console.log(res)
        this.emailCheckForm.token = res.data.data.token
        this.captchaImg = res.data.data.captchaImg
      })
    },
    toLogin() {
      this.$router.push('/login');
    },
    toRegister() {
      this.$router.push('/register');
    },
    toResetPassword(){
      this.$router.push('/resetPassword');
    },
    sendEmailCaptchaForResetPassword(){
      //TODO:发送让服务器发送邮箱验证码的请求
    },
    checkCaptcha(){
      //TODO:发送验证码验证是否正确
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