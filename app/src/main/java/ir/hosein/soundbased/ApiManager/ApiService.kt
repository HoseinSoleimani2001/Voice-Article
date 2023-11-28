package ir.hosein.soundbased.ApiManager

import ir.hosein.soundbased.ApiManager.model.ArticleData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("main.json")
    fun getArticle(

    ) : Call<ArticleData>

}