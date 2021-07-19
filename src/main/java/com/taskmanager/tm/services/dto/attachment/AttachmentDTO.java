package com.taskmanager.tm.services.dto.attachment;

import lombok.Value;

@Value
public class AttachmentDTO {
    String name;
    String url;
    String mimeType;
    long size;
}
