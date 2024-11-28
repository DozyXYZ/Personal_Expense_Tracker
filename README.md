# Personal Expense Tracker

This is the project for the course **Back End Programming 2024** at Haaga-Helia University of Applied Sciences.  
The application is a personal expense tracker built with **Spring Boot** and **Thymeleaf**, offering functionalities like user registration, login, password reset, and expense management. Users can also export expenses to CSV files and view charts for better financial insights.

---

## Features
- **User Registration and Login**
- **Password Reset**: using unique recovery code
- **Expense Management**: add, view, edit, and delete expenses
- **Filter Expenses**: by type, year, and month
- **Export to CSV**: download filtered expenses
- **Visual Analytics**: annual and monthly charts
- **User Roles and Permissions**: for authentication and authorization

---

## Getting Started

### Prerequisites
- **Java 17**
- **Maven**

### Installation

1. Clone the repository:
   git clone https://github.com/DozyXYZ/personal-expense-tracker.git
   cd personal-expense-tracker

2. Build the project:
   ./mvnw clean install

3. Run the application:
   ./mvnw spring-boot:run

4. Open your browser and navigate to:
   http://localhost:8080

---

# Usage

## Register a New User
1. Go to the registration page: http://localhost:8080/register
2. Fill in the required fields and submit the form.

## Log In
1. Go to the login page: http://localhost:8080/login
2. Enter your username and password and submit the form.

## Reset Password
1. After registration, the application will generate a unique recovery code that the user can remember
2. When the user want to change the password, go to http://localhost:8080/reset
3. Fill in the required fields: username, recovery code, and new password and submit the form

## Manage Expenses
1. After logging in, you will be redirected to the expenses page.
2. You can:
   - Add expenses
   - View expenses
   - Edit expenses
   - Delete expenses

## Export Expenses to CSV
1. On the expenses page, click the **Export to CSV** button.
2. Your expenses will be downloaded as a CSV file.

## Draw Charts
1. On the expenses page, click the **Draw chart** button to go to the chart page
2. Use the filter to select the year and month you want to see the expenses chart

## Login as Admin (admin/1234)
1. ADMIN has all the functions like USER role
2. ADMIN can access type expense page
3. ADMIN can add and edit an expense type 
