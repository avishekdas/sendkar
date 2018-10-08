package com.sendkar.planpurchase.payload;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String status;
    private String plan;

    public UserSummary(Long id, String username, String name, String status, String plan) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.status = status;
        this.plan = plan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
