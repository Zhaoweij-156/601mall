<template>
  <el-col :span="6">
    <div class="text-center">
      <div class="good-item-outline">
        <div class="good-item-content">
          <!--        <router-link to="/goodDetail?skuID={{good.pid}}">-->
          <div>
            <!--                商品图片-->
            <el-image
                :src="good.sku_default_img"
                @click="goToDetail(good.spu_id)"
                class="good-item-img"
            >
            </el-image>
          </div>
          <!--                商品价格-->
          <div class="good-item-price">
            <span class="font-weight-bold" @click="goToDetail(good.spu_id)">￥{{ good.present_price }}</span>
          </div>
          <!--                商品名称-->
          <div class="good-item-name">
            <span style="font-size: 22px" @click="goToDetail(good.spu_id)"><span v-html="good.sku_name"></span></span>
          </div>
          <div class="good-item-description">
            <span @click="goToDetail(good.spu_id)">{{ good.sku_desc }}</span>
          </div>
          <!--        </router-link>-->
        </div>
      </div>
    </div>
  </el-col>
</template>

<script>

export default {
  name: "GoodItem",
  props: ['good'],
  methods: {
    goToDetail(pid) {
      this.$router.push({path: 'goodDetail', query: {spuid: pid}});
    }
  }
}
</script>

<style scoped>
.good-item-outline {
  border: 1px solid #dee2e6 !important;
  width: 236px;
  height: 350px;
  margin: auto !important;
  margin-top: 0.5rem !important;
  margin-bottom: 0.5rem !important;
}

.good-item-content {
  margin: 0.5rem;
}

.good-item-img {
  width: 220px;
  height: 220px;
}

.good-item-price {
  text-align: left !important;
  margin-top: 0.5rem;
}

.good-item-name {
  text-align: left !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.good-item-description {
  text-align: left !important;
  line-height: 16px;
  height: 50px;
  overflow: hidden;
}

.good-item-description > span {
  font-weight: lighter !important;
  font-size: 10px;
}

.good-item-outline:hover {
  border-color: red !important;
}

.good-item-description > span:hover {
  cursor: pointer !important;
  text-decoration: underline;
  color: red;
}


.good-item-img:hover,
.good-item-price:hover,
.good-item-name:hover {
  cursor: pointer;
}

.good-item-price:hover, .good-item-name:hover {
  color: red;
}
</style>