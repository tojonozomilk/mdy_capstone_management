<template>
    <el-card class="box-card">
        <div slot="header">
            <span>Login</span>
            <el-button slot="reference" style="float: right; padding: 3px" type="primary" @click="handleRegister()">Register</el-button>
        </div>
        <el-row>
            <el-col :span="9">
                <br />
            </el-col>
            <el-col :span="6" class="login-form">
                <h1>Weclome to Mideyo Capstone</h1>
                <br />
                <el-form :model="userForm" :rules="formRules" ref="userForm" label-width="100px"
                    style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
                    <el-form-item label="Email" prop="email">
                        <el-input v-model="userForm.email" />
                    </el-form-item>
                    <el-form-item label="Password" prop="password">
                        <el-input show-password v-model="userForm.password" />
                    </el-form-item>
                    <el-form-item style="text-align: right;">
                        <el-button type="primary" @click="handleLogin()">Login</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="9">
                <br />
            </el-col>
        </el-row>
    </el-card>
</template>

<script>
import { errorHandler } from '@/utils/RequestUtils';
import { login } from '@/api/SystemAPI';
export default {
    data() {
        return {
            userForm: {
                account: '',
                password: '',
            },
            formRules: {
                email: [{ required: true, message: 'Please enter Email', trigger: 'blur' }],
                password: [{ required: true, message: 'Please enter Password', trigger: 'blur' }],
            },
        };
    },
    methods: {
        handleLogin() {
            this.$refs.userForm.validate(valid => {
                if (valid) {
                    login(this.userForm.email, this.userForm.password)
                        .then(() => {
                            this.$message.success("Login success")
                            this.$router.push('/')
                        })
                        .catch(errorHandler);
                }
            });
        },
        handleRegister() {
            this.$router.push('/register')
        }
    }
};
</script>

<style scoped>
.box-card {
    height: 78vh;
}

.login-form {
    margin-top: 10vh;
}
</style>