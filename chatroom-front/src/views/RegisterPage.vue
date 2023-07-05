<template>
  <div class="hello">
    <div class="container">
      <div class="card">
        <el-input
            class="input"
            v-model="account"
            placeholder="请输入账号"
            size="large"
        clearable
        :prefix-icon="'el-icon-user'"
        />
        <el-input
            class="input"
            show-password
            v-model="password"
            placeholder="请输入密码"
            size="large"
        clearable
        :prefix-icon="'el-icon-lock'"
        />
        <el-button
            class="button"
            @click="postRegister"
            type="primary"
        round
        :loading="loading"
        >注册
        </el-button>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "@/plugins/axios"
import router from "@/router/index"
import {ref} from "vue"
import {ElMessage} from "element-plus";

const account = ref('')
const password = ref('')

const postRegister = () => {
  const url = '/register'
  const data = {}
  const config = {
    params: {
      account: account.value,
      password: password.value
    }
  }
  axios.post(url,data,config)
      .then(response => {
        console.log(response.data)
        if(response.data=="SUCCESS"){
          ElMessage.success("注册成功")
          router.push('/login')
        }
        if(response.data=="ERROR"){
          ElMessage.error("账号已存在")
        }
      })
      .catch(error => {
        console.log(error)
      })
}
</script>

<style Reset>
.hello {

  background: linear-gradient(135deg, #00ffb2, #09c4ee, #218af3); /* 使用线性渐变作为背景 */
  background-size: contain; /* 设置渐变的大小 */
  animation: gradientAnimation 10s ease infinite; /* 使用动画库添加动画效果 */
}

@keyframes gradientAnimation {
  0% {
    background-position: 0% 50%; /* 背景位置起始点 */
  }
  50% {
    background-position: 100% 50%; /* 背景位置中点 */
  }
  100% {
    background-position: 0% 50%; /* 背景位置结束点 */
  }
}
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.card {
  width: 300px;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.input {
  margin-bottom: 20px;
}

.button {
  width: 100%;
}

</style>