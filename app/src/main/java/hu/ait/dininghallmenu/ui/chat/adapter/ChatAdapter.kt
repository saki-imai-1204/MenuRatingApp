package hu.ait.dininghallmenu.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.dininghallmenu.databinding.ChatRowBinding
import hu.ait.dininghallmenu.ui.chat.CreatePostActivity
import hu.ait.dininghallmenu.ui.chat.data.Post

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    // need a context so you can refer to the activity
    var context: Context?
    // needs to store user id (who is the user id) <-- to compare if we should show del button or not
    var currentUid: String
    // store list for post Objs
    var  postsList = mutableListOf<Post>()
    // store list for Post IDS (this is bc Firebase store post ids a lil diff)
    var  postKeys = mutableListOf<String>()

    constructor(context: Context?, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChatRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postsList.get(holder.adapterPosition)
        holder.bind(post)
    }

    fun addPost(post: Post, key: String) {
        postsList.add(post)
        postKeys.add(key)
        notifyDataSetChanged()
        notifyItemInserted(postsList.lastIndex)
    }

    // when somebody else removes an object
    fun removePostByKey(key: String) {
        val index = postKeys.indexOf(key)
        if (index != -1) {
            postsList.removeAt(index)
            postKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(val binding: ChatRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post) {
            binding.tvAuthor.text = post.author
//            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body

            if (currentUid == post.uid) {
                binding.btnDelete.visibility = View.VISIBLE
            }
            else {
                binding.btnDelete.visibility = View.GONE
            }

            binding.btnDelete.setOnClickListener {
                FirebaseFirestore.getInstance().collection(
                    CreatePostActivity.COLLECTION_POSTS
                ).document(
                    postKeys[adapterPosition]
                ).delete()
            }

            if (post.imgUrl != "") {
                binding.ivPhoto.visibility = View.VISIBLE

                context?.let {
                    Glide.with(it).load(post.imgUrl).into(
                        binding.ivPhoto
                    )
                }
            } else {
                binding.ivPhoto.visibility = View.GONE
            }
        }
    }

}