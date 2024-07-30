package com.project.management.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.management.entity.Approval;
import com.project.management.entity.Group;
import com.project.management.entity.Message;
import com.project.management.entity.Project;
import com.project.management.pojo.*;
import com.project.management.service.*;
import com.project.management.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description Front-end API Controller
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/api")
public class FrontController {

    @Value("${upload.location}")
    private String uploadLocation;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${report.location}")
    private String reportLocation;

    @Value("${report.dir}")
    private String reportDir;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    /**
     * File Uploader
     *
     * @param file a MultipartFile
     * @return Path of File
     * @throws IOException Exception
     */
    @PostMapping("upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)) {
            return Result.failed("Empty file name");
        }

        // get suffix of originFileName and generate random fileName
        String suffix = filename.substring(filename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        // save file in static dir
        File dirPath = new File(uploadLocation + uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        Path targetLocation = new File(dirPath, fileName).toPath();
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Result<String> success = Result.success();
        success.setData(uploadDir + fileName);
        return success;
    }

    /**
     * Get Project Detail by id
     *
     * @param id Identity
     * @return Result<>
     */
    @GetMapping("projectDetail")
    public Result<ProjectDetail> getProjectDetail(@RequestParam Integer id) {
        ProjectDetail result = projectService.getProjectDetail(id);
        return Result.success(result);
    }

    /**
     * Update Project Detail
     *
     * @param detail data in body
     * @return Result<>
     */
    @PostMapping("projectDetail")
    public Result<Object> saveProjectDetail(@RequestBody ProjectDetail detail) {
        projectService.saveProjectDetail(detail);
        return Result.success();
    }

    /**
     * Add Project Manager
     *
     * @param projectId Project ID
     * @param managerId User ID
     * @return Result<>
     */
    @GetMapping("addProjectManager")
    public Result<Object> addProjectManager(@RequestParam Integer projectId, @RequestParam Integer managerId) {
        boolean result = projectManagerService.addProjectManager(projectId, managerId);
        return Result.success(result);
    }

    /**
     * Add Group
     *
     * @param projectId Project ID
     * @param groupId   Group ID
     * @return Result<>
     */
    @GetMapping("addProjectGroup")
    public Result<Object> addProjectGroup(@RequestParam Integer projectId, @RequestParam Integer groupId) {
        boolean result = groupService.addProjectGroup(projectId, groupId);
        return Result.success(result);
    }

    /**
     * Remove Project Manager
     *
     * @param projectId Project ID
     * @param managerId User ID
     * @return Result<>
     */
    @DeleteMapping("removeProjectManager")
    public Result<Object> removeProjectManager(@RequestParam Integer projectId, @RequestParam Integer managerId) {
        projectManagerService.removeProjectManager(projectId, managerId);
        return Result.success();
    }

    /**
     * Remove Group from Project
     *
     * @param projectId Project ID
     * @param groupId   Group ID
     * @return Result<>
     */
    @DeleteMapping("removeProjectGroup")
    public Result<Object> removeProjectGroup(@RequestParam Integer projectId, @RequestParam Integer groupId) {
        groupService.removeProjectGroup(projectId, groupId);
        return Result.success();
    }

    /**
     * Get All Groups
     *
     * @return List of Group
     */
    @GetMapping("getAvailableGroup")
    public Result<List<SelectorOption>> getAvailableGroup() {
        List<SelectorOption> result = groupService.getAvailableGroup();
        return Result.success(result);
    }

    /**
     * Get All Group Members
     *
     * @param groupId Group ID
     * @return List of Group Members
     */
    @GetMapping("getGroupMember")
    public Result<List<SelectorOption>> getGroupMember(@RequestParam Integer groupId) {
        List<SelectorOption> result = groupMemberService.getGroupMember(groupId);
        return Result.success(result);
    }

    /**
     * Add Group Member
     *
     * @param groupId Group ID
     * @param userId  User ID
     * @return Result<>
     */
    @GetMapping("addGroupMember")
    public Result<Object> addGroupMember(@RequestParam Integer groupId, @RequestParam Integer userId) {
        boolean result = groupMemberService.addGroupMember(groupId, userId);
        return Result.success(result);
    }

    /**
     * Delete Group Member
     *
     * @param groupId Group ID
     * @param userId  User ID
     * @return Result<>
     */
    @DeleteMapping("removeGroupMember")
    public Result<Object> removeGroupMember(@RequestParam Integer groupId, @RequestParam Integer userId) {
        groupMemberService.removeGroupMember(groupId, userId);
        return Result.success();
    }

    @GetMapping("getSummary")
    public Result<Summary> getSummary() {
        Integer userId = SessionUtils.getUserId();
        List<Group> groupList = groupService.getSummaryGroup(userId);

        List<Integer> projectIdList = groupList.stream()
                .map(Group::getProjectId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Project> projectList = projectService.getSummaryProject(projectIdList);

        Summary summary = new Summary();
        summary.setProjectList(projectList);
        summary.setGroupList(groupList);

        return Result.success(summary);
    }

    @GetMapping("getMyApproval")
    public Result<PageData<Approval>> getMyApproval(MyApprovalQuery query) {
        Integer userId = SessionUtils.getUserId();
        query.setUserId(userId);
        PageData<Approval> result = approvalService.getApprovals(query);
        return Result.success(result);
    }

    @GetMapping("approve")
    public Result<Object> approve(@RequestParam Integer approvalId) {
        approvalService.approve(approvalId);
        return Result.success();
    }

    @GetMapping("reject")
    public Result<Object> reject(@RequestParam Integer approvalId) {
        approvalService.reject(approvalId);
        return Result.success();
    }

    @GetMapping("getNotifications")
    public Result<List<Notification>> getNotifications() {
        Integer userId = SessionUtils.getUserId();
        List<Notification> result = messageService.getNotifications(userId);
        return Result.success(result);
    }

    @GetMapping("readNotification")
    public Result<Object> readNotification(@RequestParam Integer messageId) {
        messageService.readNotification(messageId);
        return Result.success();
    }

    @GetMapping("getMessages")
    public Result<List<Message>> getMessages(@RequestParam Integer senderId, @RequestParam(required = false) Integer minId) {
        Integer receiverId = SessionUtils.getUserId();
        List<Message> result = messageService.getMessages(senderId, receiverId, minId);
        return Result.success(result);
    }

    @PostMapping("sendMessage")
    public Result<Object> sendMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return Result.success();
    }

    @GetMapping("getUserInfo")
    public Result<UserInfo> getUserInfo(@RequestParam Integer userId) {
        UserInfo userInfo = userService.getUserInfo(userId);
        return Result.success(userInfo);
    }

    @GetMapping("report")
    public void getReport(HttpServletResponse response) throws IOException, DocumentException {
        // save report in static dir
        File dirPath = new File(reportLocation + reportDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        String report_path = reportLocation + reportDir + "report.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(report_path)));
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
        Paragraph p1 = new Paragraph("Report", titleFont);
        document.add(p1);
        Map<String, Integer> table = projectService.getReportMap();
        int projectCount = table.getOrDefault("projectCount", 0);
        int groupCount = table.getOrDefault("groupCount", 0);
        int studentCount = table.getOrDefault("studentCount", 0);
        if (projectCount < 1 || groupCount < 1 || studentCount < 1) {
            Paragraph p2 = new Paragraph("There are not enough number of Projects/Groups to analyze.", titleFont);
            document.add(p2);
            document.close();
        } else {
            document.add(new Paragraph("Overview", subFont));
            document.add(new Paragraph("There are " + projectCount + " Projects, " + groupCount + " Groups, "
                                        + studentCount + " Students in total.", textFont));
            document.add(new Paragraph("Project", subFont));
            int projectAppCount = table.getOrDefault("projectAppCount", 0);
            int projectResCount = table.getOrDefault("projectResCount", 0);
            document.add(new Paragraph("There are " + projectAppCount + " Application Projects("
                                        + projectAppCount * 100 / projectCount + "%) and "
                                        + projectResCount + " Research Projects("
                                        + projectResCount * 100 / projectCount + "%).", textFont));
            document.add(new Paragraph("Group", subFont));
            int groupAllocatedCount = table.getOrDefault("groupAllocatedCount", 0);
            document.add(new Paragraph(groupAllocatedCount + " Group(s) have been allocated a Project, while "
                                        + (groupCount - groupAllocatedCount) + " Group(s) are/is idle.", textFont));
            document.add(new Paragraph("Student", subFont));
            int studentAppCount = table.getOrDefault("studentAppCount", 0);
            int studentResCount = table.getOrDefault("studentResCount", 0);
            document.add(new Paragraph("There are " + studentAppCount + " Students("
                    + studentAppCount * 100 / studentCount + "%) who prefer Applications and "
                    + studentResCount + "("
                    + studentResCount * 100 / studentCount + "%) that prefer Researches.", textFont));
            document.close();
        }
        File file = new File(report_path);

        // Resetting response
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + "report.pdf");
        try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //  return Result.failed("Download Failed");
        }
    }
}
