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
          <el-select v-model="searchForm.appCode" filterable placeholder="请选择" clearable>
            <el-option
              v-for="item in apps"
              :key="item.value"
              :label="item.value"
              :value="item.value">
            </el-option>
          </el-select>
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
          <el-button style="float: right;margin-top: -3px;" @click="addDialogShow"
                     type="primary" icon="el-icon-plus" circle/>
        </el-tooltip>
      </div>
      <el-table :data="tableData" stripe style="width: 100%" v-loading="loading" element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading" max-height="400">
        <el-table-column type="index" label="序号" width="100"/>
        <el-table-column prop="appCode" label="应用编码" width="160"/>
        <el-table-column prop="appName" label="应用名称" width="160"/>
        <el-table-column :formatter="registryTypeFormat" prop="registryType" label="注册方式" width="100"/>
        <el-table-column prop="address" label="注册地址" width="400">
          <template slot-scope="props">
            <el-tag v-if="props.row.address" disable-transitions
                    v-for="(type, index) in props.row.address.split(',')"
                    :key="index"
                    style="margin: 2px;">{{type}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :formatter="dateFormat" prop="createTime" label="创建时间" width="140"/>
        <el-table-column prop="creator" label="创建人" width="120"/>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
              <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-edit" circle size="small"/>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
              <el-button @click.native.prevent="deleteRow(scope.$index, scope.row)" type="danger"
                         icon="el-icon-delete" circle size="small"/>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="pageParams.pageNo"
                       :page-sizes="[10, 20, 30]"
                       :page-size="pageParams.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="pageParams.totalNum">
        </el-pagination>
      </div>
    </div>

    <el-drawer title="应用编辑" :before-close="handleClose" :visible.sync="auditDialog" direction="rtl"
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
            <el-radio v-model="ruleForm.registryType" :label="1">自动注册</el-radio>
            <el-radio v-model="ruleForm.registryType" :label="2">手动注册</el-radio>
          </el-form-item>
          <el-form-item label="注册地址：" v-if="ruleForm.registryType === 2">
            <el-input type="textarea" rows="6" v-model="ruleForm.address"></el-input>
          </el-form-item>
        </el-form>
        <div class="add-app-drawer-footer">
          <el-button @click="auditDialog = false">取 消</el-button>
          <el-button type="primary" @click="updateApp" :loading="confirmLoading">
            {{ confirmLoading ? '提交中 ...' : '确 定'}}
          </el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer title="添加应用" :before-close="handleClose" :visible.sync="dialog" direction="rtl"
               custom-class="add-app-drawer" ref="drawer">
      <div class="add-app-drawer-content">
        <el-form :model="addRuleForm" :rules="rules" ref="ruleForm" :label-width="formLabelWidth">
          <el-form-item label="应用编号：" prop="appCode">
            <el-input v-model="addRuleForm.appCode"/>
          </el-form-item>
          <el-form-item label="应用名称：" prop="appName">
            <el-input v-model="addRuleForm.appName"/>
          </el-form-item>
          <el-form-item label="注册方式：">
            <el-radio v-model="addRuleForm.registryType" :label="1">自动注册</el-radio>
            <el-radio v-model="addRuleForm.registryType" :label="2">手动注册</el-radio>
          </el-form-item>
          <el-form-item label="注册地址：" v-if="addRuleForm.registryType === 2">
            <el-input type="textarea" rows="6" v-model="addRuleForm.address"></el-input>
          </el-form-item>
        </el-form>
        <div class="add-app-drawer-footer">
          <el-button @click="dialog = false">取 消</el-button>
          <el-button type="primary" @click="addApp" :loading="confirmLoading">
            {{ confirmLoading ? '提交中 ...' : '确 定'}}
          </el-button>
        </div>
      </div>
    </el-drawer>
  </div>

</template>

<script>

  import moment from 'moment';
  import qs from 'qs';

  export default {
    name: "Apps",

    data() {
      return {
        pageParams: {
          pageNo: 1,
          pageSize: 10,
          totalNum: 0,
        },
        loading: false,
        dialog: false,
        auditDialog: false,
        confirmLoading: false,
        formLabelWidth: '100px',
        tableData: [],
        ruleForm: {
          appCode: '',
          appName: '',
          address: '',
          registryType: '',
        },
        apps: [],
        addRuleForm: {
          appCode: '',
          appName: '',
          address: '',
          registryType: 1,
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
          appCode: '',
          appName: ''
        }
      }
    },

    created() {
      this.searchSubmit();
      this.getAllApps();
    },

    methods: {
      dateFormat(row, column) {
        return moment(row.createTime).format('YYYY-MM-DD HH:mm:ss');
      },
      addDialogShow() {
        this.dialog = true;
        this.addRuleForm = {
          appCode: '',
          appName: '',
          address: '',
          registryType: 1,
        }
      },
      registryTypeFormat(row, column) {
        let registryType = '';
        if (row.registryType === 1) {
          registryType = '自动注册';
        } else if (row.registryType === 2) {
          registryType = '手动注册';
        }
        return registryType;
      },
      handleSizeChange(val) {
        this.pageParams.pageSize = val;
        this.pageParams.pageNo = 1;
        this.searchSubmit();
      },
      handleCurrentChange(val) {
        this.pageParams.pageNo = val ? val : 1;
        this.searchSubmit();
      },
      handleClose(done) {
        this.$confirm('还未保存，是否退出编辑?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          done();
        }).catch(() => {
        });
      },
      addApp() {
        let params = qs.stringify({
          appCode: this.addRuleForm.appCode,
          appName: this.addRuleForm.appName,
          registryType: this.addRuleForm.registryType,
          address: this.addRuleForm.address
        });
        this.$axios.post("/app/add", params).then(response => {
          if (response.data.code === 1) {
            this.searchSubmit();
            this.dialog = false;
            this.$notify({
              title: '成功',
              message: '新增应用配置信息成功',
              type: 'success'
            });
          } else {
            this.$notify.error({
              title: '错误',
              message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
            });
          }
        }).catch(error => {
          this.$notify.error({
            title: '错误',
            message: "系统异常，请稍后重试..."
          });
        })
      },
      updateApp() {
        let params = qs.stringify({
          id: this.ruleForm.id,
          appCode: this.ruleForm.appCode,
          appName: this.ruleForm.appName,
          registryType: this.ruleForm.registryType,
          address: this.ruleForm.address
        });
        this.$axios.put("/app/update", params).then(response => {
          if (response.data.code === 1) {
            this.searchSubmit();
            this.auditDialog = false;
            this.$notify({
              title: '成功',
              message: '更新应用配置信息成功',
              type: 'success'
            });
          } else {
            this.$notify.error({
              title: '错误',
              message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
            });
          }
        }).catch(error => {
          this.$notify.error({
            title: '错误',
            message: "系统异常，请稍后重试..."
          });
        })
      },
      getAllApps() {
        this.$axios.get('/app/apps')
          .then(response => {
            if (response.data.code === 1) {
              this.apps = [];
              if (response.data.resultObject) {
                for (let item in response.data.resultObject) {
                  this.apps.push({'value': response.data.resultObject[item]})
                }
              }
            } else {
              this.$notify.error({
                title: '错误',
                message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
              });
            }
          }).catch(error => {
          this.$notify.error({
            title: '错误',
            message: '查询错误'
          });
        })
      },
      searchSubmit() {
        const appCode = this.searchForm.appCode;
        const appName = this.searchForm.appName;
        const pageNo = this.pageParams.pageNo;
        const pageSize = this.pageParams.pageSize;
        this.loading = true;
        this.$axios.get('/app/list', {
          params: {appCode, appName, pageNo, pageSize}
        }).then(response => {
          if (response.data.code === 1) {
            this.tableData = [];
            this.tableData = response.data.resultObject ? response.data.resultObject : [];
            this.pageParams = response.data.pagingQuery;
          } else {
            this.$notify.error({
              title: '错误',
              message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
            });
          }
          this.loading = false;
        }).catch(error => {
          this.$notify.error({
            title: '错误',
            message: "系统异常，请稍后重试..."
          });
          this.loading = false;
        })
      },
      deleteRow(index, rows) {
        this.$confirm('此操作将删除该配置，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.delete("/app/delete/" + rows.id)
            .then(response => {
              if (response.data.code === 1) {
                this.searchSubmit();
                this.$notify({
                  title: '成功',
                  message: '删除应用配置信息成功',
                  type: 'success'
                });
              } else {
                this.$notify.error({
                  title: '错误',
                  message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
                });
              }
            })
            .catch(error => {
              this.$notify.error({
                title: '错误',
                message: "系统异常，请稍后重试..."
              });
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
