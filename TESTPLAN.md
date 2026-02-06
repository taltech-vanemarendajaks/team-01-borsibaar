# Test Plan — Börsibaar

## 1. Testing Objectives

**Primary objective**  
Verify that Börsibaar’s core business logic works correctly and reliably (inventory, transactions, pricing behavior), and that key user flows work end-to-end.

**Specific objectives**
- Catch regressions in service-layer business rules via automated tests.
- Validate REST API behavior (happy paths and common failures).
- Confirm authentication flows (OAuth2/JWT) behave as expected for protected endpoints.
- Ensure the app runs correctly in a realistic local environment (Docker + PostgreSQL).
- Run automated tests using GitHub Actions on pull request creation and on updates to the same pull request.
- Test UI-level logic, as a significant amount of application logic exists in the frontend layer.

---

## 2. Testing Levels

### 1. Unit Testing (Backend)
- Services (business logic)
- Utility methods
- Mappers (where valuable)

### 2. Integration Testing (Backend)
- Spring Boot context with repositories and database (dedicated test DB)
- Controller / API tests where relevant

### 3. System / End-to-End (E2E) — Mostly Manual
- Run full application (backend + DB + frontend)
- Test key workflows through UI and API

### 4. Frontend Unit Tests
- Automated UI tests for core flows and UI-side logic
- UI tests run in parallel with backend tests
- Backend correctness remains the higher priority

---

## 3. Test Scope

### In Scope (Must Test)

#### Backend
- **Authentication & Authorization**
    - JWT-protected endpoints
    - Unauthorized / forbidden behavior
- **Core Domains**
    - Inventory management (create/update stock, organization scoping)
    - Transactions (purchase/sale entries, transaction history correctness)
    - Price optimization / correction behavior (visible and critical outcomes)
- **Validation & Error Handling**
    - Bad requests
    - Missing IDs
    - Wrong organization access

#### Frontend (Manual)
- Login flow
- Dashboard core screens (inventory, transactions, pricing)
- Page rendering and data correctness spot checks
- UI-level logic related to inventory, transactions, and pricing

### Out of Scope (Due to Time)
- Full security testing beyond basic auth checks
- Load and performance testing
- Full cross-browser / device testing
- Accessibility audit (only quick obvious checks)

---

## 4. Test Approach

### Strategy
- **Risk-based testing**, focusing on:
    1. Service-layer business logic
    2. Authentication and permissions
    3. Inventory and transactions
    4. Pricing outcomes
    5. UI-level logic

- **Test pyramid**
    1. Unit tests per major service method (fast, stable)
    2. Smaller set of integration tests (DB + API)
    3. Manual E2E testing for critical flows

### Automated Testing Guidelines
- Use **JUnit** for unit tests.
- Mock external dependencies (repositories, other services).
- Integration tests run against a controlled test database.
- Automated tests are executed via GitHub Actions:
    - On pull request creation
    - On updates to an existing pull request

### Manual Testing Guidelines
- Maintain a shared checklist of critical flows.
- Focus on:
    - “Can’t break” flows (login, add inventory, record transaction, visible price updates)
    - Permission handling (wrong org / user)
    - Common negative paths (empty fields, invalid values)

---

## 5. Test Environment

### Local Development Environment
- **Backend:** Spring Boot (Java 21), Maven Wrapper
    - `./mvnw test`
    - `./mvnw spring-boot:run`
- **Frontend:** Next.js + TypeScript (`npm run dev`)
- **Database:** PostgreSQL (Docker) with Liquibase migrations
- **Full Stack:** `docker compose up` for backend and DB

### Automated Test Environment
- Separate test database is started for test execution.
- Database schema is created and migrated automatically.
- Test data is seeded before tests.
- Tests always run against the same initial database state.

### Test Data
Seed minimal data for:
- 1–2 organizations
- A few products
- Inventory rows
- Sample transactions

---

## 6. Entry and Exit Criteria

### Entry Criteria
- Backend and frontend build successfully.
- Database starts without errors.
- Key endpoints and routes are available locally.
- Team agrees on must-test flows and ownership.

### Exit Criteria
- All must-test flows executed manually and documented.
- Automated tests implemented for:
    - Core service logic (inventory, transactions, pricing)
    - Basic auth and permission checks
- No open critical bugs (data corruption, auth bypass, broken core flows).
- Known issues documented with severity and workarounds.

---

## 7. Roles and Responsibilities

### 1. Project Manager
- Oversees testing process and timeline.
- Ensures alignment with project goals and deadlines.
- Coordinates frontend and backend communication.
- Reviews test progress and final submission.

### 2. Backend Team
- Implements unit tests for core business logic.
- Performs integration testing for services, repositories, and key REST controllers.
- Verifies authentication, authorization, and error handling.

### 3. Frontend Team
- Performs manual UI and workflow testing.
- Implements automated UI tests for critical flows.
- Verifies frontend–backend API integration.
- Ensures proper rendering and basic usability.

### 4. All Team Members
- Participate in manual E2E testing when needed.
- Report defects with clear reproduction steps.
- Verify bug fixes and perform regression testing.

---

## 8. Risks and Assumptions

### Key Risks
- **Limited time:** Focus on service tests, UI tests for critical flows, and manual E2E.
- **OAuth2 complexity:** Automate JWT-protected APIs; manual OAuth smoke tests.
- **Pricing logic complexity:** Test observable outcomes and high-risk methods.
- **Flaky integration tests:** Use stable, dockerized test database.

### Assumptions
- Team can run the app locally using repository instructions.
- Business rules mainly reside in the service layer (layered architecture).

---

## 9. Test Deliverables

- Test Plan document
- Automated test code in repository:
    - Unit tests for critical services
    - Integration tests for key endpoints and repositories
- Manual test checklist and results
- Test results table:
    - Flow
    - Steps
    - Expected result
    - Actual result
    - Pass/Fail
    - Notes
- Bug / issue list:
    - Severity (Critical / Major / Minor)
    - Reproduction steps
    - Screenshots or logs (if available)
- Simple coverage summary:
    - Number of unit tests added
    - Number of integration tests added

---

## Minimal “Must-Test” Checklist
- Login works and JWT-protected AP
