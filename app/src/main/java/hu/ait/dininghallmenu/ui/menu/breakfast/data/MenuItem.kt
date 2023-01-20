package hu.ait.dininghallmenu.ui.menu.breakfast.data

data class MenuItem(
    var name: String = "",
    var rating: Float = 0f
): java.io.Serializable {
    //serializable -- automatically serializes into a long string
    //only works if all the arguments are primitive types
}