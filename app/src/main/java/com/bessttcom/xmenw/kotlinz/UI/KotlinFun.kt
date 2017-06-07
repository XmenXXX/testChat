package com.bessttcom.xmenw.kotlinz.UI

import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import com.bessttcom.xmenw.kotlinz.UI.Chats.ChatsFragment
import com.bessttcom.xmenw.kotlinz.Utils.Database.Chats
import com.firebase.ui.database.FirebaseRecyclerAdapter

/**
 * Created by xmenw on 01.06.2017.
 */


fun <T, T1: RecyclerView.ViewHolder> FirebaseRecyclerAdapter<T, T1>.forIch() : List<T> {
    val list: ArrayList<T> = ArrayList()
    (0..itemCount-1).forEach {list.add(getItem(it))}
    return list
}

fun FirebaseRecyclerAdapter<Chats, ChatsFragment.ChatMessageViewHolder>.searchItemsChats(searchString: String) : List<Chats> {
    val list: ArrayList<Chats> = ArrayList()
    (0..itemCount-1)
            .filter {getItem(it).title.startsWith(searchString)}
            .forEach {list.add(getItem(it))}
    return list
}

fun FragmentManager.clear() {

}
