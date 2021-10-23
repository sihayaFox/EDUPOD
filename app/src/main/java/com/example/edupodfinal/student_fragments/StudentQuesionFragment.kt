package com.example.edupodfinal.student_fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.net.toUri
import com.example.edupodfinal.R
import com.example.edupodfinal.databinding.FragmentDailyRecordBinding
import com.example.edupodfinal.databinding.FragmentStudentQuesionBinding
import com.example.edupodfinal.firebase.FirestoreClass
import com.example.edupodfinal.models.DailyPlanner
import com.example.edupodfinal.models.Question
import com.example.edupodfinal.models.User
import com.example.edupodfinal.util.Constants
import com.example.edupodfinal.util.Constants.FILE_REQ_CODE
import com.example.edupodfinal.util.Constants.QUESTIONS_DOC
import com.example.edupodfinal.util.getStringTrim
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RuntimePermissions
class StudentQuesionFragment : Fragment() {

    private var _binding: FragmentStudentQuesionBinding? = null
    private val binding get() = _binding!!
    private var selectedDoc:File? = null
    private var selectedDocUrl:String? = null
    private var teacher:User? =null

    var fileName:String? = null

    val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentQuesionBinding.inflate(inflater, container, false)


        binding.etAttchment.setOnClickListener {
            pickFileWithPermissionCheck()
        }

        binding.btnSend.setOnClickListener {
            FirestoreClass().uploadImageToCloudStorage(this,selectedDocUrl?.toUri(),QUESTIONS_DOC)
        }

        FirestoreClass().getTeachers(this)


        return binding.root
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {


                data?.let {
                    val list = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)


                    Log.d("files",list.toString())

                    fileName = list?.get(0)?.split("/")?.last()

                    binding.etAttchment.setText(fileName)

                    selectedDocUrl = list?.get(0)

//                    selectedDoc = File(list?.get(0))


                }
            }
        }
    }



    fun imageUploadSuccess(imageURL: String) {

        val question = Question(

            teacherId = teacher?.userId,
            studentId = FirestoreClass().getCurrentUserID(),
            date = outputDateFormat.format(System.currentTimeMillis()),
            question = binding.etQuesion.getStringTrim(),
            teacherName = teacher?.fullName,
            questionTopic = binding.etQuesionTopic.getStringTrim(),
            docUrl = imageURL,
            docName = fileName
        )

        FirestoreClass().createQuestion(this, question)

    }

    fun getTeachersList(teachersList: ArrayList<User>){

        val adapter = ArrayAdapter(requireContext(), R.layout.list_items, teachersList)
        (binding.etTeacher as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.etTeacher.setOnItemClickListener { adapterView, view, i, l ->

            teacher = teachersList.get(i)

        }

    }


    fun sucssesTermRecord(){
        Toast.makeText(
            requireContext(),
            "successfully created",
            Toast.LENGTH_SHORT
        ).show()
    }
    @NeedsPermission(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun pickFile() {
        FilePickerBuilder.instance.apply {
            setMaxCount(1)
            enableCameraSupport(false)
            addFileSupport(
                "Doc",
                arrayOf(
                    ".doc",
                    ".pdf",
                    ".docx",
                    ".ogg",
                    ".qt"
                )
            )
        }.pickFile(this@StudentQuesionFragment, FILE_REQ_CODE)
    }


}