<template>
  <div>
    <TopBar></TopBar>
    <SecBar></SecBar>
    <div style="width: 100%; height: 100px; background-color: deeppink">
      {{this.skuID}}
    </div>
    <div style="padding-left: 18%; padding-right: 18%">
      <!--      上部-->
      <div style="text-align: left;white-space: nowrap">
        <!--      划分左中右,此为左-->
        <div class="inline_block_top" style="background-color: lightgreen; width: 300px;height: 500px">
          <!--          图片-->
          <div style="margin-top: 100px">
            <el-image :src="goodDetail.skuDefaultImg" style="width: 300px; height: 300px">
            </el-image>
          </div>

        </div>
        <!--      划分左中右,此为中-->
        <div class="inline_block_top" style="background-color: #ffffff; width: 700px;height: 500px">
          <!--          加个padding-->
          <div style="padding-left: 30px; padding-right: 30px">
            <!--          标题-->
            <div style="background-color: white;width: 100%; margin-top: 40px;overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;">
              <span style="font-weight: bold; font-size: 23px;">{{ goodDetail.skuName }}</span>
            </div>
            <div style="width: 100%; height: 10px"></div>
            <!--            描述-->
            <div style="background-color: white; height: 100px;width: 100%; white-space: normal">
              <span>{{ goodDetail.skuDesc }}</span>
            </div>
          </div>
          <!--            价格-->
          <div style="padding-left: 20px; padding-right: 20px">
            <div style="background-color: #fcfaf7; width: 100%; height: 100px; border-radius: 2px">
              <div style="padding-left: 10px;">
                <!--                价格部分分为左右两格，此为左-->
                <div class="inline_block_top" style="width: 50%">
                  <!--              现价-->
                  <div>
                    <div>
                      <span style="color: #909399; font-size: 14px;">优惠价</span>
                    </div>
                    <div style="margin-top: 7px">
                      <span style="color: red; font-size: 14px;">￥</span><span
                        style="color: red; font-size: 30px">{{ goodDetail.presentPrice }}</span>
                    </div>
                  </div>
                  <div style="width: 100%; height: 20px"></div>
                  <!--              原价-->
                  <div>
                    <span style="color: #909399; font-size: 12px;">原价</span>
                    <span
                        style="color: #909399; font-size: 12px;text-decoration: line-through">￥{{
                        goodDetail.originalPrice
                      }}</span>
                  </div>
                </div>
                <!--                价格部分分为左右两格，此为右-->
<!--                TODO:可能要做点-->
                <div class="inline_block_top" style="width: 50%; margin-left: 10%">
                  <div>
                    <span style="color: #909399; font-size: 14px;">秒杀价</span>
                  </div>
                  <div style="margin-top: 7px">
                    <span style="color: red; font-size: 14px;">￥</span><span
                      style="color: red; font-size: 30px">???</span>
                  </div>
                  <div>
                    <span style="color: #909399; font-size: 14px;">秒杀活动尚未开始</span>
                  </div>
                </div>
              </div>

            </div>

          </div>
          <br>
<!--          属性选择-->
          <div style="padding-left: 30px; padding-right: 30px">
            <div style="height: 100px; width: 100%;">
              <el-radio-group v-model="skuId" @change="updateCurrentSku">
                <el-radio v-for="skuSelected in allSkuId" :label="skuSelected.id" border style="height: 80px; width: 100px">
                  <el-image
                    :src="skuSelected.skuDefaultImg"
                    style="width: 50px; height: 50px"
                  >

                  </el-image>
                </el-radio>
              </el-radio-group>
            </div>
          </div>
          <br>
          <br>

          <div style="padding-left: 30px; padding-right: 30px">
            <!--          个数选择-->
            <div class="inline_block_top">
              <el-input-number v-model="num"
                               @change="handleChange"
                               :min="1"
                               :max="999"
                               label="描述文字"
                               style="width: 150px;"
              ></el-input-number>
            </div>
            <div class="inline_block_top" style="width: 20px"></div>
            <div class="inline_block_top">
              <el-button size="big" type="danger" @click="addToCart()">
                <i class="el-icon-shopping-cart-2"></i>
                加入购物车
              </el-button>
            </div>
            <div class="inline_block_top" style="width: 20px"></div>
            <div class="inline_block_top">
              <el-button type="danger" plain @click="buyNow()">立即购买</el-button>
            </div>
          </div>
        </div>
        <!--      划分左中右,此为右-->
<!--        放置E601店铺的介绍-->
        <div class="inline_block_top" style="width: 220px;height: 500px; background-color: #F2F6FC">
          <div>
            <el-image :src="storeImg"></el-image>
          </div>
          <div>
            <h2>E601小卖铺</h2>
            <div>
              店铺星级：0x3f3f3f&nbsp;<i class="el-icon-star-on" style="color: orange"></i><br>
              购物满意度：0x3f3f3f&nbsp;<i class="el-icon-star-on" style="color: orange"></i>
            </div>
            <div style="height: 30px"></div>
            <div style="font-size: 12px">
              我们郑重承诺:<br>
              <div style="padding-left: 15px; white-space:normal; ">
              <i class="el-icon-star-on" style="color: red"></i><span>卖的都是假货</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>而且没有售后</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>买了不让退货</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>自己仓库取货</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>买贵了不退差价</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>卖便宜了倒贴商家</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>支持多种支付方式</span><br>
                <i class="el-icon-star-on" style="color: red"></i><span>支持PY交易</span><br>
                使用py交易支付，店家还会附赠20个G亻尔 忄董的高清学习视频，详细咨询客服
              </div>

            </div>

          </div>
        </div>
      </div>
    </div>
    <div style="width: 100%; height: 100px"></div>
<!--    下部-->
    <div style="padding-left: 18%; padding-right: 18%">
      <div style="width: 1220px; height: 500px;">
<!--        左右两个区域，此为左-->
        <div class="inline_block_top" style="width: 200px;background-color: white;height: 1000px; border: 1px solid; border-color: #D3DCE6;border-bottom: 0px">
<!--          同类推荐-->
          <div style="width: 100%; height: 25px;">
              <span>浏览此商品的顾客也喜欢</span>
          </div>
          <recommend-good-in-good-detail v-for="(recommendGood, index) in recommendGoodList"
                                         v-bind:good="recommendGood"
                                         v-if="index < 4"
          ></recommend-good-in-good-detail>
        </div>
<!--        左右两个区域，此为右-->
        <div class="inline_block_top" style="width: 1000px;background-color: firebrick; height: 300px">
<!--          商品详情-->
          <div></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import SecBar from "@/views/inc/SecBar";
import RecommendGoodInGoodDetail from "@/views/inc/RecommendGoodInGoodDetail";
import E601 from "../assets/img/E601.jpg"

export default {
  name: "goodDetail",
  components: {
    TopBar,
    SecBar,
    RecommendGoodInGoodDetail
  },
  data() {
    return {
      spuId: '',
      skuId: '',
      num: 1,
      storeImg: E601,
      allSkuDetail: [],
      allSkuId: [],
      goodDetail: {
        skuName: '猫城记',
        skuDesc: '随书附赠猫人藏书票，更适合收藏的精装小开本，初版小说完整呈现，收入自序、新序及《我怎样写猫城记》。科幻X讽喻X反乌托邦小说。入选世界科幻文库，国际影响力比肩于《骆驼祥子》',
        skuDefaultImg: null,
        originalPrice: 46,
        presentPrice: 44.2,
        saleCount: 0,
        stock: 0,
      },
      recommendGoodList:[
      ]
    }
  },

  methods: {
    // 获取url中的参数
    getParamInURL(){
      this.spuId = this.$route.query.spuid;
    },
    getAllSkuDetail() {
      this.getParamInURL();
      //TODO: 发送获取商品详细信息的请求
      this.$axios.get('/product/detail', {
        params: {
          spuId : this.spuId
        }
      }).then(res =>{
        this.allSkuDetail = res.data.data;
        this.skuId = res.data.data[0].id;
        console.log(this.skuId)
        this.getAllSkuId();
        this.updateCurrentSku();
        console.log(res)
      })
    },
    getMyRecommend(){
      //TODO: 发送获取商品详情区域左边的一条纵列的相关推荐
      let data = {
        sort: 'desc'
      }
      this.$axios.get('/product/allforpage', {
        params: data,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.recommendGoodList = res.data.data;
        console.log(res);
      })
    },
    updateCurrentSku(){
      for (let i = 0; i < this.allSkuDetail.length; i++) {
        if (this.allSkuDetail[i].id === this.skuId){
          this.goodDetail = JSON.parse(JSON.stringify(this.allSkuDetail[i]));
          break;
        }
      }
    },
    getAllSkuId(){
      this.allSkuId = [];
      for (let i = 0; i < this.allSkuDetail.length; i++) {
        let data = {
          id: this.allSkuDetail[i].id,
          skuDefaultImg: this.allSkuDetail[i].skuDefaultImg
        }
        this.allSkuId[i] = data
      }
      console.log(this.allSkuId);
    },
    addToCart() {
      let data = {
        skuId: this.skuId,
        skuCount: this.num,
      }
      this.$axios.post('/cart/add', this.$qs.stringify(data), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.$notify({
          title: '提示',
          message: '已成功将宝贝加入购物车',
        });
        console.log(res)
      })
    },
    buyNow() {
      let data = {
        skuId: this.skuId,
        skuCount: this.num,
      }
      this.$axios.post('/cart/add', this.$qs.stringify(data), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.$router.push('/cart')
        console.log(res)
      })
    }
  },
  created() {
    this.getAllSkuDetail();
    this.getMyRecommend();
  },

}
</script>

<style scoped>

.inline_block_top {
  display: inline-block;
  vertical-align: top;
}


</style>