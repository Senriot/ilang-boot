<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24" >
            <a-form-item label="设备ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['devId', validatorRules.devId]" placeholder="请输入设备ID" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['devName', validatorRules.devName]" placeholder="请输入名称" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="所属产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['productId', validatorRules.productId]" :trigger-change="true" dictCode="ilang_device_produce,name,id" placeholder="请选择所属产品" />
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="所在城市" :labelCol="labelCol" :wrapperCol="wrapperCol">
		      <j-area-linkage type="cascader" v-decorator="['city']" placeholder="请输入省市区" />
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['address']" placeholder="请输入详细地址" ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="到期时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择到期时间" v-decorator="['expireDate']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <a-form-item label="设备状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status', validatorRules.status]" :trigger-change="true" dictCode="status" placeholder="请选择设备状态" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="说明" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['remark']" rows="4" placeholder="请输入说明" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="设备通道" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="deviceChannelTable.loading"
          :columns="deviceChannelTable.columns"
          :dataSource="deviceChannelTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
      <a-tab-pane tab="设备日志" :key="refKeys[1]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[1]"
          :loading="deviceLogTable.loading"
          :columns="deviceLogTable.columns"
          :dataSource="deviceLogTable.dataSource"
          :always-edit="false"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="false"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: 'DeviceForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JDate,
      JDictSelectTag,
      JAreaLinkage,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          devId: {
            rules: [
              { required: true, message: '请输入设备ID!'},
              { validator: (rule, value, callback) => validateDuplicateValue('ilang_device', 'dev_id', value, this.model.id, callback)},
            ]
          },
          devName: {
            rules: [
              { required: true, message: '请输入名称!'},
              { validator: (rule, value, callback) => validateDuplicateValue('ilang_device', 'dev_name', value, this.model.id, callback)},
            ]
          },
          productId: {
            rules: [
              { required: true, message: '请输入所属产品!'},
            ]
          },
          status: {
            initialValue:"1",
            rules: [
            ]
          },
        },
        refKeys: ['deviceChannel', 'deviceLog', ],
        tableKeys:['deviceChannel', 'deviceLog', ],
        activeKey: 'deviceChannel',
        // 设备通道
        deviceChannelTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '通道编号',
              key: 'chIndex',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '通道名称',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '启用',
              key: 'enabled',
              type: FormTypes.checkbox,
              customValue: ['Y', 'N'],
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:"Y",
            },
          ]
        },
        // 设备日志
        deviceLogTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '生成时间',
              key: 'generateTime',
              type: FormTypes.date,
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: 'Topic',
              key: 'topic',
              type: FormTypes.input,
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '设备',
              key: 'deviceId',
              type: FormTypes.input,
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '内容',
              key: 'payload',
              type: FormTypes.input,
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '消息类别',
              key: 'msgType',
              type: FormTypes.input,
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
          ]
        },
        url: {
          add: "/ilang/device/add",
          edit: "/ilang/device/edit",
          queryById: "/ilang/device/queryById",
          deviceChannel: {
            list: '/ilang/device/queryDeviceChannelByMainId'
          },
          deviceLog: {
            list: '/ilang/device/queryDeviceLogByMainId'
          },
        }
      }
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：false流程表单 true普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      addBefore(){
        this.form.resetFields()
        this.deviceChannelTable.dataSource=[]
        this.deviceLogTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'devId','devName','productId','activeDate','city','address','firmwareVersion','onlineDate','onlineStatus','expireDate','status','remark')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.deviceChannel.list, params, this.deviceChannelTable)
          this.requestSubTableData(this.url.deviceLog.list, params, this.deviceLogTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          deviceChannelList: allValues.tablesValue[0].values,
          deviceLogList: allValues.tablesValue[1].values,
        }
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          })
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'devId','devName','productId','activeDate','city','address','firmwareVersion','onlineDate','onlineStatus','expireDate','status','remark'))
     },

    }
  }
</script>

<style scoped>
</style>
