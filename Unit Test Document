# Unit Test Cases for Movie & TV Discovery App

This document outlines the unit test cases for the ViewModels in the application. The goal is to ensure the business logic is correct and the UI state is managed properly under various conditions.

### Test Cases for HomeViewModel

The HomeViewModel is responsible for fetching the list of movies and TV shows and managing the home screen's UI state.

#### Test Case 1: Successful Data Fetch

- **Given:** The TitlesRepository is mocked to return a successful response with a list of movies and a list of TV shows.
- **When:** The HomeViewModel is initialized (which calls fetchTitles).
- **Then:**
  - The initial uiState should be HomeUiState.Loading.
  - The final uiState should become HomeUiState.Success.
  - The movies list in the success state should match the mocked movie data.
  - The tvShows list in the success state should match the mocked TV show data.

#### Test Case 2: Network Error on Fetch

- **Given:** The TitlesRepository is mocked to return an error (e.g., a network exception).
- **When:** The HomeViewModel is initialized.
- **Then:**
  - The initial uiState should be HomeUiState.Loading.
  - The final uiState should become HomeUiState.Error.
  - The error state should contain an appropriate error message.

#### Test Case 3: Empty Lists Returned

- **Given:** The TitlesRepository is mocked to return a successful response but with empty lists for both movies and TV shows.
- **When:** The HomeViewModel is initialized.
- **Then:**
  - The final uiState should be HomeUiState.Success.
  - The movies list in the success state should be empty.
  - The tvShows list in the success state should be empty.

### Test Cases for DetailsViewModel

The DetailsViewModel is responsible for fetching the detailed information for a single title.

#### Test Case 1: Successful Details Fetch

- **Given:** A valid titleId is passed to the ViewModel, and the TitlesRepository is mocked to return a successful response with full title details.
- **When:** The DetailsViewModel is initialized.
- **Then:**
  - The initial uiState should be DetailsUiState.Loading.
  - The final uiState should become DetailsUiState.Success.
  - The details object in the success state should match the mocked details data.

#### Test Case 2: Network Error on Details Fetch

- **Given:** A valid titleId is passed, but the TitlesRepository is mocked to return an error.
- **When:** The DetailsViewModel is initialized.
- **Then:**
  - The initial uiState should be DetailsUiState.Loading.
  - The final uiState should become DetailsUiState.Error.
  - The error state should contain an appropriate error message.

#### Test Case 3: Invalid ID (API returns not found)

- **Given:** An invalid titleId is passed, and the TitlesRepository is mocked to return an error indicating the title was not found (e.g., a 404 error).
- **When:** The DetailsViewModel is initialized.
- **Then:**
  - The final uiState should be DetailsUiState.Error with a "Title not found" message.
