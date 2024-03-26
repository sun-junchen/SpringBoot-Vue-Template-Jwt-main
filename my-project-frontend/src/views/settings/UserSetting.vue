<template>
  <div style="display: flex;">
    <div class="setting-left">
      <card :icon="User" title="账号信息设置" desc="在这里编辑您的个人信息，您可以在隐私设置中选择是否展示" v-loading="loading.form">
        <el-form :model="baseForm" :rules="rules" ref="baseFormRef" label-position="top"
          style="margin: 0 10px 10px 10px;">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="baseForm.username" maxlength="10" />
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="baseForm.gender">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="baseForm.phone" maxlength="11" />
          </el-form-item>
          <el-form-item label="qq号" prop="qq">
            <el-input v-model="baseForm.qq" maxlength="13" />
          </el-form-item>
          <el-form-item label="微信号" prop="wx">
            <el-input v-model="baseForm.wx" maxlength="20" />
          </el-form-item>
          <el-form-item label="个人简介" prop="description">
            <el-input type="textarea" :rows="6" v-model="baseForm.description" maxlength="200" />
          </el-form-item>
          <div>
            <el-button type="success" @click="saveDetails" :icon="Select" :loading="loading.base">保存用户信息</el-button>
          </div>
        </el-form>
      </card>
      <card style="margin-top: 10px;" :icon="Message" title="电子邮件设置" desc="你可以在这里修改默认绑定的电子邮件地址">
        <el-form :model="emailForm" label-position="top" ref="emailFormRef" style="margin: 0 10px 10px 10px;">
          <el-form-item label="电子邮件" prop="email">
            <el-input v-model="emailForm.email" />
          </el-form-item>

          <el-form-item prop="code">
            <el-row style="width: 100%;" :gutter="10">
              <el-col :span="18">
                <el-input placeholder="请获取验证码" v-model="emailForm.code" />
              </el-col>
              <el-col :span="6">
                <el-button type="success" style="width: 100%;" plain>获取验证码</el-button>
              </el-col>
            </el-row>

          </el-form-item>
          <div>
            <el-button type="success" :icon="Refresh">更新电子邮件</el-button>
          </div>
        </el-form>
      </card>
    </div>
    <div class="setting-right">
      <div style="position: sticky; top: 20px;">
        <card>

          <div style="text-align: center; padding: 5px 15px 0 15px">
            <div>
              <el-avatar :size="70" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <div style="font-weight: bold;">你好， {{ store.user.username }}</div>
              <el-divider style="margin: 10px 0"></el-divider>
              <div style="font-size: 14px; color: gray; padding: 10px">
                {{ description || '这个用户很懒,没有填写个人简介' }}
              </div>
            </div>
          </div>
        </card>
        <card style="margin-top: 10px; font-size: 14px;">
          <div>账号注册时间：{{ registerTime }}</div>
          <div style="color: gray;">欢迎加入我们的学习论坛!</div>
        </card>

      </div>

    </div>
  </div>

</template>

<script setup>
import Card from "@/components/Card.vue";
import { Message, Refresh, Select, User } from '@element-plus/icons-vue';
import { useStore } from "@/store";
import { computed, reactive, ref } from "vue";
import { post, get } from "@/net";
import { ElMessage } from "element-plus";

const store = useStore()

const registerTime = computed(() => new Date(store.user.registerTime).toLocaleString())


const description = ref('')
const baseFormRef = ref()
const emailFormRef = ref()

const baseForm = reactive({
  username: '',
  gender: 1,
  phone: '',
  qq: '',
  wx: '',
  description: ''
})

const emailForm = reactive({
  email: '',
  code: ''

})

const loading = reactive({
  form: true,
  base: false
})



const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { validator: validateUsername, trigger: ['blur', 'change'] },
    { min: 2, max: 10, message: '用户名的长度必须在2-10个字符之间', trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    { type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change'] }
  ],
  code: [
    { required: true, message: '请输入获取的验证码', trigger: 'blur' },
  ]
}


function saveDetails() {
  baseFormRef.value.validate(isValid => {
    if (isValid) {
      loading.base = true
      post('api/user/save-details', baseForm, () => {
        ElMessage.success('用户信息保存成功')
        store.user.username = baseForm.username
        description.value = baseForm.description
        loading.base = false
      }, (message) => {
        ElMessage.warning(message)
        loading.base = true
      })

    }

  })
}

get('/api/user/details', data => {
  baseForm.username = store.user.username
  baseForm.gender = data.gender
  baseForm.phone = data.phone
  baseForm.wx = data.wx
  baseForm.qq = data.qq
  baseForm.description = description.value = data.description
  loading.form = false
})
// export default {
//     name: "UserSetting"
// }
</script>

<style scoped>
.setting-left {
  flex: 1;
  margin: 20px;
}

.setting-right {
  width: 300px;
  margin: 20px 30px 20px 0;

}
</style>