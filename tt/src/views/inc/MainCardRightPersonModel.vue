<template>
  <div style="display:block; height: 100%; background-color: #e5e8ee">
    <div id="myAvatar" style="padding-left: 70px;padding-right: 70px;border-radius: 5px ;padding-top: 20px">
      <div style="height: 140px; width: 140px;">
        <el-image :src="userAvatar" style="height: 140px; width: 140px"></el-image>
      </div>
      <div style="height: 40px;text-align: left !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;">
        <span style="position: relative; top:-60px">{{ userNickName }}</span>
      </div>
    </div>
    <div style="height: 30px; border: 2px solid #C0C4CC">
      <span style="position: relative; top: -65px;font-size: 12px"><el-link :underline="false">关于全面减少快递配送服务费率的公告</el-link></span>
    </div>
    <div style="height: 100px">
      <div style="margin-top: 20px;height: 100px">
        <div style=" width: 49%; height: 100px; border: 1px solid #C0C4CC" class="inline_block_top clickable">
          <div style="width: 100%; height: inherit" @click="goSearch('美食')">
            <div>
            <span style="font-size: 55px; position: relative; top: -40px"><i class="el-icon-food"></i></span>
            </div>
            <div style="position: relative; top: -170px">
            <span style="font-weight: bold">美食</span>
            </div>
          </div>
        </div>

        <div style=" width: 49%; height: 100px;border: 1px solid #C0C4CC"class="inline_block_top clickable">
          <div style="width: 100%; height: inherit" @click="goSearch('电影')">
            <div>
              <span style="font-size: 55px; position: relative; top: -40px"><i class="el-icon-film"></i></span>
            </div>
            <div style="position: relative; top: -170px">
              <span style="font-weight: bold">电影</span>
            </div>
          </div>
        </div>
      </div>

      <div>
        <div style=" width: 49%; height: 100px;border: 1px solid #C0C4CC" class="inline_block_top clickable">
          <div style="width: 100%; height: inherit" @click="goSearch('旅行')">
            <div>
              <span style="font-size: 55px; position: relative; top: -40px"><i class="el-icon-sunrise-1"></i></span>
            </div>
            <div style="position: relative; top: -170px">
              <span style="font-weight: bold">旅行</span>
            </div>
          </div>
        </div>

        <div style=" width: 49%; height: 100px;border: 1px solid #C0C4CC"class="inline_block_top clickable">
          <div style="width: 100%; height: inherit" @click="goSearch('酒店')">
            <div>
              <span style="font-size: 55px; position: relative; top: -40px"><i class="el-icon-office-building"></i></span>
            </div>
            <div style="position: relative; top: -170px">
              <span style="font-weight: bold">酒店</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainCardRightPersonModel",
  props: ['userNickName', 'userAvatar'],
  methods: {
    goSearch(searchKey){
      this.$router.push( {path:'/search', query: {key: searchKey}});
    }
  }
}
</script>

<style scoped>
.inline_block_top {
  display: inline-block;
  vertical-align: top;
}

.clickable {
  cursor: pointer;
}
</style>