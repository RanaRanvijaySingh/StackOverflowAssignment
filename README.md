# StackOverflow Assignment

Android app that lists Stack Overflow users using the Stack Exchange API. The codebase follows **Clean Architecture** with **MVVM** on the presentation layer and **Hilt** for dependency injection.

## Architecture overview

The app is split into three main layers. Dependencies point **inward**: presentation and data both depend on the domain; the domain does not depend on Android, Retrofit, or Room.

| Layer | Role | Key pieces |
|--------|------|------------|
| **Presentation** | UI and screen logic | Jetpack Compose, `MainActivity`, `HomeViewModel`, `UserUiState` |
| **Domain** | Business rules and contracts | `User`, `UserRepository` interface, use cases (`GetStoredUsersUseCase`, `GetUsersUseCase`, `SetUserFollowingUseCase`) |
| **Data** | API, database, mappers, repository implementation | `StackOverflowApiService`, `UserRepositoryImpl`, `UserDao`, `UserEntity`, DTOs, `UserMapper` |

**MVVM mapping**

- **Model** — domain `User` plus repository/use cases that load and update data.
- **View** — Composable screens (`HomePageView`, `UserListView`, …) that observe state and forward events.
- **ViewModel** — `HomeViewModel` holds `UserUiState`, coordinates use cases, and survives configuration changes.

## Dependency injection

- **`@HiltAndroidApp`** — `StackOverflowApplication`
- **`@AndroidEntryPoint`** — `MainActivity`
- **`@HiltViewModel`** — `HomeViewModel` (use cases injected automatically)
- **Modules** — `NetworkModule` (Retrofit, OkHttp, API service), `DatabaseModule` (Room), `AppModule` (repository binding)

## Data flow

### 1. Opening the app (user list)

1. **`HomeViewModel`** starts **`loadUsers()`** on init.
2. **`GetStoredUsersUseCase`** reads the local database via **`UserRepository.getStoredUsers()`** (Room → `UserEntity` → domain `User` through **`UserMapper.toUiModel()`**).
3. **If the database has rows** — UI immediately shows **`UserUiState.Success`** with that list (no blocking loading screen for cached data).
4. **If the database is empty** — UI shows **`UserUiState.Loading`** until the remote step finishes.
5. **`GetUsersUseCase`** calls **`UserRepository.syncUsersFromRemote()`**:
   - HTTP request through **`StackOverflowApiService`**
   - On success: merge **follow** flags from existing DB rows, replace table contents with the new API page, then read back from Room
   - On failure: DB is not cleared; the method still returns whatever is currently stored
6. ViewModel sets **`UserUiState.Success`** with the latest list from the repository.
7. If sync throws and there was **no** successful cached list, the UI moves to **`UserUiState.Error`** (retry uses the same `loadUsers()` path).

### 2. Follow / unfollow

1. **`UserListItemView`** invokes **`HomeViewModel.onFollowClick(user)`**.
2. **`SetUserFollowingUseCase`** calls **`UserRepository.setUserFollowing(userId, isFollowing)`**.
3. **`UserRepositoryImpl`** runs **`UserDao.updateFollowing(...)`** so the flag persists in Room.
4. The ViewModel updates **`UserUiState.Success`** by mapping the list with the toggled user so the list reflects the new state immediately.

### 3. Mapping between shapes

| Direction | Function(s) |
|-----------|-------------|
| API DTO → domain | `UserDto.toDomain()` |
| Domain → Room | `User.toEntity()` |
| Room → domain (UI) | `UserEntity.toUiModel()` |

Remote and local types stay in the **data** layer; the UI and use cases work with **domain `User`** only.

## Tech stack (high level)

- Kotlin, Jetpack Compose, Material 3  
- Hilt, Room, Retrofit, OkHttp, Gson  
- Kotlin Coroutines / Flow (e.g. `UserDao.observeUsers()` for future reactive reads)  
- Unit tests with JUnit, fakes, and `kotlinx-coroutines-test` (e.g. `MainDispatcherRule` for `viewModelScope`)
