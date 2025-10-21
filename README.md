
<img width="1080" height="2400" alt="Screenshot_20251021-072744" src="https://github.com/user-attachments/assets/6d46a875-860b-42c7-9156-519e205422b5" />
<img width="1080" height="2400" alt="Screenshot_20251021-072756" src="https://github.com/user-attachments/assets/f5c1da63-0504-49fe-bd68-bb3175a5fbc0" />
<img width="1080" height="2400" alt="Screenshot_20251021-072801" src="https://github.com/user-attachments/assets/bf6e9f54-5c81-404a-a6cf-341fffcc8494" />
<img width="1080" height="2400" alt="Screenshot_20251021-072811" src="https://github.com/user-attachments/assets/a6f8d8a8-916d-420b-9c71-8d24e4e27aaf" />

# Movie & TV Discovery Android App

This is an Android application built for an internship assignment. The app allows users to discover popular movies and TV shows using the Watchmode API. It is built entirely with modern Android development tools, including Jetpack Compose, and follows Clean Architecture principles.

## Features Implemented

This project successfully implements all the requirements outlined in the assignment brief.

### Core Functionality

- **Movie & TV Show Lists:** Fetches and displays separate lists for movies and TV shows on the home screen.
- **Tabbed Navigation:** A simple tab layout on the home screen allows users to switch between the movie and TV show lists.
- **Detailed View:** Clicking on any item navigates to a detailed screen that showcases more information about the selected title.

### Technical Features

- **Modern UI:** The entire user interface is built with **Jetpack Compose**, using Material 3 components.
- **Asynchronous Operations:** Simultaneous network calls for movies and TV shows are handled efficiently using **RxKotlin's Single.zip** as required.
- **Clean Architecture:** The codebase is structured into three distinct layers (data, domain, ui) to ensure a separation of concerns, making the app scalable and testable.
- **MVVM Pattern:** The UI layer follows the Model-View-ViewModel (MVVM) pattern to separate UI controllers from business logic.
- **Dependency Injection:** **Koin** is used for dependency injection to manage object lifecycles and decouple components.
- **Networking:** **Retrofit** is used for type-safe HTTP calls to the Watchmode API.
- **Dynamic Animations:** The details screen features a sophisticated **collapsing toolbar effect**, where the header image shrinks and the title animates into the top app bar on scroll.
- **Loading & Error States:** The app displays a **shimmer loading effect** while fetching data and handles network errors gracefully by showing toasts to the user.

## Architecture

The project follows a simplified Clean Architecture model:

- **/domain:** This layer contains the core business logic and is completely independent of the Android framework. It defines the repository interfaces and the clean data models that the rest of the app uses.
- **/data:** This layer is responsible for providing the data. It contains the implementation of the repository interface, the Retrofit API service, and the network Data Transfer Objects (DTOs).
- **/ui (or /presentation):** This layer contains the ViewModels and the Jetpack Compose UI. It is responsible for observing state from the ViewModels and rendering the UI accordingly.

## Challenges Faced & Solutions

### 1\. Missing Image URLs in the List API

- **Challenge:** The list-titles endpoint from the Watchmode API provides a list of titles but critically **does not include image URLs**. To get an image, a separate API call to the details endpoint is required for every single item.
- **Solution:** Making a network call for every item in a list (a "network storm") would lead to a very slow and poor user experience. The chosen solution was to generate a dynamic placeholder image URL using the placehold.co service. This URL is created in the repository's mapper function using the movie's title. This ensures the home screen loads almost instantly while still providing a visually appealing and informative list. The real poster image is then fetched only when the user navigates to the details screen.

### 2\. Implementing the Collapsing Toolbar Effect

- **Challenge:** Creating a smooth, scroll-based animation where a large header image shrinks and its title transitions into a compact top app bar is complex in Jetpack Compose.
- **Solution:** This was achieved by creating a custom layout in the DetailsScreen. The animation is driven by observing the scroll offset of a LazyColumn. This offset is then used with lerp (linear interpolation) to animate the height, font size, and position of the toolbar elements, creating the desired fluid effect.

## How to Build and Run

- Clone the repository.
- Open the project in a recent version of Android Studio.
- **Important:** Open the WatchmodeApiService.kt file located in app/src/main/java/.../data/api/ and replace the placeholder "YOUR_API_KEY" with your actual API key from Watchmode.
- Let Gradle sync the dependencies.
- Build and run the application on an emulator or a physical device.
