<template>
    <el-menu default-active="0">
        <router-link to="/">
            <el-menu-item index="0" class="menu-item">
                <span class="menu-text">Dashboard</span>
            </el-menu-item>
        </router-link>
        <router-link to="/projects">
            <el-menu-item index="1" class="menu-item">
                <span class="menu-text">Project</span>
            </el-menu-item>
        </router-link>
        <router-link to="/groups">
            <el-menu-item index="2" class="menu-item">
                <span class="menu-text">Group</span>
            </el-menu-item>
        </router-link>
        <router-link to="/approvals">
            <el-menu-item index="4" class="menu-item">
                <span class="menu-text">Approval</span>
            </el-menu-item>
        </router-link>
        <router-link to="/profile">
            <el-menu-item index="3" class="menu-item">
                <span class="menu-text">Profile</span>
            </el-menu-item>
        </router-link>
        <el-submenu v-if="role === 'Administrator'">
            <template slot="title"><span class="menu-text">Administrator</span></template>
            <router-link to="/user">
                <el-menu-item index="5" class="menu-item">
                    <span class="menu-text">User</span>
                </el-menu-item>
            </router-link>
            <router-link to="/project">
                <el-menu-item index="6" class="menu-item">
                    <span class="menu-text">Project</span>
                </el-menu-item>
            </router-link>
            <router-link to="/projectManager">
                <el-menu-item index="7" class="menu-item">
                    <span class="menu-text">Project Manager</span>
                </el-menu-item>
            </router-link>
            <router-link to="/group">
                <el-menu-item index="8" class="menu-item">
                    <span class="menu-text">Group</span>
                </el-menu-item>
            </router-link>
            <router-link to="/groupMember">
                <el-menu-item index="9" class="menu-item">
                    <span class="menu-text">Group Member</span>
                </el-menu-item>
            </router-link>
            <router-link to="/approval">
                <el-menu-item index="10" class="menu-item">
                    <span class="menu-text">Approval</span>
                </el-menu-item>
            </router-link>
        </el-submenu>
    </el-menu>
</template>

<script>
export default {
    name: 'SystemMenu',
    data() {
        return {
            role: sessionStorage.getItem('role')
        }
    },
    created() {
        setTimeout(() => this.role = sessionStorage.getItem('role'), 1000)
    },
};
</script>

<style scoped>
.menu-item {
    transition: transform 0.3s;
}

.menu-item:hover {
    transform: scale(1.1);
}

.menu-text {
    font-size: 16px;
    font-weight: bolder;
    display: inline-block;
    width: 100%;
    height: 100%;
    pointer-events: none;
}
</style>