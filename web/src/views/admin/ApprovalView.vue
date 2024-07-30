<template>
    <div>
        <!-- Approval搜索栏 -->
        <ApprovalSearch :form="approvalSearchForm" :onSearch="fetchApprovalData" :onReset="resetApprovalSearchForm"
            :onAdd="showAddApproval" />

        <!-- Approval列表 -->
        <ApprovalTable :loading="loading" :pageData="approvalPageData" :onEdit="showEditApproval"
            :onFetchData="fetchApprovalData" :onDelete="handleDeleteApproval" :options="options" />

        <!-- 新增/编辑Approval的弹出框 -->
        <el-dialog :title="title" width="30%" :visible.sync="open">
            <ApprovalEdit :form="approvalForm" :onSubmit="handleSaveApproval" :options="options" />
        </el-dialog>
    </div>
</template>

<script>
import { getApprovalList } from '@/api/ApprovalAPI';
import { getAllUserId } from "@/api/UserAPI";
import { getApproval } from '@/api/ApprovalAPI';
import { saveApproval } from '@/api/ApprovalAPI';
import { deleteApproval } from '@/api/ApprovalAPI';
import ApprovalEdit from '@/components/edit/ApprovalEdit.vue';
import ApprovalSearch from '@/components/search/ApprovalSearch.vue';
import ApprovalTable from '@/components/table/ApprovalTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
import { getAllProjectId } from "@/api/ProjectAPI";
import { getAllGroupId } from "@/api/GroupAPI";

export default {
    components: {
        ApprovalEdit,
        ApprovalSearch,
        ApprovalTable
    },
    data() {
        return {
            open: false,
            loading: false,
            options: {
                allUserId: [],
                allProjectId: [],
                allGroupId: [],
            },
            title: "",
            approvalForm: {},
            approvalSearchForm: {},
            approvalPageData: {
                page: 0,
                size: 0,
                total: 0,
                content: [],
            },
        };
    },
    created() {
        this.fetchApprovalData();
        this.getAllOptions();
    },
    methods: {
        fetchApprovalData(page = 1, size = 10) {
            getApprovalList({ page, size, ...this.approvalSearchForm })
                .then(res => {
                    this.approvalPageData.page = res.page;
                    this.approvalPageData.size = res.size;
                    this.approvalPageData.total = res.total;
                    this.approvalPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        resetApprovalSearchForm() {
            this.approvalSearchForm = {}
        },
        showAddApproval() {
            this.approvalForm = {};
            this.open = true;
            this.title = "Add Approval"
        },
        showEditApproval(id) {
            getApproval(id)
                .then(res => {
                    this.approvalForm = { ...res };
                    this.open = true;
                    this.title = "Edit Approval"
                })
                .catch(errorHandler);
        },
        handleSaveApproval() {
            saveApproval(this.approvalForm)
                .then(() => {
                    this.open = false;
                    this.$message.success("Approval saved successfully")
                    this.fetchApprovalData();
                })
                .catch(errorHandler);
        },
        handleDeleteApproval(id) {
            deleteApproval(id)
                .then(() => {
                    this.$message.success("Approval deleted successfully")
                    this.fetchApprovalData();
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
            getAllGroupId()
                .then(res => {
                    this.options.allGroupId = res
                })
                .catch(errorHandler);
        },
    }
};
</script>