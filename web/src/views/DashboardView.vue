<template>
    <div>
        <el-card class="dashboard-card">
            <div slot="header" class="clearfix">
                <span>My Projects</span>
            </div>
            <!-- Project列表 -->
            <ProjectTable :options="options" :dataList="data.projectList" :onView="showViewProject" />
        </el-card>
        <el-divider />
        <el-card class="my-card">
            <div slot="header" class="clearfix">
                <span>My Groups</span>
            </div>
            <!-- Group列表 -->
            <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
                <GroupTable :options="options" :dataList="data.groupList" :onView="showViewGroup" />
            </ul>
        </el-card>
    </div>
</template>

<script>
import { getSummary } from '@/api/FrontAPI';
import { getAllUserId } from "@/api/UserAPI";
import { getAllProjectId } from "@/api/ProjectAPI";
import ProjectTable from '@/components/table/ProjectTable.vue';
import GroupTable from '@/components/table/GroupTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        ProjectTable, GroupTable
    },
    data() {
        return {
            data: {
                projectList: [],
                groupList: [],
            },
            options: {
                allUserId: [],
                allProjectId: [],
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
        fetchProjectData() {
            getSummary()
                .then(res => {
                    this.data = res;
                })
                .catch(errorHandler);
        },
        showViewProject(id) {
            this.$router.push('/project/' + id)
        },
        showViewGroup(id) {
            this.$router.push('/group/' + id)
        },
        getAllOptions() {
            getAllUserId()
                .then(res => {
                    this.options.allUserId = res
                })
                .catch(errorHandler);
            getAllProjectId()
                .then(res => {
                    this.options.allProjectId = res
                })
                .catch(errorHandler);
        },
    }
};
</script>