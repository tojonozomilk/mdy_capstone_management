<template>
    <el-container class="container">
      <el-header class="header">
        <SystemHeader />
      </el-header>
      <el-container>
        <el-aside class="aside">
          <SystemMenu />
        </el-aside>
        <el-main class="main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </template>
  
  <script>
  import SystemHeader from '@/components/system/SystemHeader.vue';
  import SystemMenu from '@/components/system/SystemMenu.vue';
  export default {
    components: { SystemMenu, SystemHeader },
  };
  </script>
  
  <style>
  body {
    margin: 0;
  }
  
  .el-header {
    height: 10vh !important;
    background-color: #324057;
    border-radius: 4px;
    display: flex;
    align-items: center;
  }
  
  .aside {
    padding: 20px;
    height: 90vh;
    border-radius: 4px;
  }
  
  .main {
    height: 90vh;
    border-radius: 4px;
  }
  </style>