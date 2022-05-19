<template>
  <div class="q-body">
    <div class="lt-sm">
      <q-carousel swipeable animated v-model="slide" navigation infinite>
        <q-carousel-slide
          v-for="(rs, index) in resources"
          :key="index"
          :name="index"
          class="text-center"
        >
          <img :src="rs" style="height: 100%" />
        </q-carousel-slide>
      </q-carousel>
      <label for="" class="text-weight-bold size20">{{
        productInfo.name
      }}</label>

      <div class="">
        <label class="mr-1 text-weight-bold size18 text-danger"
          >{{ Number(productInfo.price).toLocaleString("en-US") }}đ</label
        >
        <label
          v-if="productInfo.virtualPrice > 0"
          class="mr-1 text-secondary size18"
          style="text-decoration: line-through"
          >{{
            Number(productInfo.virtualPrice).toLocaleString("en-US")
          }}đ</label
        >
        <label v-if="productInfo.virtualPrice > 0" class="text-success size18"
          >Giảm {{ getDiscount(productInfo) }}%</label
        >
      </div>

      <label for="" class="row text-weight-bold size18">Số lượng</label>
      <div class="minusplusnumber">
        <div class="mpbtn minus" v-on:click="mpminus()">-</div>
        <div id="field_container">
          <input type="number" v-model="newValue" disabled />
        </div>
        <div class="mpbtn plus" v-on:click="mpplus()">+</div>
      </div>
      <div bordered class="mt-2 bg-white text-center">
        <q-btn
          class="w-100"
          color="primary"
          label="THÊM VÀO GIỎ HÀNG"
          clickable
          :disabled="loading"
          @click="addToCart"
        />
      </div>
    </div>
    <div class="gt-xs">
      <div class="row">
        <div class="col-6">
          <q-carousel swipeable animated v-model="slide" navigation infinite>
            <q-carousel-slide
              v-for="(rs, index) in resources"
              :key="index"
              :name="index"
              class="text-center"
            >
              <img :src="rs" style="height: 100%" />
            </q-carousel-slide>
          </q-carousel>
        </div>
        <div class="col-6 mt-2">
          <div>
            <label for="" class="text-weight-bold size20">{{
              productInfo.name
            }}</label>
          </div>
          <label class="mr-1 text-weight-bold size18 text-danger"
            >{{ Number(productInfo.price).toLocaleString("en-US") }}đ</label
          >
          <label
            v-if="productInfo.virtualPrice > 0"
            class="mr-1 text-secondary size18"
            style="text-decoration: line-through"
            >{{
              Number(productInfo.virtualPrice).toLocaleString("en-US")
            }}đ</label
          >
          <label v-if="productInfo.virtualPrice > 0" class="text-success size18"
            >Giảm {{ getDiscount(productInfo) }}%</label
          >
          <label for="" class="row text-weight-bold size18">Số lượng</label>
          <div class="minusplusnumber">
            <div class="mpbtn minus" v-on:click="mpminus()">-</div>
            <div id="field_container">
              <input type="number" v-model="newValue" disabled />
            </div>
            <div class="mpbtn plus" v-on:click="mpplus()">+</div>
          </div>
          <div bordered class="mt-2 bg-white text-center">
            <q-btn
              class="w-100"
              color="primary"
              label="THÊM VÀO GIỎ HÀNG"
              clickable
              :disabled="loading"
              @click="addToCart"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, onBeforeUnmount } from "vue";
import { axiosInstance } from "boot/axios";
import { LocalStorage, useQuasar, QSpinnerIos } from "quasar";
import { Constants } from "boot/Constants";

export default defineComponent({
  setup() {
    const $q = useQuasar();

    let timer;

    onBeforeUnmount(() => {
      if (timer !== void 0) {
        clearTimeout(timer);
        $q.loading.hide();
      }
    });

    function showNotification(message, color) {
      $q.notify({
        message: message,
        color: color,
        progress: true,
        multiLine: true,
        timeout: 100,
        spinner: QSpinnerIos,
      });
    }

    function showLoadingWithMessage(message) {
      $q.loading.show({
        message: message,
        boxClass: "bg-grey-2 text-grey-9",
        spinnerColor: "primary",
      });
    }

    function hideLoading() {
      $q.loading.hide();
    }

    return {
      showNotification,
      showLoadingWithMessage,
      hideLoading,
    };
  },
  name: "ProductDetailPage",
  data: function () {
    return {
      loading: false,
      slide: 1,
      resources: [],
      newValue: 1,
      min: 1,
      max: 10,
      unit: 1,
      productInfo: {},
    };
  },
  mounted: function () {
    this.getProductDetail();
  },
  methods: {
    getProductDetail: function () {
      var self = this;
      axiosInstance
        .get("/product-detail")
        .then((response) => {
          self.productInfo = response.data.data.product;
          self.resources = response.data.data.resources;
        })
        .catch((error) => {});
    },
    mpplus: function () {
      if (this.newValue + this.unit <= this.max) {
        this.newValue = this.newValue + this.unit;
      }
    },
    mpminus: function () {
      if (this.newValue - this.unit > this.min) {
        this.newValue = this.newValue - this.unit;
      } else {
        this.newValue = this.min;
      }
    },
    getDiscount: function (item) {
      return Math.floor(
        (100 * (item.virtualPrice - item.price)) / item.virtualPrice
      );
    },
    addToCart: function () {
      var self = this;
      let product = {
        id: self.productInfo.id,
        name: self.productInfo.name,
        remainPrice: self.productInfo.price,
        price: self.productInfo.virtualPrice,
        discount: self.getDiscount(self.productInfo),
        image: self.productInfo.image,
        unit: self.unit,
        max: self.max,
      };

      self.$store.dispatch("cart/addProductToCart", {
        product: product,
        quantity: self.newValue,
      });

      self.loading = true;
      self.showNotification(
        "Thêm sản phẩm vào giỏ hàng thành công!",
        "success"
      );
      setTimeout(function () {
        self.loading = false;
      }, 1000);
    },
  },
});
</script>