module.exports = {
  publicPath: '/craman/',

  pluginOptions: {
    quasar: {
      rtlSupport: true,
      treeShake: true
    }
  },
  devServer: {
    proxy: 'http://localhost:8083/craman',
  },
  transpileDependencies: [
    /[\\\/]node_modules[\\\/]quasar[\\\/]/
  ]
}
