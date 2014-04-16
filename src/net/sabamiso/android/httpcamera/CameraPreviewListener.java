package net.sabamiso.android.httpcamera;

import org.opencv.core.Mat;

public interface CameraPreviewListener {
	void onPreviewFrame(Mat image);
}
