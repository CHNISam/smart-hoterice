<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h2>配送片区管理</h2>
        <p>维护片区范围、优先级和归属服务点，为后续调度版本做准备。</p>
      </div>
      <el-button type="primary" @click="openCreate">新增片区</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="regions" border stripe>
        <el-table-column prop="name" label="片区" min-width="160" />
        <el-table-column prop="radiusKm" label="半径(km)" min-width="100" />
        <el-table-column prop="priority" label="优先级" min-width="90" />
        <el-table-column prop="minBatchThreshold" label="最小成批数" min-width="120" />
        <el-table-column prop="servicePointId" label="服务点 ID" min-width="110" />
        <el-table-column prop="driverId" label="司机 ID" min-width="100" />
        <el-table-column prop="enabled" label="启用" min-width="80">
          <template #default="{ row }">
            <el-tag :type="row.enabled ? 'success' : 'info'">{{ row.enabled ? '是' : '否' }}</el-tag>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑片区' : '新增片区'" width="760px">
      <el-form label-width="120px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="片区名称"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="半径(km)"><el-input-number v-model="form.radiusKm" :precision="2" :min="0" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="中心纬度"><el-input-number v-model="form.centerLat" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="中心经度"><el-input-number v-model="form.centerLng" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="优先级"><el-input-number v-model="form.priority" :min="1" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="最小成批数"><el-input-number v-model="form.minBatchThreshold" :min="1" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="司机 ID"><el-input-number v-model="form.driverId" :min="0" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="服务点 ID"><el-input-number v-model="form.servicePointId" :min="0" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="启用"><el-switch v-model="form.enabled" /></el-form-item></el-col>
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
import { onMounted, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import { createDeliveryRegion, deleteDeliveryRegion, listDeliveryRegions, updateDeliveryRegion } from '../../api/deliveryRegion'

const regions = ref([])
const dialogVisible = ref(false)
const form = ref(createEmptyForm())

onMounted(loadRegions)

async function loadRegions() {
  const res = await listDeliveryRegions()
  regions.value = res.data
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
  const payload = {
    ...form.value,
    centerLat: Number(form.value.centerLat || 0),
    centerLng: Number(form.value.centerLng || 0),
    radiusKm: Number(form.value.radiusKm || 0),
    priority: Number(form.value.priority || 1),
    minBatchThreshold: Number(form.value.minBatchThreshold || 1),
    driverId: Number(form.value.driverId || 0) || null,
    servicePointId: Number(form.value.servicePointId || 0) || null
  }
  if (payload.id) {
    await updateDeliveryRegion(payload)
  } else {
    await createDeliveryRegion(payload)
  }
  dialogVisible.value = false
  await loadRegions()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确认删除该配送片区？', '删除片区', { type: 'warning' })
  await deleteDeliveryRegion(id)
  await loadRegions()
}

function createEmptyForm() {
  return {
    id: null,
    name: '',
    centerLat: 0,
    centerLng: 0,
    radiusKm: 3,
    priority: 1,
    minBatchThreshold: 3,
    driverId: null,
    servicePointId: null,
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

.page-header h2 {
  margin: 0 0 6px;
}

.page-header p {
  margin: 0;
  color: #909399;
}
</style>
