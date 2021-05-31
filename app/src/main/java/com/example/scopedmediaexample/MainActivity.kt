package com.example.scopedmediaexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.scopedmediaexample.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val CHANNEL_ID : String = "Channel"
    val PERMISSION_ID : Int = 100
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       if(!CheckPermission()) {
           RequestPermission()
       }
        if(isExternalStorageWritable()){
            Toast.makeText(this,"ExternalStorageWritable", Toast.LENGTH_SHORT).show()
        }
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
            .setContentTitle("Image saved")
            .setContentText("image is saved but location can't be found")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        binding.Save.setOnClickListener(){
            var uri:Uri = SaveImage()
            binding.imageView.setImageURI(uri)

           var foluri = createFolder()
            binding.textView.text = foluri.toString()


            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(200, builder.build())
            }
        }


        binding.showAll.setOnClickListener{
            val intent = Intent(this, show_all::class.java).apply {}
            startActivity(intent)

        }
    }


    fun CheckPermission():Boolean{

        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }
    fun RequestPermission(){

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_ID
        )
    }



    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    // Checks if a volume containing external storage is available
// for read and write.
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun SaveImage():Uri{
        val drawable = ContextCompat.getDrawable(applicationContext,R.drawable.ahhaifh)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "cat.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()


            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)

    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun createFolder(): Uri {
        val sdcardRoot = Environment.getRootDirectory()
        val drawable = ContextCompat.getDrawable(applicationContext,R.drawable.ahhaifh)
        val bitmap = (drawable as BitmapDrawable).bitmap
        var file = File(getExternalFilesDir(null).toString()+ "/" + "my own app" )
        file = File(file, "cat.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            stream.flush()


            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }
//        if (!file.exists()){
//        file.mkdirs()}
//        else{
//            Toast.makeText(this,"file already created",Toast.LENGTH_SHORT).show()
//        }
        return Uri.parse(file.absolutePath)
    }
}