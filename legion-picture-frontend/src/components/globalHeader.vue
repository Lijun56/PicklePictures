<template>
  <div class="globalHeader">
<!--    :wrap="false => disable the auto start a new line-->
    <a-row :wrap="false">
      <a-col flex="200px">
        <!--    object within router-link will allow to REDIRECT to "/" -->
        <router-link to="/">
          <div class="title-bar">
            <!--      class for controlling style, alt is for the vase the image cant be load and still have text showed-->
            <img class="logo" src="../assets/logo.png" alt="logo" />
            <div class="title">Pickle</div>
          </div>
        </router-link>
      </a-col>
      <a-col flex="auto">
<!--        highlight is determined by the selectedKeys attribute and it's controlled by the variable 'current' -->
        <a-menu v-model:selectedKeys="current" mode="horizontal" :items="items" @click="doMenuClick"/>
      </a-col>

<!--      user info display-->
      <a-col flex="120px">

        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.id">
            <a-dropdown>
              <a-space>
              <a-avatar :src="loginUserStore.loginUser.userAvatar" />
              {{loginUserStore.loginUser.userName ?? 'anonymous'}}
              </a-space>
              <template #overlay>
                <a-menu>
                  <a-menu-item @click="doLogout">
                    <LogoutOutlined/>
                    Log out
                  </a-menu-item>

                </a-menu>
              </template>
            </a-dropdown>

          </div>
          <div v-else>
            <a-button type="primary" href="/user/login">Login</a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import { computed, h, ref } from 'vue'
import { HomeOutlined,LogoutOutlined } from '@ant-design/icons-vue'
import { type MenuProps, message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import { userLogoutUsingPost } from '@/api/userController.ts'

const current = ref<string[]>([])
const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: 'Home',
    title: 'Home',
  },
  {
    key: '/admin/manage',
    label: 'admin',
    title: 'admin',
  },
  {
    key: 'others',
    label: h('a', { href: 'https://www.stefenz.com', target: '_blank' }, 'Directory'),
    title: 'Directory',
  },
]
const filterMenus = (menus = [] as MenuProps['items']) =>{
  return menus?.filter((menu)=>{
    if(menu?.key?.startsWith('/admin')){
      const loginUser = loginUserStore.loginUser
      if(!loginUser || loginUser.userRole !== 'admin'){
          return false
      }
    }
    return true
  })
}
const items = computed<MenuProps['items']>(()=>filterMenus(originItems))
const router = useRouter();
router.afterEach((to, from, next) => {
  current.value = [to.path];
});
const doMenuClick =({key}:{key:string})=>{
  router.push({
    path: key,
  })
}

const loginUserStore = useLoginUserStore();

const doLogout = async () => {
  const res = await userLogoutUsingPost();
  if(res.data.code === 0){
    loginUserStore.setLoginUser(
      {
        userName:"not logged in",
      }
    )
    message.success("logged out successfully");
    await router.push('/user/login');
  }else{
    message.error("loged out failed"+ res.data.message);
  }
}
</script>
<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
  font-weight: bold;
}

.logo {
  height: 48px;
}
</style>
