<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>Group Detail</span>
            <el-button slot="reference" style="float: right; padding: 3px" type="danger" @click="onCancel"
                icon="el-icon-close" />
            <el-popconfirm confirm-button-text='yes' cancel-button-text='no' v-if="id != 0 && getEditable()"
                title="Are you sure?" @confirm="onDelete">
                <el-button slot="reference" style="float: right; padding: 3px; margin-right: 10px" type="danger"
                    icon="el-icon-delete" />
            </el-popconfirm>
        </div>
        <el-descriptions column="3">
            <el-descriptions-item label="Group Name">
                <span :contenteditable="getEditable()" class="editable" @blur="onSave" v-html="name"
                    @keydown.enter.prevent @input="(e) => onUpdate(e, 'name')" />
            </el-descriptions-item>
            <el-descriptions-item label="Admin">{{ userList.find(item => item.value === data.adminId)?.label
                }}</el-descriptions-item>
            <el-descriptions-item label="Project">
                <!--  <el-select v-if="getEditable()" v-model="data.projectId" placeholder="Please select" size="mini"
                    @change="onChangeProject" clearable>
                    <el-option v-for="project in projectList" :key="project.value" :label="project.label"
                        :value="project.value" />
                </el-select>
                <span v-else>{{ projectList.find(item => item.value === data.projectId)?.label }} </span> -->
                {{ projectList.find(item => item.value === data.projectId)?.label }}
            </el-descriptions-item>
            <el-descriptions-item label="Create Date">{{ data.createDate }}</el-descriptions-item>
            <el-descriptions-item label="Amend Date">{{ data.amendDate }}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions :colon="false" v-if="id != 0">
            <template slot="title">
                <span>Members</span>
                <el-popover placement="bottom" trigger="click" class="invite-selector">
                    <el-select filterable placeholder="Please select member" @change="onInviteMember">
                        <el-option v-for="item in selectorList" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                    <el-button slot="reference" type="primary" size="mini" icon="el-icon-plus" plain />
                </el-popover>
            </template>
            <el-descriptions-item>
                <div>
                    <el-tag v-for="member in memberList" :key="member.value" size="small"
                        :closable="member.value != data.adminId && getEditable()"
                        @close="() => onDeleteMember(member.value)" class="items-icon">
                        <UserChat :userId="member.value" :username="member.label" />
                    </el-tag>
                </div>
            </el-descriptions-item>
        </el-descriptions>
    </el-card>
</template>

<script>
import { addGroupMember, removeGroupMember, getGroupMember, removeProjectGroup } from '@/api/FrontAPI';
import { errorHandler } from '@/utils/RequestUtils';
import { getAllUserId } from '@/api/UserAPI';
import { getGroup, saveGroup, deleteGroup } from '@/api/GroupAPI';
import { getAllProjectId } from '@/api/ProjectAPI';
import UserChat from '@/components/message/UserChat.vue'
export default {
    name: "ProjectDetail",
    components: { UserChat },
    data() {
        return {
            id: 0,
            name: '',
            projectId: '',
            projectList: [],
            userList: [],
            memberList: [],
            selectorList: [],
            data: {
                id: null,
                name: '',
                adminId: '',
                projectId: '',
                createDate: '',
                amendDate: '',
            }
        };
    },
    created() {
        this.id = this.$route.params.id
        this.onInit();
    },
    methods: {
        onInit() {
            getAllProjectId()
                .then(res => {
                    this.projectList = res
                })
                .catch(errorHandler);
            getAllUserId()
                .then(res => {
                    this.userList = res
                })
                .catch(errorHandler);

            if (this.id != 0) {
                getGroup(this.id)
                    .then((res) => {
                        this.data = res;
                        this.name = res.name;
                        this.projectId = res.projectId;

                        getGroupMember(this.id)
                            .then(res => {
                                this.memberList = res
                                const memberIdList = this.memberList.map(item => item.value)
                                this.selectorList = this.userList.filter(item => !memberIdList.includes(item.value))
                            })
                            .catch(errorHandler);
                    })
                    .catch(errorHandler)
                return
            }
        },
        onUpdate(e, field) {
            this.data[field] = event.target.innerHTML;
        },
        onSave() {
            saveGroup(this.data)
                .then((res) => {
                    if (this.id == 0) {
                        this.$router.push('/group/' + res.id)
                        this.id = res.id
                    }
                    this.$message.success("Change saved successfully")
                    this.onInit()
                })
                .catch(errorHandler)
        },
        onChangeProject(projectId) {
            if (!projectId) {
                removeProjectGroup(this.projectId, this.id)
                    .then(() => {
                        this.$message.success("Project removed successfully")
                        this.onInit()
                    })
                    .catch(errorHandler)
                return
            }
            this.data.projectId = projectId
            this.onSave()
        },
        onDelete() {
            deleteGroup(this.data.id)
                .then(() => {
                    this.$message.success("Change deleted successfully")
                    this.$router.push('/groups')
                })
                .catch(errorHandler)
        },
        onInviteMember(userId) {
            addGroupMember(this.data.id, userId)
                .then((res) => {
                    if (res) {
                        this.$message.success("Member added successfully")
                    } else {
                        this.$message.warning("Wait for approve")
                    }
                    this.onInit()
                })
                .catch(errorHandler)
        },
        onDeleteMember(userId) {
            removeGroupMember(this.data.id, userId)
                .then(() => {
                    this.$message.success("Member removed successfully")
                    this.onInit()
                })
                .catch(errorHandler)

        },
        getEditable() {
            return this.id == 0 || this.memberList.find(item => item.value == sessionStorage.getItem('userId'))
        },
        onCancel() {
            this.$router.push('/groups')
        }
    },
};
</script>

<style>
.box-card {
    height: 98%;
}

.editable {
    background-color: transparent;
    border: none;
    padding: 0;
    outline: none;
}

.items-icon {
    margin: 5px;
}

.invite-selector {
    margin-left: 5px;
}
</style>