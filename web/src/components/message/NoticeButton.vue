<template>
    <el-popover placement="top-start" width="300" trigger="hover" :disabled="data.length === 0">
        <div v-for="notice in data" :key="notice.id">
            <el-row>
                <el-col :span="20" style="padding-right:5px"><span>{{ notice.type === 'Chat' ? notice.sender : 'System'
                        }}: {{ notice.content
                        }}</span></el-col>
                <el-col :span="2" v-if="!notice.isRead"> <el-link @click="() => readMessage(notice)"><i
                            :class="notice.type === 'Chat' ? 'el-icon-chat-round' : 'el-icon-circle-check'" /></el-link>
                </el-col>
                <el-col :span="2" v-if="notice.type === 'Notice'">
                    <el-link @click="() => deleteNotice(notice)"><i class='el-icon-delete' /></el-link>
                </el-col>
            </el-row>
        </div>

        <el-badge :value="length" slot="reference" :hidden="length === 0">
            <el-button size="mini" icon="el-icon-bell" round />
        </el-badge>
    </el-popover>
</template>

<script>
import { getNotifications, readNotification } from '@/api/FrontAPI';
import { deleteMessage } from '@/api/MessageAPI';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    data() {
        return {
            data: [],
            length: 0,
        };
    },
    created() {
        this.onGetNotifications()
    },
    methods: {
        onGetNotifications() {
            getNotifications()
                .then(res => {
                    this.data = res;
                    this.length = this.data.filter(item => !item.isRead).length

                    setTimeout(() => {
                        this.timer = setTimeout(() => {
                            this.onGetNotifications();
                        }, 5000);
                    }, 5000);
                })
                .catch(errorHandler);
        },
        readMessage(notice) {
            readNotification(notice.id)
            if (notice.type === 'Chat') {
                this.$router.push('/chat/' + notice.senderId)
            } else {
                getNotifications()
                    .then(res => {
                        this.data = res;
                        this.length = this.data.filter(item => !item.isRead).length
                    })
                    .catch(errorHandler);
            }
        },
        deleteNotice(notice) {
            deleteMessage(notice.id)
                .then(() => {
                    this.$message.warning("Deleted successfully")
                    getNotifications()
                        .then(res => {
                            this.data = res;
                            this.length = this.data.filter(item => !item.isRead).length
                        })
                        .catch(errorHandler);
                })
                .catch(errorHandler);
        }
    },
    beforeDestroy() {
        clearTimeout(this.timer);
    },
};
</script>

<style>
.notice-span {
    margin: 2px;
}
</style>