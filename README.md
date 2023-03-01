# Hyperlocal Weather
Local Weather app using Compose

[<img src="https://user-images.githubusercontent.com/5157474/220836110-eface564-3e0c-4874-be6d-28968b5d24f0.png" width="350"/>](https://user-images.githubusercontent.com/5157474/220836110-eface564-3e0c-4874-be6d-28968b5d24f0.png)

- Allows user to get local weathercast from their current GPS location
- Shows Hourly forecast for next 24 hours
- Uses [https://open-meteo.com](https://open-meteo.com) api

[Google Store Presence for Hyperlocal Weather app](https://play.google.com/store/apps/details?id=com.realityexpander.hyperlocalweather)

# Tech used
- Retrofit for API calls and Moshi deserialization
- Shows proper use of `Dagger-Hilt` & SOLID/CLEAN architecture
- Shows proper handling of permissions dialogs
- Uses compose for view layer, `ViewModel` and `mutableState` to send UI events
- Uses Resource sealed class to handle errors, messaging, UI status
- Accesses GPS location, converts callbacks to coroutines

To install the Apk:

1. Open this link on your Android device:
   https://github.com/realityexpander/WeatherHere/blob/master/weatherhere_1.0.apk
2. Tap the "skewer" menu and tap the "download"

   [![](https://user-images.githubusercontent.com/5157474/147434050-57102a30-af32-46ed-a90b-d94e0c4a4f35.jpg)]()
3. Allow the file to download (DO NOT click "show details")
4. After the file is downloaded, click "OK" to install
5. Click "OK" to install
6. Click "OK" to launch

If you have developer options turned on, you may need to turn off "USB Debugging" if the "Waiting for debugger" dialog is displayed.
