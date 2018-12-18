package com.example.zeeshan.quiz2.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeeshan.quiz2.MainActivity
import com.example.zeeshan.quiz2.R
import com.example.zeeshan.quiz2.models.User
import com.example.zeeshan.quiz2.adapters.ListAdapter
import com.example.zeeshan.quiz2.interfaces.FragmentUsersInteraction


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AllUserFragment : Fragment() {

    val myList : ArrayList<User> = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_all_user, container, false)


        var listAdapter : ListAdapter? = null
        val recyclerView : RecyclerView = view.findViewById(R.id.all_User_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)

//        var currUser = User("Muhammad Zeeshan", "zeeshanayaz1@gmail.com",
//            "12345","03122309493" , "Android")
//        myList.add(currUser)


        listAdapter = ListAdapter(activity!!, myList,
            {
                view, position ->
                myList.removeAt(position)
                listAdapter?.notifyDataSetChanged()
            }
//            ,{
//                view, position ->
//
//            }
        )
        recyclerView.adapter = listAdapter



        val mainActivity = activity as MainActivity
        mainActivity.setRecieveInteraction(object : FragmentUsersInteraction{
            override fun recieveData(user: User) {
                myList.add(user)
                listAdapter.notifyDataSetChanged()
            }
        })

        return view
    }



}
