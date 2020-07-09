package thiengo.com.br.yosemitesummercamp

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_itinerary.*
import kotlinx.android.synthetic.main.content_itinerary.*
import thiengo.com.br.yosemitesummercamp.adapter.MenuAdapter
import thiengo.com.br.yosemitesummercamp.data.Menu
import thiengo.com.br.yosemitesummercamp.domain.MenuItem
import thiengo.com.br.yosemitesummercamp.domain.MenuItemStatus

class ItineraryActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ){
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_itinerary )
        setSupportActionBar( toolbar )

        /*
         * HackCode para colocar um ícone  no canto superior
         * esquerdo da tela (lado esquerdo da Toolbar).
         * */
            supportActionBar?.setDisplayHomeAsUpEnabled( true );
            supportActionBar?.setDisplayShowHomeEnabled( true );

        initItineraryMenu()
    }

    private fun initItineraryMenu(){

        val layoutManager = GridLayoutManager(
            this,
            Menu.NUMBER_COLUMNS
        )
        rv_menu_items.layoutManager = layoutManager

        rv_menu_items.setHasFixedSize( true )
        rv_menu_items.adapter = MenuAdapter(
            context = this,
            items = Menu.getItems(),
            changeButtonStatusCallback = {
                items -> changeButtonStatus( items )
            }
        )
    }

    /*
     * Método responsável por atualizar o status de clique e o
     * background do botão "CONTINUE".
     *
     * Se houver ao menos um item selecionado, então o botão
     * fica como "disponivel para clique", com o background
     * laranja (bt_orange_ripple) aplicado a ele.
     * */
    private fun changeButtonStatus( items: List<MenuItem> ){

        var isEnabled = false
        var backgroundId = R.color.colorMediumGrey

        /*
         * O método any() precisa encontrar somente um item que
         * retorne true para o predicato
         * "it.isSelected == MenuItemStatus.SELECTED" que assim
         * ele para com a execução e retorna true para a variável
         * status.
         * */
        val status = items.any{
            it.isSelected == MenuItemStatus.SELECTED
        }

        if( status ){
            isEnabled = true

            /*
             * Abaixo da API 21 (Lollipop) do Android não é possível
             * utilizar a API Ripple. Uma exceção será gerada caso
             * isso ocorra.
             *
             * Por isso o bloco condicional a seguir.
             * */
            backgroundId = if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                R.drawable.bt_orange_ripple
            }
            else{
                R.color.colorAccent
            }
        }

        bt_continue.isEnabled = isEnabled
        bt_continue.background = ResourcesCompat.getDrawable(
            resources,
            backgroundId,
            null
        )
    }
}