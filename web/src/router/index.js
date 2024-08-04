import Vue from "vue";
import VueRouter from "vue-router";
import UserAdminView from "@/views/admin/UserView.vue";
import ProjectAdminView from "@/views/admin/ProjectView.vue";
import ProjectManagerAdminView from "@/views/admin/ProjectManagerView.vue";
import GroupAdminView from "@/views/admin/GroupView.vue";
import GroupMemberAdminView from "@/views/admin/GroupMemberView.vue";
import ApprovalAdminView from "@/views/admin/ApprovalView.vue";
import DashboardView from "@/views/DashboardView.vue";
import ProjectView from "@/views/ProjectView.vue";
import GroupView from "@/views/GroupView.vue";
import ProfileView from "@/views/ProfileView.vue";
import ApprovalView from "@/views/ApprovalView.vue";
import ProjectDetailView from "@/views/ProjectDetailView.vue";
import GroupDetailView from "@/views/GroupDetailView.vue";
import ChatView from "@/views/ChatView.vue";
import ContainerView from "@/views/ContainerView.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import SignContainerView from "@/views/SignContainerView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: ContainerView,
    children: [
      {
        path: "",
        name: "default",
        component: DashboardView,
      },
      {
        path: "projects",
        name: "projects",
        component: ProjectView,
      },
      {
        path: "project/:id",
        name: "projectDetail",
        component: ProjectDetailView,
      },
      {
        path: "projects",
        name: "projects",
        component: ProjectView,
      },
      {
        path: "groups",
        name: "groups",
        component: GroupView,
      },
      {
        path: "group/:id",
        name: "groupDetail",
        component: GroupDetailView,
      },
      {
        path: "chat/:id",
        name: "chat",
        component: ChatView,
      },
      {
        path: "profile",
        name: "me",
        component: ProfileView,
      },
      {
        path: "approvals",
        name: "approvals",
        component: ApprovalView,
      },
      {
        path: "user",
        name: "user",
        component: UserAdminView,
      },
      {
        path: "project",
        name: "project",
        component: ProjectAdminView,
      },
      {
        path: "projectManager",
        name: "projectManager",
        component: ProjectManagerAdminView,
      },
      {
        path: "group",
        name: "group",
        component: GroupAdminView,
      },
      {
        path: "groupMember",
        name: "groupMember",
        component: GroupMemberAdminView,
      },
      {
        path: "approval",
        name: "approval",
        component: ApprovalAdminView,
      },
    ],
  },
  {
    path: "/",
    component: SignContainerView,
    children: [{
      path: "login",
      name: "login",
      component: LoginView,
    },
    {
      path: "register",
      name: "register",
      component: RegisterView,
    },
    ]
  },

];

const router = new VueRouter({
  routes,
});

export default router;
