<template>
  <div class="container">
    <div class="login-box">
      <h2>欢迎注册</h2>
      <el-form
          :model="registerForm"
          :rules="rules"
          ref="registerForm"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input show-password type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm_password" required>
          <el-input show-password type="password" v-model="registerForm.confirm_password" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" required>
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
<!--        <el-form-item style="text-align: left">-->
<!--          <img width="90" height="37" :src="captchaImg" alt="验证码" @click="getCaptcha()" style="cursor: pointer;"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="验证码" prop="captchaCode" >-->
<!--          <el-input v-model="registerForm.captchaCode" placeholder="请输入验证码"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item style="text-align: left">
          <el-button @click="openCaptchaDialog()" :disabled="this.emailCaptchaButtonDisable">发送邮箱验证码</el-button>
          <span style="font-size: 12px; padding-left: 10px"
                v-if="this.emailCaptchaButtonDisable">
            还有{{this.emailCaptchaButtonDisableTimeCount}}s可重新发送
          </span>

        </el-form-item>
        <el-form-item label="邮箱验证码" prop="emailCaptchaCode">
          <el-input v-model="registerForm.emailCaptchaCode" placeholder="请输入邮箱验证码"></el-input>
        </el-form-item>


        <el-form-item style="margin-right: 100px">
          <el-button type="primary" @click="submitForm('registerForm')">注册</el-button>
          <el-button @click="toHome()">回首页</el-button>
          <router-link to="/login" class="no_underline">
              <el-link type="primary" :underline="false" >已有账号？去登陆</el-link>
          </router-link>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-dialog title="图形验证码" :visible.sync="dialogFormVisible" center width="250px">
       <div>
        <el-input style="width: 200px" v-model="registerForm.captchaCode"></el-input>
        <el-image  :src="captchaImg" alt="验证码" @click="getCaptcha()" style="cursor: pointer;width:90px; height:37px"></el-image>
       </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="sendEmailCaptcha()">确 定</el-button>
        </div>

      </el-dialog>
    </div>
  </div>

</template>

<script>
import Qs from 'qs';
export default {
name: "Register",
  data() {
    let checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value !== this.registerForm.password){
        callback(new Error('两次输入的密码不一致!'));
      } else {
        callback();
      }
    };
    let checkEmail = (rule, value,callback) =>{//验证邮箱
      if (value == ''){
        callback(new Error('请输入邮箱'));
      }
      else {
        const regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if(!regEmail.test(this.registerForm.email)) {
          callback(new Error('请输入有效的邮箱'));
        }
        //正确的时候要callback
        else {
          callback()
        }
      }

    }
    return {
      keepSingleRequest: true,
      dialogFormVisible:false,
      emailCaptchaButtonDisable: false,
      emailCaptchaButtonDisableTime: 60,
      emailCaptchaButtonDisableTimeCount: 0,
      emailCaptchaButtonDisableTimer: null,
      registerForm: {
        username: '',
        password: '',
        confirm_password: '',
        email: '',
        captchaCode: '',
        emailCaptchaCode: '',
        captchaId: '',
      },
      captchaImg: '',
      rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        confirm_password: [{ validator: checkPassword, trigger: "blur" }],
        email: [{validator: checkEmail, trigger: "blur"}],
        emailCaptchaCode: [{ required: true, message: "请输入邮箱验证码", trigger: "blur"}],
        // captchaCode: [{ required: true, message: "请输入图形验证码", trigger: "blur"}]
      },
    };
  },
  methods: {
    openCaptchaDialog(){
      this.dialogFormVisible = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            "username": this.registerForm.username,
            "password": this.registerForm.confirm_password,
            "email": this.registerForm.email,
            "emailCode": this.registerForm.emailCaptchaCode,
          }
          console.log(data)
          this.$axios.post('/user/registered', Qs.stringify(data), {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res =>{
            this.$message.success("注册成功，前往登陆")
            this.$router.push('/login')
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
      this.$axios.get('/captcha/get').then(res => {
        console.log(res)
        this.registerForm.captchaId = res.data.data.captchaId;
        this.captchaImg = res.data.data.captchaBody;
      })
    },
    sendEmailCaptcha(){
      if (!this.keepSingleRequest) return ;
      this.keepSingleRequest = false;
      if (this.registerForm.email === ''){
        this.$message.error('请输入邮箱');
      }
      else if (this.registerForm.captchaCode ==='') {
        this.$message.error('请输入图形验证码');
      }
      else{
        let data= {
          "captchaId": this.registerForm.captchaId,
          "captchaCode": this.registerForm.captchaCode,
          "email": this.registerForm.email
        }
        console.log(data);
        this.$axios.post('/email/send', Qs.stringify(data),{
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          this.$message.info("发送成功!");
          this.emailSendButtonCountDown();
          this.dialogFormVisible = false;
          this.keepSingleRequest = true;
        }).catch(error => {
          this.keepSingleRequest = true;
        })
      }
    },
    toHome(){
      this.$router.push('/index');
    },
    emailSendButtonCountDown(){
      this.emailCaptchaButtonDisable = true;
      this.emailCaptchaButtonDisableTimeCount = this.emailCaptchaButtonDisableTime;
      this.emailCaptchaButtonDisableTimer = setInterval( () => {
        if (this.emailCaptchaButtonDisableTimeCount > 0 && this.emailCaptchaButtonDisableTimeCount <= this.emailCaptchaButtonDisableTime){
          --this.emailCaptchaButtonDisableTimeCount;
        }else {
          this.emailCaptchaButtonDisable = false;
          clearInterval(this.emailCaptchaButtonDisableTimer);
          this.emailCaptchaButtonDisableTimer = null;
        }
      }, 1000);
    }
  },

  created(){
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
</style>