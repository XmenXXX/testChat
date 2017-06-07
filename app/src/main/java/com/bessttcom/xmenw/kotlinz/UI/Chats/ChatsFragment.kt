package com.bessttcom.xmenw.kotlinz.UI.Chats


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bessttcom.xmenw.kotlinz.App
import com.bessttcom.xmenw.kotlinz.R
import com.bessttcom.xmenw.kotlinz.UI.Main.BusMessages
import com.bessttcom.xmenw.kotlinz.Utils.Database.DB
import com.bessttcom.xmenw.kotlinz.UI.forIch
import com.bessttcom.xmenw.kotlinz.Utils.Database.Chats
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_chats.view.*
import kotlinx.android.synthetic.main.fragment_chats_item.view.*


class ChatsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_chats, container, false)
        v.RVchats.layoutManager = LinearLayoutManager(activity.baseContext) as RecyclerView.LayoutManager
        val ddf = object : FirebaseRecyclerAdapter<Chats, ChatMessageViewHolder>
        (Chats::class.java, R.layout.fragment_chats_item, ChatMessageViewHolder::class.java, DB.databaseUsersRef) {
            override fun populateViewHolder(p0: ChatMessageViewHolder, p1: Chats, p2: Int) {
                p0.layout.setOnClickListener { /*App.bus?.post(BusMessages(p1.id))*/ }
                p0.text.text = p1.lastMessage
                p0.title.text = p1.title
                p0.timestamp.text = p1.timestamp
            }
        }
        v.RVchats.adapter = ddf
        return v
    }


    class ChatMessageViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val layout: ConstraintLayout = item.ChatItemLayout
        val title: TextView = item.title
        val text: TextView = item.text
        val timestamp: TextView = item.timestamp
    }

    companion object {

        fun newInstance(): ChatsFragment {
            val fragment = ChatsFragment()
            return fragment
        }
    }
}