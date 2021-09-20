<template>
  <div style="margin-left: 30px;">
    <div style="text-align: left;">
      <h3 style="margin-top: 10px">修改密码</h3>
    </div>
    <div>
      <el-card style="width: 500px">
        <el-form
            :model="changePasswordForm"
            :rules="rules"
            ref="changePasswordForm"
            label-width="100px"
            class="demo-ruleForm"

        >
          <el-form-item label="旧密码" prop="oldPassword" required>
            <el-input show-password type="password" v-model="changePasswordForm.oldPassword" placeholder="请输入旧密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword" required>
            <el-input show-password type="password" v-model="changePasswordForm.newPassword" placeholder="请输入新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="newPassword_confirm" required>
            <el-input show-password type="password" v-model="changePasswordForm.newPassword_confirm" placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <el-form-item style="margin-right: 100px; text-align: left">
            <el-button type="primary" @click="submitForm('changePasswordForm')">修改</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChangePassword",
  data() {
    let checkPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入新密码'));
      } else if (value !== this.changePasswordForm.newPassword){
        callback(new Error('两次输入的密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      changePasswordForm: {
        oldPassword: '',
        newPassword: '',
        newPassword_confirm: '',
      },
      rules: {
        oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
        newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }],
        newPassword_confirm: [{ validator: checkPassword, trigger: "blur" }],
      },
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            oldPassword: this.changePasswordForm.oldPassword,
            newPassword: this.changePasswordForm.newPassword_confirm
          }
          this.$axios.post('/user/repassword', this.$qs.stringify(data),{
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }).then(res =>{
            this.resetForm(formName)
            this.$message.info(res.data.msg)
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
  }
}
</script>

<style scoped>

</style>