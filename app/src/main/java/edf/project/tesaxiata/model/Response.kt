package edf.project.tesaxiata.model

data class Response (
        val status:String,
        val sources: MutableList<SourceModel>
)