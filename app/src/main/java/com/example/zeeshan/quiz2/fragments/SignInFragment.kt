package com.example.zeeshan.quiz2.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.zeeshan.quiz2.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SignInFragment : Fragment() {

    var passtype = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        var textEmailAdd : TextView = view.findViewById(R.id.email_address_textview)
        var text_Password : TextView = view.findViewById(R.id.password_textview)
        var signinButton : Button = view.findViewById(R.id.signin_Btn)

        signinButton.setOnClickListener {
            if (!textEmailAdd.text.isEmpty()){
                if(!text_Password.text.isEmpty()){

                }
                else text_Password.setError("Password is required!")
            }
            else textEmailAdd.setError("Email Address is required!")
        }

        return view
    }


}
