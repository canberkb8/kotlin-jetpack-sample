package com.canberkbbc.kotlin_countries.ui.countries

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.canberkbbc.kotlin_countries.R
import com.canberkbbc.kotlin_countries.core.BaseFragment
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import com.canberkbbc.kotlin_countries.databinding.FragmentCountriesBinding
import com.canberkbbc.kotlin_countries.utils.extensions.changeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_countries.*

@AndroidEntryPoint
class CountriesFragment : BaseFragment<FragmentCountriesBinding>() {

    private lateinit var viewModel: CountriesViewModel
    private val countriesAdapter = CountriesAdapter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_countries
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.lifecycleOwner = viewLifecycleOwner


        viewModel = ViewModelProviders.of(this@CountriesFragment).get(CountriesViewModel::class.java)
        viewModel.refreshData()

        initCountriesRecycler()
        observeLiveData()
        onItemClicked()
    }

    private fun  observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer{countires->
            countires?.let {
                viewBinding.recyclerCountries.visibility =View.VISIBLE
                countriesAdapter.updateCountryList(countires)
                countriesAdapter.countryList = countires as MutableList<CountryModel>
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer{error->
            error?.let {
                if (it){
                    viewBinding.countryError.visibility = View.VISIBLE
                }else{
                    countryError.visibility = View.GONE
                }
            }
        })
        viewModel.countreyLoading.observe(viewLifecycleOwner, Observer{loading->
            loading?.let {
                if (it){
                    viewBinding.countryLoadingProgress.visibility= View.VISIBLE
                    viewBinding.recyclerCountries.visibility = View.GONE
                    viewBinding.countryError.visibility = View.GONE
                }else{
                    viewBinding.countryLoadingProgress.visibility = View.GONE
                }
            }
        })
    }

    private fun initCountriesRecycler() {
        viewBinding.recyclerCountries.apply {
            setHasFixedSize(true)
            adapter = countriesAdapter
            layoutManager = LinearLayoutManager(this@CountriesFragment.context)
        }
    }

    private fun onItemClicked() {
        /*viewBinding.btnChange.setOnClickListener{
            mainAct?.changeFragment(R.id.secondFragment);
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(5)
            mainAct?.changeFragment(action)
        }*/
        countriesAdapter.setOnItemClickListener { countryData:CountryModel->
            val action = CountriesFragmentDirections.actionCountriesFragmentToCountryDetailFragment(countryData)
            mainAct?.changeFragment(action)
        }
        viewBinding.lytRefresh.setOnRefreshListener {
            viewBinding.recyclerCountries.visibility = View.GONE
            viewBinding.countryError.visibility = View.GONE
            viewBinding.countryLoadingProgress.visibility = View.VISIBLE
            viewModel.refresgFromAPI()
            viewBinding.lytRefresh.isRefreshing = false
        }
    }
}