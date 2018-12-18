package com.example.zeeshan.quiz2.fragments


import android.Manifest.permission.CAMERA
import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.inputmethodservice.Keyboard
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider.getUriForFile
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.zeeshan.quiz2.BuildConfig
import com.example.zeeshan.quiz2.MainActivity
import com.example.zeeshan.quiz2.R
import com.example.zeeshan.quiz2.interfaces.FragmentSignupInteraction
import com.example.zeeshan.quiz2.models.User
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SignUpFragment : Fragment() {

    lateinit var courseSpinner : Spinner
    lateinit var signUpButton: Button

    lateinit var userImage : ImageView
    lateinit var userImageText : TextView
    lateinit var userImageIcon : ImageButton
    val GALLERY = 1
    val CAMERA = 2

    var sendDataInteraction : FragmentSignupInteraction? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_sign_up, container, false)



        courseSpinner = view.findViewById(R.id.course_spinner)
        signUpButton = view.findViewById(R.id.signup_Btn)
        var fullName = view.findViewById<TextView>(R.id.fullName_TextView)
        var email = view.findViewById<TextView>(R.id.email_TextView)
        var password = view.findViewById<TextView>(R.id.password_TextView)
        var contactNo = view.findViewById<TextView>(R.id.contactNo_TextView)
        userImage = view.findViewById(R.id.userImage_Id)
        userImageText = view.findViewById(R.id.user_image_text_id)
        userImageIcon = view.findViewById(R.id.userImageIcon_Id)



        courseSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.course)) as SpinnerAdapter?

        signUpButton.setOnClickListener {
            if(!fullName.text.isEmpty()){

                if(!email.text.isEmpty()){

                    if (!password.text.isEmpty()){

                        if(!contactNo.text.isEmpty()){
                            if (contactNo.text.length == 11){

                                var bitmapDrawable : BitmapDrawable? = userImage.drawable as? BitmapDrawable
                                var bitMapImg = bitmapDrawable!!.bitmap


                                var currUser = User("${fullName.text.toString()}", "${email.text.toString()}",
                                    "${password.text.toString()}","${contactNo.text.toString()}" ,
                                    "${courseSpinner.selectedItem.toString()}", bitMapImg)
//                                Toast.makeText(activity!!, currUser.toString(), Toast.LENGTH_SHORT).show()



                                if(sendDataInteraction != null){

                                    fullName.text = ""
                                    email.text = ""
                                    password.text = ""
                                    contactNo.text = ""
                                    courseSpinner.setSelection(0)
                                    bitMapImg = null

                                    closeKeyboard()

                                    sendDataInteraction?.sendData(currUser)


                                }

                            }
                            else contactNo.setError("Contact Number should be atleat 11 digits!")

                        }
                        else contactNo.setError("Contact Number field required!")

                    }
                    else password.setError("Password field required!")

                }
                else email.setError("Email field required!")

            }
            else fullName.setError("Full Name field required!")
        }


        userImageIcon.setOnClickListener {
            showPictureDialog()
        }
        return view
    }

    private fun closeKeyboard() {

    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity!!)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    userImageText.visibility = View.INVISIBLE
                    userImage.visibility = View.VISIBLE
                    Toast.makeText(activity!!, "Image Saved!", Toast.LENGTH_SHORT).show()
                    userImage!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity!!, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            userImage!!.setImageBitmap(thumbnail)
            userImageText.visibility = View.INVISIBLE
            userImage.visibility = View.VISIBLE
            saveImage(thumbnail)
            Toast.makeText(activity!!, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(activity!!,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            sendDataInteraction = context as FragmentSignupInteraction
        }
        catch (e: Exception){
            Toast.makeText(activity!!, "Can not cast Interface", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        sendDataInteraction = null
    }

}
