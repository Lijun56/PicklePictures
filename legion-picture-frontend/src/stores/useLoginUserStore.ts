import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getLoginUserUsingGet } from '@/api/userController.ts'

/**
 <use..> file is the hook in the vue, allow to modify, share one data group efficiently
 * one state stores one class/group of data
 *
 */

/**
 * useLoginUserStore stores user login infomation
 */
export const useLoginUserStore = defineStore('loginUser', () => {
  //default state value
  const loginUser = ref<API.LoginUserVO>({
    userName: 'not logged in',
  })

  //getter function of the state
  /**
   * remotely access login user info
   */
  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet()
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data
    }
  }


  //modify the state
  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  return { loginUser, fetchLoginUser, setLoginUser }
})
