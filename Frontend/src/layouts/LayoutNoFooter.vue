<template>
  <q-layout view="hHh LpR lFr">
    <q-header elevated>
      <q-toolbar class="bg-white logo">
        <q-toolbar-title>
          <a href="/"><img src="../assets/logo.png" /></a>
          <span class="gt-xs">
            <q-btn
              class="icon mr-2"
              flat
              round
              dense
              icon="shopping_cart"
              to="/cart"
              label="Giỏ hàng"
              no-caps
            >
              <q-badge rounded color="red" floating v-if="cartItemCount > 0">{{
                cartItemCount
              }}</q-badge>
            </q-btn>
            <q-btn
              class="icon"
              flat
              round
              dense
              icon="person"
              :to="linkInfo"
              label="Thông tin"
              no-caps
            />
          </span>
        </q-toolbar-title>

        <div class="lt-sm">
          <q-btn class="icon" flat round dense icon="shopping_cart" to="/cart">
            <q-badge rounded color="red" floating v-if="cartItemCount > 0">{{
              cartItemCount
            }}</q-badge>
          </q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { defineComponent } from "vue";
import { mapGetters, mapActions, mapState } from "vuex";
import { LocalStorage } from "quasar";
import { Constants } from "boot/Constants";

export default defineComponent({
  name: "MainLayout",
  computed: {
    ...mapState("cart", ["cart"]),
    ...mapGetters("cart", ["cartItemCount"]),
  },
  mounted: function () {
    this.getCartItems();
  },
  methods: {
    ...mapActions("cart", ["getCartItems"]),
  },
});
</script>
