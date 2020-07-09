package com.saimhassan.myapplication.Fragments

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.saimhassan.myapplication.Adapter.UserAdapter
import com.saimhassan.myapplication.Model.ChatList
import com.saimhassan.myapplication.Model.Users
import com.saimhassan.myapplication.R

class ChatsFragment : Fragment() {

    private var userAdapter: UserAdapter?= null
    private var mUsers:List<Users>?= null
    private var usersChatList:List<ChatList>?= null
    lateinit var recycler_view_chatList:RecyclerView
    private var firebaseUser:FirebaseUser?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_chats, container, false)

        recycler_view_chatList = view.findViewById(R.id.recycler_view_chatList)
        recycler_view_chatList.setHasFixedSize(true)
        recycler_view_chatList.layoutManager = LinearLayoutManager(context)
        firebaseUser = FirebaseAuth.getInstance().currentUser

        usersChatList = ArrayList()
        val ref = FirebaseDatabase.getInstance().reference.child("ChatList").child(firebaseUser!!.uid)
        ref!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (usersChatList as ArrayList).clear()
                for (dataSnapShot in p0.children)
                {
                    val chatlist = dataSnapShot.getValue(ChatList::class.java)
                    (usersChatList as ArrayList).add(chatlist!!)
                }
                retrieveChatList()
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


        return view
    }

    private fun retrieveChatList(){
           mUsers = ArrayList()
           val ref = FirebaseDatabase.getInstance().reference.child("Users")
           ref!!.addValueEventListener(object : ValueEventListener{
               override fun onDataChange(p0: DataSnapshot) {
                   (mUsers as ArrayList).clear()
                   for (dataSnapShot in p0.children)
                   {
                       val user = dataSnapShot.getValue(Users::class.java)
                       for (eachChatList in usersChatList!!)
                       {
                           if (user!!.getUID().equals(eachChatList.getId()))
                           {
                               (mUsers as ArrayList).add(user)
                           }
                       }
                   }
                   userAdapter = UserAdapter(context!!,(mUsers as ArrayList<Users>),true)
                   recycler_view_chatList.adapter = userAdapter
               }

               override fun onCancelled(p0: DatabaseError) {

               }
           })
    }


}