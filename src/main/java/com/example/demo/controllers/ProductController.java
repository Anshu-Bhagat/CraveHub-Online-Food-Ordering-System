package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Ye import jaruri hai
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductServices;

@Controller
public class ProductController {

    @Autowired
    private ProductServices productServices;

    // --- 1. Product Add Karna ---
    @PostMapping("/addingProduct")
    public String addProduct(@ModelAttribute Product product) {
        this.productServices.addProduct(product);
        return "redirect:/admin/services";
    }

   
    // Jab user "Edit" button par click karega, ye method chalega
    @GetMapping("/updateProduct/{productId}")
    public String openUpdatePage(@PathVariable("productId") int id, Model model) {
        
       
        Product product = this.productServices.getProduct(id); 
        
        model.addAttribute("product", product);
        return "Update_Product"; // HTML file ka naam (bina .html ke)
    }

    // --- 3. Update Data Save Karna ---
    // HTML Form se data yahan aayega. Isliye POST use kiya hai.
    @PostMapping("/updatingProduct/{productId}")
    public String updateProduct(@ModelAttribute Product product, @PathVariable("productId") int id) {
        
        this.productServices.updateproduct(product, id);
        return "redirect:/admin/services";
    }

    // --- 4. Product Delete Karna ---
    @GetMapping("/deleteProduct/{productId}")
    public String delete(@PathVariable("productId") int id) {
        this.productServices.deleteProduct(id);
        return "redirect:/admin/services";
    }
}