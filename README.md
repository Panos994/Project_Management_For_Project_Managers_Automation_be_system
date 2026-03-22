content = r'''# Project Management System API

Ένα backend REST API για **Project Management System** υλοποιημένο με **Java + Spring Boot**.  
Το σύστημα υποστηρίζει διαχείριση χρηστών, projects, tasks, comments και attachments, με **JWT authentication**, **role-based access**, **validation** και **custom exception handling**.

---

## 🚀 Features

- **User Management**
  - Register νέου χρήστη
  - Login με JWT authentication
  - Roles: `ROLE_USER`, `ROLE_ADMIN`

- **Project Management**
  - Δημιουργία project
  - Προβολή projects ανά owner
  - Προβολή projects όπου ένας χρήστης είναι member
  - Προσθήκη / αφαίρεση members από project

- **Task Management**
  - Δημιουργία task μέσα σε project
  - Ανάθεση task σε χρήστη
  - Διαχείριση status (`TODO`, `IN_PROGRESS`, `DONE`)
  - Διαχείριση priority (`LOW`, `MEDIUM`, `HIGH`)

- **Comments**
  - Προσθήκη comment σε task
  - Προβολή comments ανά task
  - Update/Delete comment μόνο από τον author

- **Attachments**
  - Προσθήκη attachment σε task
  - Προβολή attachments ανά task
  - Διαγραφή attachment

- **Security**
  - Stateless authentication με JWT
  - Protected endpoints με Spring Security
  - Method-level authorization έτοιμο για επέκταση

- **Error Handling**
  - Custom exceptions
  - Global exception handling με standard API error response

---

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JJWT)**
- **Hibernate / JPA**
- **Lombok**
- **Jakarta Validation**
- **PostgreSQL / MySQL / H2** (ανάλογα τη ρύθμιση)
- **Maven**

---

## 📁 Project Structure

```text
src/main/java/.../
├── controller        # REST Controllers
├── dto               # Request / Response DTOs
├── entity            # JPA Entities
├── repository        # Spring Data JPA repositories
├── service           # Business logic
├── security          # JWT, UserDetails, SecurityConfig
├── exception         # Custom exceptions
├── utils             # Utility classes (π.χ. PageMapper)
└── config            # Configuration classes
