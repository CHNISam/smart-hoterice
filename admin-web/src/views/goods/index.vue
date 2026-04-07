<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h2>商品管理</h2>
        <p>维护 V0.2.0 商品目录、价格和上架状态。</p>
      </div>
      <el-button type="primary" @click="openCreate">新增商品</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="goodsList" border stripe>
        <el-table-column prop="goodsSn" label="货号" min-width="110" />
        <el-table-column prop="name" label="商品名称" min-width="180" />
        <el-table-column prop="categoryId" label="分类" min-width="120">
          <template #default="{ row }">{{ categoryNameMap[row.categoryId] || '-' }}</template>
        </el-table-column>
        <el-table-column prop="retailPrice" label="零售价" min-width="100" />
        <el-table-column prop="unit" label="单位" min-width="80" />
        <el-table-column prop="isOnSale" label="上架" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.isOnSale ? 'success' : 'info'">{{ row.isOnSale ? '是' : '否' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isHot" label="热门" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.isHot ? 'danger' : 'info'">{{ row.isHot ? '是' : '否' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑商品' : '新增商品'" width="720px">
      <el-form label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="货号"><el-input v-model="form.goodsSn" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="商品名称"><el-input v-model="form.name" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类">
              <el-select v-model="form.categoryId" style="width: 100%">
                <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位"><el-input v-model="form.unit" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="零售价"><el-input-number v-model="form.retailPrice" :min="0" :precision="2" style="width: 100%" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="划线价"><el-input-number v-model="form.counterPrice" :min="0" :precision="2" style="width: 100%" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上架"><el-switch v-model="form.isOnSale" /></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="热门"><el-switch v-model="form.isHot" /></el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简介"><el-input v-model="form.brief" type="textarea" :rows="3" /></el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详情"><el-input v-model="form.detail" type="textarea" :rows="5" /></el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import { listCategories } from '../../api/category'
import { createGoods, deleteGoods, listGoods, updateGoods } from '../../api/goods'

const goodsList = ref([])
const categories = ref([])
const dialogVisible = ref(false)
const form = ref(createEmptyForm())

const categoryNameMap = computed(() =>
  categories.value.reduce((acc, item) => {
    acc[item.id] = item.name
    return acc
  }, {})
)

onMounted(async () => {
  await Promise.all([loadCategories(), loadGoods()])
})

async function loadCategories() {
  const res = await listCategories()
  categories.value = res.data
}

async function loadGoods() {
  const res = await listGoods()
  goodsList.value = res.data
}

function openCreate() {
  form.value = createEmptyForm()
  dialogVisible.value = true
}

function openEdit(row) {
  form.value = {
    ...createEmptyForm(),
    ...row
  }
  dialogVisible.value = true
}

async function handleSubmit() {
  const payload = normalizeForm(form.value)
  if (payload.id) {
    await updateGoods(payload)
  } else {
    await createGoods(payload)
  }
  dialogVisible.value = false
  await loadGoods()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('删除后商品不会出现在管理和客户端列表中，确认继续？', '删除商品', { type: 'warning' })
  await deleteGoods(id)
  await loadGoods()
}

function createEmptyForm() {
  return {
    id: null,
    goodsSn: '',
    name: '',
    categoryId: null,
    brandId: 1,
    gallery: '[]',
    keywords: '',
    brief: '',
    isOnSale: true,
    sortOrder: 100,
    picUrl: '',
    shareUrl: '',
    isNew: false,
    isHot: false,
    unit: '袋',
    counterPrice: 0,
    retailPrice: 0,
    detail: ''
  }
}

function normalizeForm(source) {
  return {
    ...source,
    categoryId: source.categoryId || 0,
    brandId: source.brandId || 1,
    retailPrice: Number(source.retailPrice || 0),
    counterPrice: Number(source.counterPrice || 0)
  }
}
</script>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}

.page-header h2 {
  margin: 0 0 6px;
}

.page-header p {
  margin: 0;
  color: #909399;
}
</style>
