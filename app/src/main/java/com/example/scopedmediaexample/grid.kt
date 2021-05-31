package com.example.scopedmediaexample

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygallery.GalleryAdapter
import com.example.mygallery.ImageModel
import com.example.scopedmediaexample.databinding.FragmentGridBinding
var imageList = mutableListOf<ImageModel>()
val collection =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

val projection = arrayOf(
    MediaStore.Images.Media._ID,
    MediaStore.Images.Media.DISPLAY_NAME,
    MediaStore.Images.Media.SIZE
)
val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"



class grid : Fragment() {


    lateinit var binding: FragmentGridBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridBinding.inflate(inflater)
        val adapter = GalleryAdapter(GalleryAdapter.OnClickListener {

        })
        binding.galleryRv.adapter = adapter
        LoadImage()
        adapter.submitList(imageList)
        return binding.root
    }



    fun LoadImage() {

        val query = context?.contentResolver?.query(
            collection,
            projection,
            null, null,
            sortOrder
        )
        query.use { cursor ->
            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    val id = idColumn?.let { cursor.getLong(it) }
                    val name = nameColumn?.let { cursor.getString(it) }

                    val contentUri: Uri? = id?.let {
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            it
                        )
                    }

                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    imageList.add(ImageModel(contentUri, name))
                }
            }

        }

    }
}