const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    navbar: path.resolve(__dirname, 'src', 'pages', 'navbar.js'),
    login: path.resolve(__dirname, 'src', 'pages', 'login.js'),
    loginPopup: path.resolve(__dirname, 'src', 'pages', 'loginPopup.js'),
    registration: path.resolve(__dirname, 'src', 'pages', 'registration.js'),
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8083,
    open: true,
    proxy: [
      {
        context: [
          '/example',
            '/User',
            '/Pantry',
            '/Recipe'
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    // reminder on adding pages from Jacobus
    new HtmlWebpackPlugin({
      template: './src/analytics.html',
      filename: 'analytics.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/pantry.html',
      filename: 'pantry.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/profile.html',
      filename: 'profile.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/recipe.html',
      filename: 'recipe.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/shared.html',
      filename: 'shared.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/registration.html',
      filename: 'registration.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/img'),
          to: path.resolve("dist/img")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
