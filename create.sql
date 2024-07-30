SET NAMES UTF8;

DROP DATABASE IF EXISTS `project_management`;
CREATE DATABASE `project_management` DEFAULT CHARACTER SET UTF8;
USE `project_management`;

CREATE TABLE `user` (
                        id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                        username VARCHAR(100) NOT NULL COMMENT 'username',
                        password VARCHAR(100) NOT NULL COMMENT 'password',
                        avatar VARCHAR(100) NOT NULL COMMENT 'avatar path',
                        email VARCHAR(100) NOT NULL COMMENT 'email',
                        role VARCHAR(20) NOT NULL COMMENT 'user role',
                        affiliation VARCHAR(100) NOT NULL COMMENT 'affiliation',
                        preference VARCHAR(20) NULL COMMENT 'preference',
                        create_date DATETIME NOT NULL COMMENT 'create date',
                        amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='user';

CREATE TABLE `project` (
                           id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                           name VARCHAR(100) NOT NULL COMMENT 'name',
                           description TEXT NOT NULL COMMENT 'description',
                           category VARCHAR(20) NOT NULL COMMENT 'category',
                           creator_id INT NOT NULL COMMENT 'creator',
                           create_date DATETIME NOT NULL COMMENT 'create date',
                           amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='project';

CREATE TABLE `project_manager` (
                                   id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                                   project_id INT NOT NULL COMMENT 'project id',
                                   manager_id INT NOT NULL COMMENT 'manager id',
                                   create_date DATETIME NOT NULL COMMENT 'create date',
                                   amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='project manager';

CREATE TABLE `group` (
                         id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                         name VARCHAR(100) NOT NULL COMMENT 'name',
                         admin_id INT NOT NULL COMMENT 'admin id',
                         project_id INT NULL COMMENT 'project id',
                         create_date DATETIME NOT NULL COMMENT 'create date',
                         amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='group';

CREATE TABLE `group_member` (
                                id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                                user_id INT NOT NULL COMMENT 'user id',
                                group_id INT NOT NULL COMMENT 'group id',
                                create_date DATETIME NOT NULL COMMENT 'create date',
                                amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='group member';

CREATE TABLE `message` (
                           id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                           type VARCHAR(20) NOT NULL COMMENT 'message type',
                           content VARCHAR(200) NOT NULL COMMENT 'content',
                           sender_id INT NULL COMMENT 'sender id',
                           receiver_id INT NOT NULL COMMENT 'receiver id',
                           is_read BIT NOT NULL COMMENT 'is read',
                           create_date DATETIME NOT NULL COMMENT 'create date',
                           amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='message';

CREATE TABLE `approval` (
                            id INT PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT 'id',
                            type VARCHAR(20) NOT NULL COMMENT 'type',
                            status VARCHAR(20) NOT NULL COMMENT 'status',
                            project_id INT NULL COMMENT 'project id',
                            group_id INT NOT NULL COMMENT 'group id',
                            creator_id INT NOT NULL COMMENT 'creator id',
                            remark VARCHAR(200) NOT NULL COMMENT 'remark',
                            create_date DATETIME NOT NULL COMMENT 'create date',
                            amend_date DATETIME NOT NULL COMMENT 'amend date'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT='approval';

-- mock users
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (1, 'The Admin', 'admin', 'avatar1', 'email@admin.admin', 'Administrator', 'CSE', 'Research', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (2, 'The Project Client', 'projectclient', 'avatar2', 'email@project.client', 'ProjectClient', 'GSOE', 'Research', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
# INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (3, 'username3', 'password3', 'avatar3', 'email3@google.com', 'ProjectClient', 'affiliation3', 'Research', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (4, 'The Tutor', 'tutor', 'avatar4', 'email@tutor.tutor', 'Tutor', 'CSE', 'Research', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
# INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (5, 'username5', 'password5', 'avatar5', 'email5@google.com', 'Tutor', 'affiliation5', 'Research', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (6, 'The Coordinator', 'coordinator', 'avatar6', 'email@coor.dinator', 'Coordinator', 'Engineering', 'Application', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
# INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (7, 'username7', 'password7', 'avatar7', 'email7@google.com', 'Coordinator', 'affiliation7', 'Application', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (8, 'The Student I', 'student1', 'avatar8', 'student1@google.com', 'Student', 'CSE', 'Application', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (9, 'The Student II', 'student2', 'avatar9', 'student2@google.com', 'Student', 'CSE', 'Application', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `user` (id, username, password, avatar, email, role, affiliation, preference, create_date, amend_date) VALUES (10, 'The Student III', 'student3', 'avatar10', 'student3@google.com', 'Student', 'LAW', 'Application', '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock projects
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (1, 'project1', 'this is project1', 'Application', 1, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (2, 'project2', 'this is project2', 'Application', 2, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (3, 'project3', 'this is project3', 'Application', 3, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (4, 'project4', 'this is project4', 'Application', 4, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (5, 'project5', 'this is project5', 'Application', 5, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (6, 'project6', 'this is project6', 'Research', 6, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (7, 'project7', 'this is project7', 'Research', 7, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (8, 'project8', 'this is project8', 'Research', 8, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (9, 'project9', 'this is project9', 'Research', 9, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (10, 'project10', 'this is project10', 'Research', 10, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (11, 'project11', 'this is project11', 'Application', 1, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (12, 'project12', 'this is project12', 'Application', 2, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (13, 'project13', 'this is project13', 'Application', 3, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (14, 'project14', 'this is project14', 'Application', 4, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (15, 'project15', 'this is project15', 'Application', 5, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (16, 'project16', 'this is project16', 'Research', 6, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (17, 'project17', 'this is project17', 'Research', 7, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (18, 'project18', 'this is project18', 'Research', 8, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (19, 'project19', 'this is project19', 'Research', 9, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project (id, name, description, category, creator_id, create_date, amend_date) VALUES (110, 'project110', 'this is project110', 'Research', 10, '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock project_managers
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (1, 1, 1, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (2, 2, 2, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (3, 3, 3, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (4, 4, 4, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (5, 5, 5, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (6, 6, 6, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (7, 7, 7, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (8, 8, 8, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (9, 9, 9, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO project_manager (id, project_id, manager_id, create_date, amend_date) VALUES (10, 10, 10, '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock groups
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (1, 'group1', 1, 1, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (2, 'group2', 2, 2, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (3, 'group3', 3, 3, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (4, 'group4', 4, 4, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (5, 'group5', 5, 5, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (6, 'group6', 6, 6, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (7, 'group7', 7, 7, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (8, 'group8', 8, 8, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (9, 'group9', 9, 9, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO `group` (id, name, admin_id, project_id, create_date, amend_date) VALUES (10, 'group10', 10, 10, '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock group_member
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (1, 1, 1, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (2, 2, 2, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (3, 3, 3, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (4, 4, 4, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (5, 5, 5, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (6, 6, 6, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (7, 7, 7, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (8, 8, 8, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (9, 9, 9, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO group_member (id, user_id, group_id, create_date, amend_date) VALUES (10, 10, 10, '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock message
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (1, 'Notice', 'content1', 1, 10, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (2, 'Notice', 'content2', 2, 9, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (3, 'Notice', 'content3', 3, 8, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (4, 'Notice', 'content4', 4, 7, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (5, 'Notice', 'content5', 5, 6, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (6, 'Chat', 'content6', 6, 5, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (7, 'Chat', 'content7', 7, 4, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (8, 'Chat', 'content8', 8, 3, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (9, 'Chat', 'content9', 9, 2, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO message (id, type, content, sender_id, receiver_id, is_read, create_date, amend_date) VALUES (10, 'Chat', 'content10', 10, 1, false, '2024-07-08 20:46:21', '2024-07-08 20:46:21');

-- mock approval
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (1, 'Project', 'Init', 1,1, 1, 'Want to join project1', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (2, 'Project', 'Init', 2,2, 2, 'Want to join project2', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (3, 'Project', 'Reject', 3, 3, 3, 'Want to join project3', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (4, 'Project', 'Approve', 4,4, 4, 'Want to join project4', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (5, 'Project', 'Approve', 5,5, 5, 'Want to join project5', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (6, 'Group', 'Init', null,6, 6, 'Want to join group6', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (7, 'Group', 'Init', null,7, 7, 'Want to join group7', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (8, 'Group', 'Init', null,8, 8, 'Want to join group8', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (9, 'Group', 'Reject', null,9, 9, 'Want to join group9', '2024-07-08 20:46:21', '2024-07-08 20:46:21');
INSERT INTO approval (id, type, status, project_id, group_id, creator_id, remark, create_date, amend_date) VALUES (10, 'Group', 'Approve', null,10, 10, 'Want to join group10', '2024-07-08 20:46:21', '2024-07-08 20:46:21');

