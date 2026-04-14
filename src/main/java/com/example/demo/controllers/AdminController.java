
package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpSession; // Import Session

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.count.Logic;
import com.example.demo.entities.*;
import com.example.demo.loginCredentials.*;
import com.example.demo.services.*;

@Controller
public class AdminController {
    
    @Autowired private UserServices services;
    @Autowired private AdminServices adminServices;
    @Autowired private ProductServices productServices;    
    @Autowired private OrderServices orderServices;


    // --- Login Logic ---
    @PostMapping("/adminLogin")
    public String getAllData(@ModelAttribute("adminLogin") AdminLogin login, Model model) {
        if(adminServices.validateAdminCredentials(login.getEmail(), login.getPassword())) {
            return "redirect:/admin/services";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Login";
        }
    }

    @PostMapping("/userLogin")
    public String userLogin(@ModelAttribute("userLogin") UserLogin login, Model model, HttpSession session) {
        if(services.validateLoginCredentials(login.getUserEmail(), login.getUserPassword())) {
            User user = this.services.getUserByEmail(login.getUserEmail());
            
            // Save user to Session (Temporary memory for this browser)
            session.setAttribute("activeUser", user); 

            List<Orders> orders = this.orderServices.getOrdersForUser(user);
            model.addAttribute("orders", orders);
            model.addAttribute("name", user.getUname());
            return "BuyProduct";
        } else {
            model.addAttribute("error2", "Invalid email or password");
            return "Login";
        }
    }

    // --- Order Logic ---
 // Updated Single Order Handler
    @PostMapping("/product/order")
    public String orderHandler(@ModelAttribute() Orders order, Model model, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        if (user == null) return "redirect:/login";

        // Calculate Amount
        double totalAmount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
        order.setTotalAmmout(totalAmount);
        
        // Save to Session (Not DB yet)
        session.setAttribute("pendingOrder", order);
        session.setAttribute("pendingOrderType", "SINGLE");
        session.setAttribute("finalAmount", totalAmount);

        return "redirect:/payment"; // Redirect to Payment Page
    }

    @PostMapping("/product/search")
    public String search(@RequestParam("productName") String name, Model model, HttpSession session) {
        
        // 1. Check user login
        User user = (User) session.getAttribute("activeUser");
        if (user == null) {
            return "redirect:/login";
        }

        // 2. Search products (Ab ye LIST return karega)
        List<Product> products = this.productServices.getProductByName(name);

        // 3. User history maintain
        model.addAttribute("orders", this.orderServices.getOrdersForUser(user));
        model.addAttribute("name", user.getUname());

        // 4. Result Handle
        if (products.isEmpty()) {
            model.addAttribute("msg", "SORRY...! Product Unavailable");
            return "BuyProduct"; 
        }

        // 5. 'products' list ko model mein add (Single 'product' nahi)
        model.addAttribute("products", products);
        
        return "BuyProduct"; 
    }
    
    
 // Ye method Admin Page se "Add Product" page kholta hai
    @GetMapping("/addProduct")
    public String addProduct() {
        return "Add_Product";
    }

    // --- Back Button Logic ---
    @GetMapping("/product/back")
    public String back(Model model, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        if (user != null) {
            List<Orders> orders = this.orderServices.getOrdersForUser(user);
            model.addAttribute("orders", orders);
            model.addAttribute("name", user.getUname());
            return "BuyProduct";
        }
        return "redirect:/login";
    }

    // ... Keep your other Admin methods (addAdmin, updateAdmin, etc.) unchanged ...
    @GetMapping("/admin/services")
    public String returnBack(Model model) {
        // ... (Your existing code for admin dashboard)
        List<User> users = this.services.getAllUser();
        List<Admin> admins = this.adminServices.getAll();
        List<Product> products = this.productServices.getAllProducts();
        List<Orders> orders = this.orderServices.getOrders();
        model.addAttribute("users", users);
        model.addAttribute("admins", admins);
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        return "Admin_Page";
    }
    
    
 // ... (Existing code inside AdminController class) ...
 // 1. PAYMENT PAGE SHOW KARO
    @GetMapping("/payment")
    public String paymentPage(Model model, HttpSession session) {
        // Check if there is a pending amount
        Double amount = (Double) session.getAttribute("finalAmount");
        if (amount == null) {
            return "redirect:/product/back"; // No transaction found
        }
        model.addAttribute("amount", amount);
        return "Payment";
    }

    // 2. PAYMENT SUBMIT HONE KE BAAD ORDER SAVE KARO
    @PostMapping("/processPayment")
    public String processPayment(Model model, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        
        // Check order type (Single vs Bulk)
        String orderType = (String) session.getAttribute("pendingOrderType");
        
        if ("SINGLE".equals(orderType)) {
            Orders order = (Orders) session.getAttribute("pendingOrder");
            if (order != null) {
                order.setUser(user);
                order.setOrderDate(new Date());
                this.orderServices.saveOrder(order);
            }
        } 
        else if ("BULK".equals(orderType)) {
            OrderWrapper form = (OrderWrapper) session.getAttribute("pendingBulkOrder");
            if (form != null && form.getOrders() != null) {
                for (Orders order : form.getOrders()) {
                    if (order.getoQuantity() > 0) {
                        double amount = Logic.countTotal(order.getoPrice(), order.getoQuantity());
                        order.setTotalAmmout(amount);
                        order.setUser(user);
                        order.setOrderDate(new Date());
                        this.orderServices.saveOrder(order);
                    }
                }
            }
        }

        // Show Amount on Success Page
        Double amount = (Double) session.getAttribute("finalAmount");
        model.addAttribute("amount", amount);

        // Clear Session Data (Payment Done)
        session.removeAttribute("pendingOrder");
        session.removeAttribute("pendingBulkOrder");
        session.removeAttribute("pendingOrderType");
        session.removeAttribute("finalAmount");

        return "Order_success";
    }
    // --- NEW: Bulk Order Logic ---

    // 1. Wrapper Class (List ko handle karne ke liye)
    public static class OrderWrapper {
        private List<Orders> orders;
        public List<Orders> getOrders() { return orders; }
        public void setOrders(List<Orders> orders) { this.orders = orders; }
    }

    // 2. New Method to save multiple orders at once
   // Updated Bulk Order Handler
    @PostMapping("/product/orderAll")
    public String orderAll(@ModelAttribute OrderWrapper form, Model model, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        if (user == null) return "redirect:/login";

        double totalAmount = 0;
        if (form.getOrders() != null) {
            for (Orders order : form.getOrders()) {
                if (order.getoQuantity() > 0) {
                    totalAmount += Logic.countTotal(order.getoPrice(), order.getoQuantity());
                }
            }
        }

        // Save to Session (Not DB yet)
        session.setAttribute("pendingBulkOrder", form);
        session.setAttribute("pendingOrderType", "BULK");
        session.setAttribute("finalAmount", totalAmount);

        return "redirect:/payment"; // Redirect to Payment Page
    }
} 
// (Class End)
    
    // Include your other mapping methods here (addAdmin, updateAdmin, etc.)
