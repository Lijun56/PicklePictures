<template>
<!-- search bar -->
  <a-form layout="inline" :model="searchParams" @finish="doSearch">
    <a-form-item label="account">
      <a-input v-model:value="searchParams.userAccount" placeholder="input user account" />
    </a-form-item>
    <a-form-item label="name">
      <a-input v-model:value="searchParams.userName" placeholder="input user sname" />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit">search</a-button>
    </a-form-item>
  </a-form>

<!--  table-->
  <div id="userManagerPage">
    <a-table :columns="columns" :data-source="dataList" :pagination="pagination">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'userAvatar'">
          <a-image :src="record.userAvatar" :width="120" />
        </template>
        <template v-else-if="column.dataIndex === 'userRole'">
          <div v-if="record.userRole === 'admin'">
            <a-tag color="green">admin</a-tag>
          </div>
          <div v-if="record.userRole === 'user'">
            <a-tag color="red">user</a-tag>
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-button danger @click="onDelete(record.id)">delete</a-button>
        </template>
      </template>
    </a-table>
  </div>

</template>
<script lang="ts" setup>
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue'
import { computed, onMounted, reactive, ref } from 'vue'
import dayjs from 'dayjs'
import { deleteUserUsingPost, listUserVoByPageUsingPost } from '@/api/userController.ts'
import { message } from 'ant-design-vue'

const columns = [
  {
    title: 'id',
    dataIndex: 'id'
  },
  {
    title: 'User Account',
    dataIndex: 'userAccount'
  },
  {
    title: 'User Name',
    dataIndex: 'userName'
  },
  {
    title: 'User Avatar',
    dataIndex: 'userAvatar'
  },
  {
    title: 'Profile',
    dataIndex: 'userProfile'
  },
  {
    title: 'Role',
    dataIndex: 'userRole'
  },
  {
    title: 'Create Time',
    dataIndex: 'createTime'
  },
  {
    title: 'Action',
    key: 'action'
  }
]

// data
const dataList = ref<API.UserVO[]>([])
const total = ref(0)

//search condition
const searchParams = reactive<API.UserQueryRequest>({
  current: 1,
  pageSize: 10,
})

// 表格变化处理
const doTableChange = (page: any) => {
  searchParams.current = page.current
  searchParams.pageSize = page.pageSize
  fetchData()
}

// 分页参数
/**
 * computed function allow to re run the pagination after the data get changed
 */
const pagination = computed(() => {
  return {
    current: searchParams.current ?? 1,
    pageSize: searchParams.pageSize ?? 10,
    total: total.value,
    showSizeChanger: true,
    showTotal: (total:number) => ` current page: ${total} users `
  }
})
//Query Data;
const fetchData = async () => {
  const res = await listUserVoByPageUsingPost({ ...searchParams })
  if (res.data.data) {
    dataList.value = res.data.data.records ?? []
    total.value = res.data.data.total ?? 0
  } else {
    message.error('fetch data fail' + res.data.message)
  }
}
// onMounted function auto run for once when page get loaded
onMounted(() => {
  fetchData()
})

const doSearch = () => {
  // 重置页码
  searchParams.current = 1
  fetchData()
}

const onDelete = async (id:string) => {
  if(!id){
    return
  }
  const res = await deleteUserUsingPost({id: Number(id)})
  if(res.data.code === 0){
    message.success("delete user account successfully")
    fetchData()
  }else{
    message.error("delete user account failed")
  }
}
</script>

