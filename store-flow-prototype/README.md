# Store-Flow-Prototype (Personal Project - In Progress | Java, Spring Boot)
A lightweight, robust backend system designed for retail shop management. This application simplifies inventory control, automates stock alerts, and handles point-of-sale (POS) checkout operations.

## Current Development Status
This project is currently a **Backend Prototype / Proof of Concept (PoC)**. The focus of the current phase is laying down the architectural foundation, database schemas, core authentication controllers, and performance/monitoring infrastructure.

## Key Features
* **Inventory & Storage Tracking:** Real-time tracking of product stock levels and remaining quantities.
* **Low Stock Alert System:** Automated notifications when items fall below a specific inventory threshold.
* **Billing & Checkout:** Quick product calculation, invoicing, and transaction processing.

## Tech Stack
* **Java:** Version 17
* **Framework:** Spring Boot 4.x (Spring Web, Spring Data JPA)
* **Database:** MySQL
* **Caching & Session:** Redis
* **Monitoring & Metrics:** Grafana / Prometheus (Micrometer)
* **Build Tool:** Gradle

## Features & Project Roadmap
### Phase 1: Core Foundation & Auth Concepts (Implemented)
* **Database & Entity Design:** Structured relational database schemas optimized for inventory tracking , product information and user management.
* **Role-Based Access Control (RBAC) Setup:** Implemented `User` and `Role` controllers to manage accounts and system permissions (e.g., Admin, Staff).
* **Spring Data JPA Integration:** Robust data persistence layer with automated schema generation.
* **Performance Optimization (Redis):** Integrated Redis Caching to optimize data retrieval and reduce database overhead.
* **System Monitoring & Observability (Grafana):** Configured Grafana and Prometheus to monitor application metrics and system health in real-time.

### Phase 2: Inventory & Business Logic (In Progress / Next Step)
* **Product Catalog Service:** Developing the Service and Controller layers for full product CRUD operations and storage tracking.
* **Billing & Checkout Module:** Preparing service logic to calculate transaction values and trigger automatic stock deduction.

## Authors
* **MR.SAKULPETCH BUAPHAN** - [GitHub Profile](https://github.com/sa73391577/sakulpetch-projects)
