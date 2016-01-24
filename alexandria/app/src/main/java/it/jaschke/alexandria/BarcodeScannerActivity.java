package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import it.jaschke.alexandria.ui.camera.CameraSourcePreview;
import it.jaschke.alexandria.ui.camera.GraphicOverlay;
import it.jaschke.alexandria.util.BarcodeTrackerFactory;

public class BarcodeScannerActivity extends AppCompatActivity implements BarcodeTrackerFactory.Callbacks {

	private CameraSource mCameraSource;
	private CameraSourcePreview mPreview;
	private GraphicOverlay mGraphicOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_barcode);

		mPreview = (CameraSourcePreview) findViewById(R.id.activity_scan_barcode_preview);
		mGraphicOverlay = (GraphicOverlay) findViewById(R.id.activity_scan_barcode_overlay);

		Context context = getApplicationContext();

		// A barcode detector is created to track barcodes.  An associated multi-processor instance
		// is set to receive the barcode detection results, track the barcodes, and maintain
		// graphics for each barcode on screen.  The factory is used by the multi-processor to
		// create a separate tracker instance for each barcode.
		BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
		BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, this);
		barcodeDetector.setProcessor(
				new MultiProcessor.Builder<>(barcodeFactory).build());

		// A multi-detector groups the two detectors together as one detector.  All images received
		// by this detector from the camera will be sent to each of the underlying detectors, which
		// will each do face and barcode detection, respectively.  The detection results from each
		// are then sent to associated tracker instances which maintain per-item graphics on the
		// screen.
		MultiDetector multiDetector = new MultiDetector.Builder()
				.add(barcodeDetector)
				.build();

		if (!multiDetector.isOperational()) {
			// Note: The first time that an app using the barcode or face API is installed on a
			// device, GMS will download a native libraries to the device in order to do detection.
			// Usually this completes before the app is run for the first time.  But if that
			// download has not yet completed, then the above call will not detect any barcodes
			// and/or faces.
			//
			// isOperational() can be used to check if the required native libraries are currently
			// available.  The detectors will automatically become operational once the library
			// downloads complete on device.
			Log.w("anst", "Detector dependencies are not yet available.");
		}

		// Creates and starts the camera.  Note that this uses a higher resolution in comparison
		// to other detection examples to enable the barcode detector to detect small barcodes
		// at long distances.
		mCameraSource = new CameraSource.Builder(context, multiDetector)
				.setFacing(CameraSource.CAMERA_FACING_BACK)
				.setRequestedPreviewSize(1600, 1024)
				.setRequestedFps(15.0f)
				.build();

	}

	/**
	 * Restarts the camera.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		startCameraSource();
	}

	/**
	 * Stops the camera.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mPreview.stop();
	}

	/**
	 * Releases the resources associated with the camera source, the associated detectors, and the
	 * rest of the processing pipeline.
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCameraSource.release();
	}

	/**
	 * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
	 * (e.g., because onResume was called before the camera source was created), this will be called
	 * again when the camera source is created.
	 */
	private void startCameraSource() {
		try {
			mPreview.start(mCameraSource, mGraphicOverlay);
		} catch (IOException e) {
			mCameraSource.release();
			mCameraSource = null;
		}
	}

	@Override
	public void onBarcodeFound(Barcode barcode) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("barcode", barcode);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

}
