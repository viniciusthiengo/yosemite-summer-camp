package thiengo.com.br.yosemitesummercamp.domain

class MenuItem(
    val label: String,
    val icon: Int,
    var isSelected: MenuItemStatus = MenuItemStatus.NOT_SELECTED )