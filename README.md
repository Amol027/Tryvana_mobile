# Tryvana Mobile

Tryvana is a marketplace application with an Android client and a Node.js/Express backend. It supports authentication, role-based user flows, product management, carts, orders, image uploads, and Google sign-in.

## Tech stack

- Android: Kotlin, Jetpack Compose, Navigation Compose, Retrofit, OkHttp, Coil
- Backend: Node.js, Express, PostgreSQL, JWT, Cloudinary, Multer

## Repository structure

```text
MyApplication/       Android Studio project
trywana-backend/     Express API and PostgreSQL schema
```

## Run the backend

1. Create a PostgreSQL database and load the schema in `trywana-backend/database/schema.sql`.
2. Copy `trywana-backend/.env.example` to `trywana-backend/.env` and set your local values.
3. Start the API:

```powershell
cd trywana-backend
npm ci
npm run dev
```

The development server uses port `8000` unless `PORT` is set.

## Run the Android app

1. Open `MyApplication` in Android Studio.
2. Use JDK 17 and install Android SDK 34.
3. Configure the API base URL in the Android networking layer for your emulator or device.
4. Run the `app` configuration on an emulator or physical device.

## Environment variables

The backend needs PostgreSQL, JWT, Cloudinary, and optional Google sign-in credentials. See [`trywana-backend/.env.example`](trywana-backend/.env.example) for the required names. Never commit `.env` files or production keys.

## Portfolio note

This is a full-stack demonstration project. Add screenshots or a short demo video before publishing to make the Android and API workflows easy to review.
