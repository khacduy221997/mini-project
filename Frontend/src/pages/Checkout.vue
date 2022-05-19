<template>
  <div class="q-body">
    <div class="text-center mt-1">
      <q-icon
        name="check"
        color="success"
        style="
          font-size: 2rem;
          background-color: rgba(40, 199, 111, 0.12);
          padding: 1rem;
          border-radius: 50%;
        "
      />
      <p class="mt-1 text-success text-weight-bold" style="font-size: 20px">
        Đặt hàng thành công !
      </p>
      <p>Bạn đã đặt hàng thành công, đơn hàng</p>
      <p>của bạn đang được chuẩn bị.</p>
    </div>
    <label for="" class="row mt-1 mb-1 text-weight-bold size20">Thông tin đặt hàng</label>
    <div>
      <div for="" class="row text-color">
        <q-icon name="fas fa-barcode" class="mr-custom mb-1" style="font-size: 30px;"/>
        <label class="size20">MST1000928</label>
      </div>
      <div for="" class="row text-color">
        <q-icon name="schedule" class="mr-custom mb-1" style="font-size: 30px;"/>
        <label class="size20">20/05/2022</label>
      </div>
      <div for="" class="row text-color">
        <q-icon name="person" class="mr-custom mb-1" style="font-size: 30px;" />
        <label class="size20">Võ Khắc Duy</label>
      </div>
      <div for="" class="row text-color">
        <q-icon name="phone" class="mr-custom mb-1" style="font-size: 30px;"/>
        <label class="size20">0398092287</label>
      </div>
      <div for="" class="row text-color">
        <q-icon name="location_on" class="mr-custom" style="font-size: 30px;" />
        <label class="size20" style="width: 90%">123 Trần Văn Hoàng, p9, Tân Bình, TPHCM</label>
      </div>
    </div>
    <q-separator
      size="7px"
      class="mt-1 mb-1 margin-custom"
      style="background: rgba(192, 192, 192, 0.2)"
    />
    <label for="" class="text-weight-bold size20">
      <q-icon
        color="primary"
        name="shopping_cart"
      /> Tóm tắt đơn hàng</label>
    <q-list v-if="items && items.length > 0">
      <q-item v-for="item in items" :key="item.product.id">
        <q-item-section top thumbnail class="q-ml-none">
          <img
            :src="item.product.image"
          />
        </q-item-section>

        <q-item-section
          ><label class="text-weight-bold size20">{{ item.product.name }}</label>
          <p>
            <label class="mr-1"
              >{{ Number(item.product.remainPrice).toLocaleString("en-US") }}đ</label
            >
            <label v-if="item.virtualPrice != 0 && item.virtualPrice != item.unitPrice"
              class="mr-1 text-secondary size20"
              style="text-decoration: line-through">{{ Number(item.product.price).toLocaleString("en-US") }}đ
            </label>
            <label v-if="item.virtualPrice != 0 && item.virtualPrice != item.unitPrice" class="text-success">Giảm {{ getDiscount(item) }}%</label>
          </p>
          <label for="" class="row size20"
            >{{ item.quantity }} sản phẩm<label class="ml-1 mr-1">|</label>
            <label class="text-primary text-weight-bold size20"
              >{{ Number(item.quantity * item.product.remainPrice).toLocaleString("en-US") }}đ</label
            ></label
          >
        </q-item-section>
      </q-item>
    </q-list>
    <q-footer bordered class="bg-white text-center">
      <q-btn
        class="w-100 text-weight-bold"
        color="primary"
        label="TIẾP TỤC MUA HÀNG"
        clickable
        @click="goHome"
      />
    </q-footer>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import { LocalStorage } from "quasar";

export default defineComponent({
  name: "CheckoutPage",
  data: function () {
    return {
      totalPrice: 0,
      order: {},
      items: [],
      rewardItems: []
    };
  },
  computed: {
  },
  mounted: function() {
    this.getCheckoutInfo();
  },
  methods: {
    getCheckoutInfo: function () {
      this.items = LocalStorage.getItem("cart");
      
      if (this.order == null) {
        this.$router.push("/");
        return;
      }

      this.totalPrice = this.order.finalCost;

      this.$store.dispatch("cart/clearCart", {});
    },
    goHome: function () {
      this.$router.push("/");
    },
    getDiscount: function(item) {
      return Math.floor(100 * (item.product.price - item.product.remainPrice) / item.product.price);
    },
  },
});
</script>