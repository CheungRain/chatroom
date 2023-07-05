<template>
  <div class="hello">
    <div class="container">
      <div class="card">
        <el-input class="input" v-model="account" placeholder="请输入数字" />
        <el-input class="input" show-password v-model="password" placeholder="请输入密码" />
        <el-button class="button" @click="postLogin">登录</el-button>
        <router-link to="register">注册</router-link>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "@/plugins/axios.js"
import { ref } from 'vue'
import router from "@/router/index.js"
import {ElMessage} from "element-plus";
const account = ref('')
const password = ref('')

const postLogin = () => {
  const url = '/login'
  const data = {};
  const config = {
    params: {
      account: account.value,
      password: password.value
    }
  }
  axios.post(url,data,config).then(response => {
    console.log(response.data)
    if(response.data=="ERROR"){
      ElMessage.error("账号或密码输入错误")
    }
    if(response.data=="SUCCESS"){
      const token = response.headers.get("Authorization")
      const account = response.headers.get("Account")
      sessionStorage.setItem("Authorization", token)
      sessionStorage.setItem("Account",account)
      ElMessage.success(account+": 登录成功")
      router.push('/chatRoom')
    }

  })
}
</script>

<style>
.hello {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: linear-gradient(45deg, #4158d0, #c850c0, #ffcc70);
  background-size: cover;
  background-repeat: no-repeat;
}

.container {
  width: 400px;
}

.card {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

.input {
  width: 100%;
  margin-bottom: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  padding: 8px;
  transition: border-color 0.3s;
}

.input:focus {
  outline: none;
  border-color: #409EFF;
  box-shadow: 0 0 3px rgba(64, 158, 255, 0.6);
}

.button {
  margin-top: 10px;
  background-color: #409EFF;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  transition: background-color 0.3s;
}

.button:hover {
  background-color: #66b1ff;
}

.button:active {
  background-color: #3a8ee6;
}

.button:focus {
  outline: none;
  box-shadow: 0 0 3px rgba(64, 158, 255, 0.6);
}
</style>

