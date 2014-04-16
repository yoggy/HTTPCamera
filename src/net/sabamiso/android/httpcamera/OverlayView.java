package net.sabamiso.android.httpcamera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.view.View;

public class OverlayView extends View implements Runnable {

	Handler handler = new Handler();

	public OverlayView(Context context) {
		super(context);
	}

	public void start() {
		handler.post(this);
	}

	public void stop() {
		handler.removeCallbacks(this);
	}

	@Override
	public void run() {
		invalidate();
		handler.postDelayed(this, 500);
	}

	public void updateHandler() {
		handler.post(this);
	}

	public void onDraw(Canvas canvas) {
		drawText(canvas, "HTTPCamera", 10, 40, 36);
		
		String url_str = "URL = ";
		String addr = getIpAddress();
		if (addr.contains("ERROR")) {
			url_str += "ERROR: please check wifi connection settings...";
		}
		else {
			url_str += "http://" + addr + ":8080/";
		}
		
		drawText(canvas, url_str, 10, 80, 36);
	}

	void drawText(Canvas canvas, String msg, float x, float y, int size) {
		Paint p = new Paint();
		p.setTypeface(Typeface.MONOSPACE);
		p.setTextSize(size);
		p.setColor(Color.BLACK);

		int w = 5;

		for (int dy = -w; dy <= w; ++dy) {
			for (int dx = -w; dx <= w; ++dx) {
				if (dx * dx + dy * dy > w * w)
					continue;
				canvas.drawText(msg, x + dx, y + dy, p);
			}
		}

		p.setColor(Color.WHITE);
		canvas.drawText(msg, x, y, p);
	}

	private String getIpAddress() {
		WifiManager wifi_manager = (WifiManager) getContext().getSystemService(
				Context.WIFI_SERVICE);
		WifiInfo info = wifi_manager.getConnectionInfo();

		int ip = info.getIpAddress();
		if (ip == 0) {
			return "ERROR: please check wifi connection...";
		}

		String ip_str = "" + ((ip >> 0) & 0xFF) + "." + ((ip >> 8) & 0xFF)
				+ "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);

		return ip_str;
	}
}
