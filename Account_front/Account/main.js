
// #ifndef VUE3
import Vue from 'vue'
import App from './App'

//引入tmVuetify
import tmVuetify from "./tm-vuetify";
Vue.use(tmVuetify)

//引入moment
import moment from 'moment';
Vue.prototype.$moment = moment;

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
import App from './App.vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif