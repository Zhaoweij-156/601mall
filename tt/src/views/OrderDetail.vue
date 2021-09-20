<template>
  <div>
    <div>
      <TopBar></TopBar>
      <SecBar></SecBar>
    </div>
    <div style="padding-left: 18%; padding-right: 18%">
      <div style="height: 50px"></div>
      <div>
        <div style="text-align: left">
          <span style="font-size: 20px; font-weight: bold">订单状态</span></div>
        <div>
          <div style="height: 50px"></div>
          <el-steps v-if="this.orderData.order.status<4" :active="this.orderData.order.status+1" align-center >
            <el-step title="拍下商品" :description=orderData.order.createTime></el-step>
            <el-step title="付款" :description=orderData.order.payTime></el-step>
            <el-step title="卖家发货" :description=orderData.order.deliveryTime></el-step>
            <el-step title="确认收货" :description=orderData.order.receiveTime></el-step>
          </el-steps>
        </div>
      </div>
      <div style="height: 30px"></div>
      <div>
        <el-card>
          <div class="inline_block_top" style="width: 300px; border: 1px solid #dcdfe6; height: 200px">
            <div>
              <sapn>订单信息</sapn>
            </div>
            <div style="height: 10px"></div>
            <div>
              <el-descriptions :column="1">
                <el-descriptions-item label="收货地址">{{ this.orderData.order.receiverName }}; {{ this.orderData.order.receiverPhone }}; {{ this.orderData.order.receiverProvince }} {{ this.orderData.order.receiverCity }} {{ this.orderData.order.receiverRegion }} {{ this.orderData.order.receiverDetailAddress }}{{this.orderData.order.receiverPostCode}}</el-descriptions-item>
                <el-descriptions-item label="订单编号">{{ this.orderData.order.orderSn }}</el-descriptions-item>
                <el-descriptions-item label="支付方式">{{ this.payTypeEnum[this.orderData.order.payType] }}
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
          <div class="inline_block_top" style="width: 850px; border: 1px solid #dcdfe6; height: 200px">
            <div style="height: 100px">
              <sapn>订单状态：{{ this.orderStatusEnum[this.orderData.order.status] }}</sapn>
            </div>
            <div style="padding-left: 100px; padding-right: 100px">
              <el-descriptions :column="1">
                <el-descriptions-item label="配送方式" span="3">{{ this.orderData.order.deliverCompany }}</el-descriptions-item>
                <el-descriptions-item label="物流编号">{{ this.orderData.order.deliverSn }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </el-card>
      </div>
      <div style="height: 30px"></div>
      <div>
        <el-card>
          <div>
            <el-table
                ref="orderItemTable"
                :data="orderData.orderItemList"
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
                    <span style="font-size: 10px">{{ scope.row.spuName }}</span>
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
                  <span style="font-weight: bold">￥{{ scope.row.skuPresentPrice }}</span>
                </div>
              </el-table-column>

              <el-table-column
                  prop="skuCount"
                  label="数量"
                  width="120px">
              </el-table-column>
            </el-table>
          </div>
        </el-card>
        <div style="height: 30px"></div>
        <el-card>
          <div>
            <div style="float: left; width: 300px;">
              <div style="text-align: left">
              <span style="font-size: 14px; margin-left: 30px">订单备注：{{this.orderData.order.note}}</span>
              </div>
            </div>
            <div style="width: 200px; float: right">
              <el-descriptions :column="1">
                <el-descriptions-item label="商品总价">￥{{ orderData.order.totalAmount }}</el-descriptions-item>
                <el-descriptions-item label="快递费">￥0</el-descriptions-item>
                <el-descriptions-item label="实付款">￥{{ orderData.order.payAmount }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import SecBar from "@/views/inc/SecBar";

export default {
  name: "OrderDetail",
  components: {
    TopBar,
    SecBar,
  },
  data() {
    return {
      orderid: '',
      payTypeEnum: [
        '支付宝支付', '微信支付', '银联支付'
      ],
      orderStatusEnum: [
        '等待付款', '等待商家发货', '包裹正在运输', '交易完成', '已退款'
      ],
      orderData: {},
    }
  },
  methods: {
    getOrderDetail(order_id){
      //TODO:发送获取订单详情的请求
      let data = {
        id: order_id
      }
      this.$axios.get('/order/getInfo',{
        params:data,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.orderData = res.data.data
        console.log(res)
      })
    },
    // 获取url中的参数
    getParamInURL(){
      this.orderid = this.$route.query.orderid;
      console.log("当前:" + this.orderid)
    },
  },
  created() {
    this.getParamInURL();
    this.getOrderDetail(this.orderid)
  }

}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}
</style>