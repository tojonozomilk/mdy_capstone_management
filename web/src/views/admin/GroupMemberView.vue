<template>
    <div>
        <!-- Group Member搜索栏 -->
        <GroupMemberSearch :form="groupMemberSearchForm" :options="options" :onSearch="fetchGroupMemberData"
		:onReset="resetGroupMemberSearchForm" :onAdd="showAddGroupMember" />

        <!-- Group Member列表 -->
        <GroupMemberTable :loading="loading" :options="options" :pageData="groupMemberPageData" :onEdit="showEditGroupMember"
            :onFetchData="fetchGroupMemberData" :onDelete="handleDeleteGroupMember" />

        <!-- 新增/编辑Group Member的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <GroupMemberEdit :form="groupMemberForm" :options="options" :onSubmit="handleSaveGroupMember" />
        </el-dialog>
    </div>
</template>

<script>
import { getGroupMemberList } from '@/api/GroupMemberAPI';
import { getGroupMember } from '@/api/GroupMemberAPI';
import { saveGroupMember } from '@/api/GroupMemberAPI';
import { deleteGroupMember } from '@/api/GroupMemberAPI';
import { getAllUserId } from "@/api/UserAPI";
import { getAllGroupId } from "@/api/GroupAPI";
import GroupMemberEdit from '@/components/edit/GroupMemberEdit.vue';
import GroupMemberSearch from '@/components/search/GroupMemberSearch.vue';
import GroupMemberTable from '@/components/table/GroupMemberTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        GroupMemberEdit,
        GroupMemberSearch,
        GroupMemberTable
    },
    data() {
        return {
            open: false,
            loading: false,
            title: "",
            options: {
                allUserId: [],
                allGroupId: [],
            },
            groupMemberForm: {},
            groupMemberSearchForm: {},
            groupMemberPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchGroupMemberData();
        this.getAllOptions();
    },
    methods: {
        fetchGroupMemberData(page = 1, size = 10) {
            getGroupMemberList({ page, size, ...this.groupMemberSearchForm })
                .then(res => {
                    this.groupMemberPageData.page = res.page;
                    this.groupMemberPageData.size = res.size;
                    this.groupMemberPageData.total = res.total;
                    this.groupMemberPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetGroupMemberSearchForm() {
            this.groupMemberSearchForm = {}
        },
        showAddGroupMember() {
            this.groupMemberForm = {};
            this.open = true;
            this.title = "Add Group Member"
        },
        showEditGroupMember(id) {
            getGroupMember(id)
                .then(res => {
                    this.groupMemberForm = { ...res };
                    this.open = true;
                    this.title = "Edit Group Member"
                })
                .catch(errorHandler);
        },
        handleSaveGroupMember() {
            saveGroupMember(this.groupMemberForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("Group Member saved successfully")
                    this.fetchGroupMemberData();
                })
                .catch(errorHandler);
        },
        handleDeleteGroupMember(id) {
            deleteGroupMember(id)
                .then(() => {
                    this.$message.success("Group Member deleted successfully")
                    this.fetchGroupMemberData();
                })
                .catch(errorHandler);
        },
        getAllOptions() {
            getAllUserId()
                .then(res => {
                    this.options.allUserId = res
                })
                .catch(errorHandler);
            getAllGroupId()
                .then(res => {
                    this.options.allGroupId = res
                })
                .catch(errorHandler);
        }
    }
};
</script>