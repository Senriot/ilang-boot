<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="设备ID">
              <a-input placeholder="请输入设备ID" v-model="queryParam.devId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.devName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="所属产品">
                <j-dict-select-tag placeholder="请选择所属产品" v-model="queryParam.productId" dictCode="ilang_device_produce,name,id"/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('设备')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>
        <template slot="pcaSlot" slot-scope="text">
          <div>{{ getPcaText(text) }}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <device-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DeviceModal from './modules/DeviceModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import Area from '@/components/_util/Area'
  import '@/assets/less/TableExpand.less'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: "DeviceList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      DeviceModal,
      JSuperQuery
    },
    data () {
      return {
        description: '设备管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          // {
          //   title:'主键',
          //   align:"center",
          //   dataIndex: 'id'
          // },
          {
            title:'设备ID',
            align:"center",
            dataIndex: 'devId'
          },
          {
            title:'名称',
            align:"center",
            dataIndex: 'devName'
          },
          {
            title:'所属产品',
            align:"center",
            dataIndex: 'productId_dictText'
          },
          {
            title:'激活时间',
            align:"center",
            dataIndex: 'activeDate'
          },
          {
            title:'所在城市',
            align:"center",
            dataIndex: 'city',
            scopedSlots: {customRender: 'pcaSlot'}
          },
          {
            title:'固件版本',
            align:"center",
            dataIndex: 'firmwareVersion'
          },
          {
            title:'在线时间',
            align:"center",
            dataIndex: 'onlineDate'
          },
          {
            title:'在线状态',
            align:"center",
            dataIndex: 'onlineStatus'
          },
          {
            title:'到期时间',
            align:"center",
            dataIndex: 'expireDate'
          },
          {
            title:'设备状态',
            align:"center",
            dataIndex: 'status_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/ilang/device/list",
          delete: "/ilang/device/delete",
          deleteBatch: "/ilang/device/deleteBatch",
          exportXlsUrl: "/ilang/device/exportXls",
          importExcelUrl: "ilang/device/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
      this.pcaData = new Area()
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      getPcaText(code){
        return this.pcaData.getText(code);
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'id',text:'主键',dictCode:''})
         fieldList.push({type:'string',value:'devId',text:'设备ID',dictCode:''})
         fieldList.push({type:'string',value:'devName',text:'名称',dictCode:''})
         fieldList.push({type:'string',value:'productId',text:'所属产品',dictCode:'ilang_device_produce,name,id'})
         fieldList.push({type:'Date',value:'activeDate',text:'激活时间',dictCode:''})
         fieldList.push({type:'pca',value:'city',text:'所在城市'})
         fieldList.push({type:'string',value:'address',text:'详细地址',dictCode:''})
         fieldList.push({type:'string',value:'firmwareVersion',text:'固件版本',dictCode:''})
         fieldList.push({type:'Date',value:'onlineDate',text:'在线时间',dictCode:''})
         fieldList.push({type:'string',value:'onlineStatus',text:'在线状态',dictCode:''})
         fieldList.push({type:'datetime',value:'expireDate',text:'到期时间'})
         fieldList.push({type:'string',value:'status',text:'设备状态',dictCode:'status'})
         fieldList.push({type:'Text',value:'remark',text:'说明',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
