import { useLoginUserStore } from '@/stores/useLoginUserStore.ts'
import router from '@/router'
import { message } from 'ant-design-vue'

/**
 * global role control, serve as router guard
 * everytime time before loading the page, it will run the access.ts
 */
let firstFetchLoginUser = true;

router.beforeEach(async (to, from, next) => {
  const loginUserStore = useLoginUserStore()
  let loginUser = loginUserStore.loginUser

  //fetch if never fetchs
  if(firstFetchLoginUser) {
    await loginUserStore.fetchLoginUser()
    loginUser = loginUserStore.loginUser
    firstFetchLoginUser = false
  }
  //role control
  const toUrl = to.fullPath
  if(toUrl.startsWith('/admin')) {
    if(!loginUser || loginUser.userRole !== 'admin'){
      message.error("You have no access")
      next(`/user/login?redirect=${to.fullPath}`)
      return
    }
  }
  next()
})
