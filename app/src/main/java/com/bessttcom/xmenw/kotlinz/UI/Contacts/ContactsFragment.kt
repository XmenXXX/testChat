package com.bessttcom.xmenw.kotlinz.UI.Contacts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.android.synthetic.main.fragment_contacts_item.view.*
import com.bessttcom.xmenw.kotlinz.R
import com.bessttcom.xmenw.kotlinz.Utils.Database.DB
import com.bessttcom.xmenw.kotlinz.Utils.Database.UserInfo
import com.firebase.ui.database.FirebaseRecyclerAdapter

class ContactsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_contacts, container, false)
        v.RVcontacts.layoutManager = LinearLayoutManager(activity.baseContext) as RecyclerView.LayoutManager
        v.RVcontacts.adapter = object : FirebaseRecyclerAdapter<UserInfo, ContactsViewHolder>
        (UserInfo::class.java, R.layout.fragment_contacts_item, ContactsViewHolder::class.java, DB.databaseChatsRef) {
            override fun populateViewHolder(p0: ContactsViewHolder, p1: UserInfo, p2: Int) {
                p0.textEmail.text = p1.email
            }
        }
        return v
    }

    class ContactsViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var textEmail: TextView = item.textView4
        var imagePhoto: ImageView = item.imageView6
    }
}
