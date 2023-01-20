package hu.ait.dininghallmenu.ui.home.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.ait.dininghallmenu.R
import hu.ait.dininghallmenu.databinding.DiningHallRowBinding
import hu.ait.dininghallmenu.ui.home.data.DiningHall

class DiningHallAdapter : RecyclerView.Adapter<DiningHallAdapter.ViewHolder>{
    // need a context so you can refer to the activity
    var context: Context?
    // store list for post Objs
    var  diningHallList = mutableListOf<DiningHall>()
    // store list for Post IDS (this is bc Firebase store post ids a lil diff)
    var  postKeys = mutableListOf<String>()
    lateinit var itemClickListener: OnItemClickListener
    lateinit var binding: DiningHallRowBinding

    constructor(context: Context?) : super() {
        this.context = context
    }

    inner class ViewHolder(val binding: DiningHallRowBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.placeHolder
                .setOnClickListener(this)
        }

        override fun onClick(view: View) {
//            var activity = view.context
//            var myFragment = DetailFragment()
//            activity.getSupportFragmentManager.beginTransaction().
//            replace(R.id.navigation_home, myFragment).addToBackStack(null).commit()

//            val activity = context as AppCompatActivity
//            activity.supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.container, DetailFragment.newInstance())
//                .commitNow()
//            findNavController().navigate(
//                HomeFragmentDirections.actionMainFragmentToDetailsFragment2(
//                    Item(
//                        binding.etItemName.text.toString(),
//                        binding.etItemPrice.text.toString().toInt())
//                )
//            )
            return itemClickListener.onItemClick(binding.root, adapterPosition)
        }


        fun bind(diningHall: DiningHall) {
            binding.tvName.text = diningHall.name
            when {
                binding.tvName.text == "Dana" -> {
                    context?.let {
                        Glide.with(it)
                            .load(
                                R.drawable.dana)
                            .into(binding.ivImage)
                    }
                }
                binding.tvName.text == "Bob's" -> {
                    context?.let {
                        Glide.with(it)
                            .load(
                                R.drawable.bobs
                            )
                            .into(binding.ivImage)
                    }
                }
                else -> {
                    context?.let {
                        Glide.with(it)
                            .load(
                                R.drawable.foss)
                            .into(binding.ivImage)
                    }
                }
            }

//            var imgId = context?.resources?.getIdentifier(diningHall.name, "drawable", context?.packageName)
//            context?.let {
//                Glide.with(it)
//                    .load(
//                        imgId)
//                    .into(binding.ivImage)
//            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DiningHallRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diningHallList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var diningHall = diningHallList.get(holder.adapterPosition)
        holder.bind(diningHall)

        var resourceId = context?.resources?.getIdentifier(diningHall.name, "drawable", context?.packageName)

        val photo = resourceId?.let { BitmapFactory.decodeResource(context?.resources, it) }
        if (photo != null) {
            Palette.from(photo).generate { palette ->
                val bgColor = palette!!.getMutedColor(
                    ContextCompat.getColor(context!!,
                    android.R.color.black))
                binding.placeNameHolder.setBackgroundColor(bgColor)
            }
        }
    }

    fun addPost(diningHall: DiningHall, key: String) {
        diningHallList.add(diningHall)
        postKeys.add(key)
        //notifyDataSetChanged()
        notifyItemInserted(diningHallList.lastIndex)
    }
}