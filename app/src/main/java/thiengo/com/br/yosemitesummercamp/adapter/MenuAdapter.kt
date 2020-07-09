package thiengo.com.br.yosemitesummercamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thiengo.com.br.yosemitesummercamp.R
import thiengo.com.br.yosemitesummercamp.domain.MenuItem

class MenuAdapter(
    val context: Context,
    val items: List<MenuItem>,
    val changeButtonStatusCallback: (List<MenuItem>)->Unit )
    : RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int ) : MenuViewHolder {

        val layout = LayoutInflater
            .from( context )
            .inflate(
                R.layout.menu_item,
                parent,
                false
            )

        return MenuViewHolder(
            adapter = this,
            changeButtonStatusCallback = changeButtonStatusCallback,
            itemView = layout
        )
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int ){

        holder.setModel(
            item = items[ position ]
        )
    }

    override fun getItemCount() = items.size
}