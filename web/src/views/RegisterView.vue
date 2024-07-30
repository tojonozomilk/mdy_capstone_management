<template>
    <el-card class="box-card">
        <div slot="header">
            <span>Register</span>
            <el-button slot="reference" style="float: right; padding: 3px" type="default" @click="handleLogin()">Go to login</el-button>
        </div>
        <el-row>
        <el-col :span="9">
        <br/>
        </el-col>
        <el-col :span="6">
        <UserEdit class="register-form" :form="userForm" :onSubmit="handleSaveUser"/>
        </el-col>
        <el-col :span="9">
        <br/>
        </el-col>
        </el-row>
    </el-card>
</template>

<script>
import { register } from '@/api/SystemAPI';
import UserEdit from '@/components/edit/UserEdit.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        UserEdit,
    },
    data() {
        return {
            userForm: {},
        };
    },
    created() {
    },
    methods: {
        handleSaveUser() {
            register(this.userForm)
                .then(() => {
                    this.$message.success("Register success")
                    this.$router.push('/login')
                })
                .catch(errorHandler);
        },
        handleLogin(){
            this.$router.push('/login')
        }
    }
};
</script>

<style scoped>
.box-card{
    height: 78vh;
}
.register-form{
    margin-top: 5vh;
}
</style>