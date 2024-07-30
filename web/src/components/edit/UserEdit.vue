<template>
    <!-- 新增/编辑User表单 -->
    <div>
        <el-form :model="editForm" :rules="formRules" ref="userForm" label-width="100px"
            style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
            <el-form-item label="ID" prop="id" hidden>
                <el-input v-model="editForm.id" />
            </el-form-item>
            <el-form-item label="Name" prop="username">
                <el-input v-model="editForm.username" />
            </el-form-item>
            <el-form-item label="Password" prop="password">
                <el-input show-password v-model="editForm.password" />
            </el-form-item>
            <el-form-item label="Avatar" prop="avatar">
                <el-upload action="http://localhost:8080/api/upload" name="file" list-type="picture-card"
                    multiple="false" :file-list="fileList" accept="jpeg,jpg,png" :on-success="handleUploadAvatar"
                    :on-remove="handleRemoveAvatar">
                    <i class="el-icon-upload"></i>
                    <div slot="tip">Only support jpg/png, size less than 500kb</div>
                </el-upload>
            </el-form-item>
            <el-form-item label="Email" prop="email">
                <el-input v-model="editForm.email" />
            </el-form-item>
            <el-form-item label="Role" prop="role" :hidden="!requiredRole">
                <el-select v-model="editForm.role" placeholder="Please select">
                    <el-option label="Administrator" value="Administrator" />
                    <el-option label="ProjectClient" value="ProjectClient" />
                    <el-option label="Tutor" value="Tutor" />
                    <el-option label="Coordinator" value="Coordinator" />
                    <el-option label="Student" value="Student" />
                </el-select>
            </el-form-item>
            <el-form-item label="Affiliation" prop="affiliation">
                <el-input v-model="editForm.affiliation" />
            </el-form-item>
            <el-form-item label="Preference" prop="preference">
                <el-select v-model="editForm.preference" placeholder="Please select">
                    <el-option label="Research" value="Research" />
                    <el-option label="Application" value="Application" />
                </el-select>
            </el-form-item>
            <el-form-item label="Creation Date" prop="createDate" hidden>
                <el-input v-model="editForm.createDate" />
            </el-form-item>
            <el-form-item label="Amend Date" prop="amendDate" hidden>
                <el-input v-model="editForm.amendDate" />
            </el-form-item>
            <el-form-item style="text-align: right;">
                <el-button type="primary" @click="onSave()">Submit</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    name: 'UserEdit',
    props: {
        form: Object,
        onSubmit: Function,
    },
    data() {
        return {
            editForm: this.form,
            fileList: [],
            requiredRole: this.form.id && sessionStorage.getItem('role') === 'Administrator',
            formRules: {
                username: [{ required: true, message: 'Please enter Your Name', trigger: 'blur' }],
                password: [{ required: true, message: 'Please enter Password', trigger: 'blur' }],
                avatar: [{ required: true, message: 'Please upload Avatar', trigger: 'blur' }],
                email: [{ required: true, message: 'Please enter Email', trigger: 'blur' }],
                role: [{ required: this.requiredRole, message: 'Please enter Role', trigger: 'blur' }],
                affiliation: [{ required: true, message: 'Please enter Affiliation', trigger: 'blur' }],
                preference: [{ required: true, message: 'Please enter Preference', trigger: 'blur' }],
            },
        };
    },
    watch: {
        form(form) {
            if (form) {
                this.editForm = form;
                if (form.avatar && form.avatar != this.fileList?.[0]?.name) {
                    this.fileList = [{ name: form.avatar, url: `http://localhost:8080${form.avatar}` }];
                }
            }
        }
    },
    methods: {
        onSave() {
            this.$refs.userForm.validate(valid => {
                if (valid) {
                    this.onSubmit()
                }
            });
        },
        handleUploadAvatar(response) {
            const url = response.data
            this.editForm.avatar = url
            this.fileList = [{ name: url, url: `http://localhost:8080${url}` }]
        },
        handleRemoveAvatar() {
            this.editForm.avatar = null
        }
    }
}
</script>