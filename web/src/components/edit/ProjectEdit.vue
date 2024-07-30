<template>
    <!-- 新增/编辑Project表单 -->
    <div>
        <el-form :model="editForm" :rules="formRules" ref="projectForm" label-width="100px"
            style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
            <el-form-item label="ID" prop="id" hidden>
				<el-input v-model="editForm.id" />
            </el-form-item>
            <el-form-item label="Project Name" prop="name" >
				<el-input v-model="editForm.name" />
            </el-form-item>
            <el-form-item label="Description" prop="description" >
                <el-input v-model="editForm.description" type="textarea" :rows="5"/>
            </el-form-item>
            <el-form-item label="Category" prop="category" >
                <el-select v-model="editForm.category" placeholder="Please select">
                    <el-option label="Research" value="Research" />
                    <el-option label="Application" value="Application" />
                </el-select>
            </el-form-item>
            <el-form-item label="Creator" prop="creatorId" hidden>
				<el-select v-model="editForm.creatorId" clearable filterable placeholder="Please select">
					<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
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
    name: 'ProjectEdit',
    props: {
        form: Object,
        options: Object,
        onSubmit: Function,
    },
    data() {
        return {
            editForm: this.form,
            formRules: {
                name: [{ required: true, message: 'Please enter Project Name', trigger: 'blur' }],
                description: [{ required: true, message: 'Please enter Description', trigger: 'blur' }],
                category: [{ required: true, message: 'Please enter Category', trigger: 'blur' }],
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
            this.$refs.projectForm.validate(valid => {
                if (valid) {
                    this.onSubmit()
                }
            });
        }
    }
}
</script>