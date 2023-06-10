package com.bangkit.productivitypilot

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog

class ProductiveCameraActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private var camera: Camera? = null
    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var toggleButton: Button
    private var isCameraOn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productivecamera)

        surfaceView = findViewById(R.id.cameraPreview)
        toggleButton = findViewById(R.id.toggleButton)

        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        toggleButton.setOnClickListener {
            if (isCameraOn) {
                stopCamera()
            } else {
                startCamera()
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            showExitConfirmationDialog()
        }
    }

    private fun showExitConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_exit_confirmation, null)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)
        val alertDialog = alertDialogBuilder.create()

        val buttonYes = dialogView.findViewById<Button>(R.id.buttonYes)
        val buttonNo = dialogView.findViewById<Button>(R.id.buttonNo)

        buttonYes.setOnClickListener {
            // If "Yes" is clicked, navigate to ProfileActivity
            navigateToProfileActivity()
            alertDialog.dismiss()
        }

        buttonNo.setOnClickListener {
            // If "No" is clicked, close the dialog
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun navigateToProfileActivity() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // No implementation required
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopCamera()
    }

    private fun startCamera() {
        val params = camera?.parameters
        params?.set("orientation", "portrait")
        camera?.parameters = params
        try {
            camera = Camera.open()
            camera?.setDisplayOrientation(0)

            val params = camera?.parameters
            params?.set("orientation", "portrait")
            camera?.parameters = params

            camera?.setPreviewDisplay(surfaceHolder)
            camera?.startPreview()
            isCameraOn = true
            toggleButton.text = "Turn Off Camera"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopCamera() {
        try {
            camera?.stopPreview()
            camera?.release()
            camera = null
            isCameraOn = false
            toggleButton.text = "Turn On Camera"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

}
