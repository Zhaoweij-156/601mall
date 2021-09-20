<template>
  <div>
    <el-container id="Sec_Bar">
      <el-row style="width: 100%">
        <el-col :span="2" offset="4">
          <el-image :src="logoSrc" style="width: 142px; height: 58px; cursor:pointer;" @click="goToHome()"></el-image>
        </el-col>
        <el-col :span="5" offset="8">
          <div style="margin-top: 10px">
          <search v-bind:param="this.searchParam" v-bind:input="searchInput" :size="searchBarSize"  @searchKeyChange="updateSearchKey()"></search>
          </div>
        </el-col>
      </el-row>
    </el-container>
  </div>
</template>

<script>
import Search from "@/views/inc/Search";
import logo from '../../assets/img/Logo.png'

export default {
  name: "SecBar",
  props: ['SearchInput', 'searchParam'],
  components: {
    Search,
  },
  data() {
    return {
      logoSrc: logo,
      searchBarSize:''
    }
  },
  methods: {
    goToHome(){
      this.$router.push('/index')
    },
    updateSearchKey(val) {
      this.$emit("searchKeyChange", val);
    }
  }

}
</script>

<style scoped>
#Sec_Bar {
  background-color: #0094ff;
  height: 80px;
  padding: 10px;
}
</style>