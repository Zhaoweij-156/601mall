<template>
  <!--            v-for一个组件-->
  <div style="padding-left: 25px; padding-right: 25px; border: 1px solid; border-color: #D3DCE6; border-top: 0px;border-left: 0px; border-right: 0px">
    <!--            图片-->
    <div style="width: 150px; height: 150px; margin-top:20px; background-color: #333333" class="pointer" @click="goToDetail(good.spu_id)">
        <el-image
            :src="good.sku_default_img"
            style="width: 150px; height: 150px" >

        </el-image>
    </div>
    <!--            描述-->
    <div style="width:100%; height: 50px; text-align: left">
      <div>
        <span style="color: red; font-size: 14px">￥{{good.present_price}}</span>
      </div>
      <div class="to_click myTextArea" >
        <span style="font-size: 14px" @click="goToDetail(good.spu_id)">{{good.sku_name}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RecommendGoodInGoodDetail",
  props: ['good'],
  methods: {
    goToDetail(pid){
      console.log(pid);
      this.$router.push({path: 'goodDetail', query: {spuid:pid}});
    }
  }

}
</script>

<style scoped>

.to_click :hover{
  text-decoration: underline;
  cursor: pointer;
}

.pointer{
  cursor: pointer;
}

.myTextArea {
  text-align: left !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

</style>