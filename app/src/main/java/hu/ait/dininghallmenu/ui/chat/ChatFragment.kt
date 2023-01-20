package hu.ait.dininghallmenu.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.dininghallmenu.databinding.FragmentChatBinding
import hu.ait.dininghallmenu.ui.chat.adapter.ChatAdapter
import hu.ait.dininghallmenu.ui.chat.data.Post

class ChatFragment : Fragment(){

    private lateinit var adapter: ChatAdapter
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private var snapshotListener : ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = ChatAdapter(context, FirebaseAuth.getInstance().currentUser!!.uid)
        binding.recyclerChat.adapter = adapter

        binding.fabAdd.setOnClickListener { view ->
            startActivity(
                Intent(
                    context, CreatePostActivity::class.java,
                ),
            )
        }

        queryItems()

        return root
    }

    fun queryItems(){
        val queryItems = FirebaseFirestore.getInstance().collection(
            CreatePostActivity.COLLECTION_POSTS)

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
                        val post = docChange.document.toObject(Post::class.java)
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
        fun newInstance() = ChatFragment()
    }
}