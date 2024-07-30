<template>
    <!-- 新增/编辑Group Member表单 -->
    <div>
        <el-form :model="editForm" :rules="formRules" ref="groupMemberForm" label-width="100px"
            style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
            <el-form-item label="ID" prop="id" hidden>
				<el-input v-model="editForm.id" />
            </el-form-item>
            <el-form-item label="User" prop="userId" >
				<el-select v-model="editForm.userId" clearable filterable placeholder="Please select">
					<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
				</el-select>
            </el-form-item>
            <el-form-item label="Group" prop="groupId" >
				<el-select v-model="editForm.groupId" clearable filterable placeholder="Please select">
					<el-option v-for="item in options.allGroupId" :key="item.value" :label="item.label" :value="item.value" />
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
    name: 'GroupMemberEdit',
    props: {
        form: Object,
        options: Object,
        onSubmit: Function,
    },
    data() {
        return {
            editForm: this.form,
            formRules: {
                userId: [{ required: true, message: 'Please enter User', trigger: 'blur' }],
                groupId: [{ required: true, message: 'Please enter Group', trigger: 'blur' }],
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
            this.$refs.groupMemberForm.validate(valid => {
                if (valid) {
                    this.onSubmit()
                }
            });
        }
    }
}
</script>