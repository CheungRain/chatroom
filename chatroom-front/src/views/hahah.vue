<template>
  <div class="chat-frame">
    <div class="message-container">
      <div v-for="message in messages" :key="message.id" class="message" :class="{'received': message.received, 'sent': !message.received}">
        <p class="content">{{ message.content }}</p>
      </div>
    </div>
  </div>
</template>

<style>
.chat-frame {
  background-color: #f6f6f6;
  padding: 20px;
  max-width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}

.message-container {
  display: flex;
  flex-direction: column;
}

.message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
  color: #fff;
  word-wrap: break-word;
}

.received {
  background-color: #0099ff;
  align-self: flex-start;
  width: 70%;
  max-width: 300px;
}

.sent {
  background-color: #4caf50;
  align-self: flex-end;
  margin-left: auto;
  width: 70%;
  max-width: 300px;
}

.content {
  white-space: pre-wrap;
}
</style>

<script setup>
import { reactive } from 'vue';

const messages = reactive([
  { id: 1, content: 'Hello', received: true },
  { id: 2, content: 'Hi, this is a longer message that will automatically increase the height of the chat bubble as the content grows.', received: false },
  { id: 3, content: 'Hello', received: false },
  { id: 4, content: 'Hi, this is a longer message that will automatically increase the height of the chat bubble as the content grows.', received: true },
  { id: 1, content: 'Hello', received: true },
  { id: 2, content: 'Hi, this is a longer message that will automatically increase the height of the chat bubble as the content grows.', received: false },
]);
</script>