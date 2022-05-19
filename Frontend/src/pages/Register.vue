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
          <h4 class="card-title mb-2 text-primary text-weight-bold">Đăng ký</h4>
          <q-form @submit="register">
            <q-input
              class="my-input mb-1"
              outlined
              label="Họ và tên *"
              placeholder="Nhập họ và tên"
              stack-label
              v-model="fullname"
              autocomplete="username"
              tabindex="1"
              lazy-rules
              :rules="[this.requiredFullname]"
              hide-bottom-space
              no-error-icon
            ></q-input>
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
              label="Mật khẩu *"
              placeholder="Nhập mật khẩu"
              stack-label
              v-model="password"
              :type="passwordType"
              autocomplete="current-password"
              tabindex="3"
              lazy-rules
              :rules="[
                this.requiredPassword,
                this.short,
                this.validatePassword,
              ]"
              hide-bottom-space
              no-error-icon
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
            <q-input
              class="my-input mb-1"
              outlined
              label="Xác nhận lại mật khẩu *"
              placeholder="Nhập lại mật khẩu"
              stack-label
              v-model="confirmPassword"
              :type="confirmPasswordType"
              autocomplete="current-password"
              tabindex="4"
              lazy-rules
              :rules="[this.requiredPassword, this.requiredConfirmPassword]"
              hide-bottom-space
              no-error-icon
            >
              <template v-slot:append>
                <q-btn
                  round
                  dense
                  flat
                  :icon="visibilityConfirmIcon"
                  @click="showConfirmPass"
                /> </template
            ></q-input>
            <q-btn
              class="mt-1 w-100"
              color="primary"
              label="Đăng Ký"
              type="submit"
              tabindex="6"
              :loading="processing"
              :disabled="!isValidToSend"
            />
            <div class="d-flex justify-content-center">
              <p class="text-center mt-2">
                <span>Bạn đã có tài khoản? </span>
                <a href="/login">
                  <span>Đăng nhập</span>
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
import { LocalStorage, useQuasar, QSpinnerIos } from "quasar";
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
        color: color,
        progress: true,
        multiLine: true,
        timeout: 200,
        spinner: QSpinnerIos,
      });
    }

    function showLoadingWithMessage(message) {
      $q.loading.show({
        message: message,
        boxClass: 'bg-grey-2 text-grey-9',
        spinnerColor: 'primary'
      });
    }

    function hideLoading() {
      $q.loading.hide();
    }

    return {
      showNotification,
      showLoadingWithMessage,
      hideLoading,
    }
  },
  name: "RegisterPage",
  data: function () {
    return {
      fullname: "Võ Khắc Duy",
      email: "khacduy221997@gmail.com",
      password: "Aa123456",
      confirmPassword: "Aa123456",
      showPassword: false,
      showConfirmPassword: false,
      isRemember: false,
      passwordType: "password",
      visibilityIcon: "visibility",
      confirmPasswordType: "password",
      visibilityConfirmIcon: "visibility",
      processing: false
    };
  },
  computed: {
    isValidToSend: function () {
      return this.isRequiredPassword && this.isValidPassword && this.isValidConfirmPassword;
    },
    isRequiredPassword: function () {
      return this.password.trim() != "" && this.confirmPassword.trim() != "" && this.fullname.trim() != "";
    },
    isValidPassword: function () {
      let regex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9\W]{8,32})$/;
      return this.password.trim().length >= 8 && this.password.trim().length <= 32 
          && regex.test(this.password);
    },
    isValidConfirmPassword: function () {
      return this.confirmPassword == this.password;
    },
  },
  methods: {
    showPass: function () {
      var self = this;
      self.showPassword = !self.showPassword;
      self.passwordType = self.showPassword ? "text" : "password";
      self.visibilityIcon = self.showPassword ? "visibility_off" : "visibility";
    },
    showConfirmPass: function () {
      var self = this;
      self.showConfirmPassword = !self.showConfirmPassword;
      self.confirmPasswordType = self.showConfirmPassword ? "text" : "password";
      self.visibilityConfirmIcon = self.showConfirmPassword
        ? "visibility_off"
        : "visibility";
    },
    requiredFullname: function (val) {
      return (val && val.length > 0) || "Vui lòng nhập họ và tên";
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
      return (val && val.length >= 8 && val.length <= 32) || "Mật khẩu của bạn phải có độ dài từ 8 đến 32 kí tự";
    },
    validatePassword: function (val) {
      var regex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9\W]{5,})$/;
      return (
        (val && regex.test(val)) || "Mật khẩu của bạn phải bao gồm kí tự thường, kí tự hoa và số"
      );
    },
    requiredConfirmPassword: function (val) {
      return (
        (val && val === this.password) || "Xác nhận lại mật khẩu mới chưa khớp"
      );
    },
    register: function () {
      var self = this;
      if (self.processing) {
        return;
      }
      self.processing = true;

      let inputData = {};
      inputData["full_name"] = self.fullname;
      inputData["login_name"] = self.email;
      inputData["password"] = self.password;
      axiosInstance
        .post("/auth/register", JSON.stringify(inputData), {
          headers: {
            "Content-type": "application/json",
          },
        })
        .then(() => {
            self.showNotification("Đăng ký thành công!", "success");
            self.$router.push("/");
            self.processing = false;
        })
        .catch((error) => {
            self.showNotification("Đăng ký thất bại!", "danger");
            self.processing = false;
        });
    },
  },
});
</script>