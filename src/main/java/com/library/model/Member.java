package com.library.model;

import java.time.LocalDate;

/**
 * Represents a library member - someone who can borrow our books!
 * Keeps track of their contact info and membership status.
 */
public class Member {
    private int id;                    // Unique member ID
    private String name;               // Full name
    private String email;              // Email for notifications
    private String phone;              // Phone number
    private String address;            // Home address
    private LocalDate joinDate;        // When they became a member
    private boolean isActive;          // Are they still an active member?
    
    // Create a new member with today's date
    public Member() {
        this.joinDate = LocalDate.now();  // They joined today!
        this.isActive = true;             // Active by default
    }
    
    // Create a member with all their info
    public Member(String name, String email, String phone, String address) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    // Constructor with all parameters
    public Member(int id, String name, String email, String phone, String address, LocalDate joinDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinDate = joinDate;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", joinDate=" + joinDate +
                ", isActive=" + isActive +
                '}';
    }
}
