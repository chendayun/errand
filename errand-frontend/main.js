import App from './App'

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})

//下面两个是必须的组件
import uniIcons from '@/components/uni-icons/uni-icons.vue'
Vue.component('uniIcons',uniIcons)
import hxNavbar from "@/components/hx-navbar/hx-navbar.vue"
Vue.component('hx-navbar',hxNavbar)
//end

app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif


