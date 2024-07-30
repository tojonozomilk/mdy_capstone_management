<template>
    <div>
        <!-- Project搜索栏 -->
        <ProjectSearch :form="projectSearchForm" :options="options" :onSearch="fetchProjectData"
		:onReset="resetProjectSearchForm" :onAdd="showAddProject" />

        <!-- Project列表 -->
        <ProjectTable :loading="loading" :options="options" :pageData="projectPageData" :onEdit="showEditProject"
            :onFetchData="fetchProjectData" :onDelete="handleDeleteProject" />

        <!-- 新增/编辑Project的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <ProjectEdit :form="projectForm" :options="options" :onSubmit="handleSaveProject" />
        </el-dialog>
    </div>
</template>

<script>
import { getProjectList } from '@/api/ProjectAPI';
import { getProject } from '@/api/ProjectAPI';
import { saveProject } from '@/api/ProjectAPI';
import { deleteProject } from '@/api/ProjectAPI';
import { getAllUserId } from "@/api/UserAPI";
import ProjectEdit from '@/components/edit/ProjectEdit.vue';
import ProjectSearch from '@/components/search/ProjectSearch.vue';
import ProjectTable from '@/components/table/ProjectTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        ProjectEdit,
        ProjectSearch,
        ProjectTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
            options: {
                allUserId: [],
            },
            projectForm: {},
            projectSearchForm: {},
            projectPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchProjectData();
        this.getAllOptions();
    },
    methods: {
        fetchProjectData(page = 1, size = 10) {
            getProjectList({ page, size, ...this.projectSearchForm })
                .then(res => {
                    this.projectPageData.page = res.page;
                    this.projectPageData.size = res.size;
                    this.projectPageData.total = res.total;
                    this.projectPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetProjectSearchForm() {
            this.projectSearchForm = {}
        },
        showAddProject() {
            this.projectForm = {};
            this.open = true;
            this.title = "Add Project"
        },
        showEditProject(id) {
            getProject(id)
                .then(res => {
                    this.projectForm = { ...res };
                    this.open = true;
                    this.title = "Edit Project"
                })
                .catch(errorHandler);
        },
        handleSaveProject() {
            saveProject(this.projectForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("Project saved successfully")
                    this.fetchProjectData();
                })
                .catch(errorHandler);
        },
        handleDeleteProject(id) {
            deleteProject(id)
                .then(() => {
                    this.$message.success("Project deleted successfully")
                    this.fetchProjectData();
                })
                .catch(errorHandler);
        },
        getAllOptions() {
            getAllUserId()
                .then(res => {
                    this.options.allUserId = res
                })
                .catch(errorHandler);
        }
    }
};
</script>