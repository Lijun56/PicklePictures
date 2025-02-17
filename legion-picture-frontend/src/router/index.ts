import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserLoginPage from '@/pages/user/UserLoginPage.vue'
import HomePage from '@/pages/HomePage.vue'
import UserRegisterPage from '@/pages/user/UserRegisterPage.vue'
import UserManagePage from '@/pages/admin/UserManagePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path:'/user/login',
      name:'userLogin',
      component:UserLoginPage,
    },
    {
      path:'/user/register',
      name:'userRegister',
      component: UserRegisterPage,
    },
    {
      path:'/admin/manage',
      name:'userManage',
      component: UserManagePage,
    },
  ],
})

export default router
