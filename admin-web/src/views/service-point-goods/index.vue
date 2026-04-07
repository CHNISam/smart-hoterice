<template>
  <div class="page">
    <div class="page-header">
      <div>
        <h2>服务点商品</h2>
        <p>绑定服务点与商品，支持不同站点只展示自己的可售商品。</p>
      </div>
      <el-button type="primary" @click="openCreate">新增绑定</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="relations" border stripe>
        <el-table-column prop="servicePointName" label="服务点" min-width="180" />
        <el-table-column prop="goodsName" label="商品" min-width="180" />
        <el-table-column prop="retailPrice" label="默认零售价" min-width="110" />
        <el-table-column prop="price" label="站点价" min-width="100">
          <template #default="{ row }">{{ row.price ?? '-' }}</template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑绑定' : '新增绑定'" width="680px">
      <el-form label-width="110px">
        <el-form-item label="服务点">
          <el-select v-model="form.servicePointId" style="width: 100%">
            <el-option v-for="item in servicePoints" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品">
          <el-select v-model="form.goodsId" style="width: 100%">
            <el-option v-for="item in goodsList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="站点价">
          <el-input-number v-model="form.price" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="启用">
          <el-switch v-model="form.enabled" />
        </el-form-item>
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
import { listGoods } from '../../api/goods'
import { listServicePoints } from '../../api/servicePoint'
import { deleteServicePointGoods, listServicePointGoods, saveServicePointGoods } from '../../api/servicePointGoods'

const relations = ref([])
const servicePoints = ref([])
const goodsList = ref([])
const dialogVisible = ref(false)
const form = ref(createEmptyForm())

onMounted(async () => {
  await Promise.all([loadRelations(), loadOptions()])
})

async function loadRelations() {
  const res = await listServicePointGoods()
  relations.value = res.data
}

async function loadOptions() {
  const [pointsRes, goodsRes] = await Promise.all([listServicePoints(), listGoods()])
  servicePoints.value = pointsRes.data
  goodsList.value = goodsRes.data
}

function openCreate() {
  form.value = createEmptyForm()
  dialogVisible.value = true
}

function openEdit(row) {
  form.value = {
    id: row.id,
    servicePointId: row.servicePointId,
    goodsId: row.goodsId,
    price: row.price ?? row.retailPrice ?? 0,
    enabled: row.enabled
  }
  dialogVisible.value = true
}

async function handleSubmit() {
  await saveServicePointGoods({
    ...form.value,
    price: form.value.price === null || form.value.price === undefined ? null : Number(form.value.price)
  })
  dialogVisible.value = false
  await loadRelations()
}

async function handleDelete(id) {
  await ElMessageBox.confirm('确认删除该服务点商品绑定？', '删除绑定', { type: 'warning' })
  await deleteServicePointGoods(id)
  await loadRelations()
}

function createEmptyForm() {
  return {
    id: null,
    servicePointId: null,
    goodsId: null,
    price: null,
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
