<template>
    <div>
        <!-- Approval列表 -->
        <el-table :data="approvalPageData.content" :row-key="row => row.id" :loading="loading" :height="'70vh'">
            <el-table-column label="Type" prop="type" />
            <el-table-column label="Status" prop="status" />
            <el-table-column label="Remark" prop="remark" />
            <el-table-column label="Creator">
                <template slot-scope="scope">
                    <UserChat :userId="scope.row.creatorId"
                        :username="options.allUserId.find((option) => option.value === scope.row.creatorId)?.label" />
                </template>
            </el-table-column>
            <el-table-column label="Creation Date" prop="createDate" />
            <el-table-column label="Amend Date" prop="amendDate" />
            <el-table-column label="Action">
                <template slot-scope="scope">
                    <div v-if="scope.row.status === 'Init' && scope.row.creatorId != userId">
                        <el-button v-if="onApprove" type="success" size="small" @click="onApprove(scope.row.id)"
                            icon="el-icon-check" />
                        <el-button v-if="onReject" type="danger" size="small" @click="onReject(scope.row.id)"
                            icon="el-icon-close" />
                    </div>
                    <el-button v-if="onEdit" type="primary" size="small" @click="onEdit(scope.row.id)">Edit</el-button>
                    <el-button v-if="onDelete" type="danger" size="small"
                        @click="onDelete(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- Approval列表分页器 -->
        <el-pagination style="margin-top: 16px; text-align: right;" layout="sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 30]" :current-page.sync="approvalPageData.page"
            :page-size.sync="approvalPageData.size" :total="approvalPageData.total" @size-change="onFetchApprovalData"
            @current-change="onFetchApprovalData" @prev-click="onFetchApprovalData" @next-click="onFetchApprovalData" />
    </div>
</template>

<script>
import UserChat from '@/components/message/UserChat.vue'
export default {
    name: 'ApprovalTable',
    components: { UserChat },
    props: {
        loading: Boolean,
        options: Object,
        pageData: Object,
        onApprove: Function,
        onReject: Function,
        onEdit: Function,
        onDelete: Function,
        onFetchData: Function,
    },
    data() {
        return {
            approvalPageData: this.pageData,
            userId: sessionStorage.getItem('userId'),
        };
    },
    methods: {
        onFetchApprovalData() {
            const page = this.approvalPageData.page
            const size = this.approvalPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>