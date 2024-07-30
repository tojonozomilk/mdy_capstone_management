<template>
  <div id="app-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <h1>Mideyo Capstone</h1>
        </div>
      </el-header>
      <el-main class="main-content">
          <router-view />
      </el-main>
      <el-footer>
        <p>&copy; 2024 MDY. All rights reserved.</p>
      </el-footer>
    </el-container>
  </div>
</template>

<style>
  #app-container {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .header-content {
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 30px;
  }

  .main-content {
    display: flex;
    justify-content: center;
    align-items: center ;
  }
</style>