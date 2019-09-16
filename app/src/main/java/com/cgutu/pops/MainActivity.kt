package com.cgutu.pops

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.cgutu.pops.core.model.SessionToken
import com.cgutu.pops.manager.IAuthentificationManager
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authentificationManager: IAuthentificationManager
    private lateinit var loading: LottieAnimationView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading = findViewById(R.id.loadingAnimation)
        authentificationManager = PopsApplication.authentificationManager
        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            loading.playAnimation()
            authenticate("techtesting-dev", "GFVSK-oNHB6")
        }
    }

    private fun authenticate(username: String, password: String) {
        authentificationManager.authenticate(username, password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe(object : SingleObserver<SessionToken> {
                override fun onSuccess(data: SessionToken) {
                    authentificationManager.setToken(data)
                    Log.i(TAG, "data $data")
                    startActivity(Intent(this@MainActivity, OrdersActivity::class.java))
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "Error on authentification : $e")
                }

            })
    }
}
