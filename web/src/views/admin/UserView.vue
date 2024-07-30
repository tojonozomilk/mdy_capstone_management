<template>
    <div>
        <!-- User搜索栏 -->
        <UserSearch :form="userSearchForm" :onSearch="fetchUserData"
		:onReset="resetUserSearchForm" :onAdd="showAddUser" />

        <!-- User列表 -->
        <UserTable :loading="loading" :pageData="userPageData" :onEdit="showEditUser"
            :onFetchData="fetchUserData" :onDelete="handleDeleteUser" />

        <!-- 新增/编辑User的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <UserEdit :form="userForm" :onSubmit="handleSaveUser" />
        </el-dialog>
    </div>
</template>

<script>
import { getUserList } from '@/api/UserAPI';
import { getUser } from '@/api/UserAPI';
import { saveUser } from '@/api/UserAPI';
import { deleteUser } from '@/api/UserAPI';
import UserEdit from '@/components/edit/UserEdit.vue';
import UserSearch from '@/components/search/UserSearch.vue';
import UserTable from '@/components/table/UserTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        UserEdit,
        UserSearch,
        UserTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
            userForm: {},
            userSearchForm: {},
            userPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchUserData();
    },
    methods: {
        fetchUserData(page = 1, size = 10) {
            getUserList({ page, size, ...this.userSearchForm })
                .then(res => {
                    this.userPageData.page = res.page;
                    this.userPageData.size = res.size;
                    this.userPageData.total = res.total;
                    this.userPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetUserSearchForm() {
            this.userSearchForm = {}
        },
        showAddUser() {
            this.userForm = {};
            this.open = true;
            this.title = "Add User"
        },
        showEditUser(id) {
            getUser(id)
                .then(res => {
                    this.userForm = { ...res };
                    this.open = true;
                    this.title = "Edit User"
                })
                .catch(errorHandler);
        },
        handleSaveUser() {
            saveUser(this.userForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("User saved successfully")
                    this.fetchUserData();
                })
                .catch(errorHandler);
        },
        handleDeleteUser(id) {
            deleteUser(id)
                .then(() => {
                    this.$message.success("User deleted successfully")
                    this.fetchUserData();
                })
                .catch(errorHandler);
        },
    }
};
</script>