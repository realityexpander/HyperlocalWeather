# WeatherHere
Local Weather app using Compose

[<img src="https://user-images.githubusercontent.com/5157474/181176637-b44aabd0-e8e8-4593-a7c0-861c50d461da.png" width="350"/>](https://user-images.githubusercontent.com/5157474/181176637-b44aabd0-e8e8-4593-a7c0-861c50d461da.png)


- Allows user to get local weathercast from his current GPS location
- Shows Hourly forecast for next 24 hours
- Uses `https://open-meteo.com` api

# Tech used
- Retrofit for API calls and GSON & Kotlinx deserialization
- Shows proper use of `Dagger-Hilt` & SOLID/CLEAN architecture
- Uses compose for view layer, `ViewModel` and `mutableState` to send UI events
- Uses Resource sealed class to handle errors, messaging, UI status
- Accesses GPS location, converts callbacks to coroutines
- 

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
