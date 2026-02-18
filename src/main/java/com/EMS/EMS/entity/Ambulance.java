package com.EMS.EMS.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ambulances")
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String status; // e.g., AVAILABLE, BUSY, MAINTENANCE

    public Ambulance() {}

    public Ambulance(String vehicleNumber, String status) {
        this.vehicleNumber = vehicleNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ambulance ambulance = (Ambulance) o;
        return Objects.equals(id, ambulance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ambulance{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
