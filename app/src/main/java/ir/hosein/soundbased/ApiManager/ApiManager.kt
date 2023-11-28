package ir.hosein.soundbased.ApiManager

import ir.hosein.soundbased.ApiManager.model.ArticleData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://dunijet.ir/Projects/soleymani/"



class ApiManager {
    private val apiService: ApiService

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun getArticleList(apiCallback: ApiCallback<List<ArticleData.ArticleDataItem>>) {

        apiService.getArticle().enqueue(object : Callback<ArticleData> {
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                val data = response.body()!!

                apiCallback.onSuccess(data)

            }

            override fun onFailure(call: Call<ArticleData>, t: Throwable) {

                apiCallback.onError(t.message!!)

            }

        })
    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)
        fun onError(errorMessage: String)

    }
}