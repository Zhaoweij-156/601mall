<template>
    <div>
        <el-form :inline="true">
            <el-form-item>
                <el-input
                        v-model="searchForm.username"
                        placeholder="请输入用户名搜索"
                        clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="showReturnList(1, pageSize)" icon="el-icon-search">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="id"
                    label="编号"
                    sortable
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="memberUsername"
                    label="用户名"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="orderSn"
                    label="订单号"
                    align="center"
                    width="360">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="申请时间"
                    align="center"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="订单状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="warning" size="small" v-show="scope.row.status == 0">待处理</el-tag>
                    <el-tag type="success" size="small" v-show="scope.row.status == 1">退货中</el-tag>
                    <el-tag size="small" v-show="scope.row.status == 2">已完成</el-tag>
                    <el-tag type="danger" size="small" v-show="scope.row.status == 3">已拒绝</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    align="center"
                    width="200">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button
                                type="info"
                                size="small"
                                @click="getReturnOrderInfoOnID(scope.row)"
                                icon="el-icon-zoom-in"
                                :disabled="!scope.row.id">详情</el-button>
                        <el-button
                                type="primary"
                                size="small"
                                @click="tableItemDeal(scope.row)"
                                icon="el-icon-document-checked"
                                :disabled="!scope.row.id || scope.row.status == 2 || scope.row.status == 3">处理</el-button>
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

        <!--退货订单信息详情窗口-->
        <el-dialog
                title="退货订单详情"
                :visible.sync="returnInfoDialogVisible"
                width="1100px"
                :before-close="handleReturnInfoClose">
            <el-form ref="returnInfoForm" :model="returnInfoForm" label-width="80px" :label-position="'right'" :inline="true">
                <el-form-item label="订单号 :" style="width: 1100px;">
                    <el-input size="medium" v-model="returnInfoForm.orderSn" :readonly="!editInfoDisabled" style="width: 360px;"></el-input>
                </el-form-item>
                <el-form-item label="申请用户 :" style="width: 300px;">
                    <el-input size="medium" v-model="returnInfoForm.memberUsername" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="申请时间 :" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="returnInfoForm.createTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="退货商品详情 :" label-width="110px">
                    <el-table
                            :data="[returnInfoForm]"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="skuId"
                                label="商品编号"
                                align="center"
                                width="80">
                        </el-table-column>
                        <el-table-column
                                prop="skuName"
                                label="商品全称"
                                align="center"
                                width="400">
                        </el-table-column>
                        <el-table-column
                                prop="skuImg"
                                label="商品图片"
                                align="center"
                                width="200">
                            <template slot-scope="scope">
                                <el-image :src="scope.row.skuImg" style="width: 100px; height: 100px;">
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
                                prop="skuOriginalPrice"
                                label="原价"
                                align="center"
                                width="120">
                        </el-table-column>
                        <el-table-column
                                prop="skuPresentPrice"
                                label="现价"
                                align="center"
                                width="120">
                        </el-table-column>
                        <el-table-column
                                prop="skuCount"
                                label="退货数量"
                                align="center"
                                width="120">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item label="退货人姓名 :" label-width="95px" style="width: 300px;">
                    <el-input size="medium" v-model="returnInfoForm.returnName" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="退货人电话 :" label-width="95px" style="width: 360px;">
                    <el-input size="medium" v-model="returnInfoForm.returnPhone" :readonly="!editInfoDisabled" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="退款金额 :" style="width: 200px;">
                    <el-input size="medium" v-model="returnInfoForm.returnAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="退货原因 :" label-width="95px" style="width: 1100px;">
                    <el-input
                            type="textarea"
                            v-model="returnInfoForm.reason"
                            :readonly="!editInfoDisabled"
                            style="width: 765px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="处理人姓名 :" label-width="95px" style="width: 300px;">
                    <el-input size="medium" v-model="returnInfoForm.handleMan" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="处理时间 :" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="returnInfoForm.handleTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="处理备注 :" label-width="95px" style="width: 1100px;">
                    <el-input
                            type="textarea"
                            v-model="returnInfoForm.handleNote"
                            :readonly="!editInfoDisabled"
                            style="width: 865px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="收货人 :" label-width="95px" style="width: 300px;">
                    <el-input size="medium" v-model="returnInfoForm.receiveMan" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="收货人电话 :" label-width="95px" style="width: 360px;">
                    <el-input size="medium" v-model="returnInfoForm.receivePhone" :readonly="!editInfoDisabled" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="收货时间 :" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="returnInfoForm.receiveTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="收货地址 :" label-width="95px" style="width: 1100px;">
                    <el-input
                            type="textarea"
                            v-model="returnInfoForm.companyAddress"
                            :readonly="!editInfoDisabled"
                            style="width: 865px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="200"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="收货备注 :" label-width="95px" style="width: 1100px;">
                    <el-input
                            type="textarea"
                            v-model="returnInfoForm.receiveNote"
                            :readonly="!editInfoDisabled"
                            style="width: 865px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--退货处理窗口-->
        <el-dialog
                title="订单处理"
                :visible.sync="dealDialogVisible"
                width="550px"
                :before-close="handleDealClose">
            <el-form :model="dealForm" :rules="dealRules" ref="dealForm" label-width="100px">
                <el-form-item label="处理人 :" prop="handleMan" style="width: 300px;">
                    <el-input v-model="dealForm.handleMan" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="处理选择 :" prop="status">
                    <el-select v-model="dealForm.status" placeholder="请选择处理方式">
                        <el-option label="不处理" :value="0"></el-option>
                        <el-option label="退货" :value="1"></el-option>
                        <el-option label="完成退货" :value="2"></el-option>
                        <el-option label="拒绝退货" :value="3"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="处理备注 :">
                    <el-input
                            type="textarea"
                            v-model="dealForm.handleNote"
                            style="width: 400px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitDealForm('dealForm')">处理</el-button>
<!--                    <el-button @click="resetDealForm('dealForm')">重置</el-button>-->
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "OrderReturn",
        data() {
            return {
                searchForm: {
                    username: '',
                },
                pageCurrent: 1,
                pageSize: 10,
                pageTotal: 0,
                tableData: [
                    {
                        id: 1,
                        memberUsername: 'fjz',
                        orderSn: '123',
                        createTime: '',
                        status: 1,
                    },
                ],
                editInfoDisabled: false,
                returnInfoDialogVisible: false,
                returnInfoForm: {
                    id: null,
                    orderId: null,
                    orderSn: '',
                    skuId: null,
                    createTime: '',
                    memberUsername: '',
                    returnAmount: 0,
                    returnName: '',
                    returnPhone: '',
                    status: 0,
                    handleTime: '',
                    skuImg: '',
                    skuName: '',
                    skuCount: 0,
                    skuOriginalPrice: 0,
                    skuPresentPrice: 0,
                    reason: '',
                    handleNote: '',
                    handleMan: '',
                    receiveMan: '',
                    receiveTime: '',
                    receiveNote: '',
                    receivePhone: '',
                    companyAddress: '',
                },
                dealDialogVisible: false,
                dealForm: {
                    id: '',
                    handleMan: '',
                    handleNote: '',
                    status: 0,
                },
                dealRules: {
                    handleMan: [
                        { required: true, message: '请输入处理人姓名', trigger: 'blur' },
                    ],
                    status: [
                        { required: true, message: '请选择处理方式', trigger: 'change' },
                    ],
                },
            }
        },
        methods: {
            tableItemDeal(row) {
                this.dealForm.id = row.id;
                this.dealDialogVisible = true;
            },
            handleReturnInfoClose(done) {
                this.returnInfoDialogVisible = false;
            },
            handleDealClose(done) {
                this.dealDialogVisible = false;
                this.dealForm = {
                    id: '',
                    handleMan: '',
                    handleNote: '',
                    status: 0,
                };
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.showReturnList(1, this.pageSize);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.showReturnList(val, this.pageSize);
            },
            showReturnList(pageNo, pageSize) {
                let params = {
                    pageNo: pageNo,
                    pageSize: pageSize,
                };
                if(this.searchForm.username && this.searchForm.username != '') {
                    // console.log('search username')
                    // console.log(this.searchForm.username)
                    params = {
                        pageNo: pageNo,
                        pageSize: pageSize,
                        username: this.searchForm.username,
                    };
                }
                // console.log("params");
                // console.log(params);
                let that = this;
                // 获取退货订单信息列表
                this.$axios.get("/admin/order/return/allByUsername", { params: params })
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
            getReturnOrderInfoOnID(row) {
                // console.log("getReturnOrderInfo_id");
                // console.log(row.id);
                let that = this;
                // 根据id获取退货订单详情信息
                this.$axios.get("/admin/order/return/detail", { params: { id: row.id } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.returnInfoForm = result.data;

                        that.returnInfoDialogVisible = true;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            submitDealForm(formName) {
                // console.log("dealReturn");
                let that = this;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // console.log("dealForm");
                        // console.log(that.dealForm);
                        // 退货
                        this.$axios.post("/admin/order/return/operate", this.$qs.stringify(that.dealForm), {
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
                                that.showReturnList(that.pageCurrent, that.pageSize);
                                that.handleDealClose();
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
            resetDealForm(formName) {
                this.$refs[formName].resetFields();
                this.dealForm.handleNote = '';
            },
        },
        created() {
            this.showReturnList(1, this.pageSize);
        },
    }
</script>

<style scoped>

    .el-pagination {
        float: right;
        margin-top: 22px;
    }

</style>