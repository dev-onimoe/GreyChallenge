# GreyChallenge
This repository has the source code of the android development challenge from Grey.

It is a native android project written primarily in Kotlin and Jetpack Compose, it uses MVVM and clean architecture , view model for data mediation and no network layer because data is stored on the local storage (SharedPreferences). 

The data module provides a clean, reactive way to persist and restore a list of PathStop objects using local storage, while keeping a proper separation of concerns between UI, ViewModel, repository, and Android-specific code.

It uses:

- SharedPreferences for persistence

- Gson for serialization

- StateFlow for reactive state updates

- A ViewModel that loads data automatically on initialization.


The goal is to persist a List<PathStop> locally and expose it reactively to the UI.

Key guarantees:

Stored data is overwritten if the key already exists

If no data exists, an empty list is emitted

The UI automatically updates when data finishes loading

Context is never leaked into the ViewModel or repository layers.

##Demo


https://github.com/user-attachments/assets/f96a01f2-48cd-480e-8130-9bb3ad0d6d28




This project can be improved by using specific DI libraries if it increases in size and files, it could also potentially adopt a remote data storage system without data having to be generated locally.


