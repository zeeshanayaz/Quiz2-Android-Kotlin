package com.example.zeeshan.quiz2.models

import android.graphics.Bitmap

open class User(var userFullName: String,
                var userEmailAdd: String,
                var userPassword: String,
                var userContactNo: String,
                var userSelectedCourse: String,
                var userImages: Bitmap?) {
    override fun toString(): String {
        return "User(userFullName='$userFullName', userEmailAdd='$userEmailAdd', userPassword='$userPassword', userContactNo='$userContactNo', userSelectedCourse='$userSelectedCourse', userImages=$userImages)"
    }
}