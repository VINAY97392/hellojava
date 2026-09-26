services:
  db:
    image: postgres:18
    environment:
      POSTGRES_DB: stocksense
      POSTGRES_USER: stocksense
      POSTGRES_PASSWORD: stocksense
    ports: ["5432:5432"]
    volumes: ["stocksense_db:/var/lib/postgresql/data"]
  backend:
    build: ./stocksense-backend
    environment:
      DB_URL: jdbc:postgresql://db:5432/stocksense
      DB_USER: stocksense
      DB_PASSWORD: stocksense
      JWT_SECRET: stocksense-super-secret-development-key-please-change-2026
    ports: ["8080:8080"]
    depends_on: [db]
  frontend:
    build:
      context: ./stocksense-frontend
      args:
        VITE_API_URL: http://localhost:8080/api
    ports: ["5173:80"]
    depends_on: [backend]
volumes:
  stocksense_db:
