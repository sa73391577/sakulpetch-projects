# Spring Batch - Thailand Address Importer

A Spring Batch project designed to fetch, process, and import Thailand's geographical data—consisting of **Provinces, Districts, and Sub-districts**—into the system's main database.

---

## 📌 Features
*   **Multi-Step Batch Processing:** Operations are separated sequentially based on data dependency (Provinces -> Districts -> Sub-districts).
*   **Chunk-Oriented Processing:** Uses chunk-based reading and writing for optimal execution performance and memory efficiency.
*   **Fail-safe & Skip Logic:** Built-in support to skip invalid data records and automatically retry transient errors.
*   **Idempotency:** Designed safely to allow re-runs without producing duplicate data.

---

## 🏗️ Batch Architecture
The system executes tasks sequentially to honor geographical data hierarchies:

```text
[ Job: importThailandAddressJob ]
   │
   ├──► [ Step 1: importProvincesStep ] ──► Fetch province data
   │
   ├──► [ Step 2: importDistrictsStep ] ──► Fetch district data (Requires Province ID)
   │
   ├──► [ Step 3: importSubDistrictsStep ] ─► Fetch sub-district data (Requires District ID)
   │
   └──► [ Step 4: insertInformationToDatabaseStep ] ─► Save all collected data into the database
```

### Step Lifecycle Flow
Each sequential step adheres to the standard Spring Batch **Reader -> Processor -> Writer** pattern:
1.  **ItemReader:** Fetches raw source data to extract all initial province information within Thailand.
2.  **ItemProcessor:** Takes the retrieved province data to pull associated districts, then uses those districts to look up and map their corresponding sub-districts.
3.  **ItemWriter:** Saves the fully mapped geographical records into the target database via MySQL batch insertion.

---

## 🛠️ Tech Stack
*   **Java:** 17 (via Gradle Toolchain)
*   **Framework:** Spring Boot 4.1.0
*   **Batch Engine:** Spring Batch 6.x (Managed by Spring Boot)
*   **Data Access:** Spring Data JPA
*   **Database Driver:** MySQL Connector/J (`com.mysql:mysql-connector-j`)
*   **Utilities:** Lombok 1.18.42, Apache Commons Lang 3.14.0
*   **Deployment Format:** WAR (Web Application Archive)

---