# Nutmeg Technical Test
This is an Android client implementations of the Nutmeg technical test.

## Requirements
* Java 8
* Kotlin 1.7
* AGP 7.4

## Build and Run
* ./gradlew assembleDebug
* ./gradlew installDebug

## Description

Integrating Json Placeholder API, showing a list of albums with a thumbnail alongside the title, photo title and username.
    
## Technologies
* Kotlin
* Hilt
* RxJava3
* Retrofit
* ViewModel
* Compose
* Mockk
* Espresso

## Architecture
### Data
#### ```Network```
API level, handling the connection to the `jsonplaceholder` BE service
#### ```Repository```
It's pretty thin in the current format. In the future this could be the place to define any logic between the different datasources (API, DB, local cache etc.)
### Domain
#### ```UseCase```
Currently contains a single usecase which combines the 3 different APIs and collects all the required information in a domian level model 
#### ```Mapper/Transformer```
With future logic, the usecases could easily grow bigger and bigger. It's worth to extract and data transformation logic and test them separately
### UI
#### ```ViewModel```
Requests and handles the albumList results. Exposes any UI change to the Activity with a LiveData which contains a screen specific UI state.
#### ```Layout```
Written in Android Compose

## Screenshot

|<img src="https://user-images.githubusercontent.com/9054730/218536406-6ddddeb5-1859-489b-bcac-b15ca458d759.png" width=300/>|
<img src="https://user-images.githubusercontent.com/9054730/218536797-070ee712-800e-47c9-bfa7-74dacd914993.png" width=300/>|


## Improvements opportunities
* Introducing local caching
* Introducing/Extracting any data transformer logic
* Improving UI design
* Setting up CI/CD
