<template>
  <div class="wrapper">
    <v-head></v-head>
    <v-sidebar></v-sidebar>
    <div class="content-box content" :class="{'content-collapse':collapse}">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import vHead from './components/common/Header'
  import vSidebar from './components/common/Sidebar'
  import bus from './components/common/bus';

  export default {
    name: 'App',

    data() {
      return {
        collapse: false
      }
    },

    components: {
      vHead, vSidebar
    },

    created() {
      bus.$on('collapse', msg => {
        this.collapse = msg;
      })
    }
  }
</script>

<style>
  @import "./assets/css/main.css";
  @import "./assets/css/icon.css";
</style>
