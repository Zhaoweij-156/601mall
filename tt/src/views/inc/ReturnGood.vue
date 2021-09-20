<template>
  <div>
    <div>
      <TopBar></TopBar>
      <SecBar></SecBar>
    </div>
    <div style="margin-left: 18%; margin-right: 18%">
      <div style="height: 30px"></div>
      <div style="text-align: left">
        <span style="font-size: 20px; font-weight: bold">退货商品</span>
      </div>
      <hr>
      <div>
        <el-table
            ref="cartTable"
            :data="goodToBeReturned"
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
                    style="width: 100px; height: 100px"
                >
                </el-image>
              </div>
              <div class="inline_block_top" style="width: 20px"></div>
              <div class="inline_block_top" style="width: 250px">
                <span style="font-size: 10px">{{scope.row.name}}</span>
              </div>
              <div class="inline_block_top" style="width: 50px"></div>
              <div class="inline_block_top" style="width: 250px">
                <span style="font-size: 10px; color: #909399">属性</span>
              </div>
            </div>
          </el-table-column>

          <el-table-column
              label="单价"
              width="120px"
          >
            <div slot-scope="scope">
              <span style="font-weight: bold">￥{{scope.row.price}}</span>
            </div>
          </el-table-column>

          <el-table-column
              prop="num"
              label="数量"
              width="120px">
          </el-table-column>

          <el-table-column
              label="金额"
              width="150px"
          >
            <div slot-scope="scope">
              <span style="color: red; font-weight: bold">￥{{scope.row.num * scope.row.price}}</span>
            </div>
          </el-table-column>
        </el-table>
      </div>
      <div style="height: 30px;"></div>
      <div style="text-align: left">
        <span style="font-size: 20px; font-weight: bold">退货原因</span>
      </div>
      <hr>
      <div style="text-align: left">
        <el-input
            type="textarea"
            v-model="reason"
            :autosize="{ minRows: 4, maxRows: 8}"
            placeholder="请填写退货的缘由，请尽可能详细"
            label="给卖家留言"
            style="width: 500px"
        ></el-input>
      </div>
      <div style="height: 30px"></div>
      <div style="text-align: left">
        <span style="font-size: 20px; font-weight: bold">退货物流信息</span>
      </div>
      <hr>
      <div style="width: 500px">
        <el-card>
          <div style="width: 500px; margin-bottom: 10px; text-align: left">
            <span style="color: #909399"><i class="el-icon-warning-outline"></i><span>寄件时请严格按照下面物流信息填写</span></span>
          </div>
          <el-descriptions class="margin-top" :column="1" border>
            <el-descriptions-item label="收货地址" > 广东省 佛山市 南海区 华南师范大学南海校区 E座601</el-descriptions-item>
            <el-descriptions-item label="收货人" > 陈泽飚 </el-descriptions-item>
            <el-descriptions-item label="收货人电话" >15889960160</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </div>
      <div style="width: 500px;margin-top: 30px;margin-bottom: 30px; text-align: left">
        <span style="color: red"><i class="el-icon-warning-outline"></i><span>此处请填写<span style="font-weight: bold">寄件人姓名</span>及<span style="font-weight: bold">寄件人电话</span></span></span>
      </div>
      <div style="width: 500px">
        <div>
        <el-form :model="returnForm" :rules="rules" ref="returnForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="寄件人姓名" prop="return_name">
            <el-input v-model="returnForm.return_name" placeholder="请输入寄件人姓名"></el-input>
          </el-form-item>
          <el-form-item label="寄件人电话" prop="return_phone">
            <el-input v-model="returnForm.return_phone" placeholder="请输入寄件人电话"></el-input>
          </el-form-item>
        </el-form>
        </div>
        <div style="text-align: right; margin-top: 50px">
          <el-button type="primary">确认退货</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import SecBar from "@/views/inc/SecBar";

export default {
  name: "ReturnGood",
  components: {
    TopBar,
    SecBar
  },
  data() {
    return {
      sku_id: '',
      order_sn: '',
      reason: '',
      returnForm: {
        return_name: '',
        return_phone: '',
      },
      rules: {
        return_name: [{ required: true, message: "请输入寄件人姓名", trigger: "blur" }],
        return_phone: [{ required: true, message: "请输入寄件人电话", trigger: "blur" }],
      },
      goodToBeReturned:[]
      //   {
      //     sku_id:100000,
      //     name: '湖南特产翻天娃薯条素香蹄筋辣条 8090儿时怀旧麻辣面筋零食小吃',
      //     price: 6.5,
      //     num: 1,
      //   }
      // ],
    }
  },
  methods: {
    // 获取url中的参数
    getParamInURL(){
      this.sku_id = this.$route.query.sku_id;
      this.order_sn = this.$route.query.order_sn;
    },
    getGoodToBeReturned(){
      this.goodToBeReturned.push(JSON.parse(sessionStorage.getItem("goodToBeReturned")));
    }
  },
  created() {
    this.getParamInURL();
    this.getGoodToBeReturned();
  }
}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}
</style>