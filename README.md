<!DOCTYPE html>
<html lang="en">

  <h1 align="center">RecipeBook Android App</h1>

  <br/>
  <hr/>
  <h2>Introduction</h2>
  
  <h3>This project is being developed for educational purposes. 
When creating this project, special attention was paid to architecture, scalability, and user interface. 
The project is currently under development and new features will be added in the future.</h3>
  
  <hr/> 
  <h2>Tech-Stack</h2>
  <ul>
  	<li>
      <h3>Language - All code in the project is written in Kotlin</h3>
    </li>
  	<li>
      <h3>Android Jetpack</h3>
      	<ul>
          <li><h3>Jetpack Compose - is the priority framework for building Ui in this project</h3></li>
          <li><h3>Room - Android architectural component used to work with databases.</h3></li>
          <li><h3>ViewModel - deaigned to store and manage UI-related data in lifecycle awareness way</h3></li>
          <li><h3>AndroidX</h3></li>
        </ul>
    </li>
    <li><h3>Kotlin Coroutines. 
Coroutines are used to build the flow of data through the entire application</h3></li>
    <li><h3>Hilt - framework for DI</h3></li>
    <li><h3>Retrofit - 
library for working with API</h3></li>
    <li><h3>Kotlin Serialization - 
to convert data to/from json format</h3></li>
    <li><h3>Glide compose - 
for working with images in Compose</h3></li>
  </ul>
    <hr/>

<h2>Architecture</h2>
  <ul>
      <li>
      <h3>Multi-module architecture</h3>
    </li>
  	<li>
      <h3>A single-activity architecture, using the <b> Jetpack Navigation</b> to manage screens.</h3>
    </li>
  	<li>
      <h3>Pattern Model-View-ViewModel (MVVM)</h3>
    </li>
    <li>
      <h3>Clean Architecture</h3>
    </li>
    <li>
      <h3>S.O.L.I.D</h3>
    </li>
    
  </ul>
    <hr/>
  
  <h2 align="center">Module dependency diagram</h2>
  <img src = "https://github.com/ComeAYouA/RecipesBook/blob/main/preview/modules.png" />
 
  <hr/>
  
  <h2 align="center">App preview</h2>
  <img src = "https://github.com/ComeAYouA/RecipesBook/blob/main/app_preview.gif" />
  
  
</html>
 
