<template>
    <!-- User搜索栏 -->
    <el-form :model="searchForm" inline style="margin-bottom: 30px; text-align: left;">
        <el-form-item label="Username">
            <el-input v-model="searchForm.username" />
        </el-form-item>
        <el-form-item label="Email">
            <el-input v-model="searchForm.email" />
        </el-form-item>
        <el-form-item label="Affiliation">
            <el-input v-model="searchForm.affiliation" />
        </el-form-item>
        <el-form-item label="Preference">
            <el-select v-model="searchForm.preference" placeholder="Please select">
                <el-option label="Research" value="Research" />
                <el-option label="Application" value="Application" />
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
    name: 'UserSearch',
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