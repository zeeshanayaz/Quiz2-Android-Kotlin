package com.example.zeeshan.quiz2.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.zeeshan.quiz2.MainActivity
import com.example.zeeshan.quiz2.R
import com.example.zeeshan.quiz2.models.User
import kotlinx.android.synthetic.main.card_row.view.*

class ListAdapter (var ctx : Context, var myUserlist : ArrayList<User>
                   ,var deleteClick: (view: View, position: Int) -> Unit
//                   , var dialClick : (view: View, position: Int) -> Unit
                    ) : RecyclerView.Adapter<ListAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        var view = LayoutInflater.from(ctx).inflate(R.layout.card_row, null)
//        var customViewHolder = CustomViewHolder(view)
//        return customViewHolder


        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myUserlist.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currUser = myUserlist[position]
        holder.fullName.text = currUser.userFullName
        holder.emailAddress.text = currUser.userEmailAdd
        holder.contactNo.text = currUser.userContactNo
        holder.course.text = currUser.userSelectedCourse
        holder.image.setImageBitmap(currUser.userImages)


        holder.callUser.setOnClickListener {
            dialAlert(it,  position)
        }
        holder.deleteUser.setOnClickListener {
            deleteClick(it, position)

        }
        holder.shareUser.setOnClickListener {
            shareUser(position)
        }

    }

    private fun shareUser(position: Int) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "" +
                    "Full Name: ${myUserlist[position].userFullName}" +
                    "\nEmail: ${myUserlist[position].userEmailAdd}" +
                    "\nContact No: ${myUserlist[position].userContactNo}" +
                    "\nCourse: ${myUserlist[position].userSelectedCourse}")
            type = "text/plain"
        }
        startActivity(ctx,Intent.createChooser(shareIntent,"Sharing ${myUserlist[position].userFullName}'s details."),Bundle())
    }

    private fun dialAlert(it: View?, position: Int) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val create: AlertDialog = dialogBuilder.create()

        dialogBuilder.setCancelable(false)

        dialogBuilder.setTitle("Dialler Alert")
        dialogBuilder.setMessage("Do you want to call ${myUserlist[position].userFullName}? \nContact Number: ${myUserlist[position].userContactNo}")
        dialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialIntent(myUserlist[position].userContactNo)
//                create.dismiss()
            }
        })

        dialogBuilder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                create.dismiss()

            }

        })
        dialogBuilder.create()
        dialogBuilder.show()

    }

    private fun dialIntent(userContactNo: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$userContactNo")
        startActivity(ctx,dialIntent, Bundle())
    }


    inner class CustomViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var fullName : TextView = view.findViewById(R.id.userFullName_Id)
        var emailAddress : TextView = view.findViewById(R.id.userEmail_Id)
//        var password : TextView = view.findViewById(R.id.user)
        var contactNo : TextView = view.findViewById(R.id.userContactNo_Id)
        var course : TextView = view.findViewById(R.id.userCourse_Id)
        var image: ImageView = view.findViewById(R.id.userImage_Id)

        var callUser = view.findViewById<ImageButton>(R.id.call_dialer_btn)
        var shareUser = view.findViewById<ImageButton>(R.id.share_btn)
        var deleteUser = view.findViewById<ImageButton>(R.id.delete_user_btn)

    }
}