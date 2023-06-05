package com.bangkit.productivitypilot

import android.hardware.Camera
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout

import java.io.IOException

class ProductiveCamera : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var camera: Camera
    private lateinit var cameraPreview: SurfaceView
    private lateinit var holder: SurfaceHolder
    private lateinit var frameOverlay: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productivecamera)

        cameraPreview = findViewById(R.id.cameraPreview)
        holder = cameraPreview.holder
        holder.addCallback(this)

        frameOverlay = findViewById(R.id.frameOverlay)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        camera = Camera.open()

        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Handle any changes to the camera preview (e.g., rotation)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera.stopPreview()
        camera.release()
    }
}
