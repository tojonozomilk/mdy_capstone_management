<template>
    <el-row class="system-head">
        <h1 class="system-title">Mideyo Capstone</h1>
        <div class="system-extra">
            <UserInfo :user="user"/>
            <NoticeButton style="float: right;" />
        </div>
    </el-row>
</template>

<script>
import NoticeButton from '@/components/message/NoticeButton.vue';
import UserInfo from '@/components/system/UserInfo.vue';
import { getCurrentUser } from '@/api/SystemAPI';
import { errorHandler } from '@/utils/RequestUtils';
export default {
    name: 'SystemHeader',
    components: {
        NoticeButton, UserInfo
    },
    data() {
        return {
            user: {}
        }
    },
    created() {
        getCurrentUser()
            .then(res => {
                this.user = res
                sessionStorage.setItem("userId", res.id)
                sessionStorage.setItem("username", res.username)
                sessionStorage.setItem("avatar", res.avatar)
                sessionStorage.setItem("role", res.role)
                sessionStorage.setItem("email", res.email)
            })
            .catch(errorHandler);
    },
};
</script>

<style scoped>
.system-title {
    margin-right: auto;
    color: white;
    font-weight: bolder;
    font-size: 36px;
}

.system-head {
    width: 100vw;
    display: flex;
    justify-content: space-between;
}

.system-extra {
    margin-top: 30px
}
</style>