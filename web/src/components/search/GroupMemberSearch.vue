<template>
    <!-- Group Member搜索栏 -->
    <el-form :model="searchForm" inline style="margin-bottom: 30px; text-align: left;">
        <el-form-item label="User">
			<el-select v-model="searchForm.userId" clearable filterable placeholder="Please select">
				<el-option v-for="item in options.allUserId" :key="item.value" :label="item.label" :value="item.value" />
			</el-select>
        </el-form-item>
        <el-form-item label="Group">
			<el-select v-model="searchForm.groupId" clearable filterable placeholder="Please select">
				<el-option v-for="item in options.allGroupId" :key="item.value" :label="item.label" :value="item.value" />
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
    name: 'GroupMemberSearch',
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