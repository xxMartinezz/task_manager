package com.taskmanager.tm.services.dto.project;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateProjectDTO {

    @NotNull
    private String name;

    private List<Long> tasks;
}
