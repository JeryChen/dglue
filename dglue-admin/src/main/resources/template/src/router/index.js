import Vue from 'vue'
import Router from 'vue-router'
import Glues from '../components/page/Glues'
import Apps from '../components/page/Apps'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/glue'
    }, {
      path: '/app',
      component: Apps
    }, {
      path: '/glue',
      component: Glues
    }
  ]
})
