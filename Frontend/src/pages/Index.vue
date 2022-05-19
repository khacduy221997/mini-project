<template>
  <div class="q-body">
    <label for="" class="mt-2 text-weight-bold text-sm">Danh sách sản phẩm</label>
    <div class="block-products">
      <div class="box-products scroll">
        <div class="list-product">
          <div
            class="item-product"
            v-for="item in products"
            :key="item"
            clickable
            @click="goToDetail(item.id)"
          >
            <div class="item-product__box-img">
              <a>
                <img
                  class="cpslazy loaded"
                  :alt="item.name"
                  data-ll-status="loaded"
                  :src="item.image"
                />
              </a>
            </div>
            <div v-if="item.virtualPrice"
              class="item-product__sticker-percent">
              <p>Giảm {{ getDiscount(item) }}%</p>
            </div>
            <div class="item-product__box-name">
              <a>
                <h3>{{ item.name }}</h3>
              </a>
            </div>
            <div class="item-product__box-price">
              <p class="special-price">
                {{ Number(item.price).toLocaleString("en-US") }}&nbsp;₫
              </p>
              <p class="old-price" v-if="item.virtualPrice">
                {{ Number(item.virtualPrice).toLocaleString("en-US") }}&nbsp;₫
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import { axiosInstance } from "boot/axios";
import { LocalStorage } from "quasar";
import { Constants } from "boot/Constants";

export default defineComponent({
  name: "HomePage",
  data: function () {
    return {
      products: [],
    };
  },
  mounted: function () {
    this.getProducts();
  },
  methods: {
    getProducts: function () {
      var self = this;
      axiosInstance
        .get(process.env.API + "/products/10")
        .then((response) => {
          self.products = response.data.data.products;
        })
        .catch((error) => {});
    },
    goToDetail: function () {
      this.$router.push("/product-detail");
    },
    getDiscount: function (item) {
      return Math.floor(
        (100 * (item.virtualPrice - item.price)) / item.virtualPrice
      );
    },
  },
});
</script>