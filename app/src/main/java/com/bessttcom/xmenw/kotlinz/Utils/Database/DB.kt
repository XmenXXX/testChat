package com.bessttcom.xmenw.kotlinz.Utils.Database

import com.bessttcom.xmenw.kotlinz.Utils.Auth.Auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.subjects.PublishSubject

object DB {

    val FAIL = "FAIL"
    val START = "START"

    private val userMap: HashMap<String, UserInfo> = HashMap()
    private val waitUserMap: HashMap<String, UserInfo> = HashMap()

    val userInfoObserver: PublishSubject<String> = PublishSubject.create()
    val userInfoSearch: PublishSubject<ArrayList<UserInfo>> = PublishSubject.create()

    val database = FirebaseDatabase.getInstance().reference
    val databaseChatsRef = database.child("chats")
    val databaseUsersRef = database.child("users")
    val databaseMessagesRef = database.child("messages")


    fun addMessage (text: String, chatsId: String) {
        databaseMessagesRef.child(chatsId).push().setValue(Message(Auth.uid!!, text, "11111"))
    }

    fun contactsSearch(text: String) {

        val list = ArrayList<UserInfo>()
        database.child("users")
                .orderByChild("email")
                .startAt(text)
                .limitToFirst(10)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onDataChange(p0: DataSnapshot?) {
                        if(p0 != null) {
                            p0.children
                                    .map {it.getValue(UserInfo::class.java)}
                                    .filter {it.email.startsWith(text)}
                                    .forEach {list.add(it)}
                        }
                        userInfoSearch.onNext(list)
                    }
                })
    }

    fun getUserInfo(uId: String) : UserInfo? {

        if (uId in userMap.keys)
            return@getUserInfo userMap[uId]
        else {
            val data: UserInfo = UserInfo(name = START, photo = START)
            waitUserMap.addUser(uId, data)
            return@getUserInfo data
        }
    }

    private fun HashMap<String, UserInfo>.addUser(uId: String, userInfo: UserInfo) {
        if (this.size == 0)
            addMapUser(uId, userInfo)
        this[uId] = userInfo
    }

    private fun addMapUser(uId: String, userInfo: UserInfo) {

        database.child("users/$uId/").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                userMap[uId] = UserInfo(name = FAIL, photo = FAIL)
            }

            override fun onDataChange(p0: DataSnapshot?) {
                userMap[uId] = p0?.value as UserInfo
                waitUserMap.deleteUser(uId)
            }
        })
    }

    private fun HashMap<String, UserInfo>.deleteUser(uId: String) {
        userInfoObserver.onNext(uId)
        this.remove(uId)
        if  (size!=0)
            for (ff in this.keys) {
                addMapUser(uId = ff, userInfo = this[ff]!!)
                break
            }
    }
}