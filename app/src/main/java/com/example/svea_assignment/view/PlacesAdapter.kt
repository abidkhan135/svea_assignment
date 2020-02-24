package com.example.svea_assignment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.svea_assignment.R
import com.example.svea_assignment.model.Place
import com.example.svea_assignment.model.Places
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class PlacesAdapter(private var places: ArrayList<Place>) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    fun updateAnimalList(newplaceList: List<Place>) {
        places.clear()
        places.addAll(newplaceList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("THe name is "+places[position].name)
        holder.itemView.name.text = places[position].name
        holder.itemView.description.text = places[position].description
        holder.itemView.placeLayout.setOnClickListener {
            val action = ListFragmentDirections.actionDetail()
            Navigation.findNavController(holder.itemView).navigate(action)
        }

    }
}