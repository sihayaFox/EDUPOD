package com.example.edupodfinal.firebase

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.edupodfinal.LoginActivity
import com.example.edupodfinal.SplashActivity
import com.example.edupodfinal.models.*
import com.example.edupodfinal.registration_fragments.TeacherRegFragment02
import com.example.edupodfinal.registration_fragments.TeacherRegFragment03
import com.example.edupodfinal.teaches_fragments.*
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

    fun getCurrentUserID(): String? {
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
            .document(getCurrentUserID()!!)
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
                when (fragment) {

                    is TeacherRegFragment02 -> {
                        fragment.successTeacherReg()
                    }

                }
            }.addOnFailureListener { e ->

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


    fun getUserDetails(activity: Activity) {

        // Here we pass the collection name from which we wants the data.

            getCurrentUserID()?.let {

                mFireStore.collection(Constants.USERS)
                    // The document id to get the Fields of user.
                    .document(it)
                    .get()
                    .addOnSuccessListener { document ->

                        Log.i(activity.javaClass.simpleName, document.toString())

                        // Here we have received the document snapshot which is converted into the User Data model object.
                        val user = document.toObject(User::class.java)

                        val sharedPreferences = activity.getSharedPreferences(
                            Constants.MYPREFERENCES,
                            Context.MODE_PRIVATE
                        )

                        // Create an instance of the editor which is help us to edit the SharedPreference.
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString(
                            Constants.CONFIRMATIONID, user?.schoolConId
                        )
                        editor.apply()

                        when (activity) {

                            is LoginActivity -> {
                                // Call a function of base activity for transferring the result to it.
                                user?.let { it1 -> activity.userLoggedInSuccess(it1) }
                            }

                            is SplashActivity ->{
                                activity.checkExistingUser(user)
                            }

                        }
                    }
                    .addOnFailureListener { e ->
                        // Hide the progress dialog if there is any error. And print the error in log.

                        Log.e(
                            activity.javaClass.simpleName,
                            "Error while getting user details.",
                            e
                        )
                    }
            }

    }

    fun createDailyRecord(fragment:Fragment, dailyRecord: DailyRecord){

        mFireStore.collection(Constants.DAILYRECORD)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(dailyRecord, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                when(fragment){

                    is DailyRecordFragment ->{
                        fragment.sucssesDailyRecord()
                    }
                }

                Log.d(
                    "term_record_created" +
                            "", "create term record successfully"
                )
            }
            .addOnFailureListener { e ->

                when(fragment){

                    is DailyRecordFragment ->{
                        fragment.failSecondStep()
                    }

                }

                Log.e(
                    fragment.javaClass.simpleName,
                    ".", e
                )
            }


    }

    fun createDayPlanner(fragment:Fragment, dailyPlanner: DailyPlanner){

        mFireStore.collection(Constants.DAILYPLANNER)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(dailyPlanner, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                when(fragment){

                    is DayPlannerFragment ->{
                        fragment.sucssesDailyPlanner()
                    }
                }

                Log.d(
                    "term_record_created" +
                            "", "create term record successfully"
                )
            }
            .addOnFailureListener { e ->

                when(fragment){

                    is DayPlannerFragment ->{
                        fragment.failSecondStep()
                    }

                }

                Log.e(
                    fragment.javaClass.simpleName,
                    ".", e
                )
            }


    }

    fun createAnnualRecord(fragment:Fragment, annualRecord: AnualRecord){

        mFireStore.collection(Constants.DAILYPLANNER)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(annualRecord, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                when(fragment){

                    is AnualRecordFragment ->{
                        fragment.sucssesDailyRecord()
                    }

                }

                Log.d(
                    "term_record_created" +
                            "", "create term record successfully"
                )
            }
            .addOnFailureListener { e ->

                when(fragment){
                    is AnualRecordFragment ->{
                        fragment.failSecondStep()
                    }

                }

                Log.e(
                    fragment.javaClass.simpleName,
                    ".", e
                )
            }


    }

    fun getDailyRecList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.DAILYRECORD)
            .whereEqualTo("userId", getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<DailyRecord> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val dailyRecord = i.toObject(DailyRecord::class.java)
                    dailyRecord!!.dailyRecId = i.id

                    productsList.add(dailyRecord)
                }

                when (fragment) {
                    is AddedRecordViewFragment -> {
                        fragment.sucssesDailyRecordView(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is AddedRecordViewFragment -> {
//                        fragment.hideProgressDialog()
                    }
                }

                Log.e("GetProductList", "Error while getting product list.", e)
            }
    }


    fun getTermsRecList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.TERMRECORD)
            .whereEqualTo("userId", getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val termRecordList: ArrayList<TermRecord> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val termRecord = i.toObject(TermRecord::class.java)
                    termRecord!!.id = i.id

                    termRecordList.add(termRecord)
                }

                when (fragment) {
                    is TermRecordViewFragment -> {
                        fragment.sucssestermRecord(termRecordList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.


                Log.e("GetProductList", "Error while getting product list.", e)
            }
    }

    fun getDailyPlanner(fragment: Fragment){

        mFireStore.collection(Constants.DAILYPLANNER)
            .whereEqualTo("userId", getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<DailyPlanner> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val dailyRecord = i.toObject(DailyPlanner::class.java)
                    dailyRecord!!.id = i.id

                    productsList.add(dailyRecord)
                }

                when (fragment) {
                    is RecordHome -> {
                        fragment.sucssesDailyRecordView(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                Log.e("GetProductList", "Error while getting product list.", e)
            }




    }

    fun getAnnualRecordFragment(fragment: Fragment){

        mFireStore.collection(Constants.ANUAL_PLANNER)
            .whereEqualTo("userId", getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<AnualRecord> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val dailyRecord = i.toObject(AnualRecord::class.java)
                    dailyRecord!!.id = i.id

                    productsList.add(dailyRecord)
                }

                when (fragment) {

                    is AnualRecordViewFragment -> {
                        fragment.sucssesAnnualRecordView(productsList)
                    }

                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                Log.e("GetProductList", "Error while getting product list.", e)
            }





    }


}