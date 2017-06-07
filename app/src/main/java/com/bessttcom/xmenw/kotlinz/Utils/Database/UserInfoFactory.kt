package com.bessttcom.xmenw.kotlinz.Utils.Database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.subjects.PublishSubject

/**
 * Created by xmenw on 04.06.2017.
 */
object UserInfoFactory {

    val map: HashMap<String, PublishSubject<UserInfo>> = HashMap()

    fun getUser(uid: String) : PublishSubject<UserInfo> {

        if(!map.containsKey(uid)) {
            val rxObject: PublishSubject<UserInfo> = PublishSubject.create()
            map.put(uid, rxObject)
            DB.databaseUsersRef.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {}
                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0 != null)
                        map[uid]!!.onNext(p0.getValue(UserInfo::class.java))
                }
            })
            return rxObject
        }
        else
            return map[uid]!!
    }
}