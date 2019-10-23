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
          <el-input v-model="searchForm.glueCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="规则名称：">
          <el-input v-model="searchForm.glueName" clearable></el-input>
        </el-form-item>
        <el-form-item label="规则类型：">
          <el-select v-model="searchForm.glueType" filterable placeholder="请选择">
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
          <el-button type="primary" @click="searchSubmit" icon="el-icon-search">查询</el-button>
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
        <el-table-column prop="glueCode" label="规则编码" width="160"/>
        <el-table-column prop="glueName" label="规则名称" width="160"/>
        <el-table-column prop="appName" label="应用编码" width="140"/>
        <el-table-column prop="glueType" label="规则类型" width="140"/>
        <el-table-column prop="version" label="版本" width="140"/>
        <el-table-column prop="updateTime" label="更新时间" width="140"/>
        <el-table-column prop="operator" label="创建人" width="160"/>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="发布" placement="bottom">
              <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-s-promotion" circle size="small"/>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
              <el-button @click="auditDialog = true" type="primary" icon="el-icon-setting" circle size="small"/>
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
        <codemirror :options="cmOptions" v-model="addCode"></codemirror>
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
              规则编码：RKRULE1005
            </div>
          </el-col>
          <el-col :span="11" :offset="2">
            <div class="text">
              规则名称：车牌号码
            </div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 30px;">
          <el-col :span="11">
            <div class="text">
              规则版本：2
            </div>
          </el-col>
          <el-col :span="11" :offset="2">
            <div class="text">
              规则类型：逻辑处理
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
        <codemirror :options="cmOptions" v-model="auditCode"></codemirror>
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

  export default {
    name: "Glue",

    data() {
      return {
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
        glueTypes: [
          {
            value: 1,
            label: '逻辑校验'
          }, {
            value: 2,
            label: '逻辑处理'
          }
        ],
        currentPage: 4,
        addDialog: false,
        auditDialog: false,
        loading: false,
        addCode: '',
        auditCode: 'package com.ars.dglue.client.zk;\n' +
          '\n' +
          'import lombok.extern.slf4j.Slf4j;\n' +
          'import org.apache.zookeeper.KeeperException;\n' +
          'import org.apache.zookeeper.WatchedEvent;\n' +
          'import org.apache.zookeeper.data.Stat;\n' +
          '\n' +
          '/**\n' +
          ' * 〈一句话介绍功能〉<br>\n' +
          ' * zookeeper broad cast watcher\n' +
          ' *\n' +
          ' * @author jierui on 2019-10-08.\n' +
          ' * @see [相关类/方法]（可选）\n' +
          ' * @since [产品/模块版本] （可选）\n' +
          ' */\n' +
          '@Slf4j\n' +
          'public abstract class AbstractZkBroadcastWatcher extends ZkClient {\n' +
          '\n' +
          '\n' +
          '    /**\n' +
          '     * zookeeper server constructor method\n' +
          '     *\n' +
          '     * @param zkServer\n' +
          '     */\n' +
          '    public AbstractZkBroadcastWatcher(String zkServer) {\n' +
          '        super(zkServer);\n' +
          '    }\n' +
          '\n' +
          '    /**\n' +
          '     * process watched event\n' +
          '     *\n' +
          '     * @param watchedEvent event\n' +
          '     */\n' +
          '    @Override\n' +
          '    public void process(WatchedEvent watchedEvent) {\n' +
          '        if (watchedEvent.getState() == Event.KeeperState.Expired) {\n' +
          '            super.close();\n' +
          '            super.getZooKeeper();\n' +
          '        }\n' +
          '        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {\n' +
          '            String path = watchedEvent.getPath();\n' +
          '\n' +
          '            /**\n' +
          '             * add one-time watch\n' +
          '             *\n' +
          '             * It is import, Because we need the latest data, Not real time, one-time can guarantee the Watch event,\n' +
          '             * will not repeat the monitoring response during the re-Watch period, But it can guarantee that\n' +
          '             * get Data is the latest, and will check if there is new data in the middle through temporary check.\n' +
          '             *\n' +
          '             */\n' +
          '            try {\n' +
          '                super.getZooKeeper().exists(path, true);\n' +
          '            } catch (Exception e) {\n' +
          '                log.error(e.getMessage(), e);\n' +
          '            }\n' +
          '\n' +
          '            String data = null;\n' +
          '            try {\n' +
          '                byte[] resultData = super.getZooKeeper().getData(path, true, null);\n' +
          '                data = new String(resultData);\n' +
          '            } catch (KeeperException e) {\n' +
          '                log.error(e.getMessage(), e);\n' +
          '            } catch (InterruptedException e) {\n' +
          '                log.error(e.getMessage(), e);\n' +
          '            }\n' +
          '            consume(path, data);\n' +
          '        }\n' +
          '    }\n' +
          '\n' +
          '    /**\n' +
          '     * message data producer\n' +
          '     *\n' +
          '     * @param nodePath    node path\n' +
          '     * @param messageData message data\n' +
          '     * @return boolean\n' +
          '     */\n' +
          '    protected boolean produce(String nodePath, String messageData) {\n' +
          '        Stat stat = setData(nodePath, messageData);\n' +
          '        boolean isSuccess = null != stat ? true : false;\n' +
          '        log.info("AbstractZkBroadcastWatcher.produce message: stat={}, nodePath={}, messageData={}", isSuccess,\n' +
          '                nodePath, messageData);\n' +
          '        return isSuccess;\n' +
          '    }\n' +
          '\n' +
          '    /**\n' +
          '     * consume message (watch node)\n' +
          '     *\n' +
          '     * @param nodePath    node path\n' +
          '     * @param messageData message data\n' +
          '     */\n' +
          '    protected void consume(String nodePath, String messageData) {\n' +
          '        try {\n' +
          '            boolean isSuccess = consumeMessage(messageData);\n' +
          '            log.info("AbstractZkBroadcastWatcher.consume message: stat={}, nodePath{}, messageData={}", isSuccess,\n' +
          '                    nodePath, messageData);\n' +
          '        } catch (Exception e) {\n' +
          '            log.error(e.getMessage(), e);\n' +
          '        }\n' +
          '    }\n' +
          '\n' +
          '    /**\n' +
          '     * watch broadcast topic (watch node)\n' +
          '     *\n' +
          '     * @param path node path\n' +
          '     * @return boolean\n' +
          '     */\n' +
          '    protected boolean watchTopic(String path) {\n' +
          '        try {\n' +
          '            Stat stat = super.getZooKeeper().exists(path, true);\n' +
          '            if (stat == null) {\n' +
          '                stat = super.isExistOrCreate(path);\n' +
          '                stat = super.getZooKeeper().exists(path, true);\n' +
          '            }\n' +
          '            boolean isSuccess = null != stat ? true : false;\n' +
          '            log.info("AbstractZkBroadcastWatcher.watchTopic:{}, path:{}", isSuccess, path);\n' +
          '            return isSuccess;\n' +
          '        } catch (KeeperException e) {\n' +
          '            log.error(e.getMessage(), e);\n' +
          '        } catch (InterruptedException e) {\n' +
          '            log.error(e.getMessage(), e);\n' +
          '        }\n' +
          '        return false;\n' +
          '    }\n' +
          '\n' +
          '    /**\n' +
          '     * consume message\n' +
          '     *\n' +
          '     * @param messageData message data\n' +
          '     * @return boolean\n' +
          '     * @throws Exception\n' +
          '     */\n' +
          '    public abstract boolean consumeMessage(String messageData) throws Exception;\n' +
          '}\n',
        checkLoading: false,
        updateLoading: false,
        saveForm: {},
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
          glueCode: '',
          glueName: '',
          appCode: '',
          glueType: ''
        },

        glueTypes: [{
          value: '1',
          label: '逻辑校验'
        }, {
          value: '2',
          label: '逻辑处理'
        }],

        tableData: [
          {
            id: 1,
            glueCode: 'RKRULE1005',
            glueName: '车牌号码',
            appName: 'loan',
            glueType: '逻辑处理',
            version: 2,
            updateTime: '2019-09-17 19:45:23',
            operator: '杰瑞'
          }
        ]
      }
    },
    created() {
      this.getAllApps();
    },
    methods: {
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
      searchSubmit() {

      },

      checkCode() {
        this.checkLoading = true;
        setTimeout(() => {
          this.checkLoading = false;
        }, 2000);
      },

      updateGlue() {
        this.updateLoading = true;
        setTimeout(() => {
          this.updateLoading = false;
        }, 2000);
      },

      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
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
