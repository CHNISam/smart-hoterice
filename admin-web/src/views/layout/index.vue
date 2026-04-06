<template>
  <el-container style="height: 100vh">
    <el-aside :width="isCollapse ? '64px' : '210px'" style="transition: width 0.3s; background: #304156;">
      <div class="logo" :class="{ collapsed: isCollapse }">
        <span v-if="!isCollapse">SmartRice</span>
        <span v-else>SR</span>
      </div>
      <el-menu
        :default-active="$route.path"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
        router
      >
        <el-menu-item index="/dashboard">
          <el-icon><DataBoard /></el-icon>
          <template #title>首页</template>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #e6e6e6; background: #fff;">
        <el-icon style="cursor: pointer; font-size: 20px;" @click="isCollapse = !isCollapse">
          <Fold v-if="!isCollapse" />
          <Expand v-else />
        </el-icon>
        <el-dropdown @command="handleCommand">
          <span style="cursor: pointer; color: #606266;">
            {{ adminName }}
            <el-icon><ArrowDown /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main style="background: #f0f2f5;">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { DataBoard, Fold, Expand, ArrowDown } from '@element-plus/icons-vue'
import { getInfo, logout } from '../../api/auth'

const router = useRouter()
const isCollapse = ref(false)
const adminName = ref('')

onMounted(async () => {
  try {
    const res = await getInfo()
    adminName.value = res.data.name
  } catch (e) {
    // handled by interceptor
  }
})

const handleCommand = async (command) => {
  if (command === 'logout') {
    await logout().catch(() => {})
    sessionStorage.removeItem('token')
    router.push('/login')
  }
}
</script>

<style scoped>
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  letter-spacing: 2px;
}
.logo.collapsed {
  font-size: 16px;
  letter-spacing: 0;
}
</style>
