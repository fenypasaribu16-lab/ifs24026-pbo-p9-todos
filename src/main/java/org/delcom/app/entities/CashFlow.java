package org.delcom.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cash_flows") 
public class CashFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; 
    
    // Field Wajib (Type, Source, Label, Amount, Description)
    private String type; 
    private String source; 
    private String label; 
    private Integer amount; // Ganti ke Integer
    private String description; 
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 1. Constructor Wajib (No-Args)
    public CashFlow() {}

    // 2. Constructor Wajib (Sesuai Test TA)
    public CashFlow(String type, String source, String label, Integer amount, String description) {
        this.type = type;
        this.source = source;
        this.label = label;
        this.amount = amount;
        this.description = description;
    }

    // Lifecycle Callbacks (Sesuai Test TA: onCreate, onUpdate)
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Pastikan semua Getter dan Setter untuk field di atas ada (kecuali setter untuk createdAt/updatedAt)
    // ... (Getters and Setters)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}