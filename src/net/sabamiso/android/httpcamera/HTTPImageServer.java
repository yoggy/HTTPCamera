package net.sabamiso.android.httpcamera;

import java.io.ByteArrayInputStream;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class HTTPImageServer extends NanoHTTPD {
	Mat image;
	
	public HTTPImageServer(int port) {
		super(port);
		image = new Mat();
	}

	@Override public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        
        System.out.println(method + " '" + uri + "' ");
        if ("/camera.jpg".equals(uri)) {
        	return serveCameraImage(session);
        }
        
        // index.html
        String html = "<html><head><script>function reload(){document.camaera_jpg.src='camera.jpg?t='+new Date().getTime();}function init() {setInterval(reload, 300);}</script></head><body onLoad='init()'><img name='camaera_jpg' src='camera.jpg'/></body><html>";
        return new NanoHTTPD.Response(html);
    }
	
	private Response serveCameraImage(IHTTPSession session) {
		Mat image = getImage();
		if (image.empty()) {
			image.create(new Size(640,  480), CvType.CV_8UC3);
			image.setTo(new Scalar(255, 0, 0));
		}
		
		MatOfInt  params = new MatOfInt(Highgui.IMWRITE_JPEG_QUALITY, 90);
		MatOfByte mat_of_buf = new MatOfByte();
		Highgui.imencode(".jpg", image, mat_of_buf, params);
		byte[] byteArray = mat_of_buf.toArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		
		NanoHTTPD.Response res = new NanoHTTPD.Response(Status.OK, "image/jpeg", bis);
		
		return res;
	}

	public synchronized void setImage(Mat image) {
		image.copyTo(this.image);
	}
	
	public synchronized Mat getImage() {
		Mat tmp = new Mat();
		image.copyTo(tmp);
		return tmp;
	}
}
