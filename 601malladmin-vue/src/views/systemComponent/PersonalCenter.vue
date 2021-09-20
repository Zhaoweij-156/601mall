<template>
    <div class="info-container">
        <div class="info-panel">
            <h2>你好！{{ userInfo.username }}</h2>
            <div class="info-header">
                <span>基本信息</span>
                <p class="info-line"></p>
            </div>
            <div class="info-list">
                <p>
                    <span>邮箱 : </span>
                    <span>{{ userInfo.email }}</span>
                </p>
                <p>
                    <span>手机 : </span>
                    <span>{{ userInfo.mobile }}</span>
                </p>
            </div>
            <div class="info-header">
                <span>&nbsp;其&nbsp;&nbsp;&nbsp;&nbsp;他&nbsp;</span>
                <p class="info-line"></p>
            </div>
            <div class="info-list">
                <p>
                    <span>创建时间 : </span>
                    <span>{{ userInfo.createTime }}</span>
                </p>
                <p>
                    <span>更改时间 : </span>
                    <span>{{ userInfo.modifiedTime }}</span>
                </p>
            </div>
            <div class="info-header">
                <span>账号状态</span>
                <p class="info-line"></p>
            </div>
            <div class="info-status">
                <el-radio-group v-model="radio">
                    <el-radio :label="0">禁用</el-radio>
                    <el-radio :label="1">正常</el-radio>
                </el-radio-group>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PersonalCenter",
        data() {
            return {
                userInfo: {
                    username: "",
                    email: "*********@*****",
                    mobile: "***********",
                    status: "1",
                    createTime: "0000-00-00 00:00:00",
                    modifiedTime: "0000-00-00 00:00:00",
                },
                radio: 1,
            }
        },
        methods: {
            getUserInfo() {
                let userInfo = JSON.parse(sessionStorage.getItem("adminUser"));
                if(userInfo) {
                    this.userInfo.username = userInfo.username;
                    this.userInfo.email = userInfo.email;
                    this.userInfo.mobile = userInfo.mobile;
                    this.userInfo.status = userInfo.status;
                    this.userInfo.createTime = userInfo.createTime;
                    this.userInfo.modifiedTime = userInfo.modifiedTime;
                    this.radio = parseInt(this.userInfo.status);
                }
            },
        },
        created() {
            this.getUserInfo();
        },
    }
</script>

<style scoped>

    .info-container {
        width: 100%;
        height: 800px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .info-panel {
        background-color: #ffffff;
        width: 800px;
        height: 780px;
        border-radius: 8px;
        padding: 10px;
        margin: 10px;
    }

    .info-panel h2 {
        text-align: center;
        font-size: 28px;
        line-height: 100px;
    }

    .info-header {
        margin-bottom: 10px;
    }

    .info-header span {
        font-size: 20px;
        padding-left: 10px;
    }

    .info-line {
        float: right;
        width: 86%;
        height: 1px;
        background-color: #E9E9E9;
        margin-top: 18px;
        margin-right: 10px;
    }

    .info-list {
        padding-bottom: 20px;
    }

    .info-list p {
        padding: 2px 20px;
    }

    .info-list p :nth-child(1) {
        color: darkgray;
        font-size: 16px;
        padding-right: 10px;
    }

    .info-list p :nth-child(2) {
        color: #333333;
        font-size: 18px;
        padding-left: 10px;
    }

    .info-status {
        margin-top: 20px;
        padding: 2px 20px;
    }

    .el-radio /deep/ .el-radio__label{
        font-size:18px !important;
    }

</style>