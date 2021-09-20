<template>
  <div>
    <div>
      <TopBar></TopBar>
      <SecBar></SecBar>
    </div>
    <div style="height: 100px"></div>
    <div>订单号: {{this.orderid}}</div>
    <div style="height: 100px"></div>
    <div>
      <el-button @click="pay(0)">支付宝支付</el-button>
      <el-button @click="pay(1)">微信支付</el-button>
      <el-button @click="pay(2)">银联支付</el-button>
    </div>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import SecBar from "@/views/inc/SecBar";
export default {
  name: "Pay",
  components: {
    TopBar,
    SecBar,
  },
  data() {
    return {
      orderid:'',
    }
  },
  methods: {
    // 获取url中的参数
    getParamInURL(){
      this.orderid = this.$route.query.orderid;
    },
    pay(pay_type){
      console.log("xx")
      //TODO：发送支付的请求
      let data = {
        payType: pay_type,
        orderId: this.orderid,
      }
      this.$axios.post('/order/pay', this.$qs.stringify(data), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res =>{
        console.log(res);
        this.$message.success("成功支付订单")
        this.$router.push('/order')
      })
    }
  },
  created() {
    this.getParamInURL();
  }
}
</script>

<style scoped>

</style>