package com.mubarakansari.scoped_img_storage_kotlin_android

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.OutputStream
import java.util.*

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //img
        val img = "https://i.ytimg.com/vi/aqz-KE-bpKQ/maxresdefault.jpg"
        Glide.with(this)
            .load(img)
            .into(img_view)

        //btn clicked
        btn_save.setOnClickListener {
            val bitmap = img_view.drawToBitmap()
            imgSaved(bitmap)
        }
    }


    private fun imgSaved(bitmap: Bitmap) {
        val fos: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_${img_view}" + ".jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "DemoFolder"
            )
            val imgUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(Objects.requireNonNull(imgUri!!))!!
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            Objects.requireNonNull(fos)
            Toast.makeText(this, "Img Saved", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Img Not Saved", Toast.LENGTH_SHORT).show()
        }

    }
}