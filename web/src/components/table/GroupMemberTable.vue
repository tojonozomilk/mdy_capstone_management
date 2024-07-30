<template>
    <div>
        <!-- Group Member列表 -->
        <el-table :data="groupMemberPageData.content" :row-key="row => row.id" :loading="loading"  :height="'70vh'">
            <el-table-column label="User">
                <template slot-scope="scope">
					{{options.allUserId.find((option) => option.value === scope.row.userId)?.label}}
                </template>
            </el-table-column>
            <el-table-column label="Group">
                <template slot-scope="scope">
					{{options.allGroupId.find((option) => option.value === scope.row.groupId)?.label}}
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

        <!-- Group Member列表分页器 -->
        <el-pagination style="margin-top: 16px; text-align: right;" layout="sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 30]" :current-page.sync="groupMemberPageData.page" :page-size.sync="groupMemberPageData.size"
            :total="groupMemberPageData.total" @size-change="onFetchGroupMemberData" @current-change="onFetchGroupMemberData"
            @prev-click="onFetchGroupMemberData" @next-click="onFetchGroupMemberData" />
    </div>
</template>

<script>
export default {
    name: 'GroupMemberTable',
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
            groupMemberPageData: this.pageData,
        };
    },
    methods: {
        onFetchGroupMemberData() {
            const page = this.groupMemberPageData.page
            const size = this.groupMemberPageData.size
            this.onFetchData(page, size)
        }
    }
};
</script>