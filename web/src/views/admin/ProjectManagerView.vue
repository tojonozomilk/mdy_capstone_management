<template>
    <div>
        <!-- Project Manager搜索栏 -->
        <ProjectManagerSearch :form="projectManagerSearchForm" :options="options" :onSearch="fetchProjectManagerData"
		:onReset="resetProjectManagerSearchForm" :onAdd="showAddProjectManager" />

        <!-- Project Manager列表 -->
        <ProjectManagerTable :loading="loading" :options="options" :pageData="projectManagerPageData" :onEdit="showEditProjectManager"
            :onFetchData="fetchProjectManagerData" :onDelete="handleDeleteProjectManager" />

        <!-- 新增/编辑Project Manager的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <ProjectManagerEdit :form="projectManagerForm" :options="options" :onSubmit="handleSaveProjectManager" />
        </el-dialog>
    </div>
</template>

<script>
import { getProjectManagerList } from '@/api/ProjectManagerAPI';
import { getProjectManager } from '@/api/ProjectManagerAPI';
import { saveProjectManager } from '@/api/ProjectManagerAPI';
import { deleteProjectManager } from '@/api/ProjectManagerAPI';
import { getAllProjectId } from "@/api/ProjectAPI";
import { getAllUserId } from "@/api/UserAPI";
import ProjectManagerEdit from '@/components/edit/ProjectManagerEdit.vue';
import ProjectManagerSearch from '@/components/search/ProjectManagerSearch.vue';
import ProjectManagerTable from '@/components/table/ProjectManagerTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        ProjectManagerEdit,
        ProjectManagerSearch,
        ProjectManagerTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
            options: {
                allProjectId: [],
                allUserId: [],
            },
            projectManagerForm: {},
            projectManagerSearchForm: {},
            projectManagerPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchProjectManagerData();
        this.getAllOptions();
    },
    methods: {
        fetchProjectManagerData(page = 1, size = 10) {
            getProjectManagerList({ page, size, ...this.projectManagerSearchForm })
                .then(res => {
                    this.projectManagerPageData.page = res.page;
                    this.projectManagerPageData.size = res.size;
                    this.projectManagerPageData.total = res.total;
                    this.projectManagerPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetProjectManagerSearchForm() {
            this.projectManagerSearchForm = {}
        },
        showAddProjectManager() {
            this.projectManagerForm = {};
            this.open = true;
            this.title = "Add Project Manager"
        },
        showEditProjectManager(id) {
            getProjectManager(id)
                .then(res => {
                    this.projectManagerForm = { ...res };
                    this.open = true;
                    this.title = "Edit Project Manager"
                })
                .catch(errorHandler);
        },
        handleSaveProjectManager() {
            saveProjectManager(this.projectManagerForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("Project Manager saved successfully")
                    this.fetchProjectManagerData();
                })
                .catch(errorHandler);
        },
        handleDeleteProjectManager(id) {
            deleteProjectManager(id)
                .then(() => {
                    this.$message.success("Project Manager deleted successfully")
                    this.fetchProjectManagerData();
                })
                .catch(errorHandler);
        },
        getAllOptions() {
            getAllProjectId()
                .then(res => {
                    this.options.allProjectId = res
                })
                .catch(errorHandler);
            getAllUserId()
                .then(res => {
                    this.options.allUserId = res
                })
                .catch(errorHandler);
        }
    }
};
</script>