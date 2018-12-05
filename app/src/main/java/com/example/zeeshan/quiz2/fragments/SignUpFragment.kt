package com.example.zeeshan.quiz2.fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.zeeshan.quiz2.MainActivity
import com.example.zeeshan.quiz2.R
import com.example.zeeshan.quiz2.interfaces.FragmentSignupInteraction
import com.example.zeeshan.quiz2.models.User
import kotlinx.android.synthetic.main.fragment_sign_up.*


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


        courseSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.course))

        signUpButton.setOnClickListener {
            if(!fullName.text.isEmpty()){

                if(!email.text.isEmpty()){

                    if (!password.text.isEmpty()){

                        if(!contactNo.text.isEmpty()){
                            if (contactNo.text.length == 11){

                                var currUser = User("${fullName.text.toString()}", "${email.text.toString()}",
                                    "${password.text.toString()}","${contactNo.text.toString()}" , "${courseSpinner.selectedItem.toString()}")
//                                Toast.makeText(activity!!, currUser.toString(), Toast.LENGTH_SHORT).show()



                                if(sendDataInteraction != null){
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

        return view
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
