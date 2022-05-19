<style lang="scss">
@import "src/css/vue/pages/page-auth.scss";
</style>

<template>
  <div class="auth-wrapper auth-v1">
    <div class="auth-inner py-2">
      <div class="card mb-0">
        <div class="card-body mb-0">
          <a href="javascript:void(0);" class="brand-logo">
            <img src="../assets/logo.png" style="height: 55px;"/>
          </a>
          <h4 class="card-title mb-2 text-primary text-weight-bold">
            Đăng nhập
          </h4>
          <q-form @submit="login">
            <q-input
              class="my-input mb-1"
              outlined
              label="Địa chỉ email *"
              placeholder="Nhập địa chỉ email đăng nhập"
              stack-label
              v-model="email"
              autocomplete="username"
              tabindex="2"
              lazy-rules
              :rules="[this.requiredEmail, this.isEmail]"
              hide-bottom-space
              no-error-icon
            ></q-input>
            <q-input
              class="my-input mb-1"
              outlined
              label="Mật khẩu"
              placeholder="Nhập mật khẩu"
              stack-label
              v-model="password"
              :type="passwordType"
              autocomplete="current-password"
              lazy-rules
              hide-bottom-space
              no-error-icon
              :rules="[this.requiredPassword, this.short, this.passwordError]"
            >
              <template v-slot:append>
                <q-btn
                  round
                  dense
                  flat
                  :icon="visibilityIcon"
                  @click="showPass"
                /> </template
            ></q-input>
            <div class="d-flex justify-content-between">
              <a href="/forgot-password">
                <small>Quên mật khẩu?</small>
              </a>
            </div>
            <q-btn
              class="mt-1 w-100"
              color="primary"
              label="Đăng nhập"
              type="submit"
              :disable="processing"
              :loading="processing"
            />
            <div class="d-flex justify-content-center">
              <p class="text-center mt-2">
                <span>Bạn chưa có tài khoản? </span>
                <a href="/register">
                  <span>Đăng ký</span>
                </a>
              </p>
            </div>
            <div class="d-flex justify-content-center mt-2">
              <a href="/">
                <small>
                  <q-icon name="chevron_left" />
                  Trở về trang chủ</small
                >
              </a>
            </div>
          </q-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, onBeforeUnmount } from "vue";
import { axiosInstance } from "boot/axios";
import { LocalStorage, useQuasar, QSpinnerComment, QSpinnerIos } from "quasar";
import { Constants } from "src/boot/Constants";

export default defineComponent({
  setup() {
    const $q = useQuasar();

    let timer;

    onBeforeUnmount(() => {
      if (timer !== void 0) {
        clearTimeout(timer)
        $q.loading.hide()
      }
    });

    function showNotification (message, color) {
      $q.notify({
        message: message,
        position: "center",
        color: color,
        progress: true,
        multiLine: true,
        timeout: 200,
        spinner: QSpinnerComment,
      });
    }

    function showLoadingWithMessage(message) {
      $q.loading.show({
        boxClass: 'bg-grey-2 text-grey-9',
        spinnerColor: 'primary',
        spinner: QSpinnerIos
      });
    }

    function hideLoading() {
      $q.loading.hide();
    }

    return {
      showLoadingWithMessage,
      hideLoading,
      showNotification
    }
  },
  name: "LoginPage",
  data: function () {
    return {
      email: "khacduy221997@gmail.com",
      password: "Aa123456",
      showPassword: false,
      passwordType: "password",
      visibilityIcon: "visibility",
      processing: false
    };
  },
  mounted: function () {},
  computed: {},
  methods: {
    showPass: function () {
      var self = this;
      self.showPassword = !self.showPassword;
      self.passwordType = self.showPassword ? "text" : "password";
      self.visibilityIcon = self.showPassword ? "visibility_off" : "visibility";
    },
    requiredEmail: function (val) {
      return (val && val.length > 0) || "Vui lòng nhập địa chỉ email";
    },
    isEmail: function (val) {
      var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return (
        (val && regex.test(val)) ||
        "Địa chỉ email không đúng định dạng."
      );
    },
    requiredPassword: function (val) {
      return (val && val.length > 0) || "Vui lòng nhập mật khẩu";
    },
    short: function (val) {
      return (val && val.length >= 5) || "Mật khẩu của bạn phải có 5 kí tự";
    },
    passwordError: function () {
      return (
        this.passwordError.length == 0 ||
        "Mật khẩu không đúng, vui lòng nhập lại mật khẩu"
      );
    },
    login: function () {
      var self = this;
      if (self.processing) {
        return;
      }
      self.processing = true;
      self.showLoadingWithMessage("Đăng nhập")

      var inputData = {};
      inputData["loginType"] = 1;
      inputData["loginName"] = self.email;
      inputData["password"] = self.password;
      axiosInstance
        .post("/auth/login", JSON.stringify(inputData), {
          headers: {
            "Content-type": "application/json",
          },
        })
        .then((response) => {
          self.loginSuccess(response);
          self.processing = false;
          self.hideLoading();
        })
        .catch((error) => {
          self.processing = false;
          self.hideLoading();
          self.showNotification("Thông tin đăng nhập không đúng.", "danger")
        });
    },
    loginSuccess: function (response) {
      LocalStorage.set(Constants.TOKEN_KEY, response.data.data.token);
      LocalStorage.set(Constants.CONSUMER_INFO_KEY, response.data.data.customer);

      let url = LocalStorage.getItem(Constants.CALLBACK_TO_APP_KEY);
      if (url != null && url != "") {
        LocalStorage.remove(Constants.CALLBACK_TO_APP_KEY);
      } else {
        url = "/";
      }
      this.$router.push(url);
    },
  },
});
</script>
