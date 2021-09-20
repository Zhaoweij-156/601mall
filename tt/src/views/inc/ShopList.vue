<template>
  <div>
    <el-row>
      <GoodItemInIndex v-for="(good, index) in goodList" v-bind:good="good" v-if="index < 8"></GoodItemInIndex>
    </el-row>
  </div>
</template>

<script>
import GoodItemInIndex from "@/views/inc/GoodItemInIndex";
export default {
  name: "ShopList",
  props:['goodList'],
  components: {
    GoodItemInIndex
  },
  data() {
    return {
    //   goodList: [
    //     {
    //       skuid: 1,
    //       img: null,
    //       name: '123',
    //       description: '123',
    //     }
    //   ]
    }
  }
}
</script>

<style scoped>
.singleGood {
  height: 260px;
  width: 200px;
  margin-left: 35px;
}

.singleGoodPicture {
    border-radius: 13px;
}



.goodText {
  line-height: 20px;
  text-align: left;
  padding-left: 10px;
  position: relative;
  top: -70px;
}

.goodTitle {
  font-family: PingFang SC;
  font-size: 16px;
  text-align: left !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.goodDesc {
  color: #909399;
  font-size: 13px;

}
</style>