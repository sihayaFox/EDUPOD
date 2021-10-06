package com.example.edupodfinal.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import com.example.edupodfinal.models.School

object Constants {

    val subjects = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val educationZones =  listOf("Option 1", "Option 2", "Option 3", "Option 4")

    const val PICK_IMAGE_REQUEST_CODE = 2
    const val READ_STORAGE_PERMISSION_CODE = 2

    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val IMAGE_URL: String = "imageUrl"

    const val REG_STATUS: String = "regStatus"
    const val TEACHERS: String = "teachers"
    const val DAILYRECORD: String = "daily_records"
    const val TERMRECORD: String = "term_record"


    val schoolList = listOf<School>(
        School("1001", "Kingswood"),
        School("1002","Anthoniz"),
        School("1003","Highshool"),
        School("1004","Mahamaya")
    )

    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val USERS: String = "users"


    fun getFileExtension(fragment: Fragment, uri: Uri?): String? {

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(fragment.activity?.contentResolver?.getType(uri!!))
    }

    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }


}