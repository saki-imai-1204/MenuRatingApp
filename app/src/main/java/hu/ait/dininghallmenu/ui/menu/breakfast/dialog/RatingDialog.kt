package hu.ait.dininghallmenu.ui.menu.breakfast.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.dininghallmenu.databinding.DialogRatingBinding
import hu.ait.dininghallmenu.ui.menu.breakfast.adapter.BreakfastAdapter
import hu.ait.dininghallmenu.ui.menu.breakfast.data.MenuItem

class RatingDialog : DialogFragment(), AdapterView.OnItemSelectedListener  {

    private var dialogViewBinding: DialogRatingBinding? = null
    private val binding get() = dialogViewBinding!!
    lateinit var menuToRate: MenuItem

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // Are we in edit mode? - Have we received a Todo object to edit?
        if (arguments != null && requireArguments().containsKey(
                BreakfastAdapter.KEY_TODO_EDIT)) {
            menuToRate =
                requireArguments().getSerializable(
                    BreakfastAdapter.KEY_TODO_EDIT) as MenuItem

            // fix the string!
            dialogBuilder.setTitle(menuToRate.name)

        }

        dialogViewBinding = DialogRatingBinding.inflate(
            requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding!!.root)

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
            val msg = binding.rBar.rating.toString().toFloat()
//            menuToRate.rating = msg
            val postsCollection = FirebaseFirestore.getInstance()
                .collection(MenuItemDialog.COLLECTION_ITEMS)
//            var menuItem = postsCollection.whereEqualTo("name", menuToRate.name)
//            menuItem.rating = msg
        }

        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }


        return dialogBuilder.create()
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long){
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}