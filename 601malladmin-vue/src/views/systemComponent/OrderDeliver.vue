<template>
    <div>
        <el-form :inline="true">
            <el-form-item label="订单删除状态">
                <el-button-group>
                    <el-button @click="showNotDeleteOrderList">未删除</el-button>
                    <el-button @click="showDeleteOrderList">已删除</el-button>
                    <el-button @click="showAllOrderList">全部</el-button>
                </el-button-group>
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
                    prop="status"
                    label="订单状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="warning" size="small" v-show="scope.row.status == 0">待付款</el-tag>
                    <el-tag type="warning" size="small" v-show="scope.row.status == 1">待发货</el-tag>
                    <el-tag type="success" size="small" v-show="scope.row.status == 2">已发货</el-tag>
                    <el-tag size="small" v-show="scope.row.status == 3">已完成</el-tag>
                    <el-tag type="danger" size="small" v-show="scope.row.status == 4">已关闭</el-tag>
                    <el-tag type="info" size="small" v-show="scope.row.status == 5">无效订单</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    prop="deleteStatus"
                    label="删除状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-show="scope.row.deleteStatus == 0">未删除</el-tag>
                    <el-tag type="warning" size="small" v-show="scope.row.deleteStatus == 1">已删除</el-tag>
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
                                @click="getOrderInfoOnID(scope.row)"
                                icon="el-icon-zoom-in"
                                :disabled="!scope.row.id">详情</el-button>
                        <el-button
                                type="primary"
                                size="small"
                                @click="deliverGoods(scope.row)"
                                icon="el-icon-truck"
                                :disabled="!scope.row.id || scope.row.status != 1 || scope.row.deleteStatus == 1">发货</el-button>
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

        <!--订单信息详情窗口-->
        <el-dialog
                title="订单详情"
                :visible.sync="orderInfoDialogVisible"
                width="1300px"
                :before-close="handleOrderInfoClose">
            <el-form ref="orderInfoForm" :model="orderInfoForm" label-width="80px" :label-position="'right'" :inline="true">
                <el-form-item label="订单号 :" style="width: 480px;">
                    <el-input size="medium" v-model="orderInfoForm.orderSn" :readonly="!editInfoDisabled" style="width: 360px;"></el-input>
                </el-form-item>
                <el-form-item label="用户名 :" style="width: 300px;">
                    <el-input size="medium" v-model="orderInfoForm.memberUsername" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="订单状态 :" style="width: 200px;">
                    <el-tag type="warning" v-show="orderInfoForm.status == 0">待付款</el-tag>
                    <el-tag type="warning" v-show="orderInfoForm.status == 1">待发货</el-tag>
                    <el-tag type="success" v-show="orderInfoForm.status == 2">已发货</el-tag>
                    <el-tag v-show="orderInfoForm.status == 3">已完成</el-tag>
                    <el-tag type="danger" v-show="orderInfoForm.status == 4">已关闭</el-tag>
                    <el-tag type="info" v-show="orderInfoForm.status == 5">无效订单</el-tag>
                </el-form-item>
                <el-form-item label="订单删除状态 :" label-width="110px" style="width: 200px;">
                    <el-tag type="success" v-show="orderInfoForm.deleteStatus == 0">未删除</el-tag>
                    <el-tag type="warning" v-show="orderInfoForm.deleteStatus == 1">已删除</el-tag>
                </el-form-item>
                <el-form-item label="订单商品详情 :" label-width="110px">
                    <el-table
                            :data="orderInfoForm.orderItems"
                            border
                            style="width: 100%">
                        <el-table-column
                                prop="skuId"
                                label="商品编号"
                                sortable
                                align="center"
                                width="80">
                        </el-table-column>
                        <el-table-column
                                prop="spuName"
                                label="商品名称"
                                align="center"
                                width="160">
                        </el-table-column>
                        <el-table-column
                                prop="skuName"
                                label="商品全称"
                                align="center"
                                width="360">
                        </el-table-column>
                        <el-table-column
                                prop="categoryId"
                                label="商品类别ID"
                                align="center"
                                width="80">
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
                                label="购买数量"
                                align="center"
                                width="120">
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item label="运费总额 :" style="width: 200px;">
                    <el-input size="medium" v-model="orderInfoForm.freightAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="订单总额 :" style="width: 200px;">
                    <el-input size="medium" v-model="orderInfoForm.totalAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="应付总额 :" style="width: 200px;">
                    <el-input size="medium" v-model="orderInfoForm.payAmount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="支付方式 :" style="width: 500px;">
                    <el-radio-group v-model="orderInfoForm.payType">
                        <el-radio :label="0" border size="medium">支付宝</el-radio>
                        <el-radio :label="1" border size="medium">微信</el-radio>
                        <el-radio :label="2" border size="medium">银联</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="物流公司 :" style="width: 360px;">
                    <el-input size="medium" v-model="orderInfoForm.deliveryCompany" :readonly="!editInfoDisabled" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="物流单号 :" style="width: 480px;">
                    <el-input size="medium" v-model="orderInfoForm.deliverySn" :readonly="!editInfoDisabled" style="width: 360px;"></el-input>
                </el-form-item>
                <el-form-item label="自动确认收货时间 :" label-width="135px" style="width: 300px;">
                    <el-input size="medium" v-model="orderInfoForm.autoConfirmDay" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                    <span style="margin-left: 10px; margin-right: 20px">天</span>
                </el-form-item>
                <el-form-item label="收货人姓名 :" label-width="95px" style="width: 300px;">
                    <el-input size="medium" v-model="orderInfoForm.receiverName" :readonly="!editInfoDisabled" style="width: 180px;"></el-input>
                </el-form-item>
                <el-form-item label="收货人电话 :" label-width="95px" style="width: 360px;">
                    <el-input size="medium" v-model="orderInfoForm.receiverPhone" :readonly="!editInfoDisabled" style="width: 240px;"></el-input>
                </el-form-item>
                <el-form-item label="收货人地址 :" label-width="95px" style="width: 1250px;">
                    <el-input size="medium" v-model="orderInfoForm.receiverProvince" :readonly="!editInfoDisabled" style="width: 120px;"></el-input>
                    <span style="margin-left: 10px; margin-right: 20px">省</span>
                    <el-input size="medium" v-model="orderInfoForm.receiverCity" :readonly="!editInfoDisabled" style="width: 120px;"></el-input>
                    <span style="margin-left: 10px; margin-right: 20px">市</span>
                    <el-input size="medium" v-model="orderInfoForm.receiverRegion" :readonly="!editInfoDisabled" style="width: 120px;"></el-input>
                    <span style="margin-left: 10px; margin-right: 20px">区</span>
                    <el-form-item label="收货人邮编 :" label-width="95px" style="width: 250px; margin-left: 40px;">
                        <el-input size="medium" v-model="orderInfoForm.receiverPostCode" :readonly="!editInfoDisabled" style="width: 120px;"></el-input>
                    </el-form-item>
                </el-form-item>
                <el-form-item label="        " label-width="95px" style="width: 1250px;">
                    <el-input
                            type="textarea"
                            v-model="orderInfoForm.receiverDetailAddress"
                            :readonly="!editInfoDisabled"
                            style="width: 750px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="200"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="备注 :" label-width="95px" style="width: 1250px;">
                    <el-input
                            type="textarea"
                            v-model="orderInfoForm.note"
                            :readonly="!editInfoDisabled"
                            style="width: 850px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="订单创建时间 :" label-width="110px" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="orderInfoForm.createTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="订单修改时间 :" label-width="110px" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="orderInfoForm.modifiedTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="支付时间 :" label-width="110px" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="orderInfoForm.payTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="发货时间 :" label-width="110px" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="orderInfoForm.deliveryTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="确认收货状态 :" label-width="110px" style="width: 200px;">
                    <el-tag type="warning" v-show="orderInfoForm.confirmStatus == 0">未确认</el-tag>
                    <el-tag type="success" v-show="orderInfoForm.confirmStatus == 1">已确认</el-tag>
                </el-form-item>
                <el-form-item label="确认收货时间 :" label-width="110px" style="width: 360px;">
                    <el-date-picker
                            type="datetime"
                            v-model="orderInfoForm.receiveTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "OrderDeliver",
        data() {
            return {
                showStatusSelect: null,
                pageCurrent: 1,
                pageSize: 10,
                pageTotal: 0,
                tableData: [
                    {
                        id: 1,
                        memberId: null,
                        orderSn: '',
                        memberUsername: '',
                        status: 5,
                        deleteStatus: 0,
                    },
                ],
                editInfoDisabled: false,
                orderInfoDialogVisible: false,
                orderInfoForm: {
                    id: null,
                    memberId: null,
                    orderSn: '',
                    memberUsername: '',
                    totalAmount: 0,
                    payAmount: 0,
                    freightAmount: 0,
                    payType: 1,
                    status: 1,
                    deliveryCompany: '',
                    deliverySn: '',
                    autoConfirmDay: 0,
                    receiverName: '',
                    receiverPhone: '',
                    receiverPostCode: '',
                    receiverProvince: '',
                    receiverCity: '',
                    receiverRegion: '',
                    receiverDetailAddress: '',
                    note: '',
                    confirmStatus: 0,
                    deleteStatus: 0,
                    payTime: '',
                    deliveryTime: '',
                    receiveTime: '',
                    createTime: '',
                    modifiedTime: '',
                    orderItems: [
                        {
                            id: null,
                            orderId: null,
                            orderSn: '',
                            spuId: null,
                            spuName: '',
                            categoryId: null,
                            skuId: null,
                            skuName: '',
                            skuImg: '',
                            skuOriginalPrice: 0,
                            skuPresentPrice: 0,
                            skuCount: 0,
                        },
                    ],
                }
            }
        },
        methods: {
            handleOrderInfoClose(done) {
                this.orderInfoDialogVisible = false;
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.showOrderList(1, this.pageSize);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.showOrderList(val, this.pageSize);
            },
            showNotDeleteOrderList() {
                this.showStatusSelect = 0;
                // console.log("showStatusSelect: " + this.showStatusSelect);
                this.showOrderList(1, this.pageSize);
            },
            showDeleteOrderList() {
                this.showStatusSelect = 1;
                // console.log("showStatusSelect: " + this.showStatusSelect);
                this.showOrderList(1, this.pageSize);
            },
            showAllOrderList() {
                this.showStatusSelect = null;
                // console.log("showStatusSelect: " + this.showStatusSelect);
                this.showOrderList(1, this.pageSize);
            },
            showOrderList(pageNo, pageSize) {
                let params = {
                    pageNo: pageNo,
                    pageSize: pageSize,
                };
                if(this.showStatusSelect != null) {
                    params = {
                        pageNo: pageNo,
                        pageSize: pageSize,
                        deleteStatus: this.showStatusSelect,
                    };
                }
                // console.log("params");
                // console.log(params);
                let that = this;
                // 获取订单信息列表
                this.$axios.get("/admin/order/all", { params: params })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData");
                        // console.log(result.data);
                        that.tableData = result.data.records;
                        that.pageCurrent = result.data.current;
                        that.pageSize = result.data.size;
                        that.pageTotal = result.data.total;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            getOrderInfoOnID(row) {
                // console.log("getOrderInfo_id");
                // console.log(row.id);
                let that = this;
                // 根据id获取发货订单详情信息
                this.$axios.get("/admin/order/detail", { params: { id: row.id } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("orderInfoForm");
                        // console.log(result.data);
                        that.orderInfoForm = result.data;

                        that.orderInfoDialogVisible = true;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            deliverGoods(row) {
                // console.log("deliver");
                let deliverForm = {
                    id: row.id,
                };
                let that = this;
                // 发货
                this.$axios.post("/admin/order/deliver", this.$qs.stringify(deliverForm), {
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
                        that.showOrderList(that.pageCurrent, that.pageSize);
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
        },
        created() {
            this.showAllOrderList(1, this.pageSize);
        }
    }
</script>

<style scoped>

    .el-pagination {
        float: right;
        margin-top: 22px;
    }

</style>