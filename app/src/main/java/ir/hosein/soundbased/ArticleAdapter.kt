package ir.hosein.soundbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.hosein.soundbased.ApiManager.model.ArticleData
import ir.hosein.soundbased.databinding.ItemArticleBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class ArticleAdapter(
    private val data: ArrayList<ArticleData.ArticleDataItem>,
    private val recyclerCallback: RecyclerCallback
) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    lateinit var binding: ItemArticleBinding

    inner class ArticleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


        fun bindData(dataArticle:ArticleData.ArticleDataItem) {

            binding.itemTvArticleName.text = dataArticle.subject.split(',')[0]
            binding.itemTvDate.text = dataArticle.subject.split(',')[1]
            binding.itemTvAuthorName.text = dataArticle.subject.split(',')[2]
            binding.itemTvNumber.text = dataArticle.subject.split(',')[3]

            Glide
                .with(itemView)
                .load(dataArticle.picUrl)
                .transform(RoundedCornersTransformation(32, 8))
                .into(binding.itemImgPlant)

            itemView.setOnClickListener {
                recyclerCallback.onArticleItemClicked(dataArticle)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemArticleBinding.inflate(inflater, parent, false)

        return ArticleViewHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<ArticleData.ArticleDataItem>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface RecyclerCallback {

        fun onArticleItemClicked(dataArticle:ArticleData.ArticleDataItem)

    }
}