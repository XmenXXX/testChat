package com.bessttcom.xmenw.kotlinz.UI.Messages


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bessttcom.xmenw.kotlinz.R
import com.bessttcom.xmenw.kotlinz.Utils.Auth.Auth
import com.bessttcom.xmenw.kotlinz.Utils.Database.DB
import com.bessttcom.xmenw.kotlinz.Utils.Database.Message
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.fragment_messages.view.*
import kotlinx.android.synthetic.main.fragment_messages_item.view.*


class MessagesFragment : Fragment() {

    override fun onDestroy() {
        Log.d("FRAGMENTQQQ", "onDestroy "+ this.id)
        childFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_messages, container, false)
        view.buttonM.setOnClickListener {DB.addMessage(text = editTextM.text.toString(), chatsId = arguments.getString("id"))}
        view.RVmessages.layoutManager = LinearLayoutManager(activity.baseContext) as RecyclerView.LayoutManager
        view.RVmessages.adapter = object : FirebaseRecyclerAdapter<Message, MessagesViewHolder>
        (Message::class.java, R.layout.fragment_messages_item, MessagesViewHolder::class.java, DB.databaseMessagesRef) {
            override fun populateViewHolder(p0: MessagesViewHolder, p1: Message, p2: Int) {
                if(p1.uerId == Auth.uid) {
                    p0.layoutLeft.visibility = View.GONE
                    p0.layoutRight.visibility = View.VISIBLE
                    p0.textRight.text = p1.message
                } else {
                    p0.layoutRight.visibility = View.GONE
                    p0.layoutLeft.visibility = View.VISIBLE
                    p0.textLeft.text = p1.message
                }
            }
        }
        return view
    }

    class MessagesViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val imageLeft: ImageView = item.leftImage
        val imageRight: ImageView = item.rightImage
        val textLeft: TextView = item.leftText
        val textRight: TextView = item.rightText
        val layoutLeft: ConstraintLayout = item.leftLayout
        val layoutRight: ConstraintLayout = item.rightLayout
    }
}
