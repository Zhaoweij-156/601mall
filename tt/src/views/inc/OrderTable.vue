<template>
    <div>
      <el-table
          ref="orderTable"
          :data="orderList"
          :span-method="arraySpanMethod"
          :cell-style="tableTdStyle"
          lazy
          :fit="false"
          empty-text="订单空空的，快去逛逛吧"


          :header-cell-style="{background:'#dcdfe6', color: 'black'}"
          @selection-change="handleSelectionChange"
      >


        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            label="宝贝"
            width="600px"
        >
          <div slot-scope="scope">
            <div style="width: 900px; height: 40px;background-color: #F2F6FC; border-bottom: 1px solid #E4E7ED; margin-left: -10px; padding-top: 10px; padding-left: 10px">
              订单号：{{ scope.row.orderSn }}
            </div>
            <div style="height: 30px"></div>
            <div v-for="(good, index) in scope.row.orderItems">
                <GoodItemInOrder
                    v-bind:good="good"
                    v-bind:status="scope.row.status"
                    v-bind:orderId="scope.row.id"
                    @updateDataTrigger="updateData()"
                ></GoodItemInOrder>
              <el-divider v-if="index != scope.row.orderItems.length-1"></el-divider>
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
            prop="skuCount"
            label="数量"
            width="80px">
        </el-table-column>

        <el-table-column
            label="商品操作"
            width="100px">
          <div slot-scope="scope">
          </div>
        </el-table-column>
        <el-table-column
            label="实付款"
            width="120px"
        >
          <div slot-scope="scope">
            <span style="color: red; font-weight: bold">￥{{scope.row.payAmount}}</span>
          </div>
        </el-table-column>

        <el-table-column
            label="交易状态"
            width="100px">
          <div slot-scope="scope">
<!--            {{scope.row.status}}-->
              {{ orderStatusEnum(scope.row.status)}}
            <div v-if="scope.row.status == 0">
              <el-link style="font-size: 13px;" @click="goPay(scope.row.id)">去付款</el-link>
            </div>
            <div v-if="scope.row.status == 2">
              <el-link style="font-size: 13px;">确认收货</el-link>
            </div>
            <div v-if="scope.row.status == 4">
              <el-link style="font-size: 13px;" >退款成功</el-link>
            </div>
            <div>
              <el-link style="font-size: 13px;" @click="goOrderDetail(scope.row.id)">订单详情</el-link>
            </div>
          </div>
        </el-table-column>
        <el-table-column
            label="操作"
            width="130">
          <div slot-scope="scope" >
            <div>
            <el-link v-if="scope.row.status === 0" style="font-size: 13px;" @click="cancelOrder(scope.row.id)"><i class="el-icon-warning-outline"></i>取消订单</el-link>
            </div>
            <div>
            <el-link v-if="scope.row.status !== 0"style="font-size: 13px;" @click="deleteOrder(scope.row.id)"><i class="el-icon-delete"></i>删除</el-link>
            </div>
          </div>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>
import GoodItemInOrder from "@/views/inc/GoodItemInOrder";
export default {
  name: "OrderTable",
  components: {
    GoodItemInOrder,
  },
  props: ['orderList'],
  computed: {

  },
  data() {
    return {

    }
  },
  methods:{
    updateData(){
      this.$emit('dataUpdateTrigger')
    },
    //TODO：有效性待考证
    arraySpanMethod({ row, column, rowIndex, columnIndex }){
        if (columnIndex === 1){
          return [1,4];
        }
        else if (columnIndex < 5 && columnIndex>0){
          return [0,0];
        }
    },
    deleteOrder(orderId){
      //TODO:发送删除订单的请求
      this.$confirm('将删除此订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          id: orderId
        }
        this.$axios.post('/order/del', this.$qs.stringify(data), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          console.log(res)
          //通知父组件更新数据
          this.$emit('dataUpdateTrigger')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });

    },
    tableTdStyle({row, rowIndex}){
      let styleJson = {}
      styleJson = {
        'border': '1px solid',
        'border-color': '#ececec'
      }
      return styleJson
    },
    orderStatusEnum(status) {
      const statusEnum = [
        '等待付款', '等待商家发货', '包裹正在运输', '交易完成'
      ]
      return statusEnum[status];
    },
    cancelOrder(id){
      //TODO：发送取消订单的请求
      this.$confirm('将删除此订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          orderId: id
        }
        this.$axios.post('/order/cancel', this.$qs.stringify(data), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res =>{
          console.log(res)
          this.$message.success("取消成功")
          this.$emit('dataUpdateTrigger')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      });
    },
    goPay(orderId){
      this.$router.push({path: 'pay', query: {orderid: orderId}});
    },
    goOrderDetail(orderId){
      this.$router.push({path: 'orderDetail', query: {orderid: orderId}});
    }

  }
}
</script>

<style scoped>

/*/deep/ .el-table tbody tr:hover>td { background-color: #fcfcff ;}*/

/deep/ .el-table tbody tr > td { padding-top: 0px}
/deep/ .el-table__body {
  border-collapse:separate;
  border-spacing:0px 50px;
}

</style>