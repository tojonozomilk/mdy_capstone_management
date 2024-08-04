<template>
  <el-popover placement="top-start" width="250" trigger="hover">
    <el-card class="box-card" shadow="never">
      <div slot="header" class="clearfix">
        <el-avatar :src="`http://192.168.1.100:8080${user.avatar}`" :size="64" shape="square" />
        <el-button type="danger" style="float: right; padding: 5px" @click="handelLogout">Logout</el-button>
      </div>
      <el-descriptions :column="1">
        <el-descriptions-item label="Email">{{ user.email }}</el-descriptions-item>
        <el-descriptions-item label="Role">{{ user.role }}</el-descriptions-item>
        <el-descriptions-item label="Affiliation">{{ user.affiliation }}</el-descriptions-item>
        <el-descriptions-item label="preference">{{ user.preference }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-link slot="reference" class="usernaame-hover">
      <span> {{ user.username }}</span>
    </el-link>
  </el-popover>
</template>

<script>
import { logout } from "@/api/SystemAPI";
import { errorHandler } from "@/utils/RequestUtils";
export default {
  props: {
    user: Object,
  },
  methods: {
    handelLogout() {
      logout()
        .then(() => {
          this.$message.success("Logout success");
          sessionStorage.clear();
          this.$router.push("/login");
        })
        .catch(errorHandler);
    },
  },
};
</script>

<style scoped>
.usernaame-hover {
  color: white !important;
  font-size: 16px;
  margin-top: 5px;
  margin-right: 50px;
}

.box-card {
  border: none;
}
</style>
