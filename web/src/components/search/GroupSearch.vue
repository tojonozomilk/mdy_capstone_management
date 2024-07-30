<template>
    <!-- Group搜索栏 -->
    <el-form :model="searchForm" inline style="margin-bottom: 30px; text-align: left;">
        <el-form-item label="Group Name">
			<el-input v-model="searchForm.name" />
        </el-form-item>
        <el-form-item label="Admin">
			<el-select v-model="searchForm.adminId" clearable filterable placeholder="Please select">
				<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
			</el-select>
        </el-form-item>
        <el-form-item label="Project">
			<el-select v-model="searchForm.projectId" clearable filterable placeholder="Please select">
				<el-option v-for="item in options.allProjectId" :key="item.value" :label="item.label" :value="item.value" />
			</el-select>
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
    name: 'GroupSearch',
    props: {
        form: Object,
        options: Object,
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