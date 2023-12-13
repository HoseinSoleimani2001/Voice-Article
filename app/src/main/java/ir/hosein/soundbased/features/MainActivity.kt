package ir.hosein.soundbased.features

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.hosein.soundbased.ApiManager.ApiManager
import ir.hosein.soundbased.ApiManager.model.ArticleData
import ir.hosein.soundbased.ArticleAdapter
import ir.hosein.soundbased.databinding.ActivityMainBinding
import ir.hosein.soundbased.ext.BaseActivity

class MainActivity : BaseActivity(), ArticleAdapter.RecyclerCallback {
    lateinit var binding: ActivityMainBinding
    private lateinit var articleAdapter: ArticleAdapter
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initUi()

    }

    private fun initUi() {
        getArticleListFromApi()

    }

    private fun getArticleListFromApi() {
        apiManager.getArticleList(object :
            ApiManager.ApiCallback<List<ArticleData.ArticleDataItem>> {
            override fun onSuccess(data: List<ArticleData.ArticleDataItem>) {

                showDataInRecycler(data)
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, "Error = $errorMessage", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun showDataInRecycler(data: List<ArticleData.ArticleDataItem>) {
        articleAdapter = ArticleAdapter(ArrayList(data), this)
        binding.rvMain.adapter = articleAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)
    }

    override fun onArticleItemClicked(dataArticle: ArticleData.ArticleDataItem) {

        val intent = Intent(this, VoiceArticle::class.java)
        intent.putExtra("dataToSend", dataArticle)
        startActivity(intent)

    }
}