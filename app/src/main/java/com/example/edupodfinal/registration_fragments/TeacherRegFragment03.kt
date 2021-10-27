package com.example.edupodfinal.registration_fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.edupodfinal.LoginActivity
import com.example.edupodfinal.R
import com.example.edupodfinal.TeachersActivity
import com.example.edupodfinal.databinding.FragmentTeacherReg01Binding
import com.example.edupodfinal.databinding.FragmentTeacherReg03Binding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.GlideLoader
import com.github.dhaval2404.imagepicker.ImagePicker
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.IOException

@RuntimePermissions
class TeacherRegFragment03 : Fragment() {

    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    private var _binding: FragmentTeacherReg03Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherReg03Binding.inflate(inflater, container, false)


        binding.btnImgUpload.setOnClickListener {
            openImagePickerWithPermissionCheck()
        }

        return binding.root

    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {

                if (data != null) {
                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                        GlideLoader(requireContext()).loadUserPicture(
                            mSelectedImageFileUri!!,
                            binding.imgProfile
                        )

                        if (mSelectedImageFileUri !=null){

                            FirestoreClass().uploadImageToCloudStorage(
                               this ,
                                mSelectedImageFileUri,
                                Constants.USER_PROFILE_IMAGE
                            )

                        }
                }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode,grantResults)
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun openImagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start(Constants.PICK_IMAGE_REQUEST_CODE)
    }

    fun imageUploadSuccess(imageURL: String) {

        val userHashMap = HashMap<String, Any>()

        userHashMap[Constants.REG_STATUS] = 2
        userHashMap[Constants.IMAGE_URL] = imageURL

        FirestoreClass().updateUserProfileData(
            this,
            userHashMap
        )

    }

    fun userProfileUpdateSuccess() {

        Toast.makeText(
            requireContext(),
            "profile updated successfully",
            Toast.LENGTH_SHORT
        ).show()

//        startActivity(Intent(requireActivity(), TeachersActivity::class.java))
        requireActivity().finish()
    }

    fun failSecondStep(message:String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }


}