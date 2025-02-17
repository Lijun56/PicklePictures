<template>
  <div id="userLoginPage">
    <h2 class="title">Pickle Pictures System</h2>
    <div class="desc">Smart Cloud Colab Pictures System</div>
    <a-form
      :model="formState"
      name="basic"
      autocomplete="off"
      @finish="handleSubmit"
    >
      <a-form-item
        name="userAccount"
        :rules="[{ required: true, message: 'Please input your account number!' }]"
      >
        <a-input v-model:value="formState.userAccount" placeholder="Account" />
      </a-form-item>

      <a-form-item
        name="userPassword"
        :rules="[{ required: true, message: 'Please input your password!' },{
        min:8,message: 'Password must be at least 8 characters long!',
      }]"
      >
        <a-input-password v-model:value="formState.userPassword" placeholder="Password" />
      </a-form-item>
      <div class="tips">
        Dont Have Account?
        <RouterLink to="/user/register">Sign Up</RouterLink>

      </div>


      <a-form-item >
        <a-button type="primary" html-type="submit" style="width: 100%">Submit</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue'
import { userLoginUsingPost } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
interface FormState {
  username: string;
  password: string;
  remember: boolean;
}
const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: ''
})

const router = useRouter()
const loginUserStore = useLoginUserStore()

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  const res = await userLoginUsingPost(values)
  // 登录成功，把登录态保存到全局状态中
  if (res.data.code === 0 && res.data.data) {
    await loginUserStore.fetchLoginUser()
    message.success('login successfully')
    router.push({
      path: '/',
      replace: true,
    })
  } else {
    message.error('login failed，' + res.data.message)
  }
}


</script>
<style scoped>
#userLoginPage{
  max-width: 360px;
  margin: 0 auto;
}
.title {
  text-align: center;
  margin-bottom: 16px;
}
.desc{
  text-align: center;
  color:#bbb;
  margin-bottom: 16px;
}
.tips{
  color: #bbb;
  text-align: right;
  font-size: 13px;
  margin-bottom: 16px;

}
</style>
