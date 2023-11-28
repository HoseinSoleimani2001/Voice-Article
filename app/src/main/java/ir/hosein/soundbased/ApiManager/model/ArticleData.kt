package ir.hosein.soundbased.ApiManager.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ArticleData : ArrayList<ArticleData.ArticleDataItem>(){
    @Parcelize
    data class ArticleDataItem(
        @SerializedName("picUrl")
        val picUrl: String,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("text")
        val text: String,
        @SerializedName("voiceUrl")
        val voiceUrl: String
    ) : Parcelable
}