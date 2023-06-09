package com.bangkit.productivitypilot
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.productivitypilot.camerax.CameraManager
import com.bangkit.productivitypilot.camerax.GraphicOverlay
import androidx.camera.view.PreviewView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import android.content.Intent
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

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            showExitConfirmationDialog()
        }
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
        previewViewFinder.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE)
        previewViewFinder.rotation = 90.0f
        cameraManager = CameraManager(
            this,
            previewViewFinder,
            this,
            graphicOverlayFinder
        )

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
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
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        }

        buttonNo.setOnClickListener {
            // If "No" is clicked, close the dialog
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}