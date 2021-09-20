<template>
  <div class="container">
    <div class="login-box">
      <h2>重置密码</h2>
      <el-form
          :model="resetPasswordForm"
          :rules="rules"
          ref="resetPasswordForm"
          label-width="100px"
          class="demo-ruleForm"
      >
        <el-form-item label="用户名/邮箱" prop="email">
          <el-input v-model="resetPasswordForm.email" placeholder="请输入用户名或邮箱"></el-input>
        </el-form-item>
<!--        <el-form-item style="text-align: left">-->
<!--          <img width="90" height="37" :src="captchaImg" alt="验证码" @click="getCaptcha()" style="cursor: pointer;"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="验证码" prop="captchaCode" >-->
<!--          <el-input v-model="resetPasswordForm.captchaCode" placeholder="请输入验证码"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item style="text-align: left">
          <el-button @click="openCaptchaDialog" :disabled="this.emailCaptchaButtonDisable">发送邮箱验证码</el-button>
          <span style="font-size: 12px; padding-left: 10px"
                v-if="this.emailCaptchaButtonDisable">
            还有{{this.emailCaptchaButtonDisableTimeCount}}s可重新发送
          </span>
        </el-form-item>
        <el-form-item label="邮箱验证码" prop="emailCaptchaCode">
          <el-input v-model="resetPasswordForm.emailCaptchaCode" placeholder="请输入邮箱验证码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input show-password type="password" v-model="resetPasswordForm.password" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirm_password" required>
          <el-input show-password type="password" v-model="resetPasswordForm.confirm_password" placeholder="请再次输入新密码"></el-input>
        </el-form-item>
        <el-form-item style="margin-right: 100px">
          <el-button type="primary" @click="submitForm('resetPasswordForm')">修改</el-button>
          <el-button @click="toHome()">回首页</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-dialog title="图形验证码" :visible.sync="dialogFormVisible" center width="250px">
        <div>
          <el-input style="width: 200px" v-model="resetPasswordForm.captchaCode"></el-input>
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
import Qs from "qs";

export default {
  name: "ResetPassword",
  data() {
    let checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value !== this.resetPasswordForm.password){
        callback(new Error('两次输入的密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      keepSingleRequest: true,
      dialogFormVisible: false,
      emailCaptchaButtonDisable: false,
      emailCaptchaButtonDisableTime: 60,
      emailCaptchaButtonDisableTimeCount: 0,
      emailCaptchaButtonDisableTimer: null,
      resetPasswordForm: {
        email: '',
        captchaCode: '',
        emailCaptchaCode: '',
        password: '',
        confirm_password: '',
        captchaId: ''
      },
      rules: {
        email: [{required: true, message:"请输入用户或邮箱", trigger: "blur"}],
        captchaCode: [{required: true, message: "请输入验证码", trigger: "blur"}],
        emailCaptchaCode: [{required: true, message: "请输入邮箱验证码", trigger: "blur"}],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        confirm_password: [{ validator: checkPassword, trigger: "blur" }],
      },
      captchaImg: null,
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
            email: this.resetPasswordForm.email,
            emailCode: this.resetPasswordForm.emailCaptchaCode,
            password: this.resetPasswordForm.confirm_password
          }
          console.log(data)
          this.$axios.post('/user/resetpwd', this.$qs.stringify(data), {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res =>{
            this.$message.info(res.data.msg);
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

    sendEmailCaptcha(){
      if (!this.keepSingleRequest) return ;
      this.keepSingleRequest = false;
      if (this.resetPasswordForm.email === ''){
        this.$message.error('请输入用户名或邮箱');

      }
      else if (this.resetPasswordForm.captchaCode ==='') {
        this.$message.error('请输入图形验证码');
      }
      else{
        let data= {
          "captchaId": this.resetPasswordForm.captchaId,
          "captchaCode": this.resetPasswordForm.captchaCode,
          "email": this.resetPasswordForm.email
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
    getCaptcha() {
      this.$axios.get('/captcha/get').then(res => {
        console.log(res)
        this.resetPasswordForm.captchaId = res.data.data.captchaId;
        this.captchaImg = res.data.data.captchaBody
      })
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