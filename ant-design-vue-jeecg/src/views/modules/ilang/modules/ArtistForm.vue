<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="歌星名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入歌星名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="拼音码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['inputCode']" placeholder="请输入拼音码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['areaId']" pcode="artist_type" placeholder="请选择区域" back="area_text" @change="handleCategoryChange" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['gender', validatorRules.gender]" :trigger-change="true" dictCode="artist_type" placeholder="请选择类别" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple :number=1 v-decorator="['avatar']" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="出生地" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['birthplace']" placeholder="请输入出生地"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="生日" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择生日" v-decorator="['birthday']" :trigger-change="true" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['company']" placeholder="请输入所属公司"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="星座" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['constellation']" placeholder="请输入星座"  ></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="热度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['hot']" placeholder="请输入热度" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status']" :trigger-change="true" dictCode="status" placeholder="请选择状态" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="体重" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['weight']" placeholder="请输入体重"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="身高" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['stature']" placeholder="请输入身高"  ></a-input>
            </a-form-item>
          </a-col>
           <a-col :span="24">
            <a-form-item label="简介" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['info']" rows="4" placeholder="请输入简介" />
            </a-form-item>
          </a-col>
            <a-form-item v-show="false">
              <a-input v-decorator="['area_text']"></a-input>
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
  import JDate from '@/components/jeecg/JDate'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JCategorySelect from '@/components/jeecg/JCategorySelect'

  export default {
    name: 'ArtistForm',
    components: {
      JFormContainer,
      JDate,
      JImageUpload,
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
          name: {
            rules: [
              { required: true, message: '请输入歌星名称!'},
            ]
          },
          gender: {
            rules: [
              { required: true, message: '请输入类别!'},
            ]
          },
        },
        url: {
          add: "/ilang/artist/add",
          edit: "/ilang/artist/edit",
          queryById: "/ilang/artist/queryById"
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
          this.form.setFieldsValue(pick(this.model,'avatar','name','inputCode','areaId','areaText','gender','birthplace','birthday','company','constellation','info','hot','status','weight','stature'))
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
        this.form.setFieldsValue(pick(row,'avatar','name','inputCode','areaId','areaText','gender','birthplace','birthday','company','constellation','info','hot','status','weight','stature'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }
    }
  }
</script>
