<template>
  <div class="vHeader">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" :class="{'collapse-btn-left':collapse}">
      <i class="el-icon-menu" @click="collapseChange"></i>
    </div>
    <div class="header-right">

      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
          <i class="el-icon-full-screen"></i>
        </el-tooltip>
      </div>

      <!--<div class="header-user-con">-->
      <!-- 用户头像 -->
      <!--<div class="user-avator"><img src="../../assets/logo.png"></div>-->
      <!--</div>-->

      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
         <span class="el-dropdown-link">
           {{username}} <i class="el-icon-caret-bottom"></i>
         </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item divided command="changePassword">修改密码</el-dropdown-item>
          <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>

  import bus from '../common/bus';

  export default {
    name: "Header",

    data() {
      return {
        fullscreen: false,
        name: '杰瑞',
        collapse: true,
      }
    },

    computed: {
      username() {
        let username = localStorage.getItem('ms_username');
        return username ? username : this.name;
      }
    },

    methods: {

      collapseChange() {
        this.collapse = !this.collapse;
        bus.$emit('collapse', this.collapse);
      },

      handleCommand(command) {
        if (command == 'loginout') {
          localStorage.removeItem('ms_username')
          this.$router.push('/login');
        }
        if (command == 'changePassword') {
          this.$router.push('/login');
        }
      },

      handleFullScreen() {
        let element = document.documentElement;
        if (this.fullscreen) {
          if (document.exitFullscreen) {
            document.exitFullscreen();
          } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
          } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
          }
        } else {
          if (element.requestFullscreen) {
            element.requestFullscreen();
          } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
          } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
          } else if (element.msRequestFullscreen) {
            // IE11
            element.msRequestFullscreen();
          }
        }
        this.fullscreen = !this.fullscreen;
      }
    },

    mounted() {
      if (document.body.clientWidth < 1500) {
        this.collapseChange();
      }
    }
  }
</script>

<style scoped>

  .vHeader {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 64px;
    font-size: 22px;
    background-color: #1876d2;
    box-shadow: 0 2px 4px -1px rgba(0, 0, 0, .2), 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12);
    z-index: 1;
  }

  .collapse-btn {
    float: left;
    padding-left: 26%;
    line-height: 64px;
  }

  .el-icon-menu {
    color: #FFFFFF;
    cursor: pointer;
  }

  .btn-fullscreen {
    margin-top: 18px;
    margin-right: 5px;
    font-size: 24px;
    color: #FFFFFF;
    position: absolute;
    width: 30px;
    height: 0px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
  }

  .header-right {
    float: right;
    padding-right: 60px;
  }

  .el-icon-full-screen {
    font-weight: bolder;
  }

  .el-dropdown-link {
    color: #fff;
    cursor: pointer;
  }

  .user-name {
    position: relative;
    margin-left: 60px;
    line-height: 64px;
  }

  .collapse-btn-left {
    padding-left: 10%
  }

</style>
