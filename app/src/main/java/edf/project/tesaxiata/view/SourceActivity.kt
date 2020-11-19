package edf.project.tesaxiata.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import edf.project.tesaxiata.R
import edf.project.tesaxiata.adapter.NewsAdapter
import edf.project.tesaxiata.adapter.SourceAdapter
import edf.project.tesaxiata.commons.LoadingAlert
import edf.project.tesaxiata.commons.RxBaseActivity
import edf.project.tesaxiata.commons.Utils
import edf.project.tesaxiata.service.ActivityService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_source.*
import kotlinx.android.synthetic.main.activity_source.toolbar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SourceActivity : RxBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)

        toolbar.title = "Source"
        toolbar.setTitleTextColor(Color.WHITE)

        val loading = LoadingAlert.progressDialog(this, this)
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
        loading.show()

        subscriptions.add(provideDataService().getDataSource(Utils.cat_selected)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                listRecyclerView.layoutManager = LinearLayoutManager(this)
                listRecyclerView.adapter = SourceAdapter(this, it.sources){
                    Utils.news_selected = it.id
                    startActivity(Intent(this, NewsActivity::class.java))
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

    private fun provideDataService(): ActivityService {
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