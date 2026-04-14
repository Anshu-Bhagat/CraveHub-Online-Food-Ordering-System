# 🍔 CraveHub (FoodFrenzy)
> *Your favorite food, delivered fresh—because great taste enhances every moment.*

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.3-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Frontend-Thymeleaf-darkgreen.svg)](https://www.thymeleaf.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 Overview
**CraveHub** is a comprehensive **Food Delivery Management System** designed to bridge the gap between hungry customers and delicious food. Built with the robust **Spring Boot** framework, it offers a seamless experience for managing inventory, processing orders, and handling user roles securely.

Whether you are an Admin managing the menu or a Customer craving a biryani, CraveHub makes it easy, fast, and secure.

---

## ✨ Features


### 👨‍💼 Admin Module
* **Dashboard:** View users, admins, products, and order statistics in one place.
* **Product Management:** Add, update, or delete food items (Images, Prices, Descriptions).
* **User Management:** Manage customer accounts and permissions.
* **Order Tracking:** Monitor incoming orders and payments.
* **Secure Login:** Role-based authentication for admins.

### 👤 User/Customer Module
* **Browse Menu:** Search and view a wide variety of food items with images.
* **Search Functionality:** Find specific dishes instantly.
* **Cart & Ordering:** Add items to cart and place single or bulk orders.
* **Order History:** View past orders and their status.
* **Contact & Locate:** Integrated Google Maps to locate the kitchen/restaurant.

---

## 🛠️ Tech Stack

| Category | Technologies Used |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot, Spring MVC, Spring Data JPA |
| **Frontend** | Thymeleaf, HTML5, CSS3, JavaScript |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **IDE** | Spring Tool Suite (STS) / Eclipse / IntelliJ IDEA |

---

## 📸 Screenshots

| Home Page | Admin Dashboard |
| :---: | :---: |
| ![Home](https://github.com/user-attachments/assets/3e34f54c-c986-42ac-96a4-ed7ad18035a6) | ![Admin](https://github.com/user-attachments/assets/c11a4710-69f8-42fd-b9d7-2b5278b2c8a3) |

| Menu/Products | Login Screen |
| :---: | :---: |
| ![Menu](https://github.com/user-attachments/assets/a4046d4e-8c3d-4629-8913-5543d709e80e) | ![Login](https://github.com/user-attachments/assets/d3cd3cdd-cda5-460a-a253-24e45cf600b0) |

---

## 🚀 Getting Started

Follow these steps to run the project locally on your machine.

### Prerequisites
* Java 17+ installed
* MySQL Server installed
* Maven installed

### Installation

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/your-username/CraveHub.git](https://github.com/your-username/CraveHub.git)
    cd FoodFrenzy
    ```

2.  **Configure Database**
    * Open MySQL and create a database named `CraveHub`.
    * Open `src/main/resources/application.properties` and update your credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/CraveHub
    spring.datasource.username=root
    spring.datasource.password=YOUR_PASSWORD
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  **Build the Project**
    ```bash
    mvn clean package -DskipTests
    ```

4.  **Run the Application**
    ```bash
    java -jar target/CraveHub-0.0.1-SNAPSHOT.jar
    ```

5.  **Access the App**
    * Go to: `http://localhost:8080`

---

## ☁️ Deployment

This project is optimized for deployment on cloud platforms like **Render** or **Railway**.

**Environment Variables Required:**
* `DB_URL`: JDBC URL for your cloud database (e.g., Aiven/TiDB).
* `DB_USERNAME`: Database CraveHub.
* `DB_PASSWORD`: Database password.

---

## 🤝 Contributing

Contributions are welcome!
1.  Fork the project.
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

---

## 📞 Contact

**Developer:** [GAGAN TIWARI , PRIYANSHU KUMAR , MAYANK KUMAR]
**Email:** tgagan368@gmail.com
**GitHub:** [https://github.com/tiwariGagans]

---

<p align="center">
  Made with ❤️ by CraveHub Team
</p>