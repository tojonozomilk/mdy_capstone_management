<template>
    <el-card class="lsit-card">
        <div slot="header">
            <span>Profile</span>
        </div>
        <UserEdit :form="userForm" :onSubmit="handleSaveUser" />
    </el-card>
</template>

<script>
import { saveUser, getUser } from '@/api/UserAPI';
import UserEdit from '@/components/edit/UserEdit.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        UserEdit,
    },
    data() {
        return {
            id: null,
            userForm: {},
        };
    },
    created() {
        this.id = sessionStorage.getItem("userId")
        this.fetchUserData()
    },
    methods: {
        fetchUserData() {
            getUser(this.id)
                .then((res) => {
                    this.userForm = res
                })
                .catch(errorHandler);
        },
        handleSaveUser() {
            saveUser(this.userForm)
                .then(() => {
                    this.$message.success("Profile saved successfully")
                    this.$router.push('/')
                })
                .catch(errorHandler);
        },
    }
};
</script>