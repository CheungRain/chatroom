import { createApp } from 'vue'
import App from '@/App.vue'
import ElementPlus from 'element-plus'  // 引入Element Plus 所需
import 'element-plus/dist/index.css'  // 引入Element Plus 所需
import router from '@/router/index.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'  // 引入Element Plus icon 所需
import axios from '@/plugins/axios.js'
import store from '@/store/index.js'
const app = createApp(App)

// 引入Element Plus icon 所需


app.use(router)
app.use(ElementPlus)// 引入Element Plus 所需
app.use(store)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.config.globalProperties.$http=axios
app.mount('#app')