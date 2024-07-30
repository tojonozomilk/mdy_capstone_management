<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span :contenteditable="getEditable()" class="editable" @blur="onSave" v-html="name" @keydown.enter.prevent
                @input="(e) => onUpdate(e, 'name')" />
            <el-button slot="reference" style="float: right; padding: 3px" type="danger" @click="onCancel"
                icon="el-icon-close" />
            <el-popconfirm v-if="id != 0 && getEditable()" confirm-button-text='yes' cancel-button-text='no'
                title="Are you sure?" @confirm="onDelete">
                <el-button slot="reference" style="float: right; padding: 3px;margin-right:10px" type="danger"
                    icon="el-icon-delete" />
            </el-popconfirm>
        </div>
        <el-row class="project-detail">
            <el-col :span="17">
                <div :contenteditable="getEditable()" class="editable" @blur="onSave" v-html="description"
                    @input="(e) => onUpdate(e, 'description')" />
            </el-col>
            <el-col :span="1"><br /></el-col>
            <el-col :span="6">
                <el-descriptions title="Detail" column="2">
                    <template slot="extra" v-if="id != 0">
                        <el-popover placement="bottom" trigger="click" class="invite-selector" v-if="getEditable()">
                            <el-select filterable placeholder="Please select manager" @change="onInviteManager">
                                <el-option v-for="item in managerList" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button slot="reference" type="primary" size="mini" icon="el-icon-plus" plain />
                        </el-popover>
                        <el-popover placement="bottom" trigger="click" class="invite-selector">
                            <el-select filterable placeholder="Please select group" @change="onInviteGroup">
                                <el-option v-for="item in groupList" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                            <el-button slot="reference" type="warning" size="mini" icon="el-icon-plus" plain />
                        </el-popover>
                    </template>
                    <el-descriptions-item label="Creator">{{ data.managerList.filter(item => item.value ===
                        data.creatorId)?.[0]?.label }}</el-descriptions-item>
                    <el-descriptions-item label="Category">
                        <el-select v-if="getEditable()" v-model="data.category" placeholder="Please select" size="mini"
                            @change="onChangeCategory">
                            <el-option label="Research" value="Research" />
                            <el-option label="Application" value="Application" />
                        </el-select>
                        <span v-else>{{ data.category }}</span>
                    </el-descriptions-item>
                    <el-descriptions-item label="Create Date">{{ data.createDate }}</el-descriptions-item>
                    <el-descriptions-item label="Amend Date">{{ data.amendDate }}</el-descriptions-item>
                </el-descriptions>
                <el-descriptions>
                    <el-descriptions-item label="Managers">
                        <div>
                            <el-tag v-for="manager in data.managerList" :key="manager" size="small"
                                :closable="manager.value != data.creatorId && getEditable()"
                                @close="() => onDeleteManager(manager.value)" class="items-icon">
                                <UserChat :userId="manager.value" :username="manager.label" />
                            </el-tag>
                        </div>
                    </el-descriptions-item>
                </el-descriptions>
                <el-descriptions>
                    <el-descriptions-item label="Groups">
                        <div>
                            <el-tag size="small" type="warning" :closable="getEditable()"
                                v-for="group in data.groupList" :key="group" @close="() => onDeleteGroup(group.value)"
                                class="items-icon">
                                <UserChat :userId="group.value" :username="group.label" />
                            </el-tag>
                        </div>
                    </el-descriptions-item>
                </el-descriptions>
            </el-col>
        </el-row>
    </el-card>
</template>

<script>
import { getProjectDetail, getAvailableGroup, addProjectManager, addProjectGroup, removeProjectManager, removeProjectGroup } from '@/api/FrontAPI';
import { errorHandler } from '@/utils/RequestUtils';
import { getAllUserId } from '@/api/UserAPI';
import { saveProject, deleteProject } from '@/api/ProjectAPI';
import UserChat from '@/components/message/UserChat.vue'
export default {
    name: "ProjectDetail",
    components: { UserChat },
    data() {
        return {
            id: 0,
            name: 'title',
            description: 'description',
            role: sessionStorage.getItem('role'),
            groupList: [],
            managerList: [],
            data: {
                id: null,
                name: 'title',
                description: 'description',
                creatorId: '',
                category: '',
                createDate: '',
                amendDate: '',
                groupList: [],
                managerList: [],
            }
        };
    },
    created() {
        this.id = this.$route.params.id
        console.log(this.id)
        this.onInit();
    },
    methods: {
        onInit() {
            if (this.id == 0) {
                getAllUserId()
                    .then(res => {
                        const managerIdList = this.data.managerList.map(item => item.value)
                        this.managerList = res.filter(item => !managerIdList.includes(item.value))
                    })
                    .catch(errorHandler);
                getAvailableGroup()
                    .then(res => {
                        this.groupList = res
                    })
                    .catch(errorHandler);
                return
            }
            getProjectDetail(this.id)
                .then((res) => {
                    this.data = res;
                    this.name = res.name;
                    this.description = res.description;

                    getAllUserId()
                        .then(res => {
                            const managerIdList = this.data.managerList.map(item => item.value)
                            this.managerList = res.filter(item => !managerIdList.includes(item.value))
                        })
                        .catch(errorHandler);
                    getAvailableGroup()
                        .then(res => {
                            this.groupList = res
                        })
                        .catch(errorHandler);
                })
                .catch(errorHandler)
        },
        onUpdate(e, field) {
            this.data[field] = event.target.innerHTML;
        },
        onSave() {
            saveProject(this.data)
                .then((res) => {
                    if (this.id == 0) {
                        this.$router.push('/project/' + res.id)
                        this.id = res.id
                    }
                    this.$message.success("Change saved successfully")
                    this.onInit()
                })
                .catch(errorHandler)
        },
        onChangeCategory(category) {
            this.data.category = category
            this.onSave()
        },
        onDelete() {
            deleteProject(this.data.id)
                .then(() => {
                    this.$message.success("Change deleted successfully")
                    this.$router.push('/projects')
                })
                .catch(errorHandler)
        },
        onInviteManager(managerId) {
            addProjectManager(this.data.id, managerId)
                .then(() => {
                    this.$message.success("Manager added successfully")
                    this.onInit()
                })
                .catch(errorHandler)
        },
        onInviteGroup(groupId) {
            addProjectGroup(this.data.id, groupId)
                .then(() => {
                    this.$message.success("Group added successfully")
                    this.onInit()
                })
                .catch(errorHandler)
        },
        onDeleteManager(managerId) {
            removeProjectManager(this.data.id, managerId)
                .then(() => {
                    this.$message.success("Manager removed successfully")
                    this.onInit()
                })
                .catch(errorHandler)

        },
        onDeleteGroup(groupId) {
            removeProjectGroup(this.data.id, groupId)
                .then(() => {
                    this.$message.success("Group removed successfully")
                    this.onInit()
                })
                .catch(errorHandler)
        },
        getEditable() {
            return this.id == 0 || this.data.managerList.find(item => item.value == sessionStorage.getItem('userId'))
        },
        onCancel() {
            this.$router.push('/projects')
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

.project-detail {
    margin-left: 20px;
    margin-right: 20px;
}

.items-icon {
    margin: 5px;
}

.invite-selector {
    margin-right: 5px;
}
</style>