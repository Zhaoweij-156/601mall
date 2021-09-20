<template>
    <div>
        <el-form :inline="true">
            <el-form-item label="商品类别选择 :">
                <el-select v-model="categoryIdSelect" placeholder="请选择商品类别">
                    <el-option label="不限" :value="-1"></el-option>
                    <el-option :label="categoryItem.name" :value="categoryItem.id" v-for="categoryItem in categoryList" :key="categoryItem.id"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button @click="showSpuList(1, pageSize)">确定</el-button>
            </el-form-item>
            <el-form-item>
                <el-button
                        type="primary"
                        @click="spuDialogVisible = true"
                        icon="el-icon-plus">新增</el-button>
            </el-form-item>
            <el-form-item>
                <el-popconfirm title="确定批量删除这些商品吗？" @confirm="deleteSpuGoodsInBatches">
                    <el-button
                            slot="reference"
                            type="danger"
                            :disabled="multipleSelection.length == 0"
                            icon="el-icon-delete">批量删除</el-button>
                </el-popconfirm>
            </el-form-item>
        </el-form>
        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                border
                style="width: 100%"
                @selection-change="handleSelectionChange">
            <el-table-column
                    fixed
                    type="selection"
                    width="65"
                    align="center">
            </el-table-column>
            <el-table-column
                    fixed
                    prop="spuId"
                    label="SPU编号"
                    width="80"
                    align="center">
            </el-table-column>
            <el-table-column
                    fixed
                    prop="skuId"
                    label="SKU编号"
                    width="80"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="skuName"
                    label="商品全称"
                    width="300"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="spuName"
                    label="所属商品"
                    width="200"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="defaultImg"
                    label="商品图片"
                    align="center"
                    min-width="200">
                <template slot-scope="scope">
                    <el-image :src="scope.row.defaultImg" style="width: 100px; height: 100px;">
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
                    prop="publishStatus"
                    label="上架状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-show="scope.row.publishStatus == 1">上架</el-tag>
                    <el-tag type="danger" size="small" v-show="scope.row.publishStatus == 0">下架</el-tag>
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
                                size="small"
                                type="primary"
                                @click="tableItemEdit(scope.$index, scope.row)"
                                icon="el-icon-edit"
                                :disabled="!scope.row.spuId">修改</el-button>
                        <el-popconfirm title="确定删除这一商品吗？" @confirm="tableItemDelete(scope.$index, scope.row)">
                            <el-button
                                    slot="reference"
                                    size="small"
                                    type="danger"
                                    icon="el-icon-delete"
                                    :disabled="!scope.row.spuId">删除</el-button>
                        </el-popconfirm>
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

        <!--spu商品操作窗口-->
        <el-dialog
                title="商品管理"
                :visible.sync="spuDialogVisible"
                width="500px"
                :before-close="handleSpuClose">
            <el-form :model="spuForm" :rules="spuRules" ref="spuForm" label-width="120px">
                <el-form-item label="所属商品名称 :" prop="name">
                    <el-input v-model="spuForm.name"></el-input>
                </el-form-item>
                <el-form-item label="商品分类" prop="categoryId">
                    <el-select v-model="spuForm.categoryId" placeholder="请选择商品类别">
                        <el-option :label="categoryItem.name" :value="categoryItem.id" v-for="categoryItem in categoryList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否上架 :" prop="publishStatus">
                    <el-radio-group v-model="spuForm.publishStatus">
                        <el-radio :label="1" border size="medium">上架</el-radio>
                        <el-radio :label="0" border size="medium">下架</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitSpuForm('spuForm')">
                        <span v-show="!spuForm.id">新增</span>
                        <span v-show="spuForm.id">修改</span>
                    </el-button>
<!--                    <el-button @click="resetSpuForm('spuForm')">重置</el-button>-->
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SpuManage",
        data() {
            return {
                categoryIdSelect: null,
                categoryList: [],
                pageCurrent: 1,
                pageSize: 10,
                pageTotal: 0,
                tableData: [
                    {
                        spuId: null,
                        skuId: null,
                        spuName: '',
                        skuName: '',
                        publishStatus: 1,
                        defaultImg: '',
                    },
                ],
                multipleSelection: [],
                spuDialogVisible: false,
                spuForm: {
                    name: '',
                    categoryId: null,
                    publishStatus: 1,
                },
                spuRules: {
                    name: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                    ],
                    categoryId: [
                        { required: true, message: '请选择商品分类', trigger: 'change' },
                    ],
                    publishStatus: [
                        { required: true, message: '请选择上架状态', trigger: 'change' },
                    ],
                },
                skuInfoForm: {
                    id: null,
                    spuId: null,
                    skuName: '',
                    skuDesc: '',
                    categoryId: null,
                    skuDefaultImg: '',
                    originalPrice: 0,
                    presentPrice: 0,
                    saleCount: 0,
                    publishStatus: 1,
                    stock: 1,
                    createTime: '',
                    modifiedTime: '',
                    skuImages: [
                        {
                            id: null,
                            skuId: null,
                            imgUrl: '',
                            defaultImg: 0,
                        },
                    ],
                },
            }
        },
        methods: {
            tableItemEdit(index, row) {
                // console.log("spu edit");
                // console.log(index, row);
                this.spuForm = {
                    id: row.spuId,
                    name: row.spuName,
                    categoryId: null,
                    publishStatus: row.publishStatus,
                };
                // console.log("spuForm");
                // console.log(this.spuForm);
                this.getSkuInfoOnId(row);
            },
            tableItemDelete(index, row) {
                // console.log(index, row);
                this.deleteSpuGoods(row.spuId);
            },
            handleSelectionChange(val) {
                // console.log("selectVal");
                // console.log(val);
                this.multipleSelection = val;
            },
            handleSpuClose(done) {
                this.spuDialogVisible = false;
                this.spuForm = {
                    name: '',
                    categoryId: null,
                    publishStatus: 1,
                };
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.showSpuList(1, this.pageSize);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.showSpuList(val, this.pageSize);
            },
            showSpuList(pageNo, pageSize) {
                let params = {
                    pageNo: pageNo,
                    pageSize: pageSize,
                };
                if(this.categoryIdSelect != -1) {
                    params = {
                        pageNo: pageNo,
                        pageSize: pageSize,
                        categoryId: this.categoryIdSelect,
                    };
                }
                // console.log("params");
                // console.log(params);
                let that = this;
                // 获取商品信息列表
                this.$axios.get("/admin/product/allforpage", { params: params })
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
            getSkuInfoOnId(row) {
                // console.log("getSkuInfo_id");
                // console.log(row.skuId);
                let that = this;
                // 根据skuId获取商品详情信息
                this.$axios.get("/admin/sku", { params: { skuId: row.skuId } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("skuInfoForm");
                        // console.log(result.data);
                        that.skuInfoForm = result.data;

                        that.spuForm.categoryId = that.skuInfoForm.categoryId;
                        that.spuDialogVisible = true;
                    })
                // .catch((error) => {
                //     console.log(error);
                // })
            },
            submitSpuForm(formName) {
                let that = this;
                this.$refs[formName].validate((valid) => {
                    // console.log(that.categoryFileList);
                    if (valid) {
                        // console.log("spuForm");
                        // console.log(that.spuForm);
                        if(!that.spuForm.id) {
                            // 新增spu商品
                            this.$axios.put("/admin/spu", this.$qs.stringify(that.spuForm), {
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
                                    that.showSpuList(that.pageCurrent, that.pageSize);
                                    that.handleSpuClose();
                                })
                                // .catch((error) => {
                                //     console.log(error);
                                // })
                        } else {
                            // 更改spu商品
                            this.$axios.post("/admin/spu", this.$qs.stringify(that.spuForm), {
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
                                    that.showSpuList(that.pageCurrent, that.pageSize);
                                    that.handleSpuClose();
                                })
                                // .catch((error) => {
                                //     console.log(error);
                                // })
                        }
                    } else {
                        console.log('error submit format !!');
                        return false;
                    }
                });
            },
            resetSpuForm(formName) {
                this.$refs[formName].resetFields();
            },
            deleteSpuGoods(spuId) {
                let that = this;
                // 删除spu商品
                this.$axios.delete("/admin/spu", { params: { spuId: spuId } })
                    .then((response) => {
                        // console.log(response);
                        this.$message({
                            showClose: true,
                            message: '操作成功！',
                            type: 'success',
                        });
                        that.showSpuList(that.pageCurrent, that.pageSize);
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            // 批量删除
            deleteSpuGoodsInBatches() {
                if(this.multipleSelection.length > 0) {
                    for(let i = 0; i < this.multipleSelection.length; i++) {
                        this.deleteSpuGoods(this.multipleSelection[i].spuId);
                    }
                }
            },
            getAllCategory() {
                let that = this;
                // 获取上架商品类别
                this.$axios.get("/admin/allcategory", { params: { flag: '1' } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.categoryList = result.data;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
        },
        created() {
            this.getAllCategory();
            this.showSpuList(1, this.pageSize);
        }
    }
</script>

<style scoped>

    .el-pagination {
        float: right;
        margin-top: 22px;
    }

</style>