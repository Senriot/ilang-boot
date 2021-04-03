<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="歌名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入歌名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="拼音码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['inputCode']" placeholder="请输入拼音码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="字数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['wordCount']" placeholder="请输入字数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="语种" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['langId']" pcode="song_language" placeholder="请选择语种" back="lang_text" @change="handleCategoryChange" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['typeId']" pcode="song_category" placeholder="请选择分类" back="type_text" @change="handleCategoryChange" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="歌星" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-popup
                v-decorator="['artistsName']"
                :trigger-change="true"
                org-fields="id,name"
                dest-fields="artistIds,artistsName"
                code="artist_list"
                @callback="popupCallback"
                />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="伴唱音轨" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['amTrack']" placeholder="请输入伴唱音轨"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="歌曲画质" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['quality']" placeholder="请输入歌曲画质"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status']" :trigger-change="true" dictCode="status" placeholder="请选择状态" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="播放量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hot']" placeholder="请输入播放量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="本地路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['filePath']" placeholder="请输入本地路径"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="文件名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['fileName']" placeholder="请输入文件名"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="原唱音量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['orVol']" placeholder="请输入原唱音量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="伴唱音量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['acVol']" placeholder="请输入伴唱音量" style="width: 100%" />
            </a-form-item>
          </a-col>
            <a-form-item v-show="false">
              <a-input v-decorator="['type_text']"></a-input>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JCategorySelect from '@/components/jeecg/JCategorySelect'

  export default {
    name: 'SongForm',
    components: {
      JFormContainer,
      JDictSelectTag,
      JCategorySelect,
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
          code: {
            rules: [
              { required: true, message: '请输入编码!'},
              { validator: (rule, value, callback) => validateDuplicateValue('ilang_song', 'code', value, this.model.id, callback)},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入歌名!'},
            ]
          },
        },
        url: {
          add: "/ilang/song/add",
          edit: "/ilang/song/edit",
          queryById: "/ilang/song/queryById"
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
          this.form.setFieldsValue(pick(this.model,'code','name','inputCode','wordCount','langId','langText','typeId','typeText','artistsName','amTrack','quality','status','hot','filePath','fileName','orVol','acVol'))
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
        this.form.setFieldsValue(pick(row,'code','name','inputCode','wordCount','langId','langText','typeId','typeText','artistsName','amTrack','quality','status','hot','filePath','fileName','orVol','acVol'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }
    }
  }
</script>