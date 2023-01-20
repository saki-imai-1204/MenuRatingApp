package hu.ait.dininghallmenu.ui.menu.breakfast.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.dininghallmenu.databinding.ItemRowBinding
import hu.ait.dininghallmenu.ui.menu.breakfast.BreakfastDetailActivity
import hu.ait.dininghallmenu.ui.menu.breakfast.data.MenuItem
import hu.ait.dininghallmenu.ui.menu.breakfast.dialog.MenuItemDialog
import hu.ait.dininghallmenu.ui.menu.breakfast.dialog.RatingDialog


class BreakfastAdapter : RecyclerView.Adapter<BreakfastAdapter.ViewHolder>{
    // need a context so you can refer to the activity
    var context: Context?
    // needs to store user id (who is the user id) <-- to compare if we should show del button or not
    lateinit var currentUid: String
    // store list for post Objs
    var  itemsList = mutableListOf<MenuItem>()
    // store list for Post IDS (this is bc Firebase store post ids a lil diff)
    var  postKeys = mutableListOf<String>()

    constructor(context: Context?) : super() {
        this.context = context
    }

    companion object {
        const val KEY_TODO_EDIT = "KEY_TODO_EDIT"
    }

    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(menuItem: MenuItem) {
            binding.tvItemName.text = menuItem.name
            binding.rBar.rating = menuItem.rating

            binding.btnDelete.setOnClickListener {
                FirebaseFirestore.getInstance().collection(
                    MenuItemDialog.COLLECTION_ITEMS
                ).document(
                    postKeys[adapterPosition]
                ).delete()
            }

            binding.btnRate.setOnClickListener {
                val fragmentActivity = context as FragmentActivity
                val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
                val ratingDialog = RatingDialog()
                val bundle = Bundle()  //bundle object that holds arguments
                bundle.putSerializable(KEY_TODO_EDIT, menuItem)
                ratingDialog.arguments = bundle
                ratingDialog.show(fragmentManager, "RatingDialog")
            }

            binding.btnDetails.setOnClickListener {
                context!!.startActivity(Intent(context, BreakfastDetailActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = itemsList.get(holder.adapterPosition)
        holder.bind(post)
    }

    fun addPost(menuItem: MenuItem, key: String) {
        itemsList.add(menuItem)
        postKeys.add(key)
        //notifyDataSetChanged()
        notifyItemInserted(itemsList.lastIndex)
    }

//    // when I remove the post object
//    private fun removePost(index: Int) {
//        FirebaseFirestore.getInstance().collection(
//            CreatePostActivity.COLLECTION_POSTS).document(
//            postKeys[index]
//        ).delete()
//
//        postsList.removeAt(index)
//        postKeys.removeAt(index)
//        notifyItemRemoved(index)
//    }

    // when somebody else removes an object
    fun removePostByKey(key: String) {
        val index = postKeys.indexOf(key)
        if (index != -1) {
            itemsList.removeAt(index)
            postKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}