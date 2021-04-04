package com.taskmanager.tm.entities.attachment;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.task.Task;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attachment")
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "url", length = 256)
    private String url;

    @Column(name = "mimeType")
    private String mimeType;

    @NotNull
    @Column(name = "size", nullable = false)
    private long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Attachment() { };

    public Attachment(String name, String url, String mimeType, long size) {
        this.name = name;
        this.url = url;
        this.mimeType = mimeType;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", size=" + size +
                '}';
    }
}
