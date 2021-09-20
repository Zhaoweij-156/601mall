<template>
  <div>
    <el-container id="Sec_Bar">
      <el-row style="width: 100%">
        <el-col :span="2" offset="4">
          <el-image :src="logoSrc" style="width: 142px; height: 58px; cursor: pointer" @click="goToHome"></el-image>
        </el-col>
        <el-col :span="11" offset="1">
          <div style="margin-top: 10px">
            <Search v-bind:param="this.searchParam" v-bind:input="searchInput" :size="searchBarSize" @searchKeyChange="updateSearchKey()"></Search>
          </div>
        </el-col>
      </el-row>
    </el-container>
  </div>
</template>

<script>
import logo from '../../assets/img/Logo.png'
import Search from "@/views/inc/Search";
export default {
  name: "SecBarWithLongSearch",
  props: ['searchInput', 'searchParam'],
  components: {
    Search,
  },
  data() {
    return {
      logoSrc: logo,
      searchBarSize: "long"
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