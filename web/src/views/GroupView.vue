<template>
    <el-card class="lsit-card">
        <div slot="header" class="clearfix">
            <span>Groups</span>
            <el-button v-if="role !== 'Administrator'" slot="reference" style="float: right; padding: 3px"
                type="success" @click="onAdd" icon="el-icon-plus" />
        </div>
        <!-- Group列表 -->
        <GroupTable :loading="loading" :options="options" :pageData="groupPageData" :onView="showViewGroup"
            :onFetchData="fetchGroupData" />
    </el-card>
</template>

<script>
import { getGroupList } from '@/api/GroupAPI';
import { getGroup } from '@/api/GroupAPI';
import { getAllUserId } from "@/api/UserAPI";
import { getAllProjectId } from "@/api/ProjectAPI";
import GroupTable from '@/components/table/GroupTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        GroupTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
            role: sessionStorage.getItem('role'),
            options: {
                allUserId: [],
                allProjectId: [],
            },
            groupForm: {},
            groupSearchForm: {},
            groupPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchGroupData();
        this.getAllOptions();
    },
    methods: {
        fetchGroupData(page = 1, size = 10) {
            getGroupList({ page, size, ...this.groupSearchForm })
                .then(res => {
                    this.groupPageData.page = res.page;
                    this.groupPageData.size = res.size;
                    this.groupPageData.total = res.total;
                    this.groupPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetGroupSearchForm() {
            this.groupSearchForm = {}
        },
        showViewGroup(id) {
            this.$router.push('/group/' + id)
        },
        showEditGroup(id) {
            getGroup(id)
                .then(res => {
                    this.groupForm = { ...res };
                    this.open = true;
                    this.title = "Edit Group"
                })
                .catch(errorHandler);
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
        onAdd() {
            this.$router.push('/group/0')
        }
    }
};
</script>