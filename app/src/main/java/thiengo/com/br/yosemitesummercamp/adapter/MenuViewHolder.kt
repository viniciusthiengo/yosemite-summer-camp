package thiengo.com.br.yosemitesummercamp.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import thiengo.com.br.yosemitesummercamp.R
import thiengo.com.br.yosemitesummercamp.domain.MenuItem
import thiengo.com.br.yosemitesummercamp.domain.MenuItemStatus


class MenuViewHolder(
    val adapter: MenuAdapter,
    val changeButtonStatusCallback: (List<MenuItem>)->Unit,
    itemView: View ) : RecyclerView.ViewHolder( itemView ), View.OnClickListener {

    var cvIcon: CardView
    var ivSelected: ImageView
    var vvGradient: View
    var ivIcon: ImageView
    var tvLabel: TextView

    init {
        itemView.setOnClickListener( this )

        cvIcon = itemView.findViewById( R.id.cv_icon )
        ivSelected = itemView.findViewById( R.id.iv_selected )
        vvGradient = itemView.findViewById( R.id.vv_gradient )
        ivIcon = itemView.findViewById( R.id.iv_icon )
        tvLabel = itemView.findViewById( R.id.tv_label )
    }

    fun setModel( item: MenuItem ) {
        tvLabel.text = item.label
        ivIcon.setImageResource( item.icon )
        ivIcon.contentDescription = item.label

        setStyle( item = item )
    }

    private fun setStyle( item: MenuItem ){
        /*
         * Até a última declaração de variavel mutável (var)
         * tem toda a definição de estilo de um
         * "item não selecionado" (isSelected == false) que
         * é o valor inicial de cada item de itinerário em
         * tela.
         * */
            var cvBackgroundResource = R.drawable.cv_background_normal
            var ivSelectedVisibility = View.INVISIBLE
            var vvGradientVisibility = View.VISIBLE
            var tvLabelColor = R.color.colorDarkGrey
            var ivIconColor = ResourcesCompat.getColor(
                adapter.context.resources,
                R.color.colorDarkPurple,
                null
            )

        if( item.isSelected == MenuItemStatus.SELECTED ){
            /*
             * A seguir toda a definição de valores de um
             * "item selecionado" (isSelected == true).
             * */
                cvBackgroundResource = R.drawable.cv_background_selected
                ivSelectedVisibility = View.VISIBLE
                vvGradientVisibility = View.INVISIBLE
                ivIconColor = Color.WHITE
                tvLabelColor = R.color.colorDarkPurple
        }

        cvIcon.setBackgroundResource( cvBackgroundResource )
        ivSelected.visibility = ivSelectedVisibility
        vvGradient.visibility = vvGradientVisibility

        /*
         * A invocação setColorFilter( ivIconColor, PorterDuff.Mode.SRC_IN )
         * informa que somente os pixels não transparentes da
         * imagem (dentro do ImageView) é que devem receber a
         * cor definida em ivIconColor.
         *
         * A constante PorterDuff.Mode.SRC_IN é responsável pela
         * regra de negócio "somente os pixels não transparentes (...)".
         * */
            ivIcon.setColorFilter( ivIconColor, PorterDuff.Mode.SRC_IN )

        tvLabel.setTextColor(
            ResourcesCompat.getColor(
                adapter.context.resources,
                tvLabelColor,
                null
            )
        )
    }

    override fun onClick( view: View ) {
        /*
         * O algoritmo a seguir é responsável por colocar, no
         * item acionado pelo usuário, o status e estilo adequados
         * de acordo com o status atual do item.
         *
         * Com a função with() sendo utilizada nós podemos abreviar
         * o código. Dentro do bloco desta função podemos utilizar
         * "this" ao invés de "adapter.items[ adapterPosition ]".
         * */
            with( adapter.items[ adapterPosition ] ){

                this.isSelected = when( this.isSelected ){
                    MenuItemStatus.SELECTED -> MenuItemStatus.NOT_SELECTED
                    else -> MenuItemStatus.SELECTED
                }
            }
            adapter.notifyItemChanged( adapterPosition )

        changeButtonStatusCallback( adapter.items )
    }
}