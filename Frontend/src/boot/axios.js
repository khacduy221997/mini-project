import { boot } from 'quasar/wrappers'
import axios from 'axios'
import router from 'src/router';
import { LocalStorage } from 'quasar';
import { Constants } from "boot/Constants";

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const axiosInstance = axios.create({ baseURL: process.env.API})

axiosInstance.interceptors.request.use(function (config) {
    // const token = LocalStorage.getItem(Constants.TOKEN_KEY);
    // if (token) {
    //   config.headers.Authorization = "Bearer " + token;
    // }
  
    return config;
});
axiosInstance.interceptors.response.use(
  function (response) {
    // LoadingBar.stop();
    return response;
  },

  function (error) {
    if (error.response) {
      
      if (error.response.status == "403") {
        LocalStorage.clear();
        window.location = '/';
      } else if (error.response.data && error.response.data.message) {
        Notify.create({
          message: error.response.data.message,
          type: 'negative'
        });
      } else {
        Notify.create({
          message: error.response.statusText || error.response.status,
          type: 'negative'
        });
      }
    } else if (error.message.indexOf("timeout") > -1) {
      Notify.create({
        message: "Network timeout",
        type: 'negative'
      });
    } else if (error.message) {
      Notify.create({
        message: error.message,
        type: 'negative'
      });
    } else {
      Notify.create({
        message: "http request error",
        type: 'negative'
      });
    }

    return Promise.reject(error);
  }
);

export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = axiosInstance
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
})

export { axiosInstance }
