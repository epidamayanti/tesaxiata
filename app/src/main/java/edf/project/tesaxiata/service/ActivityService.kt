package edf.project.tesaxiata.service

import edf.project.tesaxiata.commons.Utils
import edf.project.tesaxiata.model.Response
import edf.project.tesaxiata.model.ResponseNews
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivityService{
    @GET(Utils.DATA_ENDPOINT)
    fun getData(): Observable<Response>

    @GET(Utils.DATA_DETAIL_ENDPOINT)
    fun getDataNews(@Query("sources") sources:String
    ): Observable<ResponseNews>

    @GET(Utils.DATA_ENDPOINT)
    fun getDataSource(@Query("category") category:String
    ): Observable<Response>

}