# Salle

#### Video Demo:  
https://youtu.be/hJTyXA6EhME?si=YZLmTZL24zPRl7xR

#### Description:  
A simple Android Mobile App which allows you to store your workouts and track your progress. This project has a MVVM architecture and was wtitten in Kotlin, using Jetpack Compose and the Room persistance library.

## Overview

The project has two main screens `Routines` and `Activity` that are reachable via a bottom Navigation Bar.

* `Routines` contains a list of the different workouts that the user has saved, each workout can be edited, deleted and played  
* `Activity` contains a log with the workouts that have been completed, alongside a counter that displays how many times the user has worked out through the year

## Navigation

For this project I used Type-Safe navigation, most of the code related to navigation can be found in the `navigation` folder.

* `NavGraph.kt` contains the navigation graph for this app. It has two main nested graphs which represent the two main screens, each one has their sub routes.  
* `navBarDestinations.kt` defines the top level routes of the application, which will be useful to create the bottom navigation bar  
* `Routes.kt` defines the routes of the app by using serializable objects and data classes (for type-safe navigation), which can take parameters like in the case of RoutineEdit or RoutinePlay

## Ui Layer

The `ui` folder contains the code related to the Ui layer of the app.

### `theme`

Contains `Color.kt`, `Theme.kt` and `Type.kt` which define how the app is going to look aesthetically(color palette, typography, etc.). They were genereated using [Matherial Theme Builder](https://m3.material.io/blog/material-theme-builder).

### `routine`

Contains all the code related to the RoutineNestedGraph routes ui elements and viewModels.

* `RoutineHomeScreen.kt` is the first page that the users sees when they first open the app. If the user has saved no workout, the text "No routines" is showed, else a lazy column containing RoutineWithExercisesItem is displayed. A RoutineWithExercisesItem consists of a Card composable that contains the routine name, it's exercises and three buttons: Play, Edit and Delete. This page also shows a Floating Action Button that allows the user to add a new Routine. `RoutineHomeViewModel.kt` contains the UiState - which is list of RoutineWithExercises objects - and the viewModel, that acceses the two repositories. It is to note that a Flow is used to collect the information from the database, that way if the information changes the Ui will be updated accordingly  
* When the user presses the FAB, `RoutineEntryScreen.kt` appears, which contains a form that allows the user to input a workout and up to six exercises. `RoutineEntryViewModel.kt contains the Ui state, helper functions to parse the search results from the database, input validation, and methods to add/delete exercises and save new workouts  
* `RoutineEditScreen.kt` appears when the user presses the Edit button of one of the routines, the routineId is passed as a navigation argument and in `RoutineEditViewModel.kt` the id is used to load into the form the routine name and its exercises. Overall, this screen is very similar to `RoutineEntryScreen.kt` and reuses most of its code  
* `RoutinePlayScreen` shows the routine and its exercises along with checkboxes for each one. These checkboxes represent the number of sets of each exercise, and are meant to be used while the using is working out so he can keep track of his progress. Once all sets have been completed, the user can log this routine into the History table so that he can mark the routine as completed on a specific day. `RoutinePlayViewModel` uses the routineId to fetch the information from the workout and generate the necessary checkboxes that are represented as a boolean list in the Ui State.

### `activity`

Contains all the code related to the ActivitiesNestedGraph routes ui elements and viewModels.

* `ActivityScreen.kt` displays a Lazy Column with the last 10 completed routines, as well as a message that counts the total number of times that the user has trained on the year belonging to the last added log. `ActivityViewModel` fetches this information as a Flow and uses the method countDays to calculate the daysCounter in the UiState.

## Data Layer

The `data` folder contains the code related to the Data layer of the app

### `model`

This folder contains different data classes like `Exercise`, `Routine`, `History`, etc. that represent the tables of the app database - they are marked with the @Entity annotation. Other classes like `RoutineWithExercises` are used to fetch information from the database when there is a relation between two tables (like `Routine` and `Exercise`).

### `repositories`

This folder contains the different interfaces like `ExercisesRepository`, `HistoryRepository`, etc. that are used by `OfflineExercisesRepository`, `OfflineHistoryRepository`, etc. that allow the app to interact with the database. The repositories are put in a container to make them available to the whole app. `RoutineDao` contains and interface that is marked as a Data Access Object and used by Room to allow interactions with the database.
