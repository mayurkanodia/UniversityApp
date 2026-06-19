# FoodFlow 🍔

FoodFlow is a modular Android food ordering application built using modern Android development best practices.

The project demonstrates scalable architecture, clean separation of concerns, dependency injection, offline-first support, and testability.

---

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Clean Architecture
- Hilt Dependency Injection
- Retrofit
- OkHttp
- Room Database
- Kotlin Coroutines + Flow
- WorkManager
- MockK
- JUnit

---

## Architecture

This project follows:

- MVVM
- Clean Architecture
- Multi-module architecture

Flow:

UI → ViewModel → UseCase → Repository → API / Database

---

## Project Structure

```text
FoodFlow
│
├── app
│
├── core-common
├── core-ui
├── core-network
├── core-database
├── core-security
├── core-testing
│
├── feature-auth
├── feature-home
├── feature-cart
├── feature-orders
│
└── sync
```

## Module Responsibilities

### core-common
Shared utilities, result wrappers, constants, dispatcher abstraction.

### core-ui
Shared UI components, theme, strings, reusable Compose utilities.

### core-network
Retrofit setup, API services, interceptors, safe API handling.

### core-database
Room database setup, DAOs, entities.

### core-security
Token management and secure storage.

### core-testing
Reusable testing utilities:
- MainDispatcherRule
- TestDispatcherProvider

### feature-auth
Authentication flow.

### feature-home
Product listing, refresh sync, offline cache.

### feature-cart
Cart management.

### feature-orders
Order history and order flow.

### sync
Background sync using WorkManager.

---

## Features

- User authentication
- Product listing
- Product detail screen
- Offline-first caching
- Background periodic sync
- Error handling
- Loading states
- Modular architecture
- Unit testing support

---

## Setup

### Clone project

```bash
git clone <repo-url>
```

### API Config

Create `local.properties`

```properties
API_KEY=your_api_key
BASE_URL=https://dummyjson.com/
```

### Build

```bash
./gradlew build
```

### Run

Open project in Android Studio and run the `app` module.

---

## Testing

Run unit tests:

```bash
./gradlew test
```

Includes:

- Repository tests
- ViewModel tests
- Coroutine dispatcher testing support

---

## Design Decisions

### Why DispatcherProvider?
Improves coroutine testability by avoiding hardcoded dispatchers.

### Why Multi-module Architecture?
Improves scalability, separation of concerns, and maintainability.

### Why WorkManager?
Reliable background syncing for periodic and offline updates.

---

## Future Improvements

- UI polish
- Pagination
- Search & filters
- Better error modeling
- Integration tests
- Compose UI tests

---

## Author

Mayur