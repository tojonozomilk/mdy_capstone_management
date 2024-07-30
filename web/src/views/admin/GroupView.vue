<template>
    <div>
        <!-- Group搜索栏 -->
        <GroupSearch :form="groupSearchForm" :options="options" :onSearch="fetchGroupData"
		:onReset="resetGroupSearchForm" :onAdd="showAddGroup" />

        <!-- Group列表 -->
        <GroupTable :loading="loading" :options="options" :pageData="groupPageData" :onEdit="showEditGroup"
            :onFetchData="fetchGroupData" :onDelete="handleDeleteGroup" />

        <!-- 新增/编辑Group的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <GroupEdit :form="groupForm" :options="options" :onSubmit="handleSaveGroup" />
        </el-dialog>
    </div>
</template>

<script>
import { getGroupList } from '@/api/GroupAPI';
import { getGroup } from '@/api/GroupAPI';
import { saveGroup } from '@/api/GroupAPI';
import { deleteGroup } from '@/api/GroupAPI';
import { getAllUserId } from "@/api/UserAPI";
import { getAllProjectId } from "@/api/ProjectAPI";
import GroupEdit from '@/components/edit/GroupEdit.vue';
import GroupSearch from '@/components/search/GroupSearch.vue';
import GroupTable from '@/components/table/GroupTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        GroupEdit,
        GroupSearch,
        GroupTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
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
        showAddGroup() {
            this.groupForm = {};
            this.open = true;
            this.title = "Add Group"
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
        handleSaveGroup() {
            saveGroup(this.groupForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("Group saved successfully")
                    this.fetchGroupData();
                })
                .catch(errorHandler);
        },
        handleDeleteGroup(id) {
            deleteGroup(id)
                .then(() => {
                    this.$message.success("Group deleted successfully")
                    this.fetchGroupData();
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
        }
    }
};
</script>