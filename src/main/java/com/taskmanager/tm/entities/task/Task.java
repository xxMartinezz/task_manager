package com.taskmanager.tm.entities.task;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.comment.Comment;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "task")
    private TaskType taskType;

    @Column(name = "component")
    private String component;

    @NotNull
    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Priority priority;

    @Column(name = "estimatedTime")
    private String estimatedTime;

    @Column(name = "deadlineDate")
    private LocalDateTime deadlineDate;

    @Column(name = "reportedDate")
    @CreationTimestamp
    private LocalDateTime reportedDate;

    @Column(name = "lastChageDate")
    private LocalDateTime lastChageDate;

    @Column(name = "loggedTime")
    private String loggedTime;

    @Column(name = "taskStatus")
    private TaskStatus taskStatus;

    @OneToMany(mappedBy = "task",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Sprint> sprints;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
    private List<Attachment> attachments;

}
