# 🚀 Store Flow Prototype (`store-flow-prototype`) 
# [Angular Version 21]

**Store Flow Prototype** is a modern, high-performance web application prototype designed to simulate and optimize retail/store workflows. Built on **Angular 21**, the platform leverages **Server-Side Rendering (SSR)** to achieve fast initial page loads, seamless user interactions, and robust SEO capabilities.

---

## 🛠️ Tech Stack & Key Technologies

This prototype utilizes a scalable, modern technology stack tailored for enterprise-grade performance and clean architecture:

* **Core Framework:** **Angular 21** (with production-ready `@angular/ssr` support)
* **UI & Styling:** **Bootstrap 5**, **Angular Material (CDK)**, and **ngx-bootstrap** to ensure a fluid, accessible, and fully responsive user experience.
* **Icons:** FontAwesome (`@fortawesome/angular-fontawesome`)
* **Localization:** Multi-language routing and translation features using `@ngx-translate`
* **Authentication:** Secure client-side JWT handling via `@auth0/angular-jwt`
* **Alerts & Dialogs:** Smooth, interactive notifications powered by **SweetAlert2**
* **Testing & Tooling:** Ultra-fast unit testing via **Vitest**, with consistent formatting enforced by **Prettier**

---

## ✨ Key Features

* 🌐 **Server-Side Rendering (SSR):** Instant template rendering on the server for enhanced load speeds and web crawler visibility.
* 📦 **Workflow Simulation:** Interactive components to visualize and test step-by-step store flows, order management, or inventory tracking.
* 🌍 **Internationalization (i18n):** Full support for global deployments with dynamic language switching.
* 📱 **Mobile-First Layout:** Perfectly optimized for handheld devices used on store floors as well as desktop dashboards.

---

## 🚀 Getting Started

### 📋 Prerequisites
Ensure you have the following installed on your local machine:
* **Node.js** (LTS version recommended)
* **npm** version `11.11.0` or higher

### 🛠️ Installation
1. Clone this repository to your local directory.
2. Open your terminal in the project root folder and run:
   ```bash
   npm install
   ```

### 💻 Local Development Server
Launch the development server with the following command. The application will run at `http://localhost:4200/` and hot-reload automatically upon file changes:
```bash
npm start
```

### 🧪 Running Tests
Run your unit test suite instantly using **Vitest**:
```bash
npm run test
```

---

## 📦 Production & Deployment

### 1. Build the Application
Compile the production-ready application bundle. This script limits node memory allocation (`--max-old-space-size=512`) for cost-effective cloud builds and disables source maps for security:
```bash
npm run build
```

### 2. Run the SSR Server
Once the build is complete, spin up the local production Node.js Express server by running:
```bash
npm run serve:ssr:store-flow-prototype
```
