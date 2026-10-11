# LifeOS Student - Full Stack Architecture & Deployment

A unified, full-stack productivity and learning platform designed for students. Powered by Spring Boot 3.5, Java 21, React 19 (Vite), PostgreSQL (Neon), and Multi-LLM AI capabilities (Google Gemini & Groq).

---

## 📁 Repository Layout

```
LifeOS-Student/
├── .mvn/                     # Maven wrapper directory
├── frontend/                 # React 19 + Vite frontend
│   ├── public/               # Static assets & SVG icons
│   ├── src/                  # React components, pages, context, and services
│   ├── .env.example          # Frontend environment variables template
│   ├── package.json          # Frontend dependencies and scripts
│   ├── vercel.json           # Vercel SPA routing and configuration
│   └── vite.config.js        # Vite configuration
├── src/                      # Spring Boot 3.5 backend
│   ├── main/
│   │   ├── java/             # Controllers, Services, Entities, Repositories, AI Providers
│   │   └── resources/
│   │       └── application.properties # Parameterized 12-factor cloud properties
│   └── test/                 # Spring Boot application context tests
├── database/                 # Database migrations and SQL scripts (.gitkeep)
├── docs/                     # Architectural and operational documentation (.gitkeep)
├── uploads/                  # Uploaded documents and storage (.gitkeep)
├── .gitattributes            # Line ending normalization (LF for mvnw)
├── .gitignore                # Comprehensive Git ignore rules
├── mvnw                      # Maven wrapper executable (chmod +x)
├── mvnw.cmd                  # Windows Maven wrapper script
├── pom.xml                   # Maven project object model (Java 21, Spring Boot 3.5)
├── render.yaml               # Render Blueprint for automated backend deployment
└── README.md                 # Project documentation
```

---

## 🛠️ Tech Stack & Requirements

- **Backend**: Spring Boot `3.5.6`, Java `21`, Maven `3.9+`, Spring Security + JWT, Spring Data JPA, Hibernate ORM `6.6`.
- **Database**: PostgreSQL `18.6` hosted on **Neon Tech** (Serverless PostgreSQL with SSL required).
- **Frontend**: React `19`, Vite `6`, Tailwind CSS, Lucide React, Axios, React Router `7`.
- **AI Integration**: Google Gemini API & Groq API.
- **Cloud Infrastructure**: Render (Backend Web Service) & Vercel (Frontend SPA Hosting).

---

## 🚀 Local Development Setup

### 1. Prerequisites
Ensure you have the following installed:
- Java Development Kit (JDK) 21+ (`java -version`)
- Node.js 20+ and npm (`node -v`, `npm -v`)
- Git (`git --version`)

### 2. Backend Setup
From the repository root:

```bash
# Verify database connection and run tests
./mvnw clean test

# Run Spring Boot application (defaults to port 8080)
./mvnw spring-boot:run
```
*(On Windows PowerShell: `.\mvnw.cmd spring-boot:run`)*

The backend will start and connect to the Neon PostgreSQL database at:
`http://localhost:8080`

### 3. Frontend Setup
In a separate terminal:

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start at:
`http://localhost:5173`

---

## ☁️ Deployment Instructions

### 1. Backend Deployment (Render)

The repository includes `render.yaml` which automatically configures the web service on Render:

1. Log into your [Render Dashboard](https://dashboard.render.com/).
2. Click **New +** -> **Blueprint**.
3. Connect the repository `SupratikVarun/lifeos-student`.
4. Render will parse `render.yaml` with the following configuration:
   - **Environment**: Java
   - **Java Version**: `21`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -Dserver.port=$PORT -jar target/lifeos-student-backend-0.0.1-SNAPSHOT.jar`
5. Ensure the following Environment Variables are configured (pre-configured in `render.yaml`):
   - `SPRING_DATASOURCE_URL`: `jdbc:postgresql://ep-lingering-hall-b322ohgn.c-4.ap-southeast-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require`
   - `SPRING_DATASOURCE_USERNAME`: `neondb_owner`
   - `SPRING_DATASOURCE_PASSWORD`: `npg_fx9UbahykCg6`
   - `JWT_SECRET`: `LifeOSStudentBackendSecretKeyForJwtAuthentication2026LifeOSStudent`
   - `JWT_EXPIRATION`: `86400000`
   - `GEMINI_API_KEY`: *(Your Google Gemini API Key)*
   - `GROQ_API_KEY`: *(Your Groq API Key)*
   - `FILE_UPLOAD_DIR`: `uploads/`
6. Click **Apply**. Once built, note your live backend URL (e.g. `https://lifeos-student-backend.onrender.com`).

---

### 2. Frontend Deployment (Vercel)

1. Log into your [Vercel Dashboard](https://vercel.com/dashboard).
2. Click **Add New...** -> **Project**.
3. Import the `SupratikVarun/lifeos-student` repository.
4. In the **Project Settings**:
   - **Root Directory**: `frontend` (Click *Edit* and select the `frontend` folder).
   - **Framework Preset**: `Vite`.
   - **Build Command**: `npm run build` (auto-detected).
   - **Output Directory**: `dist` (auto-detected).
5. In the **Environment Variables** section, add:
   - `VITE_BACKEND_URL`: `https://lifeos-student-backend.onrender.com` (replace with your live Render backend URL).
   - `VITE_API_URL`: `https://lifeos-student-backend.onrender.com/api`
6. Click **Deploy**. Vercel will build and serve your frontend globally.

---

## 🔒 Security & CORS

- When deploying to production, all requests from the frontend to `/api/*` are proxied or dispatched with JWT authentication headers (`Bearer <token>`).
- Database connections use TLS/SSL enforced (`sslmode=require&channelBinding=require`).