<template>
    <el-card class="lsit-card">
        <div slot="header">
            <span>Approvals</span>
        </div>

        <!-- Approval列表 -->
        <ApprovalTable :loading="loading" :pageData="approvalPageData" :onApprove="onApprove"
            :onFetchData="fetchApprovalData" :onReject="onReject" :options="options" />
    </el-card>
</template>

<script>
import { approve, reject, getMyApproval } from '@/api/FrontAPI';
import { getAllUserId } from "@/api/UserAPI";
import ApprovalTable from '@/components/table/ApprovalTable.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        ApprovalTable
    },
    data() {
        return {
            loading: false,
            options: {
                allUserId: [],
            },
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
            getMyApproval({ page, size, ...this.approvalSearchForm })
                .then(res => {
                    this.approvalPageData.page = res.page;
                    this.approvalPageData.size = res.size;
                    this.approvalPageData.total = res.total;
                    this.approvalPageData.content = res.content;
                })
                .catch(errorHandler);
        },
        onApprove(id) {
            approve(id)
                .then(() => {
                    this.$message.success("Approved successfully")
                    this.fetchApprovalData();
                })
                .catch(errorHandler);
        },
        onReject(id) {
            reject(id)
                .then(() => {
                    this.$message.success("Rejected successfully")
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
        },
    }
};
</script>