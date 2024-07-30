import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import ElementUI, { Message } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en'

Vue.use(ElementUI, {locale});
Vue.config.productionTip = false;
Vue.prototype.$message = Message;

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");