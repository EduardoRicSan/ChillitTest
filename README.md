# ChillitTasks

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.10-blue?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-purple?logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28-orange?logo=firebase&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-green)

**ChillitTasks** is an Android application for task management with user authentication, Firestore synchronization, and local persistence using Room. The app follows **Clean Architecture** and the **MVVM** pattern, and is built entirely with **Jetpack Compose** for modern UI.

---

## 📌 Features

- User authentication (registration and login) via **Firebase Authentication**.
- Task creation and updates synchronized with **Firestore**.
- Offline support: tasks are stored locally with **Room** and synced when online.
- Modern and responsive UI with **Jetpack Compose**.
- Network state handling using `NetworkResult` (`Idle`, `Loading`, `Success`, `Error`).
- Clean and modularized architecture for scalability and maintainability.
- Colored status indicators for tasks: Pending, In Progress, Done.
- Secure logout and state management.

---

## 🏗 Architecture

The project follows **Clean Architecture**:
chillittasks/
├─ app/ # UI, navigation, Compose screens and ViewModels
├─ core/ # Shared utilities, network helpers
├─ data/ # Repository implementations, Room, Firebase
├─ domain/ # Models, UseCases, repository interfaces

### Patterns & Principles

- **MVVM**: Separation of UI and business logic.
- **Repository Pattern**: Unified data access layer.
- **Clean Architecture**: Decoupled domain, data, and UI layers.
- **Dependency Injection**: Hilt for managing dependencies.
- **Reactive programming**: Coroutines + Flow for async operations.

---

## 🛠 Technologies

- **Kotlin**  
- **Jetpack Compose** (Material3, Cards, Buttons)  
- **Firebase Authentication & Firestore**  
- **Room** for local persistence  
- **Hilt** for dependency injection  
- **Coroutines + Flow**  
- **Kotlin Serialization** for DTOs

 🚀 Usage

Register or login with email and password.

Add tasks with title and description.

Tap tasks to cycle through statuses: PENDING → IN_PROGRESS → DONE → PENDING.

Logout using the bottom button.

Offline tasks are saved locally and synced automatically when online.

🖌 UI

Tasks displayed as cards with rounded corners, shadows, and color-coded status.

Modern inputs, buttons, and loading indicators using Material3.

Smooth, responsive, and consistent design across devices.

📈 Network & Offline Handling

NetworkResult ensures consistent state management:

Idle → no operation

Loading → shows loader

Success → data updated

Error → displays error message

Offline support: all tasks created offline are stored locally and synced later.

