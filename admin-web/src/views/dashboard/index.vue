<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>今日订单</template>
          <div class="stat-number">{{ stats.todayOrderCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>今日营收</template>
          <div class="stat-number">¥{{ stats.todayRevenue }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>待处理订单</template>
          <div class="stat-number">{{ stats.pendingOrders }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>配送异常</template>
          <div class="stat-number">{{ stats.deliveryAbnormal }}</div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top: 20px;">
      <template #header>系统状态</template>
      <p>SmartRice Platform V0.1.0 — 项目骨架已就绪，更多功能开发中。</p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'

const stats = ref({
  todayOrderCount: 0,
  todayRevenue: 0,
  pendingOrders: 0,
  deliveryAbnormal: 0
})

onMounted(async () => {
  try {
    const res = await request.get('/dashboard/info')
    stats.value = res.data
  } catch (e) {
    // handled
  }
})
</script>

<style scoped>
.stat-number {
  font-size: 28px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
}
</style>
