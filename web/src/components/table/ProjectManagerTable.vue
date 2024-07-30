<template>
    <div>
        <!-- Project Manager列表 -->
        <el-table :data="projectManagerPageData.content" :row-key="row => row.id" :loading="loading"  :height="'70vh'">
            <el-table-column label="Project">
                <template slot-scope="scope">
					{{options.allProjectId.find((option) => option.value === scope.row.projectId)?.label}}
                </template>
            </el-table-column>
            <el-table-column label="Manager">
                <template slot-scope="scope">
					{{options.allUserId.find((option) => option.value === scope.row.managerId)?.label}}
                </template>
            </el-table-column>
            <el-table-column label="Creation Date" prop="createDate"/>
            <el-table-column label="Amend Date" prop="amendDate"/>
            <el-table-column label="Action">
                <template slot-scope="scope">
                    <!-- <el-button type="primary" size="small" @click="onEdit(scope.row.id)">Edit</el-button> -->
                    <el-button type="danger" size="small" @click="onDelete(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- Project Manager列表分页器 -->
        <el-pagination style="margin-top: 16px; text-align: right;" layout="sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 30]" :current-page.sync="projectManagerPageData.page" :page-size.sync="projectManagerPageData.size"
            :total="projectManagerPageData.total" @size-change="onFetchProjectManagerData" @current-change="onFetchProjectManagerData"
            @prev-click="onFetchProjectManagerData" @next-click="onFetchProjectManagerData" />
    </div>
</template>

<script>
export default {
    name: 'ProjectManagerTable',
    props: {
        loading: Boolean,
        options: Object,
        pageData: Object,
        onEdit: Function,
        onDelete: Function,
        onFetchData: Function,
    },
    data() {
        return {
            projectManagerPageData: this.pageData,
        };
    },
    methods: {
        onFetchProjectManagerData() {
            const page = this.projectManagerPageData.page
            const size = this.projectManagerPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>