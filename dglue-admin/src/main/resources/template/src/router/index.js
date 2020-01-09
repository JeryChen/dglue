import Vue from 'vue'
import Router from 'vue-router'
import Glues from '../components/page/Glues'
import Apps from '../components/page/Apps'
import Login from '../components/page/Login'
import Index from '../components/common/Index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    }, {
      path: '/login',
      component: Login,
      meta: {
        requireLogin: false
      }
    }, {
      path: '/index',
      component: Index,
      meta: {
        requireLogin: true
      },
      children: [
        {
          path: '/glue',
          component: Glues,
          meta: {
            requireLogin: true
          }
        }, {
          path: '/app',
          component: Apps,
          meta: {
            requireLogin: true
          }
        }
      ]
    }
  ]
})
