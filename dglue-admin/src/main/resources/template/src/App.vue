<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    created () {
      window.getApp = this
    },
    name: 'App'
  }
</script>

<style>
  @import "./assets/css/main.css";
  @import "./assets/css/icon.css";
</style>
