<template>
  <div class="dashboard-page">
    <el-alert
      title="当前首页展示的是 V0.1.0 数据基线，不是订单经营看板。"
      type="info"
      :closable="false"
      show-icon
    />

    <el-row :gutter="16" class="summary-grid">
      <el-col v-for="card in summaryCards" :key="card.key" :xs="12" :sm="8" :lg="6">
        <el-card shadow="hover" class="summary-card">
          <div class="summary-label">{{ card.label }}</div>
          <div class="summary-value">{{ formatValue(overview.summary?.[card.key]) }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" class="intro-card">
      <template #header>
        <div class="card-header">
          <span>版本范围</span>
          <el-tag size="small" type="success">{{ overview.version || 'V0.1.0' }}</el-tag>
        </div>
      </template>
      <p>
        当前版本已落地数据库基线、后台登录和预置数据。分类、商品、服务点、片区、司机等记录在这里可直接核对，
        完整 CRUD 与业务流转属于后续版本。
      </p>
    </el-card>

    <div class="section-grid">
      <el-card v-for="section in overview.sections || []" :key="section.key" shadow="never" class="section-card">
        <template #header>
          <div class="card-header">
            <span>{{ section.title }}</span>
            <el-tag size="small">{{ section.count }} 条</el-tag>
          </div>
        </template>

        <el-table :data="section.rows || []" border stripe size="small" empty-text="当前数据库未加载该类基线数据">
          <el-table-column
            v-for="column in section.columns || []"
            :key="column.key"
            :prop="column.key"
            :label="column.label"
            min-width="120"
            show-overflow-tooltip
          >
            <template #default="{ row }">
              {{ formatValue(row[column.key]) }}
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const summaryCards = [
  { key: 'admins', label: '管理员' },
  { key: 'roles', label: '角色' },
  { key: 'permissions', label: '权限' },
  { key: 'categories', label: '商品分类' },
  { key: 'goods', label: '测试商品' },
  { key: 'servicePoints', label: '服务点' },
  { key: 'deliveryRegions', label: '配送片区' },
  { key: 'drivers', label: '司机' }
]

const overview = ref({
  version: 'V0.1.0',
  summary: {},
  sections: []
})

onMounted(async () => {
  const res = await request.get('/dashboard/info')
  overview.value = res.data
})

const formatValue = (value) => {
  if (value === null || value === undefined || value === '') {
    return '-'
  }
  return Array.isArray(value) ? value.join(', ') : value
}
</script>

<style scoped>
.dashboard-page {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.summary-grid {
  margin: 0;
}

.summary-card {
  min-height: 112px;
}

.summary-label {
  font-size: 13px;
  color: #909399;
}

.summary-value {
  margin-top: 16px;
  font-size: 30px;
  font-weight: 700;
  color: #303133;
}

.intro-card p {
  margin: 0;
  line-height: 1.7;
  color: #606266;
}

.section-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 16px;
}

.section-card {
  min-width: 0;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  font-weight: 600;
}
</style>
