package com.sendkar.planpurchase.model;

import com.sendkar.planpurchase.model.audit.DateAudit;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

@Entity
@Table(name = "statuses")
public class Status extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 15)
    private StatusName name;

    @Column(length = 50)
    private String description;

    public Status() {

    }

    public Status(StatusName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusName getName() {
        return name;
    }

    public void setName(StatusName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
