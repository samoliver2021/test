package com.wibu.picodiploma.submissionakhir.Favorit

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wibu.picodiploma.submissionakhir.Helper.DBDiffCallback
import com.wibu.picodiploma.submissionakhir.Detail.DetailuserActivity
import com.wibu.picodiploma.submissionakhir.Main.NamaUN
import com.wibu.picodiploma.submissionakhir.R
import com.wibu.picodiploma.submissionakhir.databinding.TempleteFavoritBinding

class FavoritAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoritAdapter.FavoritViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null


    var listFavorit = ArrayList<NamaUN>()
        set(listFavorit) {
            if (listFavorit.size >= 0) {
                this.listFavorit.clear()
            }
            this.listFavorit.addAll(listFavorit)

            notifyDataSetChanged()
        }

    fun setListUN (listFavorit: ArrayList<NamaUN>) {
        val diffCallback = DBDiffCallback(this.listFavorit,listFavorit)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorit.clear()
        this.listFavorit.addAll(listFavorit)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritAdapter.FavoritViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.templete_favorit, parent, false)
        return FavoritViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritAdapter.FavoritViewHolder, position: Int) {
        holder.bind(listFavorit[position])
    }

    override fun getItemCount(): Int = this.listFavorit.size


    inner class FavoritViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bindings = TempleteFavoritBinding.bind(itemView)

        fun bind(favorit: NamaUN) {
            with(bindings) {
                Glide.with(itemView.context)
                    .load(favorit.profile_picture)
                    .apply(RequestOptions().override(55, 55))
                    .into(ppUn)

                bindings.tvUN.text = favorit.username
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(favorit)

                    rvFavorit.setOnClickListener(
                        CustomOnItemClickListener(
                            adapterPosition,
                            object : CustomOnItemClickListener.OnItemClickCallback {
                                override fun onItemClicked(view: View, position: Int) {
                                    val intent = Intent(activity, DetailuserActivity::class.java)
                                    intent.putExtra(DetailuserActivity.EXTRA_POSITION, position)
                                    intent.putExtra(DetailuserActivity.EXTRA_FAVORIT, favorit)
                                    intent.putExtra(DetailuserActivity.EXTRA_DETAIL, favorit.username)
                                    activity.startActivity(intent)
                                }
                            })
                    )
                }


            }

        }

    }
}

interface  OnItemClickCallback {
    fun onItemClicked(data : NamaUN)
    {

    }

}
