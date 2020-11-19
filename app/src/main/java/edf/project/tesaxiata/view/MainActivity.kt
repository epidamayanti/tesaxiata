package edf.project.tesaxiata.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import edf.project.tesaxiata.R
import edf.project.tesaxiata.commons.LoadingAlert
import edf.project.tesaxiata.commons.RxBaseActivity
import edf.project.tesaxiata.commons.Utils
import edf.project.tesaxiata.service.ActivityService
import id.mncplay.triviaquestions.adapters.CustomAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : RxBaseActivity() {

    private var category : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Category News"
        toolbar.setTitleTextColor(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val loading = LoadingAlert.progressDialog(this, this)
        list_category.layoutManager = LinearLayoutManager(this)


        loading.show()

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    loading.dismiss()
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    loading.dismiss()
                }
            }
        }


        subscriptions.add(provideDataService().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                for(res in it.sources){
                    if(!category.contains(res.category)){
                        category.add(res.category)
                    }
                }

                list_category.adapter = CustomAdapter(this, category){ category ->
                    Utils.cat_selected = category
                    startActivity(Intent(this, SourceActivity::class.java))
                }
                loading.dismiss()
            },{
                loading.dismiss()
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Gagal mengambil data, "+it.localizedMessage)
                    .setPositiveButton("OK", dialogClickListener)
                    .setCancelable(false)
                    .show()
            })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }




    private fun provideDataService():ActivityService{
        val clientBuilder: OkHttpClient.Builder = Utils.buildClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(Utils.ENDPOINT)
            .client(clientBuilder
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
        return retrofit.create(ActivityService::class.java)
    }







}