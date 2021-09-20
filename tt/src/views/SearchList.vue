<template>
  <div>
    <TopBar></TopBar>
    <SecBarWithLongSearch v-bind:search-input="searchKey" :search-param="this.param"></SecBarWithLongSearch>
    <div style="padding-left: 250px; padding-right: 250px">
      <div style="width: 100%; height: 100px; background: #F2F6FC">
        <div style="width: 100%; height: 50px; background: black">

        </div>
        <div>
          <div style="width: 500px; height: 50px; float: right; ">
            <div class="inline_block_top" style="width: 200px">
            <el-input class="numrule"
                      type="number"
                      style="width: 70px"
                      v-model.number="param.minPrice"
                      min="0"
                      max="999999999"
                      v-on:blur="doSearch"
            ></el-input>
            <span @click="getParamInURL">￥~</span>
            <el-input
                class="numrule"
                type="number"
                style="width: 70px"
                v-model.number="param.maxPrice"
                min="0"
                max="999999999"
                v-on:blur="doSearch"
            ></el-input>
            <span>￥</span>
            </div>
            <div class="inline_block_top" style="width: 160px">
            <el-select v-model="param.sort" placeholder="排序方式">
              <el-option
                  v-for="item in sortMethods"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            </div>
          </div>
        </div>
      </div>
      <div name="GoodListArea">
        <div class="" style=" padding-left: 150px; padding-right: 150px;">
          <el-empty v-if="emptySearchFlag" description="没有搜到宝贝喔，换个搜索词试试吧"></el-empty>
          <el-row gutter="0">
            <good-item
                v-for="good in goods"
                v-bind:key="good.pid"
                v-bind:good="good">
            </good-item>
          </el-row>
          <!--        分页模块-->
          <div class="w-100" name="for_hr"></div>
          <div class="text-center w-100">
            <div class="block">
              <el-pagination
                  @current-change="doSearchWithCurrentPage"
                  background
                  :current-page.sync="param.current_page"
                  :page-size="param.pageSize"
                  layout="prev, pager, next"
                  :total="total">
              </el-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="background-color: #0094ff; width: 100%; height: 100px">

    </div>
  </div>
</template>

<script>
import TopBar from "@/views/inc/TopBar";
import GoodItem from "@/views/inc/GoodItem";
import SecBarWithLongSearch from "@/views/inc/SecBarWithLongSearch";

export default {
  components: {
    GoodItem,
    TopBar,
    SecBarWithLongSearch
  },
  name: "SearchList",
  data() {
    return {
      sortMethods: [
          {
            value: 'nosort',
            label: '不排序'
          },
          {
            value: 'asc',
            label: '按价格升序排序'
          },
            {
            value: 'desc',
            label: '按价格降序排序'
          },
      ],
      goods: [],
      param: {
        current_page: 1,
        pageSize: 12,
        minPrice: '',
        maxPrice: '',
        sort: '',
      },
      total: 120,
      searchKey: '',
      emptySearchFlag: true
    }
  },
  methods: {
    //注意search页面进行搜索框跳转search页面需要不同的key
    // 获取url中的参数
    getParamInURL(){
      if (undefined !== this.$route.query.key) this.searchKey = this.$route.query.key;
      this.param.minPrice = this.$route.query.minPrice;
      this.param.maxPrice = this.$route.query.maxPrice;
      this.param.sort = this.$route.query.sort;
    },
    doSearch(){
      let data = {
        pageNo: this.param.current_page,
        pageSize: this.param.pageSize,
      }
      console.log(this.searchKey)
      if (this.param.minPrice !== '' && undefined !== this.param.minPrice) data.minPrice = Number(this.param.minPrice > 0 ? this.param.minPrice : 0)
      if (this.param.maxPrice !== '' && this.param.maxPrice !== 0 && undefined !== this.param.maxPrice) data.maxPrice = Number(this.param.maxPrice > 0 ? this.param.maxPrice : 0)
      if (this.param.sort !== 'nosort' && this.param.sort !== '' && undefined !== this.param.sort) data.sort = this.param.sort
      // 搜索商品
      if (null !== this.searchKey && this.searchKey !== '' && undefined !== this.searchKey)
      {
        data.keyword = this.searchKey
        this.$axios.get('/product/search', {
          params: data,
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          this.goods = res.data.data;
          if (res.data.data.length !== 0) this.emptySearchFlag = false;
          console.log(res);
        })
      }
      else {
        this.$axios.get('/product/allforpage', {
          params: data,
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          }
        }).then(res => {
          this.goods = res.data.data;
          if (res.data.data.length !== 0) this.emptySearchFlag = false;
          console.log(res);
        })
      }

    },
    doSearchWithCurrentPage () {
      this.doSearch();
    },
    updateSearchKey(val){
      this.searchKey = val;
    }
  },
  created() {
    this.getParamInURL();
    this.doSearch()
  },
  watch:{
    'param.sort'(){
      this.doSearch();
    }
  },
}
</script>

<style scoped>
#Sec_Bar {
  background-color: #0094ff;
  height: 80px;
  padding: 10px;
}

.inline_block_top {
  display: inline-block;
  vertical-align: top;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
/deep/.numrule input::-webkit-outer-spin-button,
/deep/.numrule input::-webkit-inner-spin-button {
  -webkit-appearance: none!important;
}
/deep/.numrule input[type="number"]{
  -moz-appearance: textfield;
}


</style>