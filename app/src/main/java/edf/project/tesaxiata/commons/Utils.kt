package edf.project.tesaxiata.commons

import edf.project.tesaxiata.model.SourceModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class Utils {
    companion object{

        const val ENDPOINT = "https://newsapi.org/"
        const val DATA_ENDPOINT = "v2/sources?language=en&apiKey=7e6b7d3f45f742a78a33278691584784"
        const val DATA_DETAIL_ENDPOINT = "v2/everything?language=en&apiKey=7e6b7d3f45f742a78a33278691584784"

        var cat_selected = ""
        var url_selected = ""
        var news_selected = ""

        fun buildClient(): OkHttpClient.Builder {
            val clientBuilder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            clientBuilder.addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)

            return clientBuilder
        }


    }
}