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
                <el-button @click="showSkuList(1, pageSize)">确定</el-button>
            </el-form-item>
            <el-form-item>
                <el-button
                        type="primary"
                        @click="skuDialogVisible = true"
                        icon="el-icon-plus">新增</el-button>
            </el-form-item>
            <el-form-item>
                <el-popconfirm title="确定批量删除这些商品吗？" @confirm="deleteSkuGoodsInBatches">
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
                    width="270"
                    align="center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button
                                type="info"
                                size="small"
                                @click="getSkuInfoOnId(scope.row, 'info')"
                                icon="el-icon-zoom-in"
                                :disabled="!scope.row.skuId">详情</el-button>
                        <el-button
                                size="small"
                                type="primary"
                                @click="tableItemEdit(scope.$index, scope.row)"
                                icon="el-icon-edit"
                                :disabled="!scope.row.skuId">修改</el-button>
                        <el-popconfirm title="确定删除这一商品吗？" @confirm="tableItemDelete(scope.$index, scope.row)">
                            <el-button
                                    slot="reference"
                                    size="small"
                                    type="danger"
                                    icon="el-icon-delete"
                                    :disabled="!scope.row.skuId">删除</el-button>
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

        <!--sku商品操作窗口-->
        <el-dialog
                title="商品管理"
                :visible.sync="skuDialogVisible"
                width="650px"
                :before-close="handleSkuClose">
            <el-form :model="skuForm" :rules="skuRules" ref="skuForm" label-width="115px" :label-position="'right'" :inline="true">
                <el-form-item label="所属SPU编号 :" prop="spuId" style="width: 250px;">
                    <el-input size="medium" v-model="skuForm.spuId" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="商品全称 :" prop="skuName" style="width: 600px;">
                    <el-input
                            type="textarea"
                            v-model="skuForm.skuName"
                            style="width: 400px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="200"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="商品介绍描述 :" prop="skuDesc" style="width: 600px;">
                    <el-input
                            type="textarea"
                            v-model="skuForm.skuDesc"
                            style="width: 450px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="商品分类 :" prop="category_id" style="width: 600px;">
                    <el-select v-model="skuForm.category_id">
                        <el-option :label="categoryItem.name" :value="categoryItem.id" v-for="categoryItem in categoryList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="原价 :" prop="originalPrice" style="width: 250px;">
                    <el-input size="medium" v-model="skuForm.originalPrice" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="现价 :" prop="presentPrice" style="width: 250px;">
                    <el-input size="medium" v-model="skuForm.presentPrice" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="销量 :" prop="saleCount" style="width: 250px;">
                    <el-input size="medium" v-model="skuForm.saleCount" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="库存 :" prop="stock" style="width: 250px;">
                    <el-input size="medium" v-model="skuForm.stock" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="商品是否上架 :" prop="publishStatus" style="width: 600px;">
                    <el-radio-group v-model="skuForm.publishStatus">
                        <el-radio :label="1" border size="medium">上架</el-radio>
                        <el-radio :label="0" border size="medium">下架</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="商品默认图片 :" prop="skuDefaultImg" style="width: 600px;">
                    <el-upload
                            class="upload-demo"
                            ref="uploadSkuImg"
                            :action="uploadUrl()"
                            multiple
                            :auto-upload="false"
                            accept="image/jpeg,image/png"
                            :file-list="skuFileList"
                            :on-change="handleSkuImgChange"
                            :on-preview="handleSkuImgPreview"
                            :on-remove="handleSkuImgRemove"
                            :before-remove="beforeSkuImgRemove"
                            :on-success="handleSkuImgSuccess"
                            :on-error="handleSkuImgError"
                            :limit="1"
                            :on-exceed="handleSkuImgExceed"
                            >
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpeg/png文件</div>
                    </el-upload>
                    <el-dialog :visible.sync="previewImageDialogVisible">
                        <img width="100%" :src="previewImageUrl" alt="">
                    </el-dialog>
                </el-form-item>
                <el-form-item label="       " style="width: 600px;">
                    <el-button type="primary" @click="uploadFile">
                        <span v-show="!skuForm.id">新增</span>
                        <span v-show="skuForm.id">修改</span>
                    </el-button>
<!--                    <el-button @click="resetSkuForm('skuForm')">重置</el-button>-->
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--sku商品信息详情窗口-->
        <el-dialog
                title="商品详情"
                :visible.sync="skuInfoDialogVisible"
                width="650px"
                :before-close="handleSkuInfoClose">
            <el-form :model="skuInfoForm" ref="skuInfoForm" label-width="110px" :label-position="'right'" :inline="true">
                <el-carousel trigger="click" :autoplay="false" height="250px" style="margin-bottom: 20px;">
                    <el-carousel-item>
                        <div class="carousel-div">
                            <el-image
                                    :src="skuInfoForm.skuDefaultImg"
                                    style="width: 270px; height: 240px;">
                                <div slot="placeholder" class="table-image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                                <div slot="error" class="table-image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </div>
                    </el-carousel-item>
                    <el-carousel-item v-for="skuImgItem in skuInfoForm.skuImages" :key="skuImgItem.id">
                        <div class="carousel-div">
                            <el-image
                                    :src="skuImgItem.imgUrl"
                                    style="width: 270px; height: 240px;"></el-image>
                        </div>
                    </el-carousel-item>
                </el-carousel>
                <el-form-item label="商品全称 :" style="width: 600px;">
                    <el-input
                            type="textarea"
                            v-model="skuInfoForm.skuName"
                            :readonly="!editInfoDisabled"
                            style="width: 400px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"></el-input>
                </el-form-item>
                <el-form-item label="商品介绍描述 :" style="width: 600px;">
                    <el-input
                            type="textarea"
                            v-model="skuInfoForm.skuDesc"
                            :readonly="!editInfoDisabled"
                            style="width: 450px;"
                            :autosize="{ minRows: 2, maxRows: 4 }"
                            maxlength="300"
                            show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="商品分类 :" style="width: 600px;">
                    <el-select v-model="skuInfoForm.categoryId" :disabled="!editInfoDisabled">
                        <el-option :label="categoryItem.name" :value="categoryItem.id" v-for="categoryItem in categoryList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="原价 :" style="width: 250px;">
                    <el-input size="medium" v-model="skuInfoForm.originalPrice" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="现价 :" style="width: 250px;">
                    <el-input size="medium" v-model="skuInfoForm.presentPrice" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="销量 :" style="width: 250px;">
                    <el-input size="medium" v-model="skuInfoForm.saleCount" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="库存 :" style="width: 250px;">
                    <el-input size="medium" v-model="skuInfoForm.stock" :readonly="!editInfoDisabled" style="width: 100px;"></el-input>
                </el-form-item>
                <el-form-item label="商品上架状态 :" style="width: 200px;">
                    <el-tag type="success" v-show="skuInfoForm.publishStatus == 1">上架</el-tag>
                    <el-tag type="danger" v-show="skuInfoForm.publishStatus == 0">下架</el-tag>
                </el-form-item>
                <el-form-item label="商品创建时间 :" style="width: 600px;">
                    <el-date-picker
                            type="datetime"
                            v-model="skuInfoForm.createTime"
                            :readonly="!editInfoDisabled"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 200px;"></el-date-picker>
                </el-form-item>
                <el-form-item label="商品修改时间 :" style="width: 600px;">
                    <el-date-picker
                            type="datetime"
                            v-model="skuInfoForm.modifiedTime"
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
        name: "SkuManage",
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
                skuDialogVisible: false,
                skuForm: {
                    spuId: null,
                    skuName: '',
                    skuDesc: '',
                    category_id: null,
                    skuDefaultImg: '',
                    originalPrice: 0,
                    presentPrice: 0,
                    saleCount: 0,
                    publishStatus: 1,
                    stock: 0,
                },
                originSkuDefaultImg: '',
                skuRules: {
                    spuId: [
                        { required: true, message: '请输入商品所属SPU编号', trigger: 'blur' },
                    ],
                    skuName: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                    ],
                    skuDesc: [
                        { required: true, message: '请输入商品介绍描述', trigger: 'blur' },
                    ],
                    category_id: [
                        { required: true, message: '请选择商品分类', trigger: 'change' },
                    ],
                    // skuDefaultImg: [
                    //     { required: true, message: '请上传商品默认图片', trigger: 'blur' },
                    // ],
                    originalPrice: [
                        { required: true, message: '请输入商品原价', trigger: 'blur' },
                    ],
                    presentPrice: [
                        { required: true, message: '请输入商品现价', trigger: 'blur' },
                    ],
                    saleCount: [
                        { required: true, message: '请输入商品销量', trigger: 'blur' },
                    ],
                    publishStatus: [
                        { required: true, message: '请选择商品上架状态', trigger: 'change' },
                    ],
                    stock: [
                        { required: true, message: '请输入商品库存', trigger: 'blur' },
                    ],
                },
                editInfoDisabled: false,
                skuInfoDialogVisible: false,
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
                    stock: 0,
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
                skuFileList: [],
                previewImageUrl: '',
                previewImageDialogVisible: false,
            }
        },
        methods: {
            tableItemEdit(index, row) {
                // console.log("sku edit");
                // console.log(index, row);
                this.getSkuInfoOnId(row, 'edit');
            },
            tableItemDelete(index, row) {
                // console.log(index, row);
                this.deleteSkuGoods(row.skuId);
            },
            handleSelectionChange(val) {
                // console.log("selectVal");
                // console.log(val);
                this.multipleSelection = val;
            },
            handleSkuClose(done) {
                this.skuDialogVisible = false;
                this.skuForm = {
                    spuId: null,
                    skuName: '',
                    skuDesc: '',
                    category_id: null,
                    skuDefaultImg: '',
                    originalPrice: 0,
                    presentPrice: 0,
                    saleCount: 0,
                    publishStatus: 1,
                    stock: 0,
                };
                this.originSkuDefaultImg = '';
                this.skuFileList = [];
                this.$refs.uploadSkuImg.clearFiles();
            },
            handleSkuInfoClose(done) {
                this.skuInfoDialogVisible = false;
            },
            handleSizeChange(val) {
                // console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.showSkuList(1, this.pageSize);
            },
            handleCurrentChange(val) {
                // console.log(`当前页: ${val}`);
                this.showSkuList(val, this.pageSize);
            },
            showSkuList(pageNo, pageSize) {
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
            getSkuInfoOnId(row, whichDialog) {
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

                        if(whichDialog == 'info') {
                            that.skuInfoDialogVisible = true;
                        } else if(whichDialog == 'edit') {
                            that.skuForm = {
                                id: that.skuInfoForm.id,
                                spuId: that.skuInfoForm.spuId,
                                skuName: that.skuInfoForm.skuName,
                                skuDesc: that.skuInfoForm.skuDesc,
                                category_id: that.skuInfoForm.categoryId,
                                skuDefaultImg: that.skuInfoForm.skuDefaultImg,
                                originalPrice: that.skuInfoForm.originalPrice,
                                presentPrice: that.skuInfoForm.presentPrice,
                                saleCount: that.skuInfoForm.saleCount,
                                publishStatus: that.skuInfoForm.publishStatus,
                                stock: that.skuInfoForm.stock,
                            };
                            if(that.skuForm.skuDefaultImg != '') {
                                that.skuFileList = [
                                    {
                                        name: '默认图片',
                                        url: that.skuForm.skuDefaultImg,
                                    }
                                ];
                                that.originSkuDefaultImg = that.skuForm.skuDefaultImg;
                            }
                            that.skuDialogVisible = true;
                        }
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            submitSkuForm(formName) {
                let that = this;
                this.$refs[formName].validate((valid) => {
                    // console.log(that.skuFileList);
                    if (valid) {
                        // console.log("skuForm");
                        // console.log(that.skuForm);
                        if(!that.skuForm.id) {
                            // 新增sku商品
                            this.$axios.put("/admin/sku", this.$qs.stringify(that.skuForm), {
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
                                    that.showSkuList(that.pageCurrent, that.pageSize);
                                    that.handleSkuClose();
                                })
                                // .catch((error) => {
                                //     console.log(error);
                                // })
                        } else {
                            // 更改sku商品
                            this.$axios.post("/admin/sku", this.$qs.stringify(that.skuForm), {
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
                                    that.showSkuList(that.pageCurrent, that.pageSize);
                                    that.handleSkuClose();
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
            resetSkuForm(formName) {
                this.$refs[formName].resetFields();
                if(this.originSkuDefaultImg != '') {
                    this.skuFileList = [
                        {
                            name: '默认图片',
                            url: this.originSkuDefaultImg,
                        }
                    ];
                } else {
                    this.skuFileList = [];
                }
            },
            deleteSkuGoods(skuId) {
                let that = this;
                // 删除sku商品
                this.$axios.delete("/admin/sku", { params: { skuId: skuId } })
                    .then((response) => {
                        // console.log(response);
                        this.$message({
                            showClose: true,
                            message: '操作成功！',
                            type: 'success',
                        });
                        that.showSkuList(that.pageCurrent, that.pageSize);
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            // 批量删除
            deleteSkuGoodsInBatches() {
                if(this.multipleSelection.length > 0) {
                    for(let i = 0; i < this.multipleSelection.length; i++) {
                        this.deleteSkuGoods(this.multipleSelection[i].skuId);
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

            // 文件上传
            uploadUrl:function(){
                return "https://jsonplaceholder.typicode.com/posts/";
            },
            uploadFile() {
                // console.log("sku image upload");
                if(this.skuFileList.length == 0 || this.skuFileList[0].url == this.originSkuDefaultImg) {
                    this.submitSkuForm('skuForm');
                } else {
                    this.$refs.uploadSkuImg.submit();
                }
            },
            handleSkuImgChange(file, fileList) {
                // console.log("sku image change");
                // console.log(file, fileList);
                let flag = true;
                this.skuFileList.forEach((item, index) => {
                    if (file.name == item.name) {
                        flag = false;
                    }
                })
                if(flag) {
                    this.skuFileList.push({ name: file.name, url: file.url });
                }
            },
            handleSkuImgPreview(file) {
                // console.log("sku image preview");
                // console.log(file);
                this.previewImageUrl = file.url;
                this.previewImageDialogVisible = true;
            },
            handleSkuImgRemove(file, fileList) {
                // console.log("sku image remove");
                // console.log(file, fileList);
                this.skuFileList.forEach((item, index) => {
                    if (file.name == item.name) {
                        this.skuFileList.splice(index, 1);
                    }
                });
            },
            beforeSkuImgRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
            handleSkuImgSuccess(response, file, fileList) {
                // console.log("sku image success");
                // console.log(response, file, fileList);
                if(fileList.length == this.skuFileList.length) {
                    if(this.skuFileList.length > 0) {
                        this.skuForm.skuDefaultImg = this.skuFileList[0].url;
                    }
                    this.submitSkuForm('skuForm');
                }
            },
            handleSkuImgError(err, file, fileList) {
                // console.log("sku image error");
                // console.log(err, file, fileList);
                this.$message.error(err);
            },
            handleSkuImgExceed(files, fileList) {
                this.$message.warning('当前限制上传 1 张图片，本次选择了 ${files.length} 张图片，已选择了 ${fileList.length} 张图片');
            },
        },
        created() {
            this.getAllCategory();
            this.showSkuList(1, this.pageSize);
        }
    }
</script>

<style scoped>

    .el-pagination {
        float: right;
        margin-top: 22px;
    }

    .carousel-div {
        width: 600px;
        height: 250px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

    .table-image-slot {
        width: 100%;
        height: 100%;
        font-size: 35px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

</style>