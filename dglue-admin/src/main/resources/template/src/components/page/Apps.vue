<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <el-icon class="el-icon-s-finance"></el-icon>
          <span> 应用管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="heard-nation">
      <div class="search-title">
        <el-icon class="el-icon-search"/>
        <span>应用查询</span>
      </div>
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="应用编码：">
          <el-input v-model="searchForm.appCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="应用名称：">
          <el-input v-model="searchForm.appName" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchSubmit" icon="el-icon-search">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="container">
      <div class="data-title">
        <el-icon class="el-icon-s-data"/>
        <span>数据列表</span>
        <el-tooltip class="item" effect="dark" content="添加" placement="bottom">
          <el-button style="float: right;margin-top: -3px;" @click="dialog = true"
                     type="primary" icon="el-icon-plus" circle/>
        </el-tooltip>
      </div>
      <el-table :data="tableData" stripe style="width: 100%" v-loading="loading" element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading" max-height="400">
        <el-table-column prop="index" label="序号" width="100"/>
        <el-table-column prop="appCode" label="应用编码" width="160"/>
        <el-table-column prop="appName" label="应用名称" width="160"/>
        <el-table-column prop="createTime" label="创建时间" width="140"/>
        <el-table-column prop="address" label="注册地址" width="400">
          <template slot-scope="props">
            <el-tag disable-transitions v-for="(type, index) in props.row.address" :key="index"
                    style="margin: 2px;">{{type}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operator" label="创建人" width="160"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
              <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-edit" circle size="small"/>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
              <el-button @click.native.prevent="deleteRow(scope.$index, tableData)" type="danger"
                         icon="el-icon-delete" circle size="small"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[10, 20, 30]"
                       :page-size="100"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="100">
        </el-pagination>
      </div>
    </div>

    <el-drawer title="应用编辑" :before-close="handleAuditClose" :visible.sync="auditDialog" direction="rtl"
               custom-class="add-app-drawer" ref="auditDrawer">
      <div class="add-app-drawer-content">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="formLabelWidth">
          <el-form-item label="应用编号：" prop="appCode">
            <el-input v-model="ruleForm.appCode"/>
          </el-form-item>
          <el-form-item label="应用名称：" prop="appName">
            <el-input v-model="ruleForm.appName"/>
          </el-form-item>
          <el-form-item label="注册方式：">
            <el-radio v-model="ruleForm.radio" label="1">自动注册</el-radio>
            <el-radio v-model="ruleForm.radio" label="2">手动注册</el-radio>
          </el-form-item>
          <el-form-item label="注册地址：" v-if="ruleForm.radio === '2'">
            <el-input type="textarea" rows="6" v-model="ruleForm.address"></el-input>
          </el-form-item>
        </el-form>
        <div class="add-app-drawer-footer">
          <el-button @click="auditDialog = false">取 消</el-button>
          <el-button type="primary" @click="$refs.auditDrawer.closeDrawer()" :loading="confirmLoading">
            {{ confirmLoading ? '提交中 ...' : '确 定'}}
          </el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer title="添加应用" :before-close="handleClose" :visible.sync="dialog" direction="rtl"
               custom-class="add-app-drawer" ref="drawer">
      <div class="add-app-drawer-content">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="formLabelWidth">
          <el-form-item label="应用编号：" prop="appCode">
            <el-input v-model="ruleForm.appCode"/>
          </el-form-item>
          <el-form-item label="应用名称：" prop="appName">
            <el-input v-model="ruleForm.appName"/>
          </el-form-item>
          <el-form-item label="注册方式：">
            <el-radio v-model="ruleForm.radio" label="1">自动注册</el-radio>
            <el-radio v-model="ruleForm.radio" label="2">手动注册</el-radio>
          </el-form-item>
          <el-form-item label="注册地址：" v-if="ruleForm.radio === '2'">
            <el-input type="textarea" rows="6" v-model="ruleForm.textarea"></el-input>
          </el-form-item>
        </el-form>
        <div class="add-app-drawer-footer">
          <el-button @click="dialog = false">取 消</el-button>
          <el-button type="primary" @click="$refs.drawer.closeDrawer()" :loading="confirmLoading">
            {{ confirmLoading ? '提交中 ...' : '确 定'}}
          </el-button>
        </div>
      </div>
    </el-drawer>
  </div>

</template>

<script>
  export default {
    name: "Apps",

    data() {
      return {
        currentPage: 4,
        loading: false,
        dialog: false,
        auditDialog: false,
        confirmLoading: false,
        formLabelWidth: '100px',
        tableData: [{
          index: 1,
          appCode: 'borrower',
          appName: '借款人管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }, {
          index: 2,
          appCode: 'loan',
          appName: '进件管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '2',
          operator: '杰瑞'
        }, {
          index: 3,
          appCode: 'openApi',
          appName: '网关管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }, {
          index: 4,
          appCode: 'borrower',
          appName: '借款人管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }, {
          index: 5,
          appCode: 'loan',
          appName: '进件管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '2',
          operator: '杰瑞'
        }, {
          index: 6,
          appCode: 'openApi',
          appName: '网关管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }, {
          index: 7,
          appCode: 'borrower',
          appName: '借款人管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }, {
          index: 8,
          appCode: 'loan',
          appName: '进件管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '2',
          operator: '杰瑞'
        }, {
          index: 9,
          appCode: 'openApi',
          appName: '网关管理',
          createTime: '2019-10-15 15:00:00',
          address: ['192.168.1.70', '192.168.127.700'],
          radio: '1',
          operator: '杰瑞'
        }],
        ruleForm: {
          appCode: '',
          appName: '',
          resource: '',
          address: '',
          radio: '1'
        },
        rules: {
          appCode: [
            {required: true, message: '应用编号不能为空', trigger: 'blur'},
            {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
          ],
          appName: [
            {required: true, message: '应用名称不能为空', trigger: 'blur'},
            {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
          ]
        },
        searchForm: {
          appCode: 'loan',
          appName: '进件管理'
        }
      }
    },

    methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
      handleClose(done) {
        this.$confirm('确定要提交表单吗？')
          .then(_ => {
            this.confirmLoading = true;
            setTimeout(() => {
              this.confirmLoading = false;
              done();
            }, 2000);
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success'
            });
          }).catch(_ => {
        });
      },
      handleAuditClose(done) {
        this.$confirm('确定要提交表单吗？')
          .then(_ => {
            this.confirmLoading = true;
            setTimeout(() => {
              this.confirmLoading = false;
              done();
            }, 2000);
            this.$notify({
              title: '成功',
              message: '保存成功',
              type: 'success'
            });
          }).catch(_ => {
        });
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      searchSubmit() {

      },
      deleteRow(index, rows) {
        this.$confirm('此操作将删除该配置，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          rows.splice(index, 1);
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success'
          });
        }).catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消删除'
          });
        });
      },
      handleClick(row) {
        this.ruleForm = row;
        this.auditDialog = true;
      }
    }
  }
</script>

<style scoped>

  .add-app-drawer-content {
    padding: 10px;
  }

  .add-app-drawer-footer {
    padding: 10px;
  }

</style>
