<template>
    <!-- 新增/编辑Message表单 -->
    <div>
        <el-form :model="editForm" :rules="formRules" ref="messageForm" label-width="100px"
            style="margin-bottom: 30px; margin-right: 30px; text-align: left;">
            <el-form-item label="ID" prop="id" hidden>
				<el-input v-model="editForm.id" />
            </el-form-item>
            <el-form-item label="Type" prop="type" >
				<el-input v-model="editForm.type" />
            </el-form-item>
            <el-form-item label="Content" prop="content" >
				<el-input v-model="editForm.content" />
            </el-form-item>
            <el-form-item label="Sender" prop="senderId" >
				<el-select v-model="editForm.senderId" clearable filterable placeholder="Please select">
					<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
				</el-select>
            </el-form-item>
            <el-form-item label="Receiver" prop="receiverId" >
				<el-select v-model="editForm.receiverId" clearable filterable placeholder="Please select">
					<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
				</el-select>
            </el-form-item>
            <el-form-item label="Read" prop="isRead" >
				<el-switch v-model="editForm.isRead"/>
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
    name: 'MessageEdit',
    props: {
        form: Object,
        options: Object,
        onSubmit: Function,
    },
    data() {
        return {
            editForm: this.form,
            formRules: {
                type: [{ required: true, message: 'Please enter Type', trigger: 'blur' }],
                content: [{ required: true, message: 'Please enter Content', trigger: 'blur' }],
                senderId: [{ required: true, message: 'Please enter Sender', trigger: 'blur' }],
                receiverId: [{ required: true, message: 'Please enter Receiver', trigger: 'blur' }],
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
            this.$refs.messageForm.validate(valid => {
                if (valid) {
                    this.onSubmit()
                }
            });
        }
    }
}
</script>