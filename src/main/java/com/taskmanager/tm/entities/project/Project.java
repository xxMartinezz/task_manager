package com.taskmanager.tm.entities.project;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.task.Task;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Task> tasks;

    public Project() { };

    public Project(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
