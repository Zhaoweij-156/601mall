<template>
    <div>
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="id"
                    label="编号"
                    width="100"
                    sortable
                    align="center">
            </el-table-column>
            <el-table-column
                    fixed
                    prop="username"
                    label="用户名"
                    width="120"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="avatar"
                    label="头像"
                    width="80"
                    align="center">
                <template slot-scope="scope">
                    <el-image :src="scope.row.avatar" style="width: 50px; height: 50px; border-radius: 25px; border: 1px solid #D3D3D3;">
                        <div slot="placeholder" class="table-image-slot">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                        <div slot="error" class="table-image-slot">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column
                    prop="nickname"
                    label="昵称"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="mobile"
                    label="手机号码"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="gender"
                    label="性别"
                    width="80">
            </el-table-column>
            <el-table-column
                    prop="birth"
                    label="生日"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="city"
                    label="所在城市"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="job"
                    label="职业"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="sign"
                    label="个性签名"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="用户状态"
                    width="80"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-show="scope.row.status == 1">正常</el-tag>
                    <el-tag type="warning" size="small" v-show="scope.row.status == 0">冻结</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="注册时间"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="modifiedTime"
                    label="更改时间"
                    width="200">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="270"
                    align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button
                                type="info"
                                size="small"
                                @click="getUserInfoOnId(scope.row)"
                                icon="el-icon-zoom-in"
                                :disabled="!scope.row.id">详情</el-button>
                        <el-popconfirm title="确定冻结该用户吗？" @confirm="freezeUser(scope.row, 0)">
                            <el-button
                                    slot="reference"
                                    type="warning"
                                    size="small"
                                    icon="el-icon-lock"
                                    :disabled="!scope.row.id || scope.row.status == 0">冻结</el-button>
                        </el-popconfirm>
                        <el-button
                                type="primary"
                                size="small"
                                @click="freezeUser(scope.row, 1)"
                                icon="el-icon-unlock"
                                :disabled="!scope.row.id || scope.row.status == 1">解冻</el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="total, sizes, prev, pager, next, jumper"
                :page-sizes="[10, 20, 30, 50]"
                :current-page="pageCurrent"
                :page-size="pageSize"
                :total="pageTotal">
        </el-pagination>

        <!--用户信息详情窗口-->
        <el-dialog
                title="用户详情"
                :visible.sync="userInfoDialogVisible"
                width="650px"
                :before-close="handleUserInfoClose">
            <el-form ref="userInfoForm" :model="userInfoForm" label-width="80px" :label-position="'right'" :inline="true">
                <div class="block">
                    <el-image :src="userInfoForm.avatar" style="width: 150px; height: 150px; border-radius: 75px; border: 1px solid #D3D3D3;">
                        <div slot="placeholder" class="image-slot">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                        <div slot="error" class="image-slot">
                            <i class="el-icon-picture-outline"></i>
                        </div>
                    </el-image>
                </div>
                <el-form-item label="用户名 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.username" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="昵称 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.nickname" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="手机号码 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.mobile" :readonly="!editInfoDisabled" style="width: 200px;"></el-input>
                </el-form-item>
                <el-form-item label="邮箱 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.email" :readonly="!editInfoDisabled" style="width: 200px;"></el-input>
                </el-form-item>
                <el-form-item label="性别 :" style="width: 500px;">
                    <el-radio-group v-model="userInfoForm.gender">
                        <el-radio label="男" border size="medium">男</el-radio>
                        <el-radio label="女" border size="medium">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="生日 :" style="width: 500px;">
                    <el-date-picker
                            type="date"
                            v-model="userInfoForm.birth"
                            :readonly="!editInfoDisabled"
                            format="yyyy 年 MM 月 dd 日"
                            value-format="yyyy-MM-dd"
                            style="width: 250px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="所在城市 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.city" :readonly="!editInfoDisabled" style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="职业 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.job" :readonly="!editInfoDisabled" style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="个性签名 :">
                    <el-input
                            type="textarea"
                            v-model="userInfoForm.sign"
                            :readonly="!editInfoDisabled"
                            style="width: 500px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="200"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="用户状态 :" style="width: 500px;">
                    <el-radio-group v-model="userInfoForm.status">
                        <el-radio :label="1" border size="medium">正常</el-radio>
                        <el-radio :label="0" border size="medium">冻结</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="创建时间 :" style="width: 295px;">
                    <el-date-picker
                            type="datetime"
                            v-model="userInfoForm.createTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="更改时间 :" style="width: 295px;">
                    <el-date-picker
                            type="datetime"
                            v-model="userInfoForm.modifiedTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="订单数量 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.orderCount" :readonly="!editInfoDisabled" style="width: 80px;"></el-input>
                </el-form-item>
                <el-form-item label="退货数量 :" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.returnOrderCount" :readonly="!editInfoDisabled" style="width: 80px;"></el-input>
                </el-form-item>
                <el-form-item label="累计消费金额 :" label-width="108px" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.consumeAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="累计优惠金额 :" label-width="108px" style="width: 295px;">
                    <el-input size="medium" v-model="userInfoForm.couponAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="用户登录次数 :" label-width="108px" style="width: 500px;">
                    <el-input size="medium" v-model="userInfoForm.loginCount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "UserManagge",
        data() {
            return {
                pageCurrent: 1,
                pageSize: 10,
                pageTotal: 0,
                tableData: [
                    {
                        id: 1,
                        username: '',
                        password: '',
                        salt: '',
                        nickname: '',
                        mobile: '',
                        email: '',
                        avatar: '',
                        gender: '',
                        birth: '',
                        city: '',
                        job: '',
                        sign: '',
                        status: 1,
                        createTime: '',
                        modifiedTime: '',
                    },
                ],
                editInfoDisabled: false,
                userInfoDialogVisible: false,
                userInfoForm: {
                    id: null,
                    username: '',
                    password: '',
                    salt: '',
                    nickname: '',
                    mobile: '',
                    email: '',
                    avatar: '',
                    gender: '',
                    birth: '',
                    city: '',
                    job: '',
                    sign: '',
                    status: 1,
                    createTime: '',
                    modifiedTime: '',
                    consumeAmount: 0,
                    couponAmount: 0,
                    orderCount: 0,
                    returnOrderCount: 0,
                    loginCount: 0,
                }
            }
        },
        methods: {
            handleUserInfoClose(done) {
                this.userInfoDialogVisible = false;
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.showUserList(1, this.pageSize);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.showUserList(val, this.pageSize);
            },
            showUserList(pageNo, pageSize) {
                let that = this;
                // 获取用户信息列表
                this.$axios.get("/admin/allmember", { params: { pageNo: pageNo, pageSize: pageSize } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.tableData = result.data.records;
                        that.pageCurrent = result.data.current;
                        that.pageSize = result.data.size;
                        that.pageTotal = result.data.total;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            getUserInfoOnId(row) {
                // console.log("getUserInfo_id");
                // console.log(row.id);
                let that = this;
                // 根据id获取用户详情信息
                this.$axios.get("/admin/member", { params: { id: row.id } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.userInfoForm = result.data;

                        that.userInfoDialogVisible = true;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            freezeUser(row, status) {
                console.log("frozen");
                console.log(row.status);
                let freezeForm = {
                    id: row.id,
                    status: status,
                };
                let that = this;
                // 冻结或解冻用户
                this.$axios.post("/admin/changestatus", this.$qs.stringify(freezeForm), {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                })
                    .then((response) => {
                        // console.log(response);
                        this.$message({
                            showClose: true,
                            message: '操作成功！',
                            type: 'success',
                        });
                        that.showUserList(that.pageCurrent, that.pageSize);
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
        },
        created() {
            this.showUserList(1, this.pageSize);
        }
    }
</script>

<style scoped>

    .table-image-slot {
        width: 50px;
        height: 50px;
        font-size: 15px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

    .block {
        width: 600px;
        margin-bottom: 30px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

    .image-slot {
        width: 150px;
        height: 150px;
        font-size: 20px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

    .el-pagination {
        float: right;
        margin-top: 22px;
    }

</style>