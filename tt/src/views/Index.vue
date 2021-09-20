<template>
  <el-container>
    <el-header style="height: 30px">
      <TopBar></TopBar>
    </el-header>
    <SecBar></SecBar>
    <el-container>
      <el-main>
        <div style="margin-left: 18%; margin-right: 18%">
          <el-card id="main_card">
            <div id="main_card_content">
            <div class="w-100" style="display:inline; vertical-align: top;">
              <div id= "main_card_top" style="height: 20px">
                <div style="position:relative;top: -80px;">
                  <span v-for="(enter, index) in otherEnter" @click="jumpTo(enter)" class="pointer">
                    {{ enter }}
                    <span v-if="index != Object.getOwnPropertyNames(otherEnter).length-1">&nbsp;</span>
                  </span>
                </div>
              </div>
            </div>
            <div class="w-100">
              <div id="leftNav"
                  style="display:inline-block;vertical-align: top; width:15%;" class="main_card_height">
                <div style="display:inline-block;">
                  <div style="height: 450px;">
                    <main-card-left-nav v-bind:items="this.items"></main-card-left-nav>
                  </div>
                </div>
              </div>
              <div  id="centerCarousel"
                  style="display:inline-block;vertical-align: top; width:60%; "
                  class="main_card_height"
              >
                <div style="height: 250px">
                <div style="display:inline-block; width: 65%">
                  <el-card :body-style="cardBodyStyle" style=" height: 240px">
                  <el-carousel trigger="click" height="240px"
                               style="width: 450px"
                               indicator-position="none">
                    <el-carousel-item v-for="(cItem,index) in cItem1" :key="index">
                      <el-image :src="cItem" :fit="fill"></el-image>
                    </el-carousel-item>
                  </el-carousel>
                  </el-card>
                </div>
                <div style="display:inline-block; width: 25%; margin-left: 10px">
                  <el-card :body-style="cardBodyStyle" style=" height: 240px">
                  <el-carousel trigger="click" height="240px"
                               style="width: 170px"
                               indicator-position="none"
                               arrow="never">
                    <el-carousel-item v-for="(cItem,index) in cItem2" :key="index">
                      <el-image :src="cItem" :fit="fill"></el-image>
                    </el-carousel-item>
                  </el-carousel>
                  </el-card>
                </div>
                </div>
                <div style="width: 100%; height: 15px;text-align: left; margin-left: 30px">
                  <span style="position:relative;top: -75px; font-size: 12px">买点啥，来E601大卖场</span>
                </div>
                <div>
                <div style=" width: 100%; height: 200px">
                  <div style="display:inline-block; width: 65%;">
                    <el-card :body-style="cardBodyStyle" style=" height: 190px">
                    <el-carousel trigger="click" height="190px"
                                 style="width: 450px"
                                 indicator-position="none">
                      <el-carousel-item v-for="(cItem,index) in cItem3" :key="index">
                        <el-image :src="cItem" :fit="fill"></el-image>
                      </el-carousel-item>
                    </el-carousel>
                    </el-card>
                  </div>
                  <div style="display:inline-block; width: 25%; margin-left: 10px">
                    <el-card :body-style="cardBodyStyle" style=" height: 190px">
                    <el-carousel trigger="click" height="190px"
                                 style="width: 170px"
                                 indicator-position="none"
                                 arrow="never">
                      <el-carousel-item v-for="(cItem,index) in cItem4" :key="index">
                        <el-image :src="cItem" :fit="fill"></el-image>
                      </el-carousel-item>
                    </el-carousel>
                    </el-card>
                  </div>
                </div>
                </div>
              </div>
              <div  id="rightPersonInfo"
                  style="display:inline-block;vertical-align: top; width:25%; background-color: black"
              class="main_card_height">
                <div style="height: 100%;">
                  <main-card-right-person-model :user-avatar="userAvatar" :user-nick-name="userNickName"></main-card-right-person-model>
                </div>
              </div>
            </div>
            </div>
            <div style="height: 40px"></div>
            <div style="text-align: left; height: 30px">
              <span style="position: relative; top: -70px; font-weight:bold; font-family: Microsoft YaHei; font-size: 20px">好物推荐</span>
            </div>
            <div id="Recommend">
<!--              <el-card>-->
              <div class="" style="padding-left: 50px; padding-right: 50px">
                <shop-list v-bind:good-list="this.goodListInIndex"></shop-list>
              </div>
<!--              </el-card>-->
            </div>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import TopBar from "./inc/TopBar";
import MainCardLeftNav from "@/views/inc/MainCardLeftNav";
import MainCardRightPersonModel from "@/views/inc/MainCardRightPersonModel";
import ShopList from "@/views/inc/ShopList";
import Search from "@/views/inc/Search";
import SecBar from "@/views/inc/SecBar";

import c1_1 from '../assets/img/c1_1.jpg'
import c1_2 from '../assets/img/c1_2.jpg'
import c2_1 from '../assets/img/c2_1.jpg'
import c3_1 from '../assets/img/c3_1.jpg'
import c3_2 from '../assets/img/c3_2.jpg'
import c4_1 from '../assets/img/c4_1.jpg'
export default {
  name: "Index",
  components: {
    SecBar,
    Search,
    ShopList,
    MainCardRightPersonModel,
    TopBar,
    MainCardLeftNav
  },
  data() {
    return {
      userNickName: '',
      userAvatar: '',
      fill: 'fill',
      cardBodyStyle: {
        padding: '0',
        height: '200px',
        width: '400px'
      },
      cItem1:{
        c1_1,
        c1_2
      },
      cItem2: {c2_1},
      cItem3: {
        c3_1,
        c3_2,
      },
      cItem4: {c4_1},
      goodListInIndex: [
        {
              skuid: 1,
              img: null,
              name: '123',
              description: '123',
        }
      ],
      items: [
        {
          c1:'女装',
          c2:'内衣',
          cLast:'家居'
        },
        {
          c1:'女鞋',
          c2:'男鞋',
          cLast:'箱包'
        },
        {
          c1:'母婴',
          c2:'童装',
          cLast:'玩具'
        },
        {
          c1:'男装',
          cLast:'运动户外',
        },
        {
          c1:'美妆',
          c2:'彩妆',
          cLast:'个护'
        },
        {
          c1:'手机',
          c2:'数码',
          cLast:'企业'
        },
        {
          c1:'大家电',
          cLast:'生活电器',
        },
        {
          c1:'零食',
          c2:'生鲜',
          cLast:'茶酒'
        },
        {
          c1:'厨具',
          c2:'收纳',
          cLast:'清洁'
        },
        {
          c1:'家纺',
          c2:'家饰',
          cLast:'鲜花'
        },
        {
          c1:'图书音像',
          cLast:'文具',
        },
        {
          c1:'医药保健',
          cLast:'进口',
        },
        {
          c1:'汽车',
          c2:'用品',
          cLast:'二手车',
        },
        {
          c1:'房产',
          cLast: '装修家具',
        },
        {
          c1:'手表',
          c2: '眼镜',
          cLast:'饰品',
        },
      ],
      logosrc: '../assets/img/Logo.png',
      otherEnter: {
        //需要递增，步长为1
        1:'地狗',
        2:'不划算',
        3:'地狗超市',
        4:'张三拍卖',
        5:'水狗宅家',
        6:'今天收工',
        7:'大房子',
        8:'来点旧货',
        9:'虎口逃生',
        10:'不走心的',
        11:'乡村生活'
      },
      searchParam: {
        size: "",
      }
    }
  },
  methods: {
    getBasicInfo(){
      this.userNickName = sessionStorage.getItem('userNickName');
      this.userAvatar = sessionStorage.getItem('userAvatar')
    },
    jumpTo(target){
      //TODO:跳转到对应网址
      console.log(target);
    },
    getGoodListInIndex(){
      let data = {
        sort: 'desc'
      }
      //TODO：获取index页面下方的推荐商品
      this.$axios.get('/product/allforpage', {
        params: data,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        }
      }).then(res => {
        this.goodListInIndex = res.data.data;
        console.log(res);
      })
    },

  },
  created() {
    this.getGoodListInIndex()
    this.getBasicInfo()
  }
}
</script>

<style scoped>
#Sec_Bar {
  background-color: #0094ff;
  height: 80px;
  padding: 10px;
}

.el-container {
  padding: 0;
  margin: 0;
  height: 100%
}

.el-header {
  padding: 0;
}

.pointer {
  cursor: pointer;
}

.main_card_height {
  height: 455px
}

.inline_block_top {
  display: inline-block;
  vertical-align: top;
}


.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.w-100 {
  width: 100%;
}
</style>