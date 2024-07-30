<template>
    <el-card class="lsit-card">
        <div slot="header" class="clearfix">
            <span>Projects</span>
            <el-button v-if="role === 'ProjectClient'" slot="reference" style="float: right; padding: 3px" type="success" @click="onAdd" icon="el-icon-plus"/>
        </div>
        <el-button v-if="role !== 'Student'" style="float: left; padding: 3px" type="warning" @click="goReport" href="http://localhost:8080/api/report">Generate Report</el-button>
        <!-- Project列表 -->
        <ProjectTable :loading="loading" :options="options" :pageData="projectPageData" :onView="showViewProject"
            :onFetchData="fetchProjectData" />
    </el-card>
</template>

<script>
// import { getReport } from '@/api/FrontAPI'
import { getProjectList } from '@/api/ProjectAPI';
import { getAllUserId } from "@/api/UserAPI";
import ProjectTable from '@/components/table/ProjectTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
// import axios from "axios";
export default {
    components: {
        ProjectTable
    },
    data() {
        return {
            loading: false,
            role: sessionStorage.getItem('role'),
            options: {
                allUserId: [],
            },
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
        showViewProject(id) {
            this.$router.push('/project/' + id)
        },
        getAllOptions() {
            getAllUserId()
                .then(res => {
                    this.options.allUserId = res
                })
                .catch(errorHandler);
        },
        onAdd() {
            this.$router.push('/project/0')
        },
        goReport() {
          window.open("http://localhost:8080/api/report", "_blank")
        }
    }
};
</script>