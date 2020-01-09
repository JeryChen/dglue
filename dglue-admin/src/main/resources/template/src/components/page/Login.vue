<template>
  <div class="content">
    <el-card class="box-card">
      <div style="position: relative;">
        <div class="login-title">
          <span class="title">系统登录</span>
        </div>
        <div style="position: absolute; right: 0px; top: 8px;">
          <el-button type="text" @click="submitForm('ruleForm')">注册</el-button>
        </div>
      </div>
      <div style="margin-top: 20px">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm">
          <el-form-item prop="username">
            <el-input maxlength="16" placeholder="请输入账号" prefix-icon="el-icon-user" type="text"
                      v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input maxlength="16" placeholder="请输入密码" prefix-icon="el-icon-lock" type="password"
                      v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" type="primary" @click="submitForm('ruleForm')">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "Login",

    data() {
      let validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账号'));
        }
        callback();
      };
      let validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        }
        callback();
      };
      return {
        ruleForm: {
          username: '',
          password: ''
        },
        rules: {
          username: [
            {validator: validateUsername, trigger: 'blur'}
          ],
          password: [
            {validator: validatePassword, trigger: 'blur'}
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            localStorage.setItem('username', this.ruleForm.username)
            this.$router.push("/index");
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }

  }
</script>

<style scoped>
  .box-card {
    width: 400px;
    height: 250px;
    margin: auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }

  .login-title {
    text-align: center;
    align-content: center;
    font-size: 24px;
  }
</style>
