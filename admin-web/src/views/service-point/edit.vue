<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h2>{{ point.name || '编辑服务点' }}</h2>
        <p>基础信息和可售商品配置归并在同一编辑页，商品配置入口不再独立悬浮在侧边菜单。</p>
      </div>
      <div class="page-actions">
        <el-button @click="router.push('/service-points')">返回列表</el-button>
        <el-button type="primary" @click="savePoint">保存基础信息</el-button>
      </div>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="基础信息" name="basic">
        <el-card shadow="never">
          <el-form label-width="110px">
            <el-row :gutter="16">
              <el-col :span="12"><el-form-item label="名称"><el-input v-model="point.name" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="类型"><el-input v-model="point.type" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="城市"><el-input v-model="point.city" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="电话"><el-input v-model="point.phone" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="纬度"><el-input-number v-model="point.latitude" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="经度"><el-input-number v-model="point.longitude" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="营业时间"><el-input v-model="point.businessHours" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="启用"><el-switch v-model="point.enabled" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="地址"><el-input v-model="point.address" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="说明"><el-input v-model="point.description" type="textarea" :rows="4" /></el-form-item></el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="可售商品" name="goods">
        <el-card shadow="never">
          <div class="goods-header">
            <div>
              <h3>服务点商品配置</h3>
              <p>入口跟随服务点编辑页，强调“某个服务点卖什么”，而不是“全局独立配置关系”。</p>
            </div>
            <el-button type="primary" @click="openGoodsDialog">新增商品</el-button>
          </div>

          <el-table :data="bindings" border stripe>
            <el-table-column prop="goodsName" label="商品" min-width="180" />
            <el-table-column prop="categoryId" label="分类 ID" min-width="90" />
            <el-table-column prop="retailPrice" label="平台默认价" min-width="110" />
            <el-table-column prop="price" label="服务点价" min-width="100">
              <template #default="{ row }">
                <el-input-number v-model="row.price" :min="0" :precision="2" style="width: 120px" />
              </template>
            </el-table-column>
            <el-table-column prop="enabled" label="启用" min-width="80">
              <template #default="{ row }">
                <el-switch v-model="row.enabled" />
              </template>
            </el-table-column>
            <el-table-column label="操作" width="180" fixed="right">
              <template #default="{ row }">
                <el-button link type="primary" @click="saveBinding(row)">保存</el-button>
                <el-button link type="danger" @click="removeBinding(row.id)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="goodsDialogVisible" title="新增商品" width="680px">
      <el-form label-width="100px">
        <el-form-item label="商品">
          <el-select v-model="bindingForm.goodsId" filterable style="width: 100%">
            <el-option
              v-for="item in availableGoods"
              :key="item.id"
              :label="`${item.name} (${item.goodsSn})`"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="服务点价">
          <el-input-number v-model="bindingForm.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="启用">
          <el-switch v-model="bindingForm.enabled" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="goodsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="createBinding">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { listGoods } from '../../api/goods'
import { detailServicePoint, updateServicePoint } from '../../api/servicePoint'
import { deleteServicePointGoods, listServicePointGoods, saveServicePointGoods } from '../../api/servicePointGoods'

const route = useRoute()
const router = useRouter()

const servicePointId = Number(route.params.id)
const activeTab = ref('basic')
const point = ref(createEmptyPoint())
const bindings = ref([])
const goodsList = ref([])
const goodsDialogVisible = ref(false)
const bindingForm = ref(createEmptyBinding())

const availableGoods = computed(() => {
  const selectedIds = new Set(bindings.value.map(item => item.goodsId))
  return goodsList.value.filter(item => !selectedIds.has(item.id))
})

onMounted(async () => {
  await Promise.all([loadPoint(), loadGoods(), loadBindings()])
})

async function loadPoint() {
  const res = await detailServicePoint(servicePointId)
  point.value = {
    ...createEmptyPoint(),
    ...res.data
  }
}

async function loadGoods() {
  const res = await listGoods()
  goodsList.value = res.data
}

async function loadBindings() {
  const res = await listServicePointGoods(servicePointId)
  bindings.value = res.data.map(item => ({
    ...item,
    price: item.price ?? item.retailPrice
  }))
}

async function savePoint() {
  await updateServicePoint({
    ...point.value,
    latitude: Number(point.value.latitude || 0),
    longitude: Number(point.value.longitude || 0)
  })
  await loadPoint()
}

function openGoodsDialog() {
  bindingForm.value = createEmptyBinding()
  goodsDialogVisible.value = true
}

async function createBinding() {
  await saveServicePointGoods({
    servicePointId,
    goodsId: bindingForm.value.goodsId,
    price: bindingForm.value.price,
    enabled: bindingForm.value.enabled
  })
  goodsDialogVisible.value = false
  await loadBindings()
}

async function saveBinding(row) {
  await saveServicePointGoods({
    id: row.id,
    servicePointId: row.servicePointId,
    goodsId: row.goodsId,
    price: row.price,
    enabled: row.enabled
  })
  await loadBindings()
}

async function removeBinding(id) {
  await ElMessageBox.confirm('确认移除该服务点商品？', '移除商品', { type: 'warning' })
  await deleteServicePointGoods(id)
  await loadBindings()
}

function createEmptyPoint() {
  return {
    id: servicePointId,
    name: '',
    type: 'STORE',
    city: '',
    address: '',
    latitude: 0,
    longitude: 0,
    phone: '',
    distance: '',
    imageUrl: '',
    businessHours: '08:00-18:00',
    description: '',
    facilities: '[]',
    enabled: true
  }
}

function createEmptyBinding() {
  return {
    goodsId: null,
    price: 0,
    enabled: true
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

.page-header h2,
.goods-header h3 {
  margin: 0 0 6px;
}

.page-header p,
.goods-header p {
  margin: 0;
  color: #909399;
}

.page-actions,
.goods-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}
</style>
