<template xmlns:el-col="http://www.w3.org/1999/html">
  <div style="margin-left: 30px;">
    <div style="text-align: left;">
      <h3 style="margin-top: 10px">个人信息</h3>
    </div>
    <div id="personalInfo">
      <el-row>
        <el-col :span="12">
          <div style="text-align: left" >
            <div style="margin-bottom: 15px"><span style="color: orangered;"></span></div>
            <el-form label-position="left" :model="myInfoForm" :rules="rules" ref="myInfoForm" label-width="100px"
                     class="demo-ruleForm" style="width: 50vh; " :disabled="disableChangMyInfoFlag">
              <el-form-item label="用户名">
                <el-link disabled>{{myInfoForm.username}}</el-link>
              </el-form-item>
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="myInfoForm.nickname" placeholder="请输入昵称" maxlength="24" minlength="1"></el-input>
              </el-form-item>
              <el-form-item style="text-align: left; margin-bottom: 0" prop="gender" label="性别" required>
                <el-radio v-model="myInfoForm.gender" label="男">男</el-radio>
                <el-radio v-model="myInfoForm.gender" label="女">女</el-radio>
              </el-form-item>
              <el-form-item label="绑定邮箱">
                <el-link disabled>{{myInfoForm.email}}</el-link>
              </el-form-item>
              <el-form-item label="手机号码" prop="mobile">
                <el-input v-model="myInfoForm.mobile" placeholder="请输入手机号码"></el-input>
              </el-form-item>
              <el-form-item label="生日">
                <el-date-picker
                    v-model="myInfoForm.birth"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="选择您的生日">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="个性签名" prop="sign">
                <el-input type="textarea" :autosize="{minRows:2}" v-model="myInfoForm.sign"
                          placeholder="这个人很懒，什么都没有写"></el-input>
              </el-form-item>
              <el-form-item label="现居城市" prop="city">
                <el-input v-model="myInfoForm.city" placeholder="请输入现居城市"></el-input>
              </el-form-item>
              <el-form-item label="职业" prop="job">
                <el-input v-model="myInfoForm.job" placeholder="请输入您的职业"></el-input>
              </el-form-item>
              <el-form-item style="text-align: left; margin-bottom: 0">
                <el-button type="primary" size="mini" @click="submitForm('myInfoForm')" v-if="!disableChangMyInfoFlag">保存</el-button>
                <el-button size="mini" @click="changMyInfo()" v-if="!disableChangMyInfoFlag">取消</el-button>
              </el-form-item>
            </el-form>
            <el-button @click="changMyInfo()" v-if="disableChangMyInfoFlag">修改个人信息</el-button>
          </div>
        </el-col>
        <el-col :span="12">
          <div style=" text-align: left">
            <el-image
                style="width: 240px; height: 240px"
                :src="myInfoForm.avatar"
                :fit='fit'>
            </el-image>
          </div>
          <div style=" text-align: left; margin-top: 30px; width: 240px">
            <div>
            <el-upload
                class="upload-demo"
                ref="upload"
                :limit="1"
                action="http://jysx.xyz:8082/user/personal/avatar"
                :headers="headers"
                :multiple="false"
                :auto-upload="true"
                :list-type="uploadListType"
                :on-success="uploadSuccess"
            >
              <el-button slot="trigger" size="big" type="primary" plain>上传头像</el-button>
            </el-upload>
            </div>
            <div style="height: 50px"></div>
            <div>
              <el-button type="danger" plain @click="toChangeMyPassword()">修改密码</el-button>
            </div>
          </div>

        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
export default {
  name: "MyInfo",
  data() {
    let checkNickName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'));
      }
      else {
        let l = 0;
        for (let i = 0; i < value.length; ++i)
        {
          if (/[\u4e00-\u9fa5]/.test(value[i]))
          {
            l+=3;
          }else{
            ++l;
          }
        }
        if (l>24){
          callback(new Error('昵称长度不合规范，请减少昵称长度。全中文昵称最长8个汉字'))
        }
        else{
          callback();
        }
      }
    };
    return {
      uploadListType: 'picture',
      headers: {
        token:''
      },
      disableChangMyInfoFlag: true,
      myInfoForm: {
        username: '',
        avatar: null,
        email: '',
        nickname: '',
        sign: '',
        gender: '',
        birth: '',
        mobile: '',
        job: '',
        city: '',
      },
      rules: {
        nickname: [{ validator: checkNickName, trigger: "blur" }],
        phone: [{required: true, message: "手机号码不能为空", trigger: "blur"}],
      },
    }
  },
  methods: {
    uploadSuccess(response, file, fileList){
      this.getPersonInfo()
    },
    isUploadEmpty(){
      if (this.$refs.upload.uploadFiles.length > 0) return true;
      return false;
    },
    getPersonInfo(){
      //TODO:发送获取个人信息的请求
      this.$axios.get('/user/personal/getInfo').then(res => {
        this.myInfoForm = res.data.data;
        sessionStorage.setItem('userNickName', this.myInfoForm.nickname);//持久化到sessionStorage
        sessionStorage.setItem('userAvatar', this.myInfoForm.avatar);//持久化到sessionStorage
        console.log(res)
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('将保存个人信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.log(this.myInfoForm.birth);
            //TODO:发送保存个人信息的请求
            this.$axios.post('/user/personal/save', this.myInfoForm).then(res => {
              console.log(res)
              this.getPersonInfo();
              this.changMyInfo();
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消操作'
            });
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    toChangeMyPassword(){
      this.$router.push('/changePassword')
    },
    changMyInfo(){
      this.disableChangMyInfoFlag = !this.disableChangMyInfoFlag;
      this.getPersonInfo();
    },
    getToken(){
      //FIXME 烂代码
      this.headers.token = localStorage.getItem('token');
    },
    uploadAvatar(){
      this.$refs.upload.submit();
    },
    tttt(){
      console.log(this.$refs.upload)
    }

  },
  created() {
    this.getPersonInfo();
    this.getToken();
  },

}
</script>

<style scoped>


</style>