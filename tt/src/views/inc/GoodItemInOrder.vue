<template>
  <div>
    <div style="text-align: right">
      <div class="inline_block_top" style="width: 580px; text-align: left;">
        <div name="img" class="inline_block_top" style="width: 100px">
          <el-image
              :src="good.skuImg"
              style="width: 100px; height: 100px"
          >
          </el-image>
        </div>
        <div name="padding" class="inline_block_top" style="width: 10px"></div>
        <div name="name" class="inline_block_top" style="width: 180px">
          {{ good.spuName }}
        </div>
        <div name="padding" class="inline_block_top" style="width: 20px"></div>
        <div name="name" class="inline_block_top" style="width: 250px">
          <span style="font-size: 10px; color: #909399">{{ good.skuName }}</span>
        </div>
      </div>
      <div name="price" class="inline_block_top" style="width: 120px; text-align: left">
        ￥{{ good.skuPresentPrice }}
      </div>
      <div name="num" class="inline_block_top" style="width: 80px; text-align: left">
        {{ good.skuCount }}
      </div>
      <div name="operation" class="inline_block_top" style="width: 90px; text-align: left">
        <div v-if="status != 0 && status < 4">
          <el-link style="font-size: 13px;" @click="goReturnGood(good.skuId, good)">退货</el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GoodItemInOrder",
  props: ['good', 'status', 'orderId'],
  data() {
    return {
      // good: {
      //   img: null,
      //   name: '适用格力空调遥控器全部美的万能通用款原装原厂TCL奥克斯海尔',
      //   price: 16.8,
      //   num: 1,
      // },
      // goods: [
      //   {
      //     name: '适用格力空调遥控器全部美的万能通用款原装原厂TCL奥克斯海尔',
      //     price: 16.8,
      //     num: 1,
      //   },
      //   {
      //     name: '湖南特产翻天娃薯条素香蹄筋辣条 8090儿时怀旧麻辣面筋零食小吃',
      //     price: 6.5,
      //     num: 1,
      //   }
      // ],
    }
  },
  methods: {
    goReturnGood(sku_id, good) {
      //尚未完成的部分，暂时直接退款吧
      // //sessionStorage
      // sessionStorage.setItem("goodToBeReturned", JSON.stringify(good));
      // //跳转退货页面
      // this.$router.push({path: '/returnGood', query: {sku_id: sku_id, orderId: this.orderId}});
      //直接退款
      //TODO:退款请求
      this.$confirm('将对整个订单进行退款, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          orderId: this.orderId
        }
        this.$axios.post('/order/refund', this.$qs.stringify(data), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          console.log(res)
          this.$message.info("申请退款")
          this.$emit("updateDataTrigger")
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      })
    }
  }

}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}


</style>