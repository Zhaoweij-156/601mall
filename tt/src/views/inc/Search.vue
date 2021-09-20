<template>
  <div>
    <el-input  @blur="searchKeyChange()" clearable placeholder="请输入内容" v-model="input" class="input-with-select" v-bind:class="classObject">
      <el-button slot="append" icon="el-icon-search" @click="goSearch()"></el-button>
    </el-input>
  </div>
</template>

<script>
export default {
  name: "Search",
  props: ['param', 'input', 'size'],
  data() {
    return {
      // myParam: this.param,
    }
  },
  computed: {
    classObject: function () {
      console.log(this.size);
      if (this.size == 'long'){
        return {
          size_long: true
        }
      }
      else if (this.size == 'short')
      {
        return {
          size_short: true
        }
      }
      else
      {
        return {
          size_default: true
        }
      }
    }
  },
  methods: {
    goSearch(){
      //TODO:跳转到搜索页面，同时携带搜索参数
      console.log(this.param)
      if (this.param !== undefined){
        this.$router.push({path: '/search', query: {
            key: this.input,
            minPrice: this.param.minPrice,
            maxPrice: this.param.maxPrice,
            sort: this.param.sort
          }});
      }
      else {
        this.$router.push({path: '/search', query: {
            key: this.input,
          }});
      }
    },
    searchKeyChange(){
      this.$emit("searchKeyChange", this.input);
    }
  }
}
</script>

<style scoped>
.el-select .el-input {
  width: 130px;

}

.size_long {
  width: 700px;
}

.size_default {
  width: 500px;
}

.size_short {
  width: 250px;
}


.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>