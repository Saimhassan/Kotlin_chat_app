package com.saimhassan.myapplication.Adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.saimhassan.myapplication.MainActivity
import com.saimhassan.myapplication.MessageChatActivity
import com.saimhassan.myapplication.Model.Users
import com.saimhassan.myapplication.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class UserAdapter(
    mContext:Context,
    mUsers:List<Users>,
    isChatCheck:Boolean
    ):RecyclerView.Adapter<UserAdapter.ViewHolder?>()
    {

        private val mContext:Context
        private val mUsers:List<Users>
        private val isChatCheck:Boolean

        init {
            this.mUsers=mUsers
            this.mContext = mContext
            this.isChatCheck=isChatCheck
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view:View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout,viewGroup,false)
            return UserAdapter.ViewHolder(view)
        }

        override fun getItemCount(): Int {
          return mUsers.size
        }

        override fun onBindViewHolder(holder: ViewHolder, i: Int) {
            val user:Users= mUsers[i]
            holder.userNameTxt.text = user!!.getUserName()
            Picasso.get().load(user.getProfile()).placeholder(R.drawable.ic_baseline_person_24).into(holder.profileImageView)
            holder.itemView.setOnClickListener {
                // Check Later if send or view profile
                val options = arrayOf<CharSequence>(
                      "Send Message",
                    "Visit Profile"
                )
                val builder:AlertDialog.Builder = AlertDialog.Builder(mContext)
                builder.setTitle("What do you want?")
                builder.setItems(options,DialogInterface.OnClickListener { dialog, position ->
                    if (position == 0)
                    {
                        val intent= Intent(mContext, MessageChatActivity::class.java)
                        intent.putExtra("visit_id",user.getUID())
                        mContext.startActivity(intent)
                    }
                    if (position == 1)
                    {

                    }
                })
                builder.show()
            }
        }


        class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
        {
            var userNameTxt:TextView
            var profileImageView:CircleImageView
            var onlineImageView:CircleImageView
            var offlineImageView:CircleImageView
            var lastMessageTxt:TextView

            init {
                userNameTxt = itemView.findViewById(com.saimhassan.myapplication.R.id.username)
                profileImageView = itemView.findViewById(com.saimhassan.myapplication.R.id.profile_image)
                onlineImageView = itemView.findViewById(com.saimhassan.myapplication.R.id.image_online)
                offlineImageView = itemView.findViewById(com.saimhassan.myapplication.R.id.image_offline)
                lastMessageTxt = itemView.findViewById(com.saimhassan.myapplication.R.id.message_last)
            }
        }


    }