package com.project.management.pojo;

import com.project.management.entity.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ethan
 * @description project detail
 * @date 2024-07-08 20:46:21
 */
@Getter
@Setter
public class ProjectDetail extends Project {

    /**
     * joined groups
     */
    private List<SelectorOption> groupList;

    /**
     * project managers
     */
    private List<SelectorOption> managerList;
}
