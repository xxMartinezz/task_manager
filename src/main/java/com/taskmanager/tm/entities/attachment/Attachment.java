package com.taskmanager.tm.entities.attachment;

import com.taskmanager.tm.entities.task.Task;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attachment")
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "url", length = 256)
    private String url;

    @Column(name = "mime_type")
    private String mimeType;

    @NotNull
    @Column(name = "size")
    private long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Attachment() { }

    private Attachment(String name, String url, String mimeType, long size) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String url;
        private String mimeType;
        private long size;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder withSize(long size) {
            this.size = size;
            return this;
        }

        public Attachment build() {
            return new Attachment(name, url, mimeType, size);
        }
    }
}
