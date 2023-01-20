package hu.ait.dininghallmenu.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.*
import hu.ait.dininghallmenu.databinding.FragmentHomeBinding
import hu.ait.dininghallmenu.ui.home.adapter.DiningHallAdapter
import hu.ait.dininghallmenu.ui.home.data.DiningHall

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
    lateinit private var adapter: DiningHallAdapter
    private var snapshotListener : ListenerRegistration? = null

    companion object {
        const val COLLECTION_ITEMS = "diningHallItems"
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val onItemClickListener = object : DiningHallAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
//            startActivity(
//                Intent(
//                    context, DetailActivity::class.java,
//                ),
//            )
            val intentDetails = Intent()
            intentDetails.setClass(
                context!!, DetailActivity::class.java
            )
            intentDetails.putExtra("key", position)
            context!!.startActivity(intentDetails)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerDiningHall.layoutManager = staggeredLayoutManager

        adapter = DiningHallAdapter(requireContext())
        binding.recyclerDiningHall.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)

        queryItems()

        return root
    }

    fun queryItems(){
        val queryItems = FirebaseFirestore.getInstance().collection(
            COLLECTION_ITEMS)

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
                        val post = docChange.document.toObject(DiningHall::class.java)
                        adapter.addPost(post, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        //adapter.removePostByKey(docChange.document.id)
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
    }
}