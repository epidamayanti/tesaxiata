package edf.project.tesaxiata.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edf.project.tesaxiata.R
import edf.project.tesaxiata.model.NewsModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val context: Context, private val items: List<NewsModel>, private val listener: (NewsModel) -> Unit)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(context: Context, items: NewsModel, listener: (NewsModel) -> Unit) {
            containerView.contentTextView.text = items.description
            containerView.titleTextView.text = items.title
            Picasso.with(context).load(items.urlToImage).into(containerView.newsImageView)

            containerView.setOnClickListener { listener(items) }
        }
    }

}

