
package com.rotemDemo.zzz.api.unit.service.impl;

import com.rotemDemo.zzz.api.unit.mapper.ProjectMapper;
import com.rotemDemo.zzz.api.unit.service.ProjectService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service 
public class ProjectServiceimpl implements ProjectService {

    private final ProjectMapper projectMapper;

    public ProjectServiceimpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public List<Map<String, Object>> selectProjectList(Map<String, Object> data) {
        return projectMapper.selectProjectList(data);
    }
    

}