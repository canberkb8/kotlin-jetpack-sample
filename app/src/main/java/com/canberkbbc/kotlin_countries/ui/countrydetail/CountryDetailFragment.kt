package com.canberkbbc.kotlin_countries.ui.countrydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.canberkbbc.kotlin_countries.R
import com.canberkbbc.kotlin_countries.core.BaseFragment
import com.canberkbbc.kotlin_countries.databinding.FragmentCountryDetailBinding
import com.canberkbbc.kotlin_countries.ui.countries.CountriesAdapter
import com.canberkbbc.kotlin_countries.ui.countries.CountriesViewModel
import com.canberkbbc.kotlin_countries.utils.extensions.getImage
import com.canberkbbc.kotlin_countries.utils.extensions.placeHolderProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailFragment : BaseFragment<FragmentCountryDetailBinding>() {
    private lateinit var viewModel: CountryDetailViewModel
    private var countryUuid = 0
    override fun getLayoutRes(): Int {
        return R.layout.fragment_country_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryDetailFragmentArgs.fromBundle(it).countryData.uuid
        }

        viewBinding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProviders.of(this).get(CountryDetailViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)
        obserLiveData()
        onItemClicked()
    }

    private fun obserLiveData(){
        viewModel.countryDetailLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                viewBinding.countryDetail = country
            }
        })
    }

    private fun onItemClicked() {
        /*viewBinding.btnChange.setOnClickListener{
            mainAct?.changeFragment(R.id.secondFragment);
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(5)
            mainAct?.changeFragment(action)
        }*/
    }
}