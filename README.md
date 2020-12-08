# Sample App (Kotlin)

Project was constructed using `Android Studio 3.6.3` with a `compileSdkVersion` of `29`

## Features
The app has 2 screen that consists of showing a list of Marvel characters and when clicking on the list items it show the details of the character.

## Architecture
The project follows the MVVM (model view view-model) design paradigm.  This approach decouples the view and business logic as well as the data layer into distinct layers.

<img width="622" alt="MMVM Archtiecture" src="https://user-images.githubusercontent.com/8928884/101540386-ec15c400-396d-11eb-9cf3-c8d156b25f2f.png">

## Dependency Injection
Uses [Koin](https://github.com/InsertKoinIO/koin) as a lightwight alternative to `Dagger` to provided dependencies to compoments.  Koin is written in Kotlin and provides a very simple setup to get going.

## Networking
Uses [Retrofit2](https://square.github.io/retrofit/) a well known library for Android

## Unit test
Unit test cover the view model and repository layers and can be ran via Android Studio or commandline `./gradlew test`

