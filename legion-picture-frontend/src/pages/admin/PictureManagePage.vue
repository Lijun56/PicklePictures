<template>
  <!-- search bar -->
  <a-form layout="inline" :model="searchParams" @finish="doSearch">
    <a-form-item label="Keyword" name="searchText">
      <a-input
        v-model:value="searchParams.searchText"
        placeholder="Search by name and introduction"
        allow-clear
      />
    </a-form-item>
    <a-form-item label="Category" name="category">
      <a-input v-model:value="searchParams.category" placeholder="Enter category" allow-clear />
    </a-form-item>
    <a-form-item label="Tags" name="tags">
      <a-select
        v-model:value="searchParams.tags"
        mode="tags"
        placeholder="Enter tags"
        style="min-width: 180px"
        allow-clear
      />
    </a-form-item>
    <a-form-item label="Review Status" name="reviewStatus">
      <a-select
        v-model:value="searchParams.reviewStatus"
        :options="PIC_REVIEW_STATUS_OPTIONS"
        placeholder="Please select review status"
        style="min-width: 180px"
        allow-clear
      />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit">Search</a-button>
    </a-form-item>
  </a-form>
  <!--  table-->
  <div id="pictureManagerPage">
    <a-table :columns="columns" :data-source="dataList" :pagination="pagination">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'url'">
          <a-image :src="record.url" :width="120" />
        </template>
        <!-- 标签 -->
        <template v-if="column.dataIndex === 'tags'">
          <a-space wrap>
            <a-tag v-for="tag in JSON.parse(record.tags || '[]')" :key="tag">{{ tag }}</a-tag>
          </a-space>
        </template>
        <!-- 图片信息 -->
        <template v-if="column.dataIndex === 'picInfo'">
          <div>Format: {{ record.picFormat }}</div>
          <div>Width: {{ record.picWidth }}</div>
          <div>Height: {{ record.picHeight }}</div>
          <div>Aspect Ratio: {{ record.picScale }}</div>
          <div>Size: {{ (record.picSize / 1024).toFixed(2) }}KB</div>
        </template>
        <template v-if="column.dataIndex === 'reviewMessage'">
          <div>Review State：{{ PIC_REVIEW_STATUS_MAP[record.reviewStatus] }}</div>
          <div>Review Info：{{ record.reviewMessage }}</div>
          <div>Reviewer：{{ record.reviewerId }}</div>
          <div v-if="record.reviewTime">
            Review Time：{{dayjs(record.reviewTime).format('YYYY-MM-DD HH:mm:ss')}}
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.dataIndex === 'editTime'">
          {{ dayjs(record.editTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space wrap>
            <a-button
              v-if="record.reviewStatus !== PIC_REVIEW_STATUS_ENUM.PASS"
              @click="handleReview(record, PIC_REVIEW_STATUS_ENUM.PASS)"
            >
              Approve
            </a-button>
            <a-button
              v-if="record.reviewStatus !== PIC_REVIEW_STATUS_ENUM.REJECT"
              danger
              @click="handleReview(record, PIC_REVIEW_STATUS_ENUM.REJECT)"
            >
              Reject
            </a-button>
            <a-button :href="`/add_picture?id=${record.id}`" target="_blank"
            >Edit
            </a-button>
            <a-button danger @click="doDelete(record.id)">Delete</a-button>
          </a-space>
        </template>
      </template>

    </a-table>
  </div>

</template>
<script lang="ts" setup>
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue'
import { computed, onMounted, reactive, ref } from 'vue'
import dayjs from 'dayjs'
import {
  deletePictureUsingPost,
  doPictureReviewUsingPost,
  listPictureByPageUsingPost
} from '@/api/pictureController.ts'
import { message } from 'ant-design-vue'
import { PIC_REVIEW_STATUS_ENUM, PIC_REVIEW_STATUS_MAP, PIC_REVIEW_STATUS_OPTIONS } from '../../constants/picture.ts'

const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    width: 80
  },
  {
    title: 'Image',
    dataIndex: 'url'
  },
  {
    title: 'Name',
    dataIndex: 'name'
  },
  {
    title: 'Introduction',
    dataIndex: 'introduction',
    ellipsis: true
  },
  {
    title: 'Category',
    dataIndex: 'category'
  },
  {
    title: 'Tags',
    dataIndex: 'tags'
  },
  {
    title: 'Image Info',
    dataIndex: 'picInfo'
  },
  {
    title: 'User ID',
    dataIndex: 'pictureId',
    width: 80
  },
  {
    title: 'Created Time',
    dataIndex: 'createTime'
  },
  {
    title: 'Edited Time',
    dataIndex: 'editTime'
  },
  {
    title: 'Actions',
    key: 'action'
  },
  {
    title: 'Review Info',
    dataIndex: 'reviewMessage',
  },
]
// data
const dataList = ref<API.Picture[]>([])
const total = ref(0)

//search condition
const searchParams = reactive<API.PictureQueryRequest>({
  current: 1,
  pageSize: 10
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
    showTotal: (total: number) => ` current page: ${total} pictures `
  }
})
//Query Data;
const fetchData = async () => {
  const res = await listPictureByPageUsingPost({ ...searchParams })
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

const doDelete = async (id: string) => {
  if (!id) {
    return
  }
  const res = await deletePictureUsingPost({ id })
  if (res.data.code === 0) {
    message.success('delete picture account successfully')
    fetchData()
  } else {
    message.error('delete picture account failed')
  }
}

const handleReview = async (record: API.Picture, reviewStatus: number) => {
  const reviewMessage = reviewStatus === PIC_REVIEW_STATUS_ENUM.PASS ? 'approved' : 'rejected'
  const res = await doPictureReviewUsingPost({
    id: record.id,
    reviewStatus,
    reviewMessage,
  })
  if (res.data.code === 0) {
    message.success('审核操作成功')
    // 重新获取列表
    fetchData()
  } else {
    message.error('审核操作失败，' + res.data.message)
  }
}

</script>

