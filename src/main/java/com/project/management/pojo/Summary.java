package com.project.management.pojo;

import com.project.management.entity.Group;
import com.project.management.entity.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Summary {
    List<Project> projectList;
    List<Group> groupList;
}
