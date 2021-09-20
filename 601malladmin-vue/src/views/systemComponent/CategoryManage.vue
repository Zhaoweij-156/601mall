<template>
    <div>
        <el-form :inline="true">
            <el-form-item label="显示状态">
                <el-button-group>
                    <el-button @click="showDisplayCategory">显示</el-button>
                    <el-button @click="showHideCategory">不显示</el-button>
                </el-button-group>
            </el-form-item>
            <el-form-item>
                <el-button
                        type="primary"
                        @click="categoryDialogVisible = true"
                        icon="el-icon-plus">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData.filter(data => !search || data.id === parseInt(search))"
                style="width: 100%"
                border>
            <el-table-column
                    label="ID"
                    prop="id"
                    sortable
                    align="center">
            </el-table-column>
            <el-table-column
                    label="分类名称"
                    prop="name"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="显示状态"
                    prop="showStatus"
                    align="center">
                <template slot-scope="scope">
                    <el-tag type="success" size="small" v-show="scope.row.showStatus == 1">显示</el-tag>
                    <el-tag type="danger" size="small" v-show="scope.row.showStatus == 0">不显示</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    label="分类图标"
                    prop="icon"
                    align="center">
                <template slot-scope="scope">
                    <el-image :src="scope.row.defaultImg" style="width: 50px; height: 50px;">
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
                    align="center">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="small"
                            placeholder="输入分类ID进行搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button
                                size="small"
                                type="primary"
                                @click="tableItemEdit(scope.$index, scope.row)"
                                icon="el-icon-edit"
                                :disabled="!scope.row.id">修改</el-button>
                        <el-popconfirm title="确定删除这一商品分类吗？" @confirm="tableItemDelete(scope.$index, scope.row)">
                            <el-button
                                    slot="reference"
                                    size="small"
                                    type="danger"
                                    icon="el-icon-delete"
                                    :disabled="!scope.row.id">删除</el-button>
                        </el-popconfirm>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>

        <!--商品类别操作窗口-->
        <el-dialog
                title="商品分类"
                :visible.sync="categoryDialogVisible"
                width="500px"
                :before-close="handleCategoryClose">
            <el-form :model="categoryForm" :rules="categoryRules" ref="categoryForm" label-width="100px">
                <el-form-item label="分类名称 :" prop="name">
                    <el-input v-model="categoryForm.name"></el-input>
                </el-form-item>
                <el-form-item label="是否显示 :" prop="showStatus">
                    <el-radio-group v-model="categoryForm.showStatus">
                        <el-radio :label="1" border size="medium">显示</el-radio>
                        <el-radio :label="0" border size="medium">不显示</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分类图标 :">
                    <el-upload
                            ref="uploadCategoryIcon"
                            :action="uploadUrl()"
                            list-type="picture-card"
                            :auto-upload="false"
                            accept="image/jpeg,image/png"
                            :file-list="categoryFileList"
                            :on-change="categoryPictureCardChange"
                            :on-success="categoryPictureCardSuccess"
                            :on-error="categoryPictureCardError"
                            :limit="1"
                            :on-exceed="categoryPictureCardExceed">
                        <i slot="default" class="el-icon-plus"></i>
                        <div slot="file" slot-scope="{file}">
                            <img
                                    class="el-upload-list__item-thumbnail"
                                    :src="file.url" alt=""
                            >
                            <span class="el-upload-list__item-actions">
                            <span
                                    class="el-upload-list__item-preview"
                                    @click="categoryPictureCardPreview(file)">
                                <i class="el-icon-zoom-in"></i>
                            </span>
                            <span
                                    v-if="!categoryPictureCardDisabled"
                                    class="el-upload-list__item-delete"
                                    @click="categoryPictureCardDownload(file)">
                                <i class="el-icon-download"></i>
                            </span>
                            <span
                                    v-if="!categoryPictureCardDisabled"
                                    class="el-upload-list__item-delete"
                                    @click="categoryPictureCardRemove(file)">
                                <i class="el-icon-delete"></i>
                            </span>
                        </span>
                        </div>
                    </el-upload>
                    <el-dialog :visible.sync="previewImageDialogVisible">
                        <img width="100%" :src="previewImageUrl" alt="">
                    </el-dialog>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="uploadFile">
                        <span v-show="!categoryForm.id">新增</span>
                        <span v-show="categoryForm.id">修改</span>
                    </el-button>
<!--                    <el-button @click="resetCategoryForm('categoryForm')">重置</el-button>-->
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "CategoryManagement",
        data() {
            return {
                showStatusSelect: '1',
                tableData: [
                    {
                        id: 1,
                        name: '无',
                        showStatus: 1,
                        icon: ''
                    }
                ],
                search: '',
                categoryDialogVisible: false,
                categoryForm: {
                    name: '',
                    showStatus: 1,
                    icon: '',
                },
                originCategoryIcon: '',
                categoryRules: {
                    name: [
                        { required: true, message: '请输入分类名称', trigger: 'blur' },
                    ],
                    showStatus: [
                        { required: true, message: '请选择分类显示状态', trigger: 'change' },
                    ],
                },
                categoryFileList: [],
                categoryPictureCardDisabled: false,
                previewImageUrl: '',
                previewImageDialogVisible: false,
            }
        },
        methods: {
            tableItemEdit(index, row) {
                // console.log("category edit");
                // console.log(index, row);
                let that = this;
                // 获取上架商品类别
                this.$axios.get("/admin/category", { params: { id: row.id } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("categoryForm");
                        // console.log(result.data);
                        that.categoryForm = result.data;
                        if(that.categoryForm.icon != '') {
                            that.categoryFileList = [
                                {
                                    name: '分类图标',
                                    url: that.categoryForm.icon,
                                }
                            ];
                        }
                        that.originCategoryIcon = that.categoryForm.icon;
                        that.categoryDialogVisible = true;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            tableItemDelete(index, row) {
                // console.log(index, row);
                this.deleteCategory(row.id);
            },
            handleCategoryClose() {
                console.log("handleCategoryClose");
                this.categoryDialogVisible = false;
                this.categoryForm = {
                    name: '',
                    showStatus: 1,
                    icon: '',
                };
                this.originCategoryIcon = '';
                this.categoryFileList = [];
                this.$refs.uploadCategoryIcon.clearFiles();
            },
            submitCategoryForm(formName) {
                // console.log("submit");
                let that = this;
                this.$refs[formName].validate((valid) => {
                    // console.log(that.categoryFileList);
                    if (valid) {
                        // console.log("categoryForm");
                        // console.log(that.categoryForm);
                        if(!that.categoryForm.id) {
                            // 新增商品类别
                            this.$axios.put("/admin/category", this.$qs.stringify(that.categoryForm), {
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
                                    if(that.showStatusSelect == '1') {
                                        that.showDisplayCategory();
                                    } else if(that.showStatusSelect == '0') {
                                        that.showHideCategory();
                                    }
                                    that.handleCategoryClose();
                                })
                                // .catch((error) => {
                                //     console.log(error);
                                // })
                        } else {
                            // 更改商品类别
                            this.$axios.post("/admin/category", this.$qs.stringify(that.categoryForm), {
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
                                    if(that.showStatusSelect == '1') {
                                        that.showDisplayCategory();
                                    } else if(that.showStatusSelect == '0') {
                                        that.showHideCategory();
                                    }
                                    that.handleCategoryClose();
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
            resetCategoryForm(formName) {
                this.$refs[formName].resetFields();
                console.log("resetFields");
                console.log(this.categoryForm);
                if(this.originCategoryIcon != '') {
                    this.categoryFileList = [
                        {
                            name: '分类图标',
                            url: this.originCategoryIcon,
                        }
                    ];
                } else {
                    this.categoryFileList = [];
                }
            },
            deleteCategory(categoryId) {
                let that = this;
                // 删除商品类别
                this.$axios.delete("/admin/category", { params: { id: categoryId } })
                    .then((response) => {
                        // console.log(response);
                        this.$message({
                            showClose: true,
                            message: '操作成功！',
                            type: 'success',
                        });
                        if(that.showStatusSelect == '1') {
                            that.showDisplayCategory();
                        } else if(that.showStatusSelect == '0') {
                            that.showHideCategory();
                        }
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            // 显示上架商品类别按钮点击事件回调函数
            showDisplayCategory() {
                this.showStatusSelect = '1';
                // console.log("showStatusSelect: " + this.showStatusSelect);
                let that = this;
                // 获取上架商品类别
                this.$axios.get("/admin/allcategory", { params: { flag: that.showStatusSelect } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.tableData = result.data;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },
            // 显示下架商品类别按钮点击事件回调函数
            showHideCategory() {
                this.showStatusSelect = '0';
                // console.log("showStatusSelect: " + this.showStatusSelect);
                let that = this;
                // 获取下架商品类别
                this.$axios.get("/admin/allcategory", { params: { flag: that.showStatusSelect } })
                    .then((response) => {
                        // console.log(response);
                        let result = response.data;
                        // console.log("tableData")
                        // console.log(result.data)
                        that.tableData = result.data;
                    })
                    // .catch((error) => {
                    //     console.log(error);
                    // })
            },

            // 文件上传
            uploadUrl(){
                return "https://jsonplaceholder.typicode.com/posts/";
            },
            uploadFile() {
                // console.log("sku image upload");
                if(this.categoryFileList.length == 0 || this.categoryFileList[0].url == this.originCategoryIcon) {
                    this.submitCategoryForm('categoryForm');
                } else {
                    this.$refs.uploadCategoryIcon.submit();
                }
            },
            categoryPictureCardChange(file, fileList) {
                // console.log("picture card change");
                // console.log(file, fileList);
                let flag = true;
                this.categoryFileList.forEach((item, index) => {
                    if (file.name == item.name) {
                        flag = false;
                    }
                })
                if(flag) {
                    this.categoryFileList.push({ name: file.name, url: file.url });
                }
            },
            categoryPictureCardSuccess(response, file, fileList) {
                console.log("picture card success");
                // console.log(response, file, fileList);
                if(fileList.length == this.categoryFileList.length) {
                    this.categoryForm.icon = this.categoryFileList[0].url;
                    this.submitCategoryForm('categoryForm');
                }
            },
            categoryPictureCardError(err, file, fileList) {
                console.log("picture card error");
                // console.log(err, file, fileList);
                this.$message.error(err);
            },
            categoryPictureCardPreview(file) {
                // console.log("picture card preview");
                // console.log(file);
                this.previewImageUrl = file.url;
                this.previewImageDialogVisible = true;
            },
            categoryPictureCardDownload(file) {
                // console.log("picture card download");
                // console.log(file);
                this.$message.error('无法下载！');
            },
            categoryPictureCardRemove(file, fileList) {
                // console.log("picture card remove");
                // console.log(file);
                this.categoryFileList.forEach((item, index) => {
                    if (file.name == item.name) {
                        this.categoryFileList.splice(index, 1);
                    }
                });
            },
            categoryPictureCardExceed(files, fileList) {
                this.$message({
                    type: "warning",
                    message: "最多上传1张",
                });
            },
        },
        created() {
            this.showDisplayCategory();
        }
    }
</script>

<style scoped>

    .table-image-slot {
        width: 50px;
        height: 50px;
        font-size: 20px;
        display:flex;
        justify-content:center;
        align-items:center;
    }

</style>