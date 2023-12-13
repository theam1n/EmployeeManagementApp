package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "department")
    private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();

    @PreRemove
    public void preRemoveMethod() {
        System.out.println("Department is deleted: " + this.getId());
    }
}
