# cordova-plugin-raygun-native
iOS and Android native side integration of Raygun for cordova projects.

**AgriWebb fork updates version of SDKs**

## SETUP

to set your android api use the plugin variable when adding to your project
    cordova plugin add cordova-plugin-raygun-native --variable ANDROID_API_KEY="YOUR_API_KEY_HERE"

### iOS

Require cordova iOS engine 4.3.0 and Cordova 6.5.0

For iOS pass in your API via the JS Interface during/after the device ready callback
    RaygunNativePlugin.startNativeRaygun('user_email_address', 'YOUR_API_KEY_HERE');


Click your project in Xcode, and then select your main app target. Go to the "Build Settings" tab, search for "Other Linker Flags" and add -lc++.
