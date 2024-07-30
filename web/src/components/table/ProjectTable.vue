<template>
    <div>
        <!-- Project列表 -->
        <el-table :data="projectPageData?.content || dataList" :row-key="row => row.id" :loading="loading"
            :height="pageData ? '70vh' : '28vh'">
            <el-table-column label="Project Name" prop="name" />
            <el-table-column label="Category" prop="category" />
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
                    <el-button v-if="onView" type="warning" size="small" @click="onView(scope.row.id)"
                        icon="el-icon-view" />
                    <el-button v-if="onEdit" type="primary" size="small" @click="onEdit(scope.row.id)">Edit</el-button>
                    <el-button v-if="onDelete" type="danger" size="small"
                        @click="onDelete(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- Project列表分页器 -->
        <el-pagination v-if="pageData" style="margin-top: 16px; text-align: right;"
            layout="sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30]"
            :current-page.sync="projectPageData.page" :page-size.sync="projectPageData.size"
            :total="projectPageData.total" @size-change="onFetchProjectData" @current-change="onFetchProjectData"
            @prev-click="onFetchProjectData" @next-click="onFetchProjectData" />
    </div>
</template>

<script>
import UserChat from '@/components/message/UserChat.vue'
export default {
    name: 'ProjectTable',
    props: {
        loading: Boolean,
        options: Object,
        pageData: Object,
        dataList: Array,
        onView: Function,
        onEdit: Function,
        onDelete: Function,
        onFetchData: Function,
    },
    components: { UserChat },
    data() {
        return {
            projectPageData: this.pageData,
        };
    },
    methods: {
        onFetchProjectData() {
            const page = this.projectPageData.page
            const size = this.projectPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>