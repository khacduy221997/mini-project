<template>
  <div class="q-body">
    <div class="lt-sm mt-1">
      <div class="d-flex">
        <a href="/" style="color: #343c44">
          <small>
            <q-icon name="chevron_left" />
            Tiếp tục mua hàng</small
          >
        </a>
      </div>
      <div class="row mt-1 d-flex align-items-center">
        <label for="" class="text-weight-bold size14">
          <q-icon color="primary" name="shopping_cart" /> Giỏ hàng của
          bạn</label
        >
      </div>
      <q-list v-if="cart && cart.length > 0">
        <q-item v-for="item in cart" :key="item.product.id">
          <q-item-section top thumbnail class="q-ml-none">
            <img
              :src="item.product.image"
              style="width: 70px; height: 70px; border-radius: 8px"
            />
          </q-item-section>

          <q-item-section
            ><label class="text-weight-bold">{{ item.product.name }}</label>
            <p>
              <label class="mr-1"
                >{{
                  Number(item.product.remainPrice).toLocaleString("en-US")
                }}đ</label
              >
              <label
                v-if="
                  item.virtualPrice != 0 && item.virtualPrice != item.unitPrice
                "
                class="mr-1 text-secondary"
                style="text-decoration: line-through"
                >{{
                  Number(item.product.price).toLocaleString("en-US")
                }}đ</label
              >
              <label
                v-if="
                  item.virtualPrice != 0 && item.virtualPrice != item.unitPrice
                "
                class="text-success"
                >Giảm {{ getDiscount(item.product) }}%</label
              >
            </p>
            <div class="row">
              <div class="minusplusnumber">
                <q-btn
                  class="mpbtn minus"
                  label="-"
                  clickable
                  @click="mpminus(item.product, false)"
                  :disabled="processing"
                />
                <div id="field_container">
                  <input type="number" v-model="item.quantity" disabled />
                </div>
                <q-btn
                  class="mpbtn plus"
                  style="right: -3px"
                  label="+"
                  clickable
                  @click="mpplus(item.product)"
                  :disabled="processing"
                />
              </div>
              <label
                class="text-primary tex-center d-flex align-items-center ml-1"
                >có {{ item.product.max }} sản phẩm</label
              >
            </div>
          </q-item-section>
        </q-item>
      </q-list>
      <div class="text-center" v-if="!cart || cart.length == 0">
        <q-img src="~assets/no-cart.png" />
        <p>Giỏ hàng trống</p>
        <q-btn
          class="w-70 text-weight-bold mt-1 mb-1"
          color="primary"
          label="TIẾP TỤC MUA HÀNG"
          clickable
          @click="goHome"
        />
      </div>
      <q-separator
        size="7px"
        class="mt-1 mb-1 margin-custom"
        style="background: rgba(192, 192, 192, 0.2)"
      />
      <div v-if="cart && cart.length > 0">
        <div class="d-flex align-items-center">
          <label for="" class="text-weight-bold w-100 size14"
            ><q-icon color="primary" name="payment" /> Tóm tắt thanh toán</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row size14">Tạm tính</label>
          <label for="" class="row size14"
            >{{ Number(cartTotalPrice).toLocaleString("en-US") }}đ</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row size14">Tổng giảm giá</label>
          <label for="" class="row size14"
            >{{ Number(discount).toLocaleString("en-US") }}đ</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row text-primary text-weight-bold size18"
            >Tổng giá trị đơn hàng</label
          >
          <label for="" class="row text-danger text-weight-bold size18"
            >{{
              Number(cartTotalPrice - discount).toLocaleString("en-US")
            }}đ</label
          >
        </div>
      </div>
    </div>
    <div class="gt-xs mt-1">
      <div class="d-flex">
        <a href="/" style="color: #343c44">
          <q-icon name="chevron_left" />
          Tiếp tục mua hàng
        </a>
      </div>
      <div class="row mt-1 d-flex align-items-center">
        <label for="" class="text-weight-bold size20">
          <q-icon color="primary" name="shopping_cart" /> Giỏ hàng của
          bạn</label
        >
      </div>
      <q-list v-if="cart && cart.length > 0">
        <q-item v-for="item in cart" :key="item.product.id">
          <q-item-section top thumbnail class="q-ml-none">
            <img
              :src="item.product.image"
              style="width: auto; height: 200px; border-radius: 8px"
            />
          </q-item-section>

          <q-item-section
            ><label class="text-weight-bold size20">{{ item.product.name }}</label>
            <p>
              <label class="mr-1 size20"
                >{{
                  Number(item.product.remainPrice).toLocaleString("en-US")
                }}đ</label
              >
              <label
                v-if="
                  item.virtualPrice != 0 && item.virtualPrice != item.unitPrice
                "
                class="mr-1 text-secondary size20"
                style="text-decoration: line-through"
                >{{
                  Number(item.product.price).toLocaleString("en-US")
                }}đ</label
              >
              <label
                v-if="
                  item.virtualPrice != 0 && item.virtualPrice != item.unitPrice
                "
                class="text-success size20"
                >Giảm {{ getDiscount(item.product) }}%</label
              >
            </p>
            <div class="row">
              <div class="minusplusnumber">
                <q-btn
                  class="mpbtn minus"
                  label="-"
                  clickable
                  @click="mpminus(item.product, false)"
                  :disabled="processing"
                />
                <div id="field_container">
                  <input type="number" v-model="item.quantity" disabled />
                </div>
                <q-btn
                  class="mpbtn plus"
                  style="right: -3px"
                  label="+"
                  clickable
                  @click="mpplus(item.product)"
                  :disabled="processing"
                />
              </div>
            </div>
          </q-item-section>
        </q-item>
      </q-list>
      <div class="text-center" v-if="!cart || cart.length == 0">
        <q-img src="~assets/no-cart.png" style="height: 300px;"/>
        <p>Giỏ hàng trống</p>
        <q-btn
          class="w-70 text-weight-bold mt-1 mb-1"
          color="primary"
          label="TIẾP TỤC MUA HÀNG"
          clickable
          @click="goHome"
        />
      </div>
      <q-separator
        size="7px"
        class="mt-1 mb-1 margin-custom"
        style="background: rgba(192, 192, 192, 0.2)"
      />
      <div v-if="cart && cart.length > 0">
        <div class="d-flex align-items-center">
          <label for="" class="text-weight-bold w-100 size20"
            ><q-icon color="primary" name="payment" /> Tóm tắt thanh toán</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row size20">Tạm tính</label>
          <label for="" class="row size20"
            >{{ Number(cartTotalPrice).toLocaleString("en-US") }}đ</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row size20">Tổng giảm giá</label>
          <label for="" class="row size20"
            >{{ Number(discount).toLocaleString("en-US") }}đ</label
          >
        </div>
        <div class="d-flex justify-content-between">
          <label for="" class="row text-primary text-weight-bold size20"
            >Tổng giá trị đơn hàng</label
          >
          <label for="" class="row text-danger text-weight-bold size20"
            >{{
              Number(cartTotalPrice - discount).toLocaleString("en-US")
            }}đ</label
          >
        </div>
      </div>
    </div>
    <q-footer
      v-if="cart && cart.length > 0"
      bordered
      class="bg-white text-center"
    >
      <div class="d-flex justify-content-between" style="padding: 15px 50px">
        <div>
          <p class="text-dark">Tổng thanh toán</p>
          <p class="text-danger text-weight-bold">
            {{ Number(cartTotalPrice - discount).toLocaleString("en-US") }}đ
          </p>
        </div>
        <q-btn
          color="primary"
          :label="authenticated ? 'Đặt hàng' : 'Đăng nhập'"
          clickable
          class="font-weight-bolder"
          :loading="processing"
          @click="goToOrderDetail()"
        />
      </div>
    </q-footer>
    <q-dialog v-model="notification" @hide="closeModal()">
      <q-card class="text-center" style="width: 285px">
        <q-card-section>
          <div class="text-primary text-weight-bold">
            Xóa sản phẩm khỏi đơn hàng
          </div>
        </q-card-section>
        <q-card-section class="row items-center">
          <span class="q-ml-sm" style="width: 100%; font-size: 14px">
            Bản có muốn xóa sản phẩm này ra khỏi đơn hàng?
          </span>
        </q-card-section>
        <q-card-section>
          <q-btn
            color="secondary"
            label="Hủy"
            clickable
            @click="closeModal()"
          />
          <q-btn
            color="primary"
            label="Xác nhận"
            class="ml-1"
            clickable
            @click="mpminus(tmpProduct, true)"
          />
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { defineComponent, onBeforeUnmount } from "vue";
import { axiosInstance } from "boot/axios";
import { LocalStorage, useQuasar, QSpinnerIos } from "quasar";
import { mapGetters } from "vuex";
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
        timeout: 500,
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
  name: "Giỏ hàng",
  data: function () {
    return {
      min: 0,
      notification: false,
      tmpProduct: null,
      processing: false,
      error: "",
      totalItems: [],
      cart: [],
      authenticated: false,
      selectedRetailerId: 0,
      defaultAddress: null,
      discount: 0.0,
      rewardItems: [],
      errorInactive: "",
    };
  },
  computed: {
    ...mapGetters("cart", ["cartTotalPrice"]),
  },
  mounted: function () {
    var self = this;
    self.cart = LocalStorage.getItem("cart");

    let token = LocalStorage.getItem(Constants.TOKEN_KEY);
    let consumer = LocalStorage.getItem(Constants.CONSUMER_INFO_KEY);
    self.authenticated =
      token != null &&
      consumer != null &&
      token != undefined &&
      consumer != undefined;
  },
  methods: {
    mpplus: function (product) {
      this.$store.dispatch("cart/increaseQuantity", {
        product: product,
      });

      this.cart = LocalStorage.getItem("cart");
    },
    mpminus: function (product, confirmed) {
      if (!confirmed) {
        let productInCart = this.cart.find((item) => {
          return item.product.id === product.id;
        });

        if (productInCart.quantity == productInCart.product.unit) {
          this.notification = true;
          this.tmpProduct = product;
        }
      }

      if (!this.notification || confirmed) {
        this.tmpProduct = null;
        this.notification = false;
        this.$store.dispatch("cart/decreaseQuantity", {
          product: product,
        });

        this.cart = LocalStorage.getItem("cart");
        this.checkPromotion();
      }
    },
    goToOrderDetail: function () {
      var self = this;
      if (self.processing) {
        return;
      }
      if (!self.authenticated) {
        LocalStorage.set(Constants.CALLBACK_TO_APP_KEY, "/cart");
        self.$router.push("/login");
        return;
      }
      self.processing = true;
      self.showLoadingWithMessage("Đang đặt hàng...");
      self.error = "";

      let items = [];
      for (const pr of self.cart) {
        let obj = {};
        obj.productVariationId = pr.product.id;
        obj.amount = pr.quantity;
        items.push(obj);
      }

      let inputData = {};
      inputData["customerId"] = 1;
      inputData["products"] = items;
      axiosInstance
        .post(process.env.API + "/orders", JSON.stringify(inputData), {
          headers: {
            "Content-type": "application/json",
          },
        })
        .then(() => {
          self.processing = false;
          self.error = "";
          self.hideLoading();
          self.$router.push("/checkout");
        })
        .catch((error) => {});
    },
    getDiscount: function (item) {
      let result = Math.floor(
        (100 * (item.price - item.remainPrice)) / item.price
      );
      return result;
    },
    goHome: function () {
      this.$router.push("/");
    },
  },
});
</script>