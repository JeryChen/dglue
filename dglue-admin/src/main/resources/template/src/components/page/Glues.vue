<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <el-icon class="el-icon-s-finance"></el-icon>
          <span> 规则管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="heard-nation">
      <div class="search-title">
        <el-icon class="el-icon-search"/>
        <span>规则查询</span>
      </div>
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="规则编码：">
          <el-input v-model="searchForm.ruleCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="规则名称：">
          <el-input v-model="searchForm.ruleName" clearable></el-input>
        </el-form-item>
        <el-form-item label="规则类型：">
          <el-select v-model="searchForm.ruleType" filterable placeholder="请选择" clearable>
            <el-option v-for="item in glueTypes"
                       :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
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
          <el-button type="primary" @click="searchRule" icon="el-icon-search">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="container">
      <div class="data-title">
        <el-icon class="el-icon-s-data"/>
        <span>数据列表</span>
        <el-tooltip class="item" effect="dark" content="添加" placement="bottom">
          <el-button style="float: right;margin-top: -3px;" @click="addDialog = true"
                     type="primary" icon="el-icon-plus" circle/>
        </el-tooltip>
      </div>
      <el-table :data="tableData" stripe style="width: 100%" v-loading="loading" element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading" max-height="400">
        <el-table-column prop="id" label="规则编号" width="100"/>
        <el-table-column prop="ruleCode" label="规则编码" width="160"/>
        <el-table-column prop="ruleName" label="规则名称" width="160"/>
        <el-table-column prop="appCode" label="应用编码" width="140"/>
        <el-table-column :formatter="getRuleType" prop="ruleType" label="规则类型" width="140"/>
        <el-table-column prop="version" label="版本" width="100"/>
        <el-table-column :formatter="dateFormat" prop="modifyTime" label="更新时间" width="140"/>
        <el-table-column prop="creator" label="创建人" width="160"/>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="发布" placement="bottom">
              <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-s-promotion" circle size="small"/>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
              <el-button @click="auditClick(scope.row)" type="primary" icon="el-icon-setting" circle size="small"/>
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
                       :current-page="pageParams.pageNo"
                       :page-sizes="[10, 20, 30]"
                       :page-size="pageParams.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="pageParams.totalNum">
        </el-pagination>
      </div>
    </div>

    <!--新增规则-->
    <el-drawer title="新增规则" :before-close="handleClose" :visible.sync="addDialog" direction="rtl"
               custom-class="add-glue-drawer" ref="drawer" size="40%">
      <el-card class="box-card" style="margin-top: -15px;">
        <div slot="header" class="clearfix">
          <span>规则详情</span>
          <el-tooltip class="item" effect="dark" content="保存" placement="bottom">
            <el-button style="float: right" size="small" :loading="updateLoading" icon="el-icon-edit-outline"
                       type="primary" circle @click="updateGlue"/>
          </el-tooltip>
        </div>
        <el-row>
          <el-col :span="4">
            <div class="text">
              规则编码：
            </div>
          </el-col>
          <el-col :span="7">
            <el-input class="col-margin"
                      v-model="saveForm.glueCode"
                      clearable>
            </el-input>
          </el-col>
          <el-col :span="4" :offset="2">
            <div class="text">
              规则名称：
            </div>
          </el-col>
          <el-col :span="7">
            <el-input class="col-margin"
                      v-model="saveForm.glueName"
                      clearable>
            </el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 30px;">
          <el-col :span="4">
            <div class="text">
              规则类型：
            </div>
          </el-col>
          <el-col :span="7">
            <el-select v-model="saveForm.glueType" filterable placeholder="请选择" class="col-margin">
              <el-option
                v-for="item in glueTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4" :offset="2">
            <div class="text">
              应用编号：
            </div>
          </el-col>
          <el-col :span="7">
            <el-select v-model="saveForm.appCode" filterable placeholder="请选择" clearable class="col-margin">
              <el-option
                v-for="item in apps"
                :key="item.value"
                :label="item.value"
                :value="item.value">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
      </el-card>
      <el-card class="box-card" style="margin-top: 10px">
        <div slot="header" class="clearfix">
          <span>规则代码</span>
          <el-tooltip class="item" effect="dark" content="校验" placement="bottom">
            <el-button style="float: right" size="small" :loading="checkLoading" icon="el-icon-check" type="primary"
                       circle @click="checkCode"/>
          </el-tooltip>
        </div>
        <codemirror :options="cmOptions" v-model="saveForm.source"></codemirror>
      </el-card>
    </el-drawer>

    <!--编辑规则-->
    <el-drawer title="规则编辑" :before-close="handleClose" :visible.sync="auditDialog" direction="rtl"
               custom-class="audit-glue-drawer" ref="drawer" size="40%">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>规则详情</span>
          <el-tooltip class="item" effect="dark" content="保存" placement="bottom">
            <el-button style="float: right" size="small" :loading="updateLoading" icon="el-icon-edit-outline"
                       type="primary" circle @click="updateGlue"/>
          </el-tooltip>
        </div>
        <el-row>
          <el-col :span="11">
            <div class="text">
              规则编码：{{auditRule.ruleCode}}
            </div>
          </el-col>
          <el-col :span="11" :offset="2">
            <div class="text">
              规则名称：{{auditRule.ruleName}}
            </div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 30px;">
          <el-col :span="11">
            <div class="text">
              规则版本：{{auditRule.version}}
            </div>
          </el-col>
          <el-col :span="11" :offset="2">
            <div class="text">
              规则类型：{{auditRule.ruleType | ruleTypeFormat}}
            </div>
          </el-col>
        </el-row>
      </el-card>
      <el-card class="box-card" style="margin-top: 10px">
        <div slot="header" class="clearfix">
          <span>规则代码</span>
          <el-tooltip class="item" effect="dark" content="校验" placement="bottom">
            <el-button style="float: right" size="small" :loading="checkLoading" icon="el-icon-check" type="primary"
                       circle @click="checkCode"/>
          </el-tooltip>
        </div>
        <codemirror :options="cmOptions" v-model="auditRule.source"></codemirror>
      </el-card>
    </el-drawer>
  </div>
</template>

<script>

  // language js
  import 'codemirror/mode/clike/clike.js'
  import 'codemirror/addon/hint/show-hint.js'
  import 'codemirror/addon/fold/indent-fold.js'
  // theme css
  import 'codemirror/theme/idea.css'
  import 'codemirror/addon/hint/show-hint.css'
  import 'codemirror/addon/hint/anyword-hint.js'

  import moment from 'moment';
  import qs from 'qs';

  export default {
    name: "Glue",

    data() {
      return {
        glueTypes: [
          {
            value: 1,
            label: '逻辑校验'
          }, {
            value: 2,
            label: '逻辑处理'
          }
        ],
        pageParams: {
          pageNo: 1,
          pageSize: 10,
          totalNum: 0,
        },
        addDialog: false,
        auditDialog: false,
        loading: false,
        addCode: '',
        auditCode: '',
        checkLoading: false,
        updateLoading: false,
        saveForm: {
          glueCode: '',
          glueName: '',
          glueType: '',
          appCode: '',
          source: ''
        },
        cmOptions: {
          tabSize: 4,
          mode: 'text/x-java',
          theme: 'idea',
          lineNumbers: true,
          line: true,
          matchBrackets: true,
          extraKeys: {"Ctrl": "autocomplete"}
        },
        apps: [],
        searchForm: {
          ruleCode: '',
          ruleName: '',
          ruleType: '',
          appCode: '',
          appName: ''
        },

        auditRule: {},

        glueTypes: [{
          value: '1',
          label: '逻辑校验'
        }, {
          value: '2',
          label: '逻辑处理'
        }],

        tableData: []
      }
    },
    created() {
      this.getAllApps();
      this.searchRule();
    },
    filters: {
      ruleTypeFormat(indexType) {
        const ruleTypeObj = {
          1: '逻辑校验',
          2: '逻辑处理',
        };
        return ruleTypeObj[indexType];
      }
    },
    methods: {
      getRuleType(row, column) {
        const ruleTypeObj = {
          1: '逻辑校验',
          2: '逻辑处理',
        };
        return ruleTypeObj[row.ruleType];
      },
      dateFormat(row, column) {
        return moment(row.modifyTime).format('YYYY-MM-DD HH:mm:ss');
      },
      getAllApps() {
        this.$axios.get('app/apps')
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
      searchRule() {
        const ruleCode = this.searchForm.ruleCode;
        const ruleName = this.searchForm.ruleName;
        const ruleType = this.searchForm.ruleType;
        const appCode = this.searchForm.appCode;
        const appName = this.searchForm.appName;
        const pageNo = this.pageParams.pageNo;
        const pageSize = this.pageParams.pageSize;
        this.loading = true;
        this.$axios.get('/glue/list', {
          params: {ruleCode, ruleName, ruleType, appCode, appName, pageNo, pageSize}
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
      auditClick(row) {
        this.auditRule = row;
        this.auditDialog = true;
      },

      checkCode() {
        let params = qs.stringify({
          source: this.saveForm.source
        });
        this.checkLoading = true;
        this.$axios.post("/glue/test", params).then(response => {
          if (response.data.code === 1) {
            this.$notify({
              title: '成功',
              message: '代码检测成功',
              type: 'success'
            });
          } else {
            this.$notify.error({
              title: '错误',
              message: response.data.message ? response.data.message : "系统异常，请联系管理员..."
            });
          }
          this.checkLoading = false;
        }).catch(error => {
          this.$notify.error({
            title: '错误',
            message: "系统异常，请稍后重试..."
          });
          this.checkLoading = false;
        })
      },

      updateGlue() {
        this.updateLoading = true;
        setTimeout(() => {
          this.updateLoading = false;
        }, 2000);
      },

      handleSizeChange(val) {
        this.pageParams.pageSize = val;
        this.pageParams.pageNo = 1;
        this.searchRule();
      },
      handleCurrentChange(val) {
        this.pageParams.pageNo = val ? val : 1;
        this.searchRule();
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
      }
    }
  }
</script>

<style scoped>

  .text {
    font-size: 14px;
    color: gray;
  }

  .item {
    margin-bottom: 18px;
  }

  .box-card {
    margin: 0 2px;
  }

  .col-margin {
    margin-top: -4px;
  }

</style>
