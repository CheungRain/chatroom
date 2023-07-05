const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    //代理axios
    proxy: 'http://192.168.1.11:1113',
    //vue自己启动的端口
    port:1112
  }
})
