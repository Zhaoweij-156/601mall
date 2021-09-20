<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="所有订单" name="first">
        <div style="padding-left: 30px; padding-right: 27px">
          <OrderTable v-bind:order-list="this.allOrderList" v-on:dataUpdateTrigger="getAllOrder()"></OrderTable>
        </div>
        <div class="text-center w-100">
          <div class="block">
            <el-pagination
                @current-change="getOrderListWithCurrentPage"
                background
                :current-page.sync="param.current_page"
                :page-size="param.pageSize"
                layout="prev, pager, next"
                :total="param.total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="待付款" name="second" lazy >
        <div style="padding-left: 30px; padding-right: 27px">
          <OrderTable v-bind:order-list="this.allOrderList" v-on:dataUpdateTrigger="getAllOrder()"></OrderTable>
        </div>
        <div class="text-center w-100">
          <div class="block">
            <el-pagination
                @current-change="getOrderListWithCurrentPage"
                background
                :current-page.sync="param.current_page"
                :page-size="param.pageSize"
                layout="prev, pager, next"
                :total="param.total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="待发货" name="third" lazy>
        <div style="padding-left: 30px; padding-right: 27px">
        <OrderTable v-bind:order-list="this.allOrderList" v-on:dataUpdateTrigger="getAllOrder()"></OrderTable>
      </div>
        <div class="text-center w-100">
          <div class="block">
            <el-pagination
                @current-change="getOrderListWithCurrentPage"
                background
                :current-page.sync="param.current_page"
                :page-size="param.pageSize"
                layout="prev, pager, next"
                :total="param.total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="待收货" name="fourth" lazy>
          <div style="padding-left: 30px; padding-right: 27px">
            <OrderTable v-bind:order-list="this.allOrderList" v-on:dataUpdateTrigger="getAllOrder()"></OrderTable>
          </div>
        <div class="text-center w-100">
          <div class="block">
            <el-pagination
                @current-change="getOrderListWithCurrentPage"
                background
                :current-page.sync="param.current_page"
                :page-size="param.pageSize"
                layout="prev, pager, next"
                :total="param.total">
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="秒杀订单" name="fifth" lazy>秒杀订单</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import OrderTable from "@/views/inc/OrderTable";
export default {
  name: "Order",
  components:{
    OrderTable,
  },
  data() {
    return {
      param: {
        current_page: 1,
        pageSize: 7,
        total: 100,
      },
      tabActive: '-1',
      activeName: 'first',
      allOrderList: [],
      orderNoPayList: [],
      orderNoDeliverList: [],
      orderNoReceiveList: [],
      secKillOrderList: []
    }
  },
  methods: {
    getOrderListWithCurrentPage() {
      this.getAllOrder();
    },
    resetPageParam(){
      this.param.current_page = 1;
      this.param.pageSize = 7;
      this.param.total = 120;
    },
    handleClick(tab, event){
      // console.log(tab);
      if (tab.index === "0"){
        //标识当前开启的tab，方便收到子组件传来的更新请求还有用
        this.tabActive = "-1";
      }
      else if (tab.index === "1"){
        this.tabActive = "0";
      }
      else if (tab.index === "2"){
        this.tabActive = "1";
      }
      else if (tab.index === "3"){
        this.tabActive = "2";
      }
      else if (tab.index === "4"){

      }
      //清空已有数据，因为如果没有订单，就会返回错误，然后显示上一个tab的数据
      this.allOrderList = []
      //重置分页控制数据
      this.resetPageParam()
      //发送获取请求
      this.getAllOrder()
    },
    getAllOrder(){
      let data = {
        status: this.tabActive,
        pageSize: this.param.pageSize,
        page: this.param.current_page
      }
      this.$axios.get('/order/getList', {
        params: data,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.allOrderList = res.data.data;
        console.log(res);
      })
      //TODO：获取所有订单
    },
  },
  created() {
    this.getAllOrder()
  }
}
</script>

<style scoped>

</style>