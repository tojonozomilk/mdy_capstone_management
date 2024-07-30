<template>
    <div>
        <!-- User列表 -->
        <el-table :data="userPageData.content" :row-key="row => row.id" :loading="loading"  :height="'70vh'">
            <el-table-column label="Name" prop="username" />
            <el-table-column label="Avatar">
                <template slot-scope="scope">
                    <el-avatar shape="square" :size="32" :src="`http://localhost:8080${scope.row.avatar}`"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column label="Email" prop="email" />
            <el-table-column label="Role" prop="role" />
            <el-table-column label="Affiliation" prop="affiliation" />
            <el-table-column label="Preference" prop="preference" />
            <el-table-column label="Creation Date" prop="createDate" />
            <el-table-column label="Amend Date" prop="amendDate" />
            <el-table-column label="Action">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" @click="onEdit(scope.row.id)">Edit</el-button>
                    <el-button type="danger" size="small" @click="onDelete(scope.row.id)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- User列表分页器 -->
        <el-pagination style="margin-top: 16px; text-align: right;" layout="sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 30]" :current-page.sync="userPageData.page" :page-size.sync="userPageData.size"
            :total="userPageData.total" @size-change="onFetchUserData" @current-change="onFetchUserData"
            @prev-click="onFetchUserData" @next-click="onFetchUserData" />
    </div>
</template>

<script>
export default {
    name: 'UserTable',
    props: {
        loading: Boolean,
        pageData: Object,
        onEdit: Function,
        onDelete: Function,
        onFetchData: Function,
    },
    data() {
        return {
            userPageData: this.pageData,
        };
    },
    methods: {
        onFetchUserData() {
            const page = this.userPageData.page
            const size = this.userPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>