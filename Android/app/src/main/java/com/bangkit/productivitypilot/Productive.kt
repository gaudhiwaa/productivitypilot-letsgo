package com.bangkit.productivitypilot
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.productivitypilot.camerax.CameraManager
import com.bangkit.productivitypilot.camerax.GraphicOverlay
import androidx.camera.view.PreviewView
//import kotlinx.android.synthetic.main.activity_main.*

class Productive : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    private lateinit var btnSwitch: Button
    private lateinit var previewViewFinder: PreviewView
    private lateinit var graphicOverlayFinder: GraphicOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // Manually bind the views
        btnSwitch = findViewById(R.id.btnSwitch)
        previewViewFinder = findViewById(R.id.previewView_finder)
        graphicOverlayFinder = findViewById(R.id.graphicOverlay_finder)

        createCameraManager()
        checkForPermission()
        onClicks()
    }

    private fun checkForPermission() {
        if (allPermissionsGranted()) {
            cameraManager.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun onClicks() {
        btnSwitch.setOnClickListener {
            cameraManager.changeCameraSelector()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraManager.startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }


    private fun createCameraManager() {

        cameraManager = CameraManager(
            this,
            previewViewFinder,
            this,
            graphicOverlayFinder
        )
//        previewViewFinder.rotation = 90.0f
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

}