package com.example.edupodfinal.firebase

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.edupodfinal.models.Teachers
import com.example.edupodfinal.models.TermRecord
import com.example.edupodfinal.models.User
import com.example.edupodfinal.registration_fragments.TeacherRegFragment02
import com.example.edupodfinal.registration_fragments.TeacherRegFragment03
import com.example.edupodfinal.teaches_fragments.TermRecordFragmant
import com.example.edupodfinal.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(fragment: Fragment, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.userId!!)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                when (fragment) {
                    is TeacherRegFragment02 -> {
                        fragment.successSecondStep()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (fragment) {
                    is TeacherRegFragment02 -> {
                        fragment.failSecondStep()
                    }
                }

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }


    fun uploadImageToCloudStorage(fragment: Fragment, imageFileURI: Uri?, imageType: String) {

        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                fragment,
                imageFileURI
            )
        )

        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable_URL", uri.toString())

                        // Here call a function of base activity for transferring the result to it.
                        when (fragment) {

                            is TeacherRegFragment03 -> {
                                fragment.imageUploadSuccess(uri.toString())
                            }

                        }
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.

                Log.e("upload_fail", exception.message.toString())

                when (fragment) {

                    is TeacherRegFragment03 -> {
                        fragment.failSecondStep("image upload fail")
                    }

                }

            }
    }

    fun updateUserProfileData(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        // Collection Name
        mFireStore.collection(Constants.USERS)
            // Document ID against which the data to be updated. Here the document id is the current logged in user id.
            .document(getCurrentUserID())
            // A HashMap of fields which are to be updated.
            .update(userHashMap)
            .addOnSuccessListener {

                // Notify the success result.
                when (fragment) {
                    is TeacherRegFragment03 -> {
                        // Call a function of base activity for transferring the result to it.
                        fragment.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (fragment) {
                    is TeacherRegFragment03 -> {
                        // Hide the progress dialog if there is any error. And print the error in log.
                        fragment.failSecondStep("Profile update fail")
                    }
                }

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }


    fun registerTeacher(fragment: Fragment, teacher: Teachers) {

        mFireStore.collection(Constants.TEACHERS)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(teacher, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.

                when(fragment){

                    is TeacherRegFragment02 ->{
                        fragment.successTeacherReg()
                    }

                }
            }
            .addOnFailureListener { e ->

                Log.e(
                    fragment.javaClass.simpleName,
                    "Error Register teacher last step", e
                )
            }
    }

    fun createTermRecord(fragment:Fragment, termRecord: TermRecord){

        mFireStore.collection(Constants.TERMRECORD)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(termRecord, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                when(fragment){

                    is TermRecordFragmant ->{
                        fragment.sucssesTermRecord()
                    }
                }

                Log.d(
                    "term_record_created" +
                            "", "create term record successfully"
                )
            }
            .addOnFailureListener { e ->

                when(fragment){

                    is TermRecordFragmant ->{
                        fragment.failSecondStep()
                    }

                }

                Log.e(
                    fragment.javaClass.simpleName,
                    ".", e
                )
            }


    }




}