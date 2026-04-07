<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h2>服务点管理</h2>
        <p>维护门店/服务点基础信息，供客户端选择履约站点。</p>
      </div>
      <el-button type="primary" @click="openCreate">新增服务点</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="points" border stripe>
        <el-table-column prop="name" label="服务点" min-width="180" />
        <el-table-column prop="type" label="类型" min-width="110" />
        <el-table-column prop="city" label="城市" min-width="110" />
        <el-table-column prop="address" label="地址" min-width="220" show-overflow-tooltip />
        <el-table-column prop="phone" label="电话" min-width="140" />
        <el-table-column prop="businessHours" label="营业时间" min-width="120" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑服务点' : '新增服务点'" width="760px">
      <el-form label-width="110px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="名称"><el-input v-model="form.name" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="类型"><el-input v-model="form.type" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="城市"><el-input v-model="form.city" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="纬度"><el-input-number v-model="form.latitude" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="经度"><el-input-number v-model="form.longitude" :precision="6" :step="0.000001" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="营业时间"><el-input v-model="form.businessHours" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="启用"><el-switch v-model="form.enabled" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="地址"><el-input v-model="form.address" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="说明"><el-input v-model="form.description" type="textarea" :rows="4" /></el-form-item></el-col>
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
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { createServicePoint, deleteServicePoint, listServicePoints, updateServicePoint } from '../../api/servicePoint'

const router = useRouter()
const points = ref([])
const dialogVisible = ref(false)
const form = ref(createEmptyForm())

onMounted(loadPoints)

async function loadPoints() {
  const res = await listServicePoints()
  points.value = res.data
}

function openCreate() {
  form.value = createEmptyForm()
  dialogVisible.value = true
}

function openEdit(row) {
  router.push(`/service-points/${row.id}`)
}

async function handleSubmit() {
  const payload = {
    ...form.value,
    latitude: Number(form.value.latitude || 0),
    longitude: Number(form.value.longitude || 0)
  }
  if (payload.id) {
    await updateServicePoint(payload)
  } else {
    await createServicePoint(payload)
  }
  dialogVisible.value = false
  await loadPoints()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确认删除该服务点？', '删除服务点', { type: 'warning' })
  await deleteServicePoint(id)
  await loadPoints()
}

function createEmptyForm() {
  return {
    id: null,
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
