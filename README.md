# ChillitTasks

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.10-blue?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-purple?logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28-orange?logo=firebase&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-green)

**ChillitTasks** is an Android application for task management with user authentication, Firestore synchronization, and local persistence using Room. The app follows **Clean Architecture** and the **MVVM** pattern, and is built entirely with **Jetpack Compose** for modern UI.

---

## 📌 Architecture

The project follows **Clean Architecture** and **MVVM**:

- **Presentation layer**: Jetpack Compose screens and ViewModels handle UI and state.
- **Domain layer**: Models, UseCases, and repository interfaces manage business logic.
- **Data layer**: Repository implementations, Room for local persistence, and Firebase Firestore for remote data.

This separation ensures maintainable, testable, and scalable code.

---

## ⚙️ Key Technical Decisions

- **MVVM + Clean Architecture** for clear separation of concerns.
- **Room + Firestore** to support offline-first functionality.
- **Hilt** for dependency injection across modules.
- **Coroutines + Flow** for asynchronous and reactive data handling.
- **NetworkResult** to unify network state (`Idle`, `Loading`, `Success`, `Error`).
- **Jetpack Compose** for declarative, responsive UI components.

---
