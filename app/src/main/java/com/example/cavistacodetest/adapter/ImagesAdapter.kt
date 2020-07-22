package com.example.cavistacodetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cavistacodetest.databinding.ItemImageShapesBinding
import com.example.cavistacodetest.model.Image
import com.squareup.picasso.Picasso


class ImagesAdapter(var list:List<Image>,val recyclerView:RecyclerView ,val clickListener: (Image) -> Unit) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>()  {
    var mContext: Context? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder(ItemImageShapesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Picasso.with(mContext)
            .load(list[position].link)
            .fit()
            //.error(R.drawable.placeholder)
            .into(holder.binding.ivShapes)
        holder.binding.ivShapes.setOnClickListener {
            clickListener.invoke(list[position])
        }

    }



    inner class ViewHolder(val binding: ItemImageShapesBinding) :
        RecyclerView.ViewHolder(binding.root){}


}