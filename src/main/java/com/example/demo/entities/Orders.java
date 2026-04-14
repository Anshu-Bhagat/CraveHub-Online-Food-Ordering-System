package com.example.demo.entities;

import java.util.Date;
import jakarta.persistence.*; // Use this for clean imports

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <--- CRITICAL FIX: Changed from AUTO to IDENTITY
    private int oId;

    private String oName;
    private double oPrice;
    private int oQuantity;
    private Date orderDate;
    private double totalAmmout;

    @ManyToOne
    @JoinColumn(name = "user_u_id")
    private User user;

    // --- Getters and Setters ---

    public int getoId() { return oId; }
    public void setoId(int oId) { this.oId = oId; }

    public String getoName() { return oName; }
    public void setoName(String oName) { this.oName = oName; }

    public double getoPrice() { return oPrice; }
    public void setoPrice(double oPrice) { this.oPrice = oPrice; }

    public int getoQuantity() { return oQuantity; }
    public void setoQuantity(int oQuantity) { this.oQuantity = oQuantity; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public double getTotalAmmout() { return totalAmmout; }
    public void setTotalAmmout(double totalAmmout) { this.totalAmmout = totalAmmout; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Orders [oId=" + oId + ", oName=" + oName + ", oPrice=" + oPrice + ", oQuantity=" + oQuantity + "]";
    }
}