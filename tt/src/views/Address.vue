<template>
  <div style="height: 100vh; margin-left: 30px;">
    <div style="text-align: left;">
      <el-row>
        <el-col :span="2">
      <h3 style="margin-top: 10px">地址管理</h3>
        </el-col>
        <el-col :span="1">
      <el-button size="mini"
                 type="danger"
                 @click="switchToAddNewAddress()"
                 v-bind:class="isEditingAddress ? 'openging' : 'closing'"
                 style="margin-top: 10px"
                >
        新增收获地址
      </el-button>
        </el-col>
      </el-row>
    </div>
    <el-row>
      <el-col :span="12">
        <div id="newAddress" v-bind:class="[this.isAddingAddress ? 'opening' : 'closing']">
        <div style="width: 50%; text-align: left">
        <div style="margin-bottom: 15px"><span style="color: orangered;">新增收货地址</span></div>
        <el-form label-position="left" :model="addressForm" :rules="rules" ref="addressForm" label-width="100px"
                 class="demo-ruleForm" style="width: 50vh; ">
          <el-form-item label="地址" required>
            <el-col :span="6">
              <el-form-item prop="province">
                <el-input v-model="addressForm.province" placeholder="省/直辖市"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" class="line">省</el-col>
            <el-col :span="6">
              <el-form-item prop="city">
                <el-input v-model="addressForm.city" placeholder="城市"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" class="line">市</el-col>
            <el-col :span="6">
              <el-form-item prop="region">
                <el-input v-model="addressForm.region" placeholder="区"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2" class="line">区</el-col>
          </el-form-item>
          <el-form-item label="邮政编码" prop="postCode">
            <el-input v-model="addressForm.postCode" placeholder="请输入邮政编码"></el-input>
          </el-form-item>
          <el-form-item label="详细地址" prop="detailAddress">
            <el-input type="textarea" :autosize="{minRows:2}" v-model="addressForm.detailAddress"
                      placeholder="请输入详细地址,如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
          </el-form-item>
          <el-form-item label="收货人姓名" prop="name">
            <el-input v-model="addressForm.name" placeholder="请输入收件人姓名"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="addressForm.phone" placeholder="请输入手机号码"></el-input>
          </el-form-item>
          <el-form-item style="text-align: left; margin-bottom: 0">
            <el-button type="primary" size="mini" @click="submitForm('addressForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
      </el-col>
      <el-col :span="12">
        <div id="editAddress" v-bind:class="[isEditingAddress ? 'opening' : 'closing']">
          <div style="width: 50%; text-align: left">
            <div style="margin-bottom: 15px"><span style="color: orangered;">修改收货地址</span></div>
            <el-form label-position="left" :model="editAddressForm" :rules="rules" ref="editAddressForm" label-width="100px"
                     class="demo-ruleForm" style="width: 50vh; ">
              <el-form-item label="地址" required>
                <el-col :span="6">
                  <el-form-item prop="province">
                    <el-input v-model="editAddressForm.province" placeholder="省/直辖市"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="line">省</el-col>
                <el-col :span="6">
                  <el-form-item prop="city">
                    <el-input v-model="editAddressForm.city" placeholder="城市"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="line">市</el-col>
                <el-col :span="6">
                  <el-form-item prop="region">
                    <el-input v-model="editAddressForm.region" placeholder="区"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="2" class="line">区</el-col>
              </el-form-item>
              <el-form-item label="邮政编码">
                <el-input v-model="editAddressForm.postCode" placeholder="请输入邮政编码"></el-input>
              </el-form-item>
              <el-form-item label="详细地址" prop="detailAddress">
                <el-input type="textarea" :autosize="{minRows:2}" v-model="editAddressForm.detailAddress"
                          placeholder="请输入详细地址,如道路、门牌号、小区、楼栋号、单元等信息"></el-input>
              </el-form-item>
              <el-form-item label="收货人姓名" prop="name">
                <el-input v-model="editAddressForm.name" placeholder="请输入收件人姓名"></el-input>
              </el-form-item>
              <el-form-item label="手机号码" prop="phone">
                <el-input v-model="editAddressForm.phone" placeholder="请输入手机号码"></el-input>
              </el-form-item>
              <el-form-item style="text-align: left; margin-bottom: 0">
                <el-checkbox label="是否设置为默认地址" v-model="editAddressForm.defaultStatusFlag"></el-checkbox>
              </el-form-item>
              <el-form-item style="text-align: left; margin-bottom: 0">
                <el-button type="primary" size="mini" @click="submitForm('editAddressForm')">保存</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>
    <div style="height: 70px; text-align: left">
      <div style="padding-top: 20px">
        <i class="el-icon-warning-outline pure_muted" style="font-size: 30px"></i>
        <span style="margin-left: 10px" class="pure_muted">你保存了{{ this.addressNum}}条地址</span>
      </div>
    </div>
    <div id="addressList" style="margin-right: 50px">
      <el-table
          :data="addressList"
          border
          lazy
          empty-text="没有设置收件地址"
          style="width: 100%"
          :header-cell-style="{background:'#dcdfe6', color: 'black'}"
      >
        <el-table-column
            label="序号"
            type="index"
            >
        </el-table-column>
        <el-table-column
            prop="name"
            label="收货人"
            width="110">
        </el-table-column>

        <el-table-column
            label="所在地区">
          <div slot-scope="scope">
            <span>{{scope.row.province}}/{{scope.row.city}}/{{scope.row.region}}</span>
          </div>
        </el-table-column>

        <el-table-column
            prop="detailAddress"
            label="详细地址"
            width="300">
        </el-table-column>

        <el-table-column
            prop="postCode"
            label="邮编"
            width="90">
        </el-table-column>

        <el-table-column
            prop="phone"
            label="电话/手机号码">
        </el-table-column>

        <el-table-column
            label="操作"
            width="130">
          <div slot-scope="scope">
<!--            <span class="my_muted" @click="editAddress(scope.row.id)">编辑</span>-->
            <el-link style="font-size: 13px" @click="editAddress(scope.row.id)"><i class="el-icon-edit"></i>编辑</el-link>
            <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
<!--            <span class="my_muted" @click="deleteAddress(scope.row.id)">删除</span>-->
            <el-link style="font-size: 13px" @click="deleteAddress(scope.row.id)">删除<i class="el-icon-delete"></i></el-link>
          </div>
        </el-table-column>
        <el-table-column
            prop="defaultStatus"
            label="">
          <template slot-scope="scope">
            <el-button v-if="scope.row.defaultStatus" type="primary" size="medium" class="not_button" plain>默认地址</el-button>
            <el-link v-else @click="setAddressAsDefault(scope.row.id)" style="font-size: 13px">设为默认</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import Qs from "qs";

export default {
  name: "Address",
  data() {
    return {
      addressNum: 0,
      isAddingAddress: true,
      isEditingAddress: false,
      addressList: [
        // {
        //   id:'1',
        //   name: '陈泽飚',
        //   phone: '111111111',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: '菜鸟驿站',
        //   defaultStatus: true
        // },
        // {
        //   id:'2',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },
        // {
        //   id:'3',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },
        // {
        //   id:'4',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },
        // {
        //   id:'5',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },
        // {
        //   id:'6',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },
        // {
        //   id:'7',
        //   name: 'dsa',
        //   phone: '123321231',
        //   postCode: '000000',
        //   province: '广东省',
        //   city: '佛山市',
        //   region: '南海区',
        //   location: 'q12321321',
        //   detailAddress: 'oignregeroignregeroignreger',
        //   defaultStatus: false
        // },

      ],
      addressForm: {
        name: '',
        phone: '',
        postCode: '',
        province: '',
        city: '',
        region: '',
        detailAddress: '',
      },
      editAddressForm: {
        id: '',
        name: '',
        phone: '',
        postCode: '',
        province: '',
        city: '',
        region: '',
        detailAddress: '',
        defaultStatusFlag: false,
        defaultStatus: 0,
      },
      rules: {
        name: [{required: true, message: "收货人姓名不能为空", trigger: "blur"}],
        phone: [{required: true, message: "手机号码不能为空", trigger: "blur"}],
        detailAddress: [{required: true, message: "详细地址不能为空", trigger: "blur"}],
        province: [{required: true, message: "不能为空", trigger: "blur"}],
        city: [{required: true, message: "不能为空", trigger: "blur"}],
        region: [{required: true, message: "不能为空", trigger: "blur"}]
      },
    }
  },
  methods: {
    ToLog(msg) {
      console.log(msg);
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === 'addressForm') {
            this.$confirm('将新增收货地址, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$axios.post('/user/address/add', this.addressForm).then(res => {
                console.log(res)
                this.resetForm('addressForm')
                this.getAddressList();
              })
              this.getAddressList();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消操作'
              });
            });
          }
          else if (formName === 'editAddressForm') {
            this.$confirm('将修改此收货地址, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if (this.editAddressForm.defaultStatusFlag) this.editAddressForm.defaultStatus = 1
              this.$axios.post('/user/address/mod', this.editAddressForm).then(res => {
                console.log(res)
                this.resetForm('editAddressForm')
                this.getAddressList();
              })
              this.getAddressList();
              this.switchToAddNewAddress();
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消操作'
              });
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getAddressList() {
      this.$axios.get('/user/address/getList').then(res => {
        this.addressList = res.data.data;
        if (undefined !== res.data.data) this.addressNum = res.data.data.length;
        console.log(res.data);
      })
    },
    editAddress(id) {
      for (let i = 0; i < this.addressList.length; i++) {
        if (this.addressList[i].id === id){
            this.editAddressForm = JSON.parse(JSON.stringify(this.addressList[i]));
            if (this.editAddressForm.defaultStatus === 1) this.editAddressForm.defaultStatusFlag = true
            break;
        }
      }
      this.switchToEditAddress();
    },
    switchToEditAddress() {
      this.isAddingAddress = false;
      this.isEditingAddress = true;
    },
    switchToAddNewAddress() {
      this.isAddingAddress = true;
      this.isEditingAddress = false;
    },
    setAddressAsDefault(id){
      this.$confirm('将此地址设为默认地址, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          id: id
        }
        this.$axios.post('/user/address/setDef', this.$qs.stringify(data), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          console.log(res)
          this.getAddressList();
        })
        this.getAddressList();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    deleteAddress(addressId)
    {
      this.$confirm('将此地址删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          id: addressId
        }
        this.$axios.post('/user/address/del', Qs.stringify(data), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res =>{
          console.log(res)
          this.getAddressList();
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
      //TODO
    }
  },
  created(){
    this.getAddressList();
  }
}
</script>

<style scoped>
.opening {
  visibility: visible;
}

.closing {
  visibility: hidden;
}


.not_button {
  pointer-events: none;
}


.my_muted {
  color: #6c757d !important;
  font-size: 13px;
}

.pure_muted {
  color: #6c757d !important;
}

.my_muted:hover {
  text-decoration: underline;
  color: #409EFF !important;
  cursor: pointer !important;
}

.my_muted.active {
  color: #409EFF !important;
  text-decoration: underline;
}

</style>