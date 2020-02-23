package com.example.svea_assignment.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.svea_assignment.R
import com.example.svea_assignment.model.Place
import com.example.svea_assignment.model.Places
import com.example.svea_assignment.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private var listAdapter = PlacesAdapter(arrayListOf())

    private val placesListDataObserver = Observer<List<Place>> { list ->
        list?.let {
            placeList.visibility = View.VISIBLE
            println("Inside list fragment $it")
           // listAdapter.updateAnimalList(it)
        }

    }
    private val errorLiveDataObserver = Observer<Boolean> { iserror ->
        loadError.visibility = if (iserror) View.VISIBLE else View.GONE

    }
    private val loadingLiveDataObserver = Observer<Boolean> { isloading ->
        loadingView.visibility = if (isloading) View.VISIBLE else View.GONE
        if (isloading) {
            placeList.visibility = View.GONE
            loadError.visibility = View.GONE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.place.observe(this, placesListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)

        viewModel.refresh()
        placeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

    }
}
