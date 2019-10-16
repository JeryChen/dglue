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
          <el-select v-model="value" filterable placeholder="请选择">
            <el-option v-for="item in glueTypes"
              :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
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
  </div>
</template>

<script>
  export default {
    name: "Glue",

    data() {
      return {
        searchForm: {
          glueCode: '',
          glueName: '',
          appCode: ''
        },

        glueTypes: [{
          value: '1',
          label: '逻辑校验'
        }, {
          value: '2',
          label: '逻辑处理'
        }],
      }
    },

    methods: {
      searchSubmit() {

      }
    }
  }
</script>

<style scoped>

</style>
