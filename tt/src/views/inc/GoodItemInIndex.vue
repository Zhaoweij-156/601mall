<template>
  <el-col :span="6">
    <div style="" class="singleGood" @click="goDetail(good.spu_id)">
      <el-image
        :src="good.sku_default_img"
        style="height:200px; width:200px"
        class="singleGoodPicture"
      >

      </el-image>
      <div class="goodText">
        <span class="goodTitle" >{{good.sku_name}}</span>
        <br/>
        <span class="goodDesc">{{good.sku_desc}}</span>
      </div>
    </div>
  </el-col>
</template>

<script>
export default {
  name: "GoodItemInIndex",
  props: ['good'],
  methods: {
    goDetail(skuid){
      this.$router.push({path: '/goodDetail', query:{spuid:skuid}});
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

.singleGood :hover {
  cursor: pointer;
}

.singleGoodPicture {
  border-radius: 13px;
}



.goodText {
  line-height: 20px;
  padding-left: 10px;
  position: relative;
  top: -70px;
  text-align: left !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.goodTitle {
  font-family: PingFang SC;
  font-size: 16px
}

.goodDesc {
  color: #909399;
  font-size: 13px;
}


</style>