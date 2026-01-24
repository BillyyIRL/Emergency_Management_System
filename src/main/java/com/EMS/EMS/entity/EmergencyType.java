package com.EMS.EMS.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "emergency_types")
public class EmergencyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer severityLevel; // 1 (Low) to 5 (Critical)

    public EmergencyType() {
    }

    public EmergencyType(String name, Integer severityLevel) {
        this.name = name;
        this.severityLevel = severityLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyType that = (EmergencyType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmergencyType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", severityLevel=" + severityLevel +
                '}';
    }
}
