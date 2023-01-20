package hu.ait.dininghallmenu.ui.menu.breakfast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.*
import hu.ait.dininghallmenu.databinding.FragmentBreakfastBinding
import hu.ait.dininghallmenu.ui.menu.breakfast.adapter.BreakfastAdapter
import hu.ait.dininghallmenu.ui.menu.breakfast.data.MenuItem
import hu.ait.dininghallmenu.ui.menu.breakfast.dialog.MenuItemDialog

class BreakfastFragment : Fragment(){

    private lateinit var adapter: BreakfastAdapter
    private var _binding: FragmentBreakfastBinding? = null
    private val binding get() = _binding!!
    private var snapshotListener : ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreakfastBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = BreakfastAdapter(context)
        binding.recyclerBreakfast.adapter = adapter

        binding.fabAdd.setOnClickListener { view ->
            val menuItemDialog = MenuItemDialog()
            menuItemDialog.show(parentFragmentManager, "ItemDialog")
//            val foodDialog = FoodDialog(this)
//            foodDialog.show(getParentFragmentManager(), "FoodDialog")
        }

        queryItems()

        return root
    }

    fun queryItems(){
        val queryItems = FirebaseFirestore.getInstance().collection(
            MenuItemDialog.COLLECTION_ITEMS)

//        val orderedQueryItems = FirebaseFirestore.getInstance().collection(
//            MenuItemDialog.COLLECTION_ITEMS).orderBy()

        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        context, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val post = docChange.document.toObject(MenuItem::class.java)
                        adapter.addPost(post, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        adapter.removePostByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    }
                }
            }
        }
        snapshotListener = queryItems.addSnapshotListener(eventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        snapshotListener?.remove()
    }

    companion object {
        @JvmStatic
        fun newInstance() = BreakfastFragment()
    }
}