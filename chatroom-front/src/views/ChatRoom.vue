<template>
  <div class="all-con">
    <div class="user-account">当前用户: {{ account }}</div>
    <div class="msg-con">
      <div class="messages">
        <div v-for="msg in messages" :key="msg.id" class="msg" :class="[msg.received ? 'received' : 'sent']">
          <p class="sender">{{ msg.sender }}---{{ msg.time }}</p>
          <p class="content">{{ msg.content }}</p>
        </div>
      </div>
    </div>
    <div class="input-container">
      <el-input class="input" v-model="inputData" placeholder="请输入要发送的消息" />
      <el-button class="send-button" @click="sendMessage">发送</el-button>
      <el-select class="select-form" v-model="selectForm" placeholder="选择发送人">
        <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value" />
      </el-select>
    </div>
  </div>
</template>

<style>
.all-con{
}
.user-account {
  font-size: 24px;
  font-weight: bold;
  background-color: #4b7ba2;
  color: white;
}

.messages {
  overflow-y: auto;
  max-height:350px;
  padding: 10px;
}
.msg-con{
  height: 350px;
}
.msg {
  background-color: #0099ff;
  border-radius: 15px;
  margin-bottom: 10px;
}
.received {
  background-color: #0099ff;
  align-self: flex-start;
  margin-right: auto;
  width: 50%;
  max-width: 100%;
  word-wrap: break-word;
}

.sent {
  background-color: #4caf68;
  align-self: flex-start;
  margin-left: auto;
  width: 50%;
  max-width: 100%;
  word-wrap: break-word;
}
.sender {
  font-size: 12px;
  font-weight: bold;
  color: #a24b62;
  margin: 0 0 5px;
}

.content {
  font-size: 14px;
  line-height: 1.5;
  margin: 5px 0;
}

.input-container {
  /*display: flex;*/
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.input {
  flex: 1;
}
.input:hover{
  background-color: rgba(102, 234, 223, 0.77);
}
.send-button {
  width: 80px;
  background-color: #ff4081;

  border: none;
  border-radius: 5px;
}

.select-form {
  width: 150px;
  border: none;
  border-radius: 5px;
  background-color: #333;

}
</style>

<script setup>
import {ElMessage} from "element-plus"
import {onMounted, onUpdated, reactive, ref} from "vue"
import axios from "@/plugins/axios"
const options = reactive([])
const selectForm = ref('')
const inputData = ref('')
const account = sessionStorage.getItem("Account")
const accountSet = new Set()
let message = reactive({
  fromWho: "",
  toWho: "",
  text: "",
  date: new Date()
})
const sendMessage = () => {
  message.fromWho = account
  message.text = inputData.value
  message.toWho = selectForm.value
  message.date = new Date()
  if(message.toWho&&message.text) {
    ws.send(JSON.stringify(message))
    const msg = {sender:account,time:message.date,content: message.text,received: false}
    messages.push(msg)
    inputData.value = ""
  }
}
const messages = reactive([])
const ws = new WebSocket("ws://192.168.1.11:1113/webSocket/"+account)
//连接建立时
ws.onopen = function (evt){
  console.log("connect")
  const url = "/onlineUsers"
  const data = {}
  const config = {
    params: {
      account: account
    }
  }
  axios.post(url,data,config)
      .then(response => {
        const nameSet = response.data
        if(nameSet){
          for(let valueAccount of nameSet) {
            const newOption = {label: valueAccount,value: valueAccount}
            if(!accountSet.has(valueAccount)){
              options.push(newOption)
              accountSet.add(valueAccount)
            }
          }
        }
      })
}

//接收到服务器消息时
ws.onmessage = function (evt){
  message = JSON.parse(evt.data)
  if(message.fromWho=="system_connect_open") {
    if(message.text!=account) {
      ElMessage.info(message.text + "上线了")
      const newOption = {label: message.text, value: message.text}
      if (!accountSet.has(message.text)) {
        options.push(newOption)
      }
      accountSet.add(message.text)
    }
  }else if(message.fromWho=="system_connect_close") {
    ElMessage.info(message.text+"下线了")
    accountSet.delete(message.text)
    for (const option of options) {
      const index = options.indexOf(option);
      if (option.label === message.text) {
        options.splice(index,1);
        selectForm.value = ""
      }
    }
  }else{
    const msg = {sender:message.fromWho,time:new Date(),content: message.text,received: true}
    messages.push(msg)
  }
}
//连接断开时
ws.onclose = function (evt){
  console.log("close")
}
//发生错误时
ws.onerror = function (evt){
  console.log("error")
}

</script>
