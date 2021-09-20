<template>
  <div>
    <TopBar></TopBar>
    <SecBar></SecBar>
    <div style="padding-left: 18%; padding-right: 18%">
      <div name="addressConfirm" style="margin-top: 50px;">
        <div class="inline_block_top" style="text-align: left; width: 50%">
          <span style="font-size: 20px; font-weight: bold">确认收货地址</span>
        </div>
        <div class="inline_block_top" style="width: 50%; text-align: right">
          <el-button style="width: 150px; border: 0" size="medium" @click="toAddressManager()">管理地址</el-button>
        </div>
        <hr>
        <div style="text-align: left">
          <div style="display: block; padding-top: 10px;" v-for="(address, index) in addressList">
            <div class="inline_block_top" style="width: 6%; text-align: left; padding-top: 8px">
              <span v-if="amISelected(address.id, addressSelectedId)">寄送到</span>
            </div>
            <div class="inline_block_top" style="width: 76%">
            <el-radio
                :class="[amISelected(address.id, addressSelectedId) ? 'font_bold' : '']"
                :key="index"
                v-model="addressSelectedId"
                :label="address.id"
                border
                @change="selectAddress"
            >
              <span>{{ address.province }} {{ address.city }} {{ address.region }} {{
                  address.detailAddress
                }} ({{ address.name }}&nbsp; 收) {{ address.phone }}</span>
            </el-radio>
            <span v-if="address.defaultStatus">默认地址</span>
            </div>
            <div class="inline_block_top" style="width: 200px; text-align: right">
              <el-button
                  size="medium"
                  style="border:0"
                  v-if="amISelected(address.id, addressSelectedId)"
                  @click="openEditAddressDrawer()"
              >修改本地址</el-button>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 50px"></div>
      <div name="cartItemConfirm">
        <div style="text-align: left">
          <span style="font-size: 20px; font-weight: bold">确认订单信息</span>
        </div>
        <hr>
        <div>
          <el-table
              ref="cartTable"
              :data="cartItemsSelected"
              border
              lazy
              style="width: 100%"
              :header-cell-style="{background:'#dcdfe6', color: 'black'}"
          >
            <el-table-column
                prop="name"
                label="商品信息"
            >
              <div slot-scope="scope">
                <div class="inline_block_top" style="width: 100px">
                  <el-image
                      :src="scope.row.skuImg"
                      style="width: 100px; height: 100px"
                  >
                  </el-image>
                </div>
                <div class="inline_block_top" style="width: 20px"></div>
                <div class="inline_block_top" style="width: 250px">
                  <span style="font-size: 10px">{{scope.row.spuName}}</span>
                </div>
                <div class="inline_block_top" style="width: 50px"></div>
                <div class="inline_block_top" style="width: 250px">
                  <span style="font-size: 10px; color: #909399">{{ scope.row.skuName }}</span>
                </div>
              </div>
            </el-table-column>

            <el-table-column
                label="单价"
                width="120px"
            >
              <div slot-scope="scope">
                <span style="font-weight: bold">￥{{scope.row.skuPresentPrice}}</span>
              </div>
            </el-table-column>

            <el-table-column
                label="数量"
                prop="skuCount"
                width="120px">
            </el-table-column>

            <el-table-column
                label="小计"
                width="150px"
            >
              <div slot-scope="scope">
                <span style="color: red; font-weight: bold">￥{{line_total(scope.row.skuPresentPrice, scope.row.skuCount, scope.row.index)}}</span>
              </div>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div style="height: 50px"></div>
      <div>
        <div class="inline_block_top" style="width: 50%; text-align: left">
          <div class="inline_block_top" style="width: 100px">
            <span>给卖家留言</span>
          </div>
          <div class="inline_block_top" style="width: 300px">
            <el-input
                type="textarea"
                v-model="note"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="选填，请先和商家协商一致"
                label="给卖家留言"
            >
            </el-input>
          </div>
        </div>
        <div class="inline_block_top" style="width: 50%; text-align: right">
          <div>
            运送方式：普通配送 快递 免邮
          </div>
          <div style="height: 10px"></div>
          <div>
            实付：<span style="color: red; font-weight: bold;">￥{{getSummaries}}</span>
          </div>
        </div>
      </div>
      <div style="height: 20px"></div>
      <div style="text-align: right;display: block">
        <div style=" float: right;">
          <div style=" border: 1px solid;width: 400px;">
          <div  style=" margin-right: 10px; margin-top: 20px; margin-bottom: 30px">
            <div style="text-align: right;">
              <span style="font-weight: bold">实付款:</span><span style="color: red; font-size: 34px; font-weight: bold">￥{{ getSummaries }}</span>
            </div>
            <div style="height: 10px"></div>
            <div>
              <span style="font-weight: bold">寄送至：</span><span>{{ addressSelected.province }} {{ addressSelected.city }} {{ addressSelected.region }} {{ addressSelected.detailAddress }} </span>
            </div>
            <div style="height: 10px"></div>
            <div>
              <span style="font-weight: bold">收件人：</span>{{ addressSelected.name }} {{ addressSelected.phone }}
            </div>
          </div>
          </div>
            <div style="width: 200px; text-align: right; float: right">
              <div>
              <el-button style="width: 200px; height: 50px; border-radius: 0" type="danger" @click="submitOrder()">提交订单</el-button>
              </div>
            </div>
        </div>
      </div>
    </div>
    <el-drawer
        title="修改地址"
        :visible.sync="editAddressDrawer"
        :before-close="handleClose">
      <div style="padding-left: 20px">
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
    </el-drawer>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import SecBar from "@/views/inc/SecBar";

export default {
  //注意逻辑，特别是axios的异步请求问题
  name: "OrderConfirm",
  components: {
    TopBar,
    SecBar,
  },
  data() {
    return {
      //循环询问的标志位
      askOrderStatusFlag: true,
      //订单中的商品
      cartItemsSelected: [],
      cartItemSelectedId: [],
      //获取到的所有地址
      addressList: [],
      //目前选中的地址的id
      addressSelectedId: '',
      //目前选中的地址
      addressSelected: null,
      //订单的备注
      note: '',
      //每个商品的小计
      sumEachLine:[],
      //总共付款
      total_all:0,
      //开启编辑地址的flag
      editAddressDrawer: false,
      //编辑的地址其实也就是选中的地址
      //编辑的地址id
      editAddressId: '',
      //编辑的地址数据
      editAddressForm: {
        id: '',
        name: '',
        phone: '',
        postCode: '',
        province: '',
        city: '',
        region: '',
        detailAddress: '',
        defaultStatus: 0,
        defaultStatusFlag: false,
      },
      //记录可能成功后返回的orderid
      orderId:'',
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
  computed: {
    getSummaries() {
      //TODO:合计
      this.total_all= 0;
      this.cartItemsSelected.forEach(cartItem => {this.total_all+=cartItem.skuCount * cartItem.skuPresentPrice});
      return this.total_all
    },

  },
  methods: {
    line_total(price, num, index){
      this.sumEachLine[index] = price*num;
      return price*num;
    },
    getCartItem() {
      this.cartItemsSelected = JSON.parse(sessionStorage.getItem("cartItemsSelected"))
      for (let i = 0; i < this.cartItemsSelected.length; ++i)
      {
        this.cartItemSelectedId[i] = this.cartItemsSelected[i].id;
      }
    },
    init() {
      for (let i = 0; i < this.addressList.length; ++i) {
        if (this.addressList[i].defaultStatus) {
          this.addressSelectedId = this.addressList[i].id;
          break;
        }
      }
    },
    getAddressList() {
      this.$axios.get('/user/address/getList',).then(res => {
        this.addressList = res.data.data;
        console.log(res.data.data)
        this.init();
        this.selectAddress(this.addressSelectedId);
      })
    },
    selectAddress(label) {
      for (let i = 0; i < this.addressList.length; ++i) {
        if (this.addressList[i].id === label) {
          this.addressSelected = this.addressList[i];
          break;
        }
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    toAddressManager() {
      this.$router.push('/address');
    },
    amISelected(id, sId){
      if (id === sId)
      {
        return true;
      }
      return false;
    },
    openEditAddressDrawer(){
      this.editAddressForm = JSON.parse(JSON.stringify(this.addressSelected));
      if (this.addressSelected.defaultStatus ===1) this.editAddressForm.defaultStatusFlag = true
      this.editAddressDrawer = true;
    },
    handleClose(done) {
      this.$confirm('尚未保存，是否不保存退出？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    closeDrawer(){
      this.editAddressDrawer = false;
    },
    submitOrder(){

      let data = {
        cartIds: this.cartItemSelectedId,
        addressId: this.addressSelectedId,
        note: this.note
      }
      this.$axios.post('/order/add', this.$qs.stringify(data,{ indices: false }), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        console.log(res)
        let orderGenerateId = res.data.data;
        this.waitForOrderGeneration(orderGenerateId)
      })
      //返回成功后
      // this.goPay(123);
    },

    waitForOrderGeneration(orderGenerateId) {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.askOrderStatusFlag = true;
      this.askOrderGenerationStatus(orderGenerateId, loading)
      setTimeout(() => {
        if (this.askOrderStatusFlag){
          this.$message.error('服务器繁忙，请稍后查看订单具体情况！');
          this.askOrderStatusFlag = false;
          loading.close();
        }
      }, 10000);


    },
    //循环订单是否成功提交
    askOrderGenerationStatus(id, loading) {
      if (!this.askOrderStatusFlag) return;
      let data = {
        id: id
      }
      this.$axios.get('/order/status', {
        params: data,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        console.log(res)
        //FIXME:此处使用的code需要改进
        let code = 400;
        code = res.data.data.code;
        console.log(code)
        if (code === 0){
          console.log("查询成功，无事发生，1s后继续下一个查询")
          setTimeout(()=>{
            this.askOrderGenerationStatus(id, loading)
            }, 1000)
          //查询成功，无事发生，1s后继续下一个查询
        }
        else if (code === 200){
          console.log("查询成功，订单生成成功，销毁循环，前往支付")
          //查询成功，订单生成成功，销毁循环，前往支付
          sessionStorage.removeItem("cartItemsSelected")
          this.askOrderStatusFlag = false;
          loading.close()
          this.$message.success("提交订单成功，自动跳转支付页面")
          this.$router.push({path: 'pay', query: {orderid:res.data.data.id}});
          //TODO：去支付
        }
        else if (code === 400){
          console.log("查询成功，订单生成失败，销毁循环")
          //查询成功，订单生成失败，销毁循环
          this.askOrderStatusFlag = false;
          loading.close()
          this.$message.error("服务器繁忙，订单提交失败，请稍后再试！")
        }
        else {
          console.log("未知错误")
          //未知错误
          this.askOrderStatusFlag = false;
          loading.close()
          sessionStorage.removeItem("cartItemsSelected")
          this.$message.error("未知错误，请稍后再试！")
        }
      })
    },
    goPay(order_sn){
      this.$router.push({path: 'pay', query: {ordersn: order_sn}});
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (formName === 'editAddressForm') {
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
                this.closeDrawer();
              })
              this.getAddressList();
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
  },
  created() {
    this.getCartItem();
    this.getAddressList();
  }

}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}

.font_bold {
  font-weight: bold;
}
</style>