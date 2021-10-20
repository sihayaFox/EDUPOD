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

    val subjects = listOf("Science", "Mathematics", "Sinhala", "English", "Chemistry", "Biology", "Physics", "Combined Maths", "Commerce")
    val educationZones =  listOf("Wattegama", "Gampola", "Walala", "Kandy", "Galle", "Matale")

    const val PICK_IMAGE_REQUEST_CODE = 2
    const val READ_STORAGE_PERMISSION_CODE = 2

    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val IMAGE_URL: String = "imageUrl"

    const val REG_STATUS: String = "regStatus"
    const val TEACHERS: String = "teachers"
    const val DAILYRECORD: String = "daily_records"
    const val TERMRECORD: String = "term_record"

    const val MYPREFERENCES:String = "mypreferences"
    const val CONFIRMATIONID:String = "confirmation_id"

    const val DAILYPLANNER:String = "daily_planner"

    const val ANUAL_PLANNER:String = "anual_planner"


    val schoolList = listOf<School>(
        School("1001", "Kingswood College, Kandy"),
        School("1002","St.Anthony's College, Kandy "),
        School("1003","Highshool Girl's College, kandy"),
        School("1004","Mahamaya Girl's College")
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