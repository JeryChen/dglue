<template>

  <div class="vSidebar">
    <div class="sidebar-header">
      <div class="collapse-btn">
        <img src="@/assets/imgs/logo.png" width="30" height="30">
        <span style="margin-left: 30px;" v-if="!collapse">Distributed Glue</span>
      </div>
    </div>
    <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#ffffff"
             text-color="black" active-text-color="#20a0ff" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.index">
            <template slot="title">
              <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
              <el-tag v-if="item.badge" disabled size="mini" style="margin-left: 20px;" effect="dark">
                {{item.badge}}
              </el-tag>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                <template slot="title">
                  <i :class="subItem.icon"></i><span>{{ subItem.title }}</span>
                </template>
                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :index="threeItem.index">
                  {{ threeItem.title }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subItem.index" :key="subItem.index">
                <i :class="subItem.icon"></i><span>{{ subItem.title }}</span>
                <el-tag v-if="subItem.badge" disabled size="mini"
                        style="margin-left: 5px;" effect="dark">
                  {{subItem.badge}}
                </el-tag>
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <span slot="title">
            {{ item.title }}
            </span>
            <el-tag v-if="item.badge" disabled size="mini" style="float: right; margin-right: 20px; margin-top: 18px;"
                    effect="dark">
              {{item.badge}}
            </el-tag>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>

  import bus from '../common/bus';

  export default {
    name: "Sidebar",

    data() {
      return {
        collapse: false,
        items: [
          {
            icon: 'el-icon-s-grid',
            index: 'glue',
            title: '规则管理',
          },
          {
            icon: 'el-icon-s-finance',
            index: 'app',
            title: '应用管理',
          }
        ]
      }
    },

    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    },

    computed: {
      onRoutes() {
        return this.$route.path.replace("/", "");
      }
    },

    created() {
      // 通过 Event Bus 进行组件间通信，来折叠侧边栏
      bus.$on('collapse', msg => {
        this.collapse = msg;
      })
    }

  }
</script>

<style scoped>

  .sidebar-header {
    box-sizing: border-box;
    height: 64px;
    font-size: 22px;
    background-color: #005eb6;
  }

  .vSidebar {
    display: block;
    position: absolute;
    bottom: 0;
    left: 0;
    top: 0;
    overflow-y: scroll;
    z-index: 2;
  }

  .vSidebar::-webkit-scrollbar {
    width: 0;
  }

  /*.vSidebar > ul {*/
  /*height: 100%;*/
  /*}*/

  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 300px;
  }

  .sidebar-el-menu {
    height: calc(100% - 64px);
  }

  .collapse-btn {
    float: left;
    line-height: 64px;
    padding: 0px 15px;
    font-size: 20px;
    color: #ffffff;
    font-weight: bolder;
    position: absolute;
    margin-top: 8px;
  }

</style>
