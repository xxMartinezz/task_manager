package com.taskmanager.tm.repositories.attachment;

import com.taskmanager.tm.entities.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
