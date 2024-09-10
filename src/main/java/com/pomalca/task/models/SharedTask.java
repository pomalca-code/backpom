package com.pomalca.task.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shared_tasks")
@Data
public class SharedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shared_with_id", nullable = false)
    private User sharedWith;
}
