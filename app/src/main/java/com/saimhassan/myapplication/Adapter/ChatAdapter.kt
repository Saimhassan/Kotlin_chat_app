package com.saimhassan.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.saimhassan.myapplication.Model.Chat
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import de.hdodenhof.circleimageview.R

class ChatAdapter(
    mContext:Context,
    mChatList: List<Chat>,
    imageUrl:String
):RecyclerView.Adapter<ChatAdapter.ViewHolder?>()
{

    private val mContext:Context
    private val mChatList:List<Chat>
    private val imageUrl:String
    var firebaseUser:FirebaseUser=FirebaseAuth.getInstance().currentUser!!

    init {
        this.mChatList = mChatList
        this.mContext = mContext
        this.imageUrl = imageUrl
        this.firebaseUser = firebaseUser
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            return if (position == 1)
            {
                val view:View = LayoutInflater.from(mContext).inflate(com.saimhassan.myapplication.R.layout.message_item_right,parent,false)
                ViewHolder(view)
            }
        else
            {
                val view:View = LayoutInflater.from(mContext).inflate(com.saimhassan.myapplication.R.layout.message_item_left,parent,false)
                ViewHolder(view)
            }
    }

    override fun getItemCount(): Int {
          return mChatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          val chat:Chat = mChatList[position]

        Picasso.get().load(imageUrl).into(holder.profile_image)

        //images messages
        if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals(""))
        {
            //image message - right sender
            if (chat.getSender().equals(firebaseUser!!.uid))
            {
                holder.show_text_message!!.visibility = View.GONE
                holder.right_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.right_image_view)

            }
            //Image message - left side
            else if (!chat.getSender().equals(firebaseUser!!.uid))
            {
                holder.show_text_message!!.visibility = View.GONE
                holder.lefT_image_view!!.visibility = View.VISIBLE
                Picasso.get().load(chat.getUrl()).into(holder.lefT_image_view)
            }
        }
        //text messages
        else
        {
            holder.show_text_message!!.text = chat.getMessage()
        }
        //send and seen message
        if (position == mChatList.size-1)
        {
            if (chat.isIsseen())
            {
                holder.text_seen!!.text = "Seen"
                if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals(""))
                {
                    val lp:RelativeLayout.LayoutParams?= holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams?
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams = lp
                }
            }
            else
            {
                holder.text_seen!!.text = "Sent"
                if (chat.getMessage().equals("sent you an image") && !chat.getUrl().equals(""))
                {
                    val lp:RelativeLayout.LayoutParams?= holder.text_seen!!.layoutParams as RelativeLayout.LayoutParams?
                    lp!!.setMargins(0,245,10,0)
                    holder.text_seen!!.layoutParams = lp
                }
            }
        }
        else
        {
            holder.text_seen!!.visibility = View.GONE


        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var profile_image: CircleImageView? = null
        var show_text_message:TextView?= null
        var lefT_image_view:ImageView?= null
        var text_seen:TextView?= null
        var right_image_view:ImageView?=null

        init {
            profile_image = itemView.findViewById(com.saimhassan.myapplication.R.id.profile_image)
            show_text_message = itemView.findViewById(com.saimhassan.myapplication.R.id.show_text_message)
            lefT_image_view = itemView.findViewById(com.saimhassan.myapplication.R.id.lefT_image_view)
            text_seen = itemView.findViewById(com.saimhassan.myapplication.R.id.text_seen)
            right_image_view = itemView.findViewById(com.saimhassan.myapplication.R.id.right_image_view)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (mChatList[position].getSender().equals(firebaseUser!!.uid))
        {
            1
        }
        else
        {
            0
        }
    }
}