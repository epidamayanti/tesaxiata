package edf.project.tesaxiata.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edf.project.tesaxiata.R
import edf.project.tesaxiata.model.SourceModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_source.view.*

class SourceAdapter(private val context: Context, private val items: List<SourceModel>, private val listener: (SourceModel) -> Unit)
    : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_source, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(items: SourceModel, listener: (SourceModel) -> Unit) {
            containerView.titleTextView.text = items.name
            containerView.contentTextView.text = items.description
            containerView.categoryTextView.text = items.category

            containerView.setOnClickListener { listener(items) }
        }
    }

}

