package com.bessttcom.xmenw.kotlinz.Utils.Auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.subjects.BehaviorSubject

object Auth {

    val EMPTY = "EMPTY"
    val START = "START"
    val FAIL = "FAIL"
    val SUCCESS = "SUCCESS"
    
    val autListener: BehaviorSubject<String> = BehaviorSubject.create()
    val completeListener: BehaviorSubject<String> = BehaviorSubject.create()
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var uid: String? = null

    init {
        mAuth.addAuthStateListener {
            val user = it.currentUser
            if(user != null) {
                autListener.onNext(user.uid)
                uid = user.uid
            }
            else autListener.onNext(EMPTY)
        }
    }

    private fun Task<AuthResult>.addlistener() = this.addOnCompleteListener {
        completeListener.onNext(START)
        if (it.isSuccessful) {
            completeListener.onNext(SUCCESS)
        }
        else {
            completeListener.onNext(FAIL)
        }
    }

    fun singUp(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass).addlistener()
    }

    fun singIn(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass).addlistener()
    }
}