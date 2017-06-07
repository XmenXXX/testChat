package com.bessttcom.xmenw.kotlinz.UI

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bessttcom.xmenw.kotlinz.R
import com.bessttcom.xmenw.kotlinz.UI.Main.MainActivityImpl
import com.bessttcom.xmenw.kotlinz.Utils.Auth.Auth
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var dispose: Disposable? = null
    private var dispose1: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        enterButton.setOnClickListener {
            Auth.singIn(emailEditText.editText?.text.toString(), passwordEditText.editText?.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        dispose = Auth.autListener.subscribe({

            Toast.makeText(this, "dfgdh", Toast.LENGTH_SHORT).show()
            if(it != Auth.EMPTY)
                startActivity(Intent(this, MainActivityImpl::class.java))
        })

        dispose1 = Auth.completeListener.subscribe({
            if (it == Auth.START)
                progressBar2.visibility = View.VISIBLE
            else
                progressBar2.visibility = View.GONE
        })
    }

    override fun onPause() {
        super.onPause()
        dispose?.dispose()
        dispose1?.dispose()
    }
}
