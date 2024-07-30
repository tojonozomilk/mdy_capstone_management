<template>
    <el-card class="lsit-card">
        <div slot="header" class="clearfix">
            <span>Chat with {{ `${contact.username}(${contact.email})` }}</span>
            <el-button slot="reference" style="float: right; padding: 3px" type="danger" icon="el-icon-close"
                @click="onClose" />
        </div>
        <MessageBox :messageList="messageList" :onSendMessage="onSendMessage" :onLoadMessage="getMessageList" />
    </el-card>
</template>

<script>
import { saveUser, getUser } from '@/api/UserAPI';
import { getUserInfo, getMessages, sendMessage } from '@/api/FrontAPI';
import MessageBox from '@/components/message/MessageBox.vue';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    components: {
        MessageBox,
    },
    data() {
        return {
            me: {},
            contact: {},
            messageList: [],
        };
    },
    created() {
        this.me.userId = sessionStorage.getItem("userId")
        this.me.username = sessionStorage.getItem("username")
        this.me.avatar = sessionStorage.getItem("avatar")

        const receiverId = this.$route.params.id
        getUserInfo(receiverId)
            .then((res) => {
                this.contact = res
                this.getMessageList()
            })
            .catch(errorHandler);
    },
    methods: {
        fetchUserData() {
            getUser(this.id)
                .then((res) => {
                    this.userForm = res
                })
                .catch(errorHandler);
        },
        handleSaveUser() {
            saveUser(this.userForm)
                .then(() => {
                    this.$message.success("Profile saved successfully")
                    this.fetchUserData();
                })
                .catch(errorHandler);
        },
        onClose() {
            this.$router.push('/')
        },
        getMessageList(minId) {
            getMessages(this.contact.userId, minId)
                .then((res) => {
                    const ids = this.messageList.map(message => message.id)

                    for (const message of res) {
                        const id = message.id
                        if (ids.includes(id)) {
                            continue;
                        }

                        const sentByMe = message.senderId == this.me.userId
                        message.avatar = sentByMe ? this.me.avatar : this.contact.avatar
                        message.avatar = `http://localhost:8080${message.avatar}`
                        message.sentByMe = sentByMe;

                        let insert = false;
                        for (let i = 0; i < this.messageList.length; i++) {
                            if (this.messageList[i].id > id) {
                                this.messageList.splice(i, 0, message);
                                insert = true;
                                break
                            }
                        }
                        if (!insert) {
                            this.messageList.push(message)
                        }
                    }
                    setTimeout(() => {
                        this.timer = setTimeout(() => {
                            this.getMessageList();
                        }, 500);
                    }, 500);
                })
                .catch(errorHandler);
        },
        onSendMessage(content) {
            const message = {
                "type": "Chat",
                "content": content,
                "senderId": this.me.userId,
                "receiverId": this.contact.userId,
                "isRead": false,
            }
            sendMessage(message)
                .catch(errorHandler);
        },
    },
    beforeDestroy() {
        clearTimeout(this.timer);
    },
};
</script>