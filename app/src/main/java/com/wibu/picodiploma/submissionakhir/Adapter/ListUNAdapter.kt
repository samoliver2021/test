package com.wibu.picodiploma.submissionakhir.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wibu.picodiploma.submissionakhir.Detail.DetailuserActivity
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.databinding.ListUnBinding


class ListUNAdapter(private val listUN : ArrayList<NamaUN>) : RecyclerView.Adapter<ListUNAdapter.ListViewHolder>(){
    private var onItemClickCallback : OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
        DetailuserActivity.EXTRA_DETAIL


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ListUnBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUN[position])


    }

    override fun getItemCount(): Int = listUN.size

    inner class ListViewHolder(private val binding : ListUnBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (user : NamaUN) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(user.profile_picture)
                        .apply(RequestOptions().override(55, 55))
                        .into(ppUn)

                tvUN.text = user.username
                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(user)

                }

            }
        }

    }


    interface  OnItemClickCallback {
        fun onItemClicked(data : NamaUN)
        {

        }

    }
}

