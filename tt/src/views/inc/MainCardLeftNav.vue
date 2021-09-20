<template>
  <div style="text-align: left;" class="w-100">
    <div v-for="item in items" class="left_nav_item" style=" position: relative; top: 0px">
      <div class="pointer" style="display: inline-block; width: 150px; height: 30px; text-align: left; vertical-align: top; ">
      <span v-for="(cate, name, index) in item"
            @click="goSearch(cate)"
            class="pointer"
            style="text-align: left; position: relative; top: -60px; font-size: 15px"
      >
        {{cate}}<span v-if="name !== 'cLast'" style="color: black">&nbsp;/&nbsp;</span>
      </span>

      </div>
      <div style=" display: inline-block;text-align: right;width: 18px; vertical-align: top; position: relative; top: -60px">
        <i class="el-icon-arrow-right"></i>
      </div>
      </div>

  </div>
</template>

<script>
export default {
  name: "MainCardLeftNav",
  props: ['items'],
  data(){
    return {

    }
  },
  methods:{
    goSearch(searchKey){
      //TODO:跳转搜索，带着cat
      this.$router.push( {path:'/search', query: {key: searchKey}});
    }
  }
}
</script>

<style scoped>



.left_nav_item{
  position: relative;
  top: -55px;
  /*left: -20px;*/
  height: 30px;
}

.pointer {
  cursor: pointer;
}

.pointer :hover{
  color: red;
}

.w-100 {
  width: 100%;
}
</style>