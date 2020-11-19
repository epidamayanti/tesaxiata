package edf.project.tesaxiata.model

data class ResponseNews (
        val status:String,
        val articles: MutableList<NewsModel>
)