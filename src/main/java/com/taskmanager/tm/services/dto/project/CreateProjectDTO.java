package com.taskmanager.tm.services.dto.project;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProjectDTO {

    @NotNull
    private String name;
}
