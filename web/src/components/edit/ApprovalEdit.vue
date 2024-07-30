<template>
    <!-- 新增/编辑Approval表单 -->
    <div>
        <el-form :model="editForm" :rules="formRules" ref="approvalForm" label-width="100px"
            style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
            <el-form-item label="ID" prop="id" hidden>
                <el-input v-model="editForm.id" />
            </el-form-item>
            <el-form-item label="Type" prop="type">
                <el-select v-model="editForm.type" placeholder="Please select">
                    <el-option label="Project" value="Project" />
                    <el-option label="Group" value="Group" />
                </el-select>
            </el-form-item>
            <el-form-item label="Status" prop="status">
                <el-select v-model="editForm.status" placeholder="Please select">
                    <el-option label="Init" value="Init" />
                    <el-option label="Approved" value="Approved" />
                    <el-option label="Reject" value="Reject" />
                </el-select>
            </el-form-item>
            <el-form-item label="Project" prop="projectId">
                <el-select v-model="editForm.projectId" clearable filterable placeholder="Please select">
                    <el-option v-for="item in options.allProjectId" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="Group" prop="groupId">
                <el-select v-model="editForm.groupId" clearable filterable placeholder="Please select">
                    <el-option v-for="item in options.allGroupId" :key="item.value" :label="item.label"
                        :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="Remark" prop="remark">
                <el-input v-model="editForm.remark" />
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
    name: 'ApprovalEdit',
    props: {
        form: Object,
        options: Object,
        onSubmit: Function,
    },
    data() {
        return {
            editForm: this.form,
            formRules: {
                type: [{ required: true, message: 'Please select Type', trigger: 'blur' }],
                status: [{ required: true, message: 'Please select Status', trigger: 'blur' }],
                groupId: [{ required: true, message: 'Please select Group', trigger: 'blur' }],
                remark: [{ required: true, message: 'Please enter Remark', trigger: 'blur' }],
            },
        };
    },
    watch: {
        form(form) {
            if (form) {
                this.editForm = form;
            }
        }
    },
    methods: {
        onSave() {
            this.$refs.approvalForm.validate(valid => {
                if (valid) {
                    this.onSubmit()
                }
            });
        }
    }
}
</script>