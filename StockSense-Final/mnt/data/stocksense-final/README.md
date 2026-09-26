# StockSense — Inventory Management System

Full-stack starter/final demo package for StockSense.

## Stack
- Java 21 + Spring Boot 4.1.1
- Spring Security + JWT
- PostgreSQL 18
- React + TypeScript + Vite
- Docker Compose

## Run with Docker (recommended)
1. Install Docker Desktop.
2. Open terminal in this folder.
3. Run: `docker compose up --build`
4. Open frontend: http://localhost:5173
5. Click **Create Demo Manager** once, then login.

## Core API flow
All protected requests need header: `Authorization: Bearer <token>`.

### 1. Register manager
POST `http://localhost:8080/api/auth/register`
```json
{"name":"Vinay","email":"manager@stocksense.local","password":"Password@123","role":"INVENTORY_MANAGER"}
```

### 2. Create warehouse
POST `/api/warehouses`
```json
{"name":"Main Warehouse","code":"WH-01","address":"Main Campus"}
```

### 3. Create location
POST `/api/warehouses/1/locations`
```json
{"name":"Rack A","code":"RACK-A"}
```
Create a second location, e.g. Production Rack.

### 4. Create product
POST `/api/products`
```json
{"name":"Steel Rod","sku":"STL-ROD-001","unitOfMeasure":"KG","minimumStock":50,"reorderQuantity":100}
```

### 5. Receive stock
POST `/api/receipts/validate`
```json
{"productId":1,"locationId":1,"quantity":100,"supplier":"ABC Metals"}
```
Expected total: 100.

### 6. Internal transfer
POST `/api/transfers/validate`
```json
{"productId":1,"sourceLocationId":1,"destinationLocationId":2,"quantity":30}
```
Total remains 100.

### 7. Delivery
POST `/api/deliveries/validate`
```json
{"productId":1,"locationId":2,"quantity":20,"customer":"Customer A"}
```
Total becomes 80.

### 8. Adjustment
If Production location currently contains 10 and 3 are damaged, physical count is 7.
POST `/api/adjustments/validate`
```json
{"productId":1,"locationId":2,"countedQuantity":7,"reason":"Damaged stock"}
```
Total becomes 77.

### 9. Check ledger
GET `/api/movements`

### 10. Dashboard
GET `/api/dashboard/kpis`

## Accuracy / validation already included
- Duplicate SKU blocked
- Negative/zero movement quantities blocked
- Negative physical count blocked
- Delivery greater than available stock blocked
- Transfer greater than available stock blocked
- Same-location transfer blocked
- Adjustment reason required
- Database transaction boundaries for stock operations
- Pessimistic locking + JPA version field to reduce concurrent overselling
- Every stock operation creates stock-movement ledger records
- Passwords hashed with BCrypt
- JWT-protected APIs

## OTP reset
`POST /api/auth/forgot-password` with `{"email":"..."}`. For this free/local package the six-digit OTP is printed in the backend console. Replace the console line with Brevo/Resend API before public deployment.

## Before hackathon final deployment
1. Replace development JWT secret.
2. Replace console OTP with transactional email API.
3. Add persistent Receipt/Delivery/Transfer document tables and statuses (Draft/Waiting/Ready/Done/Canceled).
4. Add role-specific endpoint authorization.
5. Add barcode scanning UI and WebSocket/SSE event push.
6. Add automated integration tests and seed/demo script.
7. Use Supabase/PostgreSQL cloud DB and deploy frontend/backend.

## Demo story
Receive +100 Steel -> Transfer 30 -> Deliver 20 -> Adjustment -3 -> final total 77. Open `/api/movements` to prove the complete ledger.
