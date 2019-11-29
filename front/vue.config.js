module.exports = {
  publicPath: '/craman/',

  pluginOptions: {
    quasar: {
      rtlSupport: true,
      treeShake: true
    }
  },
  devServer: {
    proxy: 'http://localhost:8082/craman',
    auth: {
      username: '',
      password: ''
    }
  },
  transpileDependencies: [
    /[\\\/]node_modules[\\\/]quasar[\\\/]/
  ]
}
