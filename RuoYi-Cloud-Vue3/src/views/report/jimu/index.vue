<template>
  <i-frame :src="openUrl" id="jimuReportFrame" />
</template>

<script setup>
import { ref } from 'vue'
import { getToken } from '@/utils/auth'
import iFrame from '@/components/iFrame/index'

// Vite 环境变量必须以 VITE_ 开头
const openUrl = ref(
  //`${import.meta.env.VITE_APP_BASE_API}/ruoyi-report/jmreport/list?token=${getToken()}`
  `${import.meta.env.VITE_APP_BASE_API}/report/jmreport/list?token=${getToken()}`
  //`${import.meta.env.VITE_APP_BASE_API}/report/jmreport/list?token=${encodeURIComponent(getToken())}`
)
</script>