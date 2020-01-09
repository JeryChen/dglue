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
  import vHead from './Header'
  import vSidebar from './Sidebar'
  import bus from './bus';

  export default {
    name: 'Index',

    data() {
      return {
        collapse: false
      }
    },

    components: {
      vHead, vSidebar
    },

    created() {
      window.getApp = this

      bus.$on('collapse', msg => {
        this.collapse = msg;
      })
    }
  }
</script>

<style scoped>

</style>
