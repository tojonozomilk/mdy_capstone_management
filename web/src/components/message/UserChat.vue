<template>
    <el-popover placement="top-start" width="200" trigger="click" @show="onShowUserInfo" :disabled="isMe">
        <el-card class="box-card" shadow="never">
            <div slot="header" class="contact-header">
                <el-avatar :src="user.url" :size="30" shape="square" />
                <el-link type="success" style="float: right; padding: 3px" @click="startChat"
                    icon="el-icon-chat-dot-round" />
            </div>
            <el-descriptions :column="1" size="mini">
                <el-descriptions-item label="Email">{{ user.email }}</el-descriptions-item>
                <el-descriptions-item label="Role">{{ user.role }}</el-descriptions-item>
                <el-descriptions-item label="Affiliation">{{ user.affiliation }}</el-descriptions-item>
                <el-descriptions-item label="preference">{{ user.preference }}</el-descriptions-item>
            </el-descriptions>
        </el-card>
        <el-link slot="reference">
            <span> {{ username }}</span>
        </el-link>
    </el-popover>
</template>

<script>
import { getUserInfo } from '@/api/FrontAPI';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    props: {
        userId: Object,
        username: Object,
    },
    data() {
        return {
            isMe: sessionStorage.getItem('userId') == this.userId,
            user: {

            }
        }
    },
    methods: {
        startChat() {
            if (this.userId == sessionStorage.getItem('userId')) {
                this.$message.warning("Cannot chat with youself")
                return
            }
            this.$router.push('/chat/' + this.userId)
        },
        onShowUserInfo() {
            console.log(this.isMe)
            getUserInfo(this.userId)
                .then((res) => {
                    this.user = res
                    this.user.url = `http://localhost:8080${this.user.avatar}`
                })
                .catch(errorHandler);
        }
    }
};
</script>

<style scoped>
.box-card {
    border: none;
}
.contact-header{
    margin-top: -10px;
    margin-bottom: -10px;
}
</style>