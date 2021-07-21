# WeatherApp with Retrofit and kotlin
<b>Implementation of Weather Live app by using kotlin </b>
<br>
<ol type="1">
<li>
First you have to check the API response thoroughly<br>
</li>
</li>
<Li>
  Identify the structure..
<li>
Generally it will be either @GET or @Post HTTP request method
  <ul>
<li>
@GET : you can see both key & value in the URL
  </li>
    <li>
@Post : you cant see key or value, it will be send as body
      </li></ul>
</li>
<li>
In the @GET or @Post HTTP request method there can be different kind of parameters pass inside the method
<ul>
<li>
@Body – Sends Java objects as request body.
</li>
<li>
@PATH – api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
</li>
<li>
@Url – use dynamic URLs.
</li>
<li>
@Query – We can simply add a method parameter with @Query() and a query parameter name, describing the type. To URL encode a query use the form: @Query(value = "auth_token",encoded = true) String auth_token – api.openweathermap.org/data/2.5/weather?
</li>
<li>
@Field – send data as form-urlencoded. This requires a @FormUrlEncoded annotation attached with the method. The @Field parameter works only with a POST
</li>
</ul>
</li>
<li>
Retrofit automatically serialises the JSON response using a POJO(Plain Old Java Object) which must be defined in advanced for the JSON Structure. To serialise JSON we need a converter to convert it into Gson first. 
</li>
<li>
Gson : <ul>
<li>
Json is a FORMAT.
  </li>
<li>
We need convert our data when it comes through Api so
Object ---> Json OR Json ---> object(POJO) so thats why we need Gson.
  </li>
  </ul>
  
   --------------Flow of MVP Works--------------
   * MODEL :
   * In Android, the role of a model is usually played by a data access layer
   * such as database API or REST API.
   *
   * VIEW :
   * View is basically a passive interface cum UI which is
   * responsible for routing user’s action to the presenter.
   * In Android, View can be your Activity, fragment or RecyclerView Adapters.
   * In general, View is not visible to your Model except
   * for the POJOS and entities of your application.
   * To put in more simpler terms, Views do not communicate directly to Models.
   * However, they talk to presenters.
   *
   PRESENTER :
   * 1. In MVP the presenter assumes the functionality of the "middle-man".
   * All presentation logic is pushed to the presenter.
   * 2. Listens to user action and model updates
   * 3. Updates model and view
   * 4. Presenter is the middleman or mediator between View and Model
   * which hold responsibilities of everything which has to deal
   * with presentation logic in your application.
   * In general terms, Presenter does the job of
   * querying your Model, updating the View while responding to the user’s interactions.
   * It monitors Model and talks to View so that
   * they can handle when a particular View needs to be updated and when to not.
   *
   Business logic should be part of presenter
    * Presenter: This is the brain of the app. All the business logic is kept here. It is responsible for keeping the view and model away from each other. View and Model can only talk through the Presenter. The view-model interaction can be:
       The view is requesting data from the model to show to the user.
       The model is updated in the background and now the view needs to be updated to show the latest information
       The view needs to update the model according to the user interaction.
       Presenter prides itself to handle all of this!!
</ol>

