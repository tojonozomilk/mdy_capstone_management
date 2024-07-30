<template>
    <div class="chat-container">
        <div class="chat-messages" ref="messageContainer" @scroll="handleScroll">
            <div v-for="message in messageList" :key="message.id" class="message"
                :class="{ 'message-sent': message.sentByMe }">
                <div class="avatar-container" v-if="!message.sentByMe">
                    <el-avatar :src="message.avatar" :size="40" class="message-avatar"></el-avatar>
                </div>
                <div class="message-content" :class="getTimeAlignment(message.sentByMe)">
                    <div class="message-text">{{ message.content }}</div>
                    <div class="message-time">{{ message.createDate }}</div>
                </div>
                <div class="avatar-container" v-if="message.sentByMe">
                    <el-avatar :src="message.avatar" :size="40" class="message-avatar"></el-avatar>
                </div>
            </div>
        </div>
        <div class="chat-input">
            <el-input v-model="newMessage" placeholder="Please enter text message"
                @keyup.enter.native="sendMessage"></el-input>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        messageList: Array,
        onSendMessage: Function,
        onLoadMessage: Function,
    },
    data() {
        return {
            newMessage: ''
        };
    },
    watch: {
        messageList() {
            this.scrollToBottom();
        }
    },
    methods: {
        scrollToBottom() {
            this.$nextTick(() => {
                const container = this.$refs.messageContainer;
                container.scrollTop = container.scrollHeight;
            });
        },
        handleScroll() {
            const container = this.$refs.messageContainer;
            if (container.scrollTop === 0) {
                const mixMessage = this.messageList?.[0]
                if (mixMessage) {
                    this.onLoadMessage(mixMessage.id)
                }
            }
        },
        sendMessage() {
            if (this.newMessage.trim() !== '') {
                this.onSendMessage(this.newMessage)
                this.newMessage = '';
                this.scrollToBottom();
            }
        },
        getTimeAlignment(sentByMe) {
            return sentByMe ? 'time-right' : 'time-left';
        }
    }
};
</script>

<style scoped>
.chat-container {
    max-width: 100%;
    margin: 0 auto;
    padding: 30px;
    height: 68vh !important;
    display: flex;
    flex-direction: column;
}

.chat-messages {
    flex: 1;
    overflow-y: auto;
}

.message {
    display: flex;
    margin-bottom: 20px;
}

.message-sent {
    justify-content: flex-end;
}

.avatar-container {
    margin-left: 10px;
    margin-right: 10px;
}

.message-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.message-content {
    display: flex;
    flex-direction: column;
}

.message-text {
    margin-bottom: 5px;
}

.message-time {
    font-size: 10px;
    color: #999;
    margin-top: 5px;
}

.time-left {
    align-items: start;
}

.time-right {
    align-items: end;
}

.chat-input {
    margin-top: 20px;
}
</style>