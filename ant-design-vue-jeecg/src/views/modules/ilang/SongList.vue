<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="编码">
              <a-input placeholder="请输入编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="歌名">
              <a-input placeholder="请输入歌名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="拼音码">
                <a-input placeholder="请输入拼音码" v-model="queryParam.inputCode"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="语种">
                <j-category-select placeholder="请选择语种" v-model="queryParam.langId" pcode="song_language"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="分类">
                <j-category-select placeholder="请选择分类" v-model="queryParam.typeId" pcode="song_category"/>
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
      <a-button type="primary" icon="download" @click="handleExportXls('歌曲')">导出</a-button>
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

    <song-modal ref="modalForm" @ok="modalFormOk"></song-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SongModal from './modules/SongModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JCategorySelect from '@comp/jeecg/JCategorySelect'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'SongList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JCategorySelect,
      SongModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '歌曲管理页面',
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
            title:'编码',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'歌名',
            dataIndex: 'name'
          },
          {
            title:'拼音码',
            dataIndex: 'inputCode'
          },
          {
            title:'字数',
            align:"center",
            dataIndex: 'wordCount'
          },
          {
            title:'语种',
            align:"center",
            dataIndex: 'langText'
          },
          {
            title:'分类',
            align:"center",
            dataIndex: 'typeText'
          },
          {
            title:'歌星',
            align:"center",
            dataIndex: 'artistsName'
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
          list: "/ilang/song/list",
          delete: "/ilang/song/delete",
          deleteBatch: "/ilang/song/deleteBatch",
          exportXlsUrl: "/ilang/song/exportXls",
          importExcelUrl: "ilang/song/importExcel",

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
        fieldList.push({type:'string',value:'code',text:'编码',dictCode:''})
        fieldList.push({type:'string',value:'name',text:'歌名',dictCode:''})
        fieldList.push({type:'string',value:'inputCode',text:'拼音码',dictCode:''})
        fieldList.push({type:'int',value:'wordCount',text:'字数',dictCode:''})
        fieldList.push({type:'string',value:'langId',text:'语种'})
        fieldList.push({type:'string',value:'langText',text:'语种',dictCode:''})
        fieldList.push({type:'string',value:'typeId',text:'分类'})
        fieldList.push({type:'string',value:'typeText',text:'分类',dictCode:''})
        fieldList.push({type:'popup',value:'artistsName',text:'歌星', popup:{code:'artist_list',field:'id',orgFields:'id',destFields:'artist_ids'}})
        fieldList.push({type:'string',value:'amTrack',text:'伴唱音轨',dictCode:''})
        fieldList.push({type:'string',value:'quality',text:'歌曲画质',dictCode:''})
        fieldList.push({type:'string',value:'status',text:'状态',dictCode:'status'})
        fieldList.push({type:'int',value:'hot',text:'播放量',dictCode:''})
        fieldList.push({type:'string',value:'filePath',text:'本地路径',dictCode:''})
        fieldList.push({type:'string',value:'fileName',text:'文件名',dictCode:''})
        fieldList.push({type:'int',value:'orVol',text:'原唱音量',dictCode:''})
        fieldList.push({type:'int',value:'acVol',text:'伴唱音量',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
