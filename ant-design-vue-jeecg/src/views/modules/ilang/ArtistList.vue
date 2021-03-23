<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="歌星名称">
              <a-input placeholder="请输入歌星名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="拼音码">
              <a-input placeholder="请输入拼音码" v-model="queryParam.inputCode"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="区域">
                <j-category-select placeholder="请选择区域" v-model="queryParam.areaId" pcode="artist_type"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="类别">
                <j-dict-select-tag placeholder="请选择类别" v-model="queryParam.gender" dictCode="artist_type"/>
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
      <a-button type="primary" icon="download" @click="handleExportXls('歌星')">导出</a-button>
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
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
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

    <artist-modal ref="modalForm" @ok="modalFormOk"></artist-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ArtistModal from './modules/ArtistModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JCategorySelect from '@comp/jeecg/JCategorySelect'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'ArtistList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JDictSelectTag,
      JCategorySelect,
      ArtistModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '歌星管理页面',
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
          {
            title:'图片',
            align:"center",
            dataIndex: 'avatar',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'歌星名称',
            dataIndex: 'name'
          },
          {
            title:'拼音码',
            align:"center",
            dataIndex: 'inputCode'
          },
          {
            title:'区域',
            align:"center",
            dataIndex: 'areaText'
          },
          {
            title:'类别',
            align:"center",
            dataIndex: 'gender_dictText'
          },
          {
            title:'出生地',
            align:"center",
            dataIndex: 'birthplace'
          },
          {
            title:'热度',
            align:"center",
            dataIndex: 'hot'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'status_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/ilang/artist/list",
          delete: "/ilang/artist/delete",
          deleteBatch: "/ilang/artist/deleteBatch",
          exportXlsUrl: "/ilang/artist/exportXls",
          importExcelUrl: "ilang/artist/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
        isorter:{
          column: 'hot',
          order: 'desc',
        },
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'avatar',text:'图片',dictCode:''})
        fieldList.push({type:'string',value:'name',text:'歌星名称',dictCode:''})
        fieldList.push({type:'string',value:'inputCode',text:'拼音码',dictCode:''})
        fieldList.push({type:'string',value:'areaId',text:'区域'})
        fieldList.push({type:'string',value:'areaText',text:'区域',dictCode:''})
        fieldList.push({type:'string',value:'gender',text:'类别',dictCode:'artist_type'})
        fieldList.push({type:'string',value:'birthplace',text:'出生地',dictCode:''})
        fieldList.push({type:'date',value:'birthday',text:'生日'})
        fieldList.push({type:'string',value:'company',text:'所属公司',dictCode:''})
        fieldList.push({type:'string',value:'constellation',text:'星座',dictCode:''})
        fieldList.push({type:'Text',value:'info',text:'简介',dictCode:''})
        fieldList.push({type:'int',value:'hot',text:'热度',dictCode:''})
        fieldList.push({type:'string',value:'status',text:'状态',dictCode:'status'})
        fieldList.push({type:'string',value:'weight',text:'体重',dictCode:''})
        fieldList.push({type:'string',value:'stature',text:'身高',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
