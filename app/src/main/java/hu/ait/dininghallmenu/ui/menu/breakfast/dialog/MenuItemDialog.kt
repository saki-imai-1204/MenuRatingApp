package hu.ait.dininghallmenu.ui.menu.breakfast.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.dininghallmenu.databinding.DialogBinding
import hu.ait.dininghallmenu.ui.menu.breakfast.data.MenuItem

class MenuItemDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    companion object {
        const val COLLECTION_ITEMS = "breakfastItems"
        const val REQUEST_CAMERA_PERMISSION = 1001
    }

    private var dialogViewBinding: DialogBinding? = null
    private val binding get() = dialogViewBinding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("New menu item")

        dialogViewBinding = DialogBinding.inflate(
            requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding!!.root)

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
            if (dialogViewBinding!!.etItem.text!!.isNotEmpty()){
                uploadItem()
            }
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

    private fun uploadItem(imgUrl: String = "") {
        val menuItem = MenuItem(
            dialogViewBinding!!.etItem.text!!.toString(),
            0f
        )

        val postsCollection = FirebaseFirestore.getInstance()
            .collection(COLLECTION_ITEMS)

        postsCollection.add(menuItem)
//            .addOnSuccessListener {
//                Toast.makeText(context,
//                    "Post saved", Toast.LENGTH_SHORT).show()
//
//            }
//            .addOnFailureListener{
//                Toast.makeText(context,
//                    "Error: ${it.message}",
//                    Toast.LENGTH_SHORT).show()
//            }
    }

}