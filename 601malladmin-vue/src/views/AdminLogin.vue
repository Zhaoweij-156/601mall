<template>
    <el-row type="flex" class="row-bg" justify="center">
        <el-col :xl="6" :lg="7">
            <h2>欢迎来到601大卖部在线商城后台管理系统</h2>
            <el-image :src="require('@/assets/admin_login.jpeg')" style="width: 180px; height: 160px;"></el-image>
            <p>大扎好，我系渣渣辉</p>
            <p>是兄弟，就来砍我！我在贪玩蓝月等你。</p>
        </el-col>
        <el-col :span="1">
            <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :xl="6" :lg="7">
            <el-form :model="adminLoginForm" :rules="rules" ref="adminLoginForm" label-width="100px" class="demo-adminLoginForm">
                <el-form-item label="用户名" prop="username" style="width: 380px;">
                    <el-input v-model="adminLoginForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" style="width: 380px;">
                    <el-input type="password" v-model="adminLoginForm.password"></el-input>
                </el-form-item>
<!--                <el-form-item label="验证码" prop="code" style="width: 380px;">-->
<!--                    <el-input v-model="adminLoginForm.code" style="width: 172px; float: left;"></el-input>-->
<!--                    <el-image :src="captchaImageUrl" class="captchaImage"></el-image>-->
<!--                </el-form-item>-->
                <el-form-item style="width: 380px;">
                    <el-button type="primary" @click="submitForm('adminLoginForm')">后台登录</el-button>
                    <el-button @click="resetForm('adminLoginForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "AdminLogin",
        data() {
            return {
                captchaImageUrl: null,
                token: '',
                adminLoginForm: {
                    username: '',
                    password: '',
                    // code: '',
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ],
                    // code: [
                    //     { required: true, message: '请输入验证码', trigger: 'blur' },
                    //     { min: 5, max: 5, message: '验证码长度为5个字符', trigger: 'blur' }
                    // ],
                },
            };
        },
        methods: {
            submitForm(formName) {
                let that = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$axios.post("/admin/login", this.$qs.stringify(this.adminLoginForm), {
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                        })
                            .then((response) => {
                                // console.log("adminLogin");
                                // console.log(response);
                                let result = response.data;
                                let token = result.data.token;
                                // console.log("token");
                                // console.log(token);
                                that.$store.commit('SET_TOKEN', token);
                                let adminUser = result.data.admin;
                                let adminUserStr = JSON.stringify(adminUser);
                                sessionStorage.setItem("adminUser", adminUserStr);
                                // 跳转到后台主页
                                that.$router.push("/admin/index");
                            })
                            // .catch((error) => {
                            //     console.log(error);
                            // })
                    } else {
                        console.log('error submit format !!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getCaptchaImage() {
                let that = this;
                this.$axios.post("/admin/captcha")
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("adminCaptcha_token: " + result.token);
                        that.token = result.token;
                        that.captchaImageUrl = result.data.captchaImageUrl;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
        },
        created() {
            // this.getCaptchaImage();
        }
    }
</script>

<style scoped>
    .el-row {
        background-color: #fafafa;
        height: 100%;
        display: flex;
        align-items: center;
        text-align: center;
    }
    .el-divider {
        height: 200px;
    }

    .captchaImage {
        float: left;
        margin-left: 8px;
        border-radius: 4px;
    }
</style>