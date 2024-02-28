
  # RecipeBook Android App
  
  ## Introduction
  
  This project is being developed for educational purposes. When creating this project, special attention was paid to architecture, scalability, and user interface. The project is currently under development and new features will be added in the future
  
  ## Tech-Stack
  - Language - All code in the project is written in Kotlin
  - Android Jetpack
      - Jetpack Compose - is the priority framework for building Ui in this project
      - Room - Android architectural component used to work with databases.
      - ViewModel - deaigned to store and manage UI-related data in lifecycle awareness way
      - AndroidX
  - Kotlin Coroutines. Coroutines are used to build the flow of data through the entire application
  - Hilt - framework for DI
  - Retrofit - library for working with API
  - Kotlin Serialization - to convert data to/from json format
  - Glide compose - for working with images in Compose

## Architecture
  - Multi-module architecture
  - A single-activity architecture, using the <b> Jetpack Navigation</b> to manage screens.
  - Pattern Model-View-ViewModel (MVVM)
  - Clean Architecture
  - S.O.L.I.D

  <h2 align="center">Module dependency diagram (for now)</h2>
  <p align="center"><img src = "https://github.com/ComeAYouA/RecipesBook/blob/main/preview/modules.png" width="580" height="465"></p>
 
  <h2 align="center">App preview (for now)</h2>
  <p align="center"><img src = "https://github.com/ComeAYouA/RecipesBook/blob/main/app_preview.gif" width="180" height="400"/></p>
  
 
