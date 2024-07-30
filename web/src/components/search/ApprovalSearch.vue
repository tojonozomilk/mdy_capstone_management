<template>
    <!-- Approval搜索栏 -->
    <el-form :model="searchForm" inline style="margin-bottom: 30px; text-align: left;">
        <el-form-item label="Type">
			<el-input v-model="searchForm.type" />
        </el-form-item>
        <el-form-item label="Status">
			<el-input v-model="searchForm.status" />
        </el-form-item>
        <el-form-item label="Related">
			<el-input v-model="searchForm.relateId" />
        </el-form-item>
        <el-form-item label="Remark">
			<el-input v-model="searchForm.remark" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSearch()">Search</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="default" @click="onReset()">Reset</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="success" @click="onAdd()">Add</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
export default {
    name: 'ApprovalSearch',
    props: {
        form: Object,
        onAdd: Function,
        onReset: Function,
        onSearch: Function,
    },
    data() {
        return {
            searchForm: this.form,
        };
    },
    watch: {
        form(form) {
            if (form) {
                this.searchForm = form;
            }
        }
    },
}
</script>