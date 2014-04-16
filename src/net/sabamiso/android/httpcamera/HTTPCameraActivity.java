package net.sabamiso.android.httpcamera;

import java.io.IOException;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class HTTPCameraActivity extends Activity implements
		CameraPreviewListener {

	OverlayView overlay_view;
	CameraPreviewView preview_view;
	HTTPImageServer http_image_server;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		super.onCreate(savedInstanceState);

		OpenCVLoader.initDebug();

		RelativeLayout layout = new RelativeLayout(this);
		setContentView(layout);

		@SuppressWarnings("deprecation")
		int fp = ViewGroup.LayoutParams.FILL_PARENT;

		overlay_view = new OverlayView(this);
		preview_view = new CameraPreviewView(this, 640, 480, this);
		layout.addView(preview_view, new RelativeLayout.LayoutParams(fp, fp));

		layout.addView(overlay_view, new RelativeLayout.LayoutParams(fp, fp));

		http_image_server = new HTTPImageServer(8080);
	}

	@Override
	protected void onResume() {
		super.onResume();
		overlay_view.start();
		try {
			http_image_server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		http_image_server.stop();
		overlay_view.stop();
		preview_view.stop();
		super.onPause();
		finish();
	}

	@Override
	public void onPreviewFrame(Mat image) {
		if (image == null || image.empty() == true)
			return;
		
		http_image_server.setImage(image);
	}
}
