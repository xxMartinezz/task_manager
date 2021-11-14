package com.taskmanager.tm.services.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilterDTO {
    private String name;
    private String surname;
    private Boolean active;
}
