package org.sniffsnirr.testbankhlynov.presentation.ui.trackrv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sniffsnirr.testbankhlynov.R
import org.sniffsnirr.testbankhlynov.databinding.TrackRvItemBinding
import org.sniffsnirr.testbankhlynov.domain.entity.ArtistTopTrack
import org.sniffsnirr.testbankhlynov.presentation.ui.trackrv.ArtistTopTracksAdapter.TrackViewHolder

class ArtistTopTracksAdapter(
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var artistTopTrackList: List<ArtistTopTrack> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ArtistTopTrack>) {
        artistTopTrackList = list
        notifyDataSetChanged()
    }

    inner class TrackViewHolder(val binding: TrackRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder {
        val binding = TrackRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrackViewHolder,
        position: Int
    ) {
        val track = artistTopTrackList.getOrNull(position)
        track?.let {
            with(holder.binding) {
                itemText.text = track.name
                Glide
                    .with(itemImage.context)
                    .load(track.imageUrl)
                    .error(R.drawable.outline_no_photography_24)
                    .into(itemImage)
            }
        }
    }

    override fun getItemCount() = artistTopTrackList.size
}