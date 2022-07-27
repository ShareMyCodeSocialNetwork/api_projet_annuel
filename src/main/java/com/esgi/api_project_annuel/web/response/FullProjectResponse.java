package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Project;

import java.util.List;

public class FullProjectResponse {
    private Project project;
    private List<Code> codesInProject;

    public Project getProject() {
        return project;
    }

    public FullProjectResponse setProject(Project project) {
        this.project = project;
        return this;
    }

    public List<Code> getCodesInProject() {
        return codesInProject;
    }

    public FullProjectResponse setCodesInProject(List<Code> codesInProject) {
        this.codesInProject = codesInProject;
        return this;
    }
}
