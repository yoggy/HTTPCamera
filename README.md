HTTPCamera
================
Simple web camera http server program for Android.

Google Play URL
* https://play.google.com/store/apps/details?id=net.sabamiso.android.httpcamera


Usage
================
1. Install HTTPCamera on your Android smartphone.
  * [HTTPCamera](https://play.google.com/store/apps/details?id=net.sabamiso.android.httpcamera) (Google Play)

2. Setup the Wi-Fi configuration on your Android smartphone in order to connect to the PC through the Wi-Fi network.

3. Launch HTTPCamera on your Android smartphone.

4. Access the HTTPCamera from PC using web browser.
  * URL is displayed on the screen of the HTTPCamera.

for Processing
================
sample client sketch for processing.

<pre>
  void setup() {
    size(640, 480);
  }
  
  void draw() {
    PImage img = loadImage("http://192.168.1.101/camera.jpg");
    if (img != null) {
      image(img, 0, 0, width, height);
    }
  }
</pre>

Libraries
================
HTTPCamera uses the following libraries.

OpenCV
* http://opencv.org/

NanoHTTPD
* https://github.com/elonen/nanohttpd
