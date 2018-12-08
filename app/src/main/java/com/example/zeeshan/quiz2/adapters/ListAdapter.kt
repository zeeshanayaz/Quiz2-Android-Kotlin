package com.example.zeeshan.quiz2.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.zeeshan.quiz2.R
import com.example.zeeshan.quiz2.models.User
import kotlinx.android.synthetic.main.card_row.view.*

class ListAdapter (var ctx : Context, var myUserlist : ArrayList<User>) : RecyclerView.Adapter<ListAdapter.CustomViewHolder>(){
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
    }


    inner class CustomViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var fullName : TextView = view.findViewById(R.id.userFullName_Id)
        var emailAddress : TextView = view.findViewById(R.id.userEmail_Id)
//        var password : TextView = view.findViewById(R.id.user)
        var contactNo : TextView = view.findViewById(R.id.userContactNo_Id)
        var course : TextView = view.findViewById(R.id.userCourse_Id)
        var image: ImageView = view.findViewById(R.id.userImage_Id)
    }
}