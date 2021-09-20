<template>
  <div id="cardList" style="margin-right: 50px">
    <el-table
        ref="cartTable"
        :data="cardList"
        border
        lazy
        empty-text="购物车空空的，快去装点宝贝吧"
        style="width: 100%"
        :header-cell-style="{background:'#dcdfe6', color: 'black'}"
        @selection-change="handleSelectionChange"
    >

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="spuName"
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
          <div class="inline_block_top" style="width: 30px"></div>
          <div class="inline_block_top" style="width: 250px;">
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
          label="数量"
          width="120px">
        <div slot-scope="scope">
          <el-input-number size="mini" style="width: 100px" v-model="scope.row.skuCount" :min="1" :max="999"
                           label="描述文字" @change="updateCartItemNum(scope.row.id, scope.row.skuCount)"></el-input-number>
        </div>
      </el-table-column>

      <el-table-column
          label="金额"
          width="150px"
      >
        <div slot-scope="scope">
          <span
              style="color: red; font-weight: bold">￥{{ line_total(scope.row.skuPresentPrice, scope.row.skuCount, scope.row.index) }}</span>
        </div>
      </el-table-column>

      <el-table-column
          label="操作"
          width="130">
        <div slot-scope="scope">
          <!--            <span class="my_muted" @click="deleteAddress(scope.row.id)">删除</span>-->
          <el-link style="font-size: 13px" @click="deleteCartItem(scope.row.id)"><i class="el-icon-delete"></i>删除
          </el-link>
        </div>
      </el-table-column>
    </el-table>
    <div style="height: 30px;"></div>
    <div style="height: 60px; width: 100%;background-color: #dcdfe6">
      <div class="inline_block_top" style="width: 100px; padding-top: 19px">
        <el-link @click="deleteCartItemSelected()">全部删除</el-link>
      </div>
      <div class="inline_block_top" style="width: 92.3%; text-align: right">
        <div class="inline_block_top" style="width: 200px;padding-top: 18px">
          <span style="font-size: 16px; ">总计：&nbsp;</span>
          <span style="color: red; font-weight: bold; font-size: 22px">{{ getSummaries }}</span>
        </div>
        <div class="inline_block_top" style="width: 170px;">
          <el-button type="danger" style="width: 130px; height: 60px; font-size: 20px" @click="goConfirm()">结算
          </el-button>
        </div>
      </div>

    </div>
  </div>

</template>

<script>
export default {
  name: "Cart",
  data() {
    return {
      cardList: [],
      multipleSelection: [],
      sumEachLine: [],
      total_all: 0,
    }
  },
  computed: {
    getSummaries() {
      //TODO:合计
      this.total_all = 0;
      this.multipleSelection.forEach(cartItem => {
        this.total_all += cartItem.skuCount * cartItem.skuPresentPrice
      });
      return this.total_all
    },
  },
  methods: {
    getCartList() {
      this.$axios.get('/cart/getList').then(res => {
        this.cardList = res.data.data;
        this.insertIndexToData();
        console.log(res)
      })
      //TODO:发送获取购物车商品的请求
    },
    line_total(price, num, index) {
      this.sumEachLine[index] = price * num;
      return price * num;
    },
    insertIndexToData() {
      //往数据里面插入index
      let index = -1;
      this.cardList.forEach(cartItem => {
        cartItem.index = ++index
      });
      this.sumEachLine.length = index + 1;
    },
    deleteCartItem(id) {
      this.$confirm('将从购物车中移除次商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //TODO:发送删除购物车商品的请求
        let data = {
          cartIds: [id]
        }
        this.$axios.post('/cart/del', this.$qs.stringify(data, {indices: false}), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          console.log(res)
          this.$message('删除成功');
          this.getCartList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },

    deleteCartItemSelected() {
      this.$confirm('将移除所有选中的商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          cartIds: []
        }
        for (let i = 0; i < this.multipleSelection.length; ++i) {
          data.cartIds[i] = this.multipleSelection[i].id;
        }
        this.$axios.post('/cart/del', this.$qs.stringify(data, {indices: false}), {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          console.log(res)
          this.$message('删除成功');
          this.getCartList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    goConfirm() {
      if (this.multipleSelection.length > 0) {
        let dataLength
        this.$axios.get('/user/address/getList').then(res => {
          dataLength = res.data.data.length;
          if (dataLength !== 0) {
            sessionStorage.setItem("cartItemsSelected", JSON.stringify(this.multipleSelection));
            this.$router.push('/orderConfirm')
          } else {
            this.$message.error("你还没有设置收货地址，将前往设置收货地址")
            this.$router.push('/address')
          }
        })
      } else {
        this.$message.error("你还没选中要购买的商品")
      }


    },
    updateCartItemNum(cartItemId, num) {
      //TODO：发送更改购物车商品数量的请求
      let data = {
        id: cartItemId,
        skuCount: num
      }
      this.$axios.post('/cart/edit', this.$qs.stringify(data), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        console.log(res)
      })
    }
  },
  created() {
    this.getCartList();
  }
}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}

</style>