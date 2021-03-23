<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="简拼" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['pinyin']" placeholder="请输入简拼"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="作者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['author']" placeholder="请输入作者"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['recommends']"  ></j-switch>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="封面" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple :number=1 v-decorator="['cover']" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="分类ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['categoryId']" pcode="ld_category" placeholder="请选择分类ID" back="category_text" @change="handleCategoryChange" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="背景图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple :number=1 v-decorator="['bgPic']" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="背景音乐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['bgMusic']" dict="ilang_ld_bgm,name,id"  />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status', validatorRules.status]" :trigger-change="true" dictCode="status" placeholder="请选择状态" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="字幕" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['lyric']" rows="4" placeholder="请输入字幕" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['content']" rows="4" placeholder="请输入内容" />
            </a-form-item>
          </a-col>
            <a-form-item v-show="false">
              <a-input v-decorator="['category_text']"></a-input>
            </a-form-item>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import JSwitch from '@/components/jeecg/JSwitch'

  export default {
    name: 'LangDuForm',
    components: {
      JFormContainer,
      JImageUpload,
      JDictSelectTag,
      JSearchSelectTag,
      JCategorySelect,
      JSwitch,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
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
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          name: {
            rules: [
              { required: true, message: '请输入名称!'},
            ]
          },
          status: {
            initialValue:"1",
            rules: [
            ]
          },
        },
        url: {
          add: "/ilang/langDu/add",
          edit: "/ilang/langDu/edit",
          queryById: "/ilang/langDu/queryById"
        }
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
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','pinyin','author','recommends','cover','categoryId','categoryText','bgPic','bgMusic','readCount','status','lyric','content'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'name','pinyin','author','recommends','cover','categoryId','categoryText','bgPic','bgMusic','readCount','status','lyric','content'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }
    }
  }
</script>