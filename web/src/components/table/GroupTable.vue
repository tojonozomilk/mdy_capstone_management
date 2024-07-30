<template>
    <div>
        <!-- Group列表 -->
        <el-table :data="groupPageData?.content || dataList" :row-key="row => row.id" :loading="loading"
            :height="pageData ? '70vh' : '28vh'">
            <el-table-column label="Group Name" prop="name" />
            <el-table-column label="Admin">
                <template slot-scope="scope">
                    <UserChat :userId="scope.row.adminId"
                        :username="options.allUserId.find((option) => option.value === scope.row.adminId)?.label" />
                </template>
            </el-table-column>
            <el-table-column label="Project">
                <template slot-scope="scope">
                    {{ options.allProjectId.find((option) => option.value === scope.row.projectId)?.label }}
                </template>
            </el-table-column>
            <el-table-column label="Creation Date" prop="createDate" />
            <el-table-column label="Amend Date" prop="amendDate" />
            <el-table-column label="Action">
                <template slot-scope="scope">
                    <el-button v-if="onView" type="warning" size="small" @click="onView(scope.row.id)"
                        icon="el-icon-view" />
                    <el-button v-if="onEdit" type="primary" size="small" @click="onEdit(scope.row.id)">Edit</el-button>
                    <el-button v-if="onDelete" type="danger" size="small"
                        @click="onDelete(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- Group列表分页器 -->
        <el-pagination v-if="pageData" style="margin-top: 16px; text-align: right;"
            layout="sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30]" :current-page.sync="groupPageData.page"
            :page-size.sync="groupPageData.size" :total="groupPageData.total" @size-change="onFetchGroupData"
            @current-change="onFetchGroupData" @prev-click="onFetchGroupData" @next-click="onFetchGroupData" />
    </div>
</template>

<script>
import UserChat from '@/components/message/UserChat.vue'
export default {
    name: 'GroupTable',
    components: { UserChat },
    props: {
        loading: Boolean,
        options: Object,
        dataList: Array,
        pageData: Object,
        onView: Function,
        onEdit: Function,
        onDelete: Function,
        onFetchData: Function,
    },
    data() {
        return {
            groupPageData: this.pageData,
        };
    },
    methods: {
        onFetchGroupData() {
            const page = this.groupPageData.page
            const size = this.groupPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>