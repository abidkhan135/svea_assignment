package com.example.svea_assignment.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.svea_assignment.R
import com.example.svea_assignment.databinding.FragmentDetailBinding
import com.example.svea_assignment.model.Place


class DetailFragment : Fragment() {

    var place: Place? = null
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            place = DetailFragmentArgs.fromBundle(it).placeList
        }
        dataBinding.place = place
    }


}
