<template>
  <div id="userRegisterPage">
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
      <a-form-item
        name="checkPassword"
        :rules="[{ required: true, message: 'Please Retype your password!' },{
        min:8,message: 'Retyped Password must be at least 8 characters long!',
      }]"
      >
        <a-input-password v-model:value="formState.checkPassword" placeholder="Retype Password" />
      </a-form-item>
      <div class="tips">
        Dont Have Account?
        <RouterLink to="/user/login">Login</RouterLink>

      </div>


      <a-form-item >
        <a-button type="primary" html-type="submit" style="width: 100%">Sign Up</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import { reactive } from 'vue'
import { userRegisterUsingPost } from '@/api/userController.ts'
import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
interface FormState {
  username: string;
  password: string;
  remember: boolean;
}
const formState = reactive<API.UserRegisterRequest>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

const router = useRouter()
const loginUserStore = useLoginUserStore()

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  if(formState.userPassword !== formState.checkPassword) {
    message.error('passwords do not match!')
    return;
  }
  const res = await userRegisterUsingPost(values)
  // signup successfully
  if (res.data.code === 0 && res.data.data) {
    message.success('sign up successfully')
    router.push({
      path: '/user/login',
      replace: true,
    })
  } else {
    message.error('sign up failed，' + res.data.message)
  }
}


</script>
<style scoped>
#userRegisterPage{
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
