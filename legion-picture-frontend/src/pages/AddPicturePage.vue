
<template>
  <div id="addPicturePage">
    <h2 style="margin-bottom: 16px">
      {{ route.query?.id ?'Edit Picture':'Add Picture' }}
    </h2>
    <PictureUpload :picture="picture" :onSuccess="onSuccess" />
    <a-form v-if="picture" layout="vertical" :model="pictureForm" @finish="handleSubmit">
      <a-form-item label="name" name="name">
        <a-input v-model:value="pictureForm.name" placeholder="please input name" />
      </a-form-item>
      <a-form-item label="introduction" name="introduction">
<!--        autoSize allow auto extend the text box area, :rows=2 restrict the limitof rows-->
        <a-textarea
          v-model:value="pictureForm.introduction"
          placeholder="please input introduction"
          :auto-Size="{ minRows: 2, maxRows: 5 }"
          allowClear
        />


      </a-form-item>
      <a-form-item label="category" name="category">
        <a-auto-complete
          v-model:value="pictureForm.category"
          placeholder="please input category"
          :options="categoryOptions"
          allowClear
        />
      </a-form-item>
      <a-form-item label="tags" name="tags">
        <a-select
          v-model:value="pictureForm.tags"
          mode="tags"
          placeholder="please input tags"
          :options = "tagOptions"
          allowClear
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">Create</a-button>
      </a-form-item>
    </a-form>
  </div>

</template>
<script setup lang="ts">
import PictureUpload from '@/components/PictureUpload.vue'
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  editPictureUsingPost,
  getPictureVoByIdUsingGet,
  listPictureTagCategoryUsingGet
} from '@/api/pictureController.ts'
import { message } from 'ant-design-vue'

const picture = ref<API.PictureVO>()
const router = useRouter()
const pictureForm = reactive<API.PictureEditRequest>({})
const onSuccess = (newpicture: API.PictureVO) => {
  picture.value = newpicture;
  pictureForm.name = newpicture.name
}

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  const pictureId = picture.value?.id
  if (!pictureId) {
    return
  }
  const res = await editPictureUsingPost({
    id: pictureId,
    ...values,
  })
  if (res.data.code === 0 && res.data.data) {
    message.success('创建成功')
    // 跳转到图片详情页
    router.push({
      path: `/picture/${pictureId}`,
    })
  } else {
    message.error('创建失败，' + res.data.message)
  }
}


const categoryOptions = ref<string[]>([])
const tagOptions = ref<string[]>([])

// 获取标签和分类选项
const getTagCategoryOptions = async () => {
  const res = await listPictureTagCategoryUsingGet()
  if (res.data.code === 0 && res.data.data) {
    // 转换成下拉选项组件接受的格式
    tagOptions.value = (res.data.data.tagList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
    categoryOptions.value = (res.data.data.categoryList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
  } else {
    message.error('load category list fail，' + res.data.message)
  }
}

/**
 * route is different from router
 * route is for access the route_url
 * router is redirection
 */
const route = useRoute()

// 获取老数据
const getOldPicture = async () => {
  // 获取数据
  const id = route.query?.id
  if (id) {
    const res = await getPictureVoByIdUsingGet({
      id: id,
    })
    if (res.data.code === 0 && res.data.data) {
      const data = res.data.data
      picture.value = data
      pictureForm.name = data.name
      pictureForm.introduction = data.introduction
      pictureForm.category = data.category
      pictureForm.tags = data.tags
    }
  }
}

onMounted(() => {
  getOldPicture()
})

onMounted(() => {
  getTagCategoryOptions()
})

</script>

<style scoped>
#addPicturePage {
  max-width: 720px;
  margin: 0 auto;
}
</style>
