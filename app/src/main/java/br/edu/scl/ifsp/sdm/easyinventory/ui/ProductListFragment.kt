package br.edu.scl.ifsp.sdm.easyinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.scl.ifsp.sdm.easyinventory.R
import br.edu.scl.ifsp.sdm.easyinventory.adapter.ProductAdapter
import br.edu.scl.ifsp.sdm.easyinventory.viewmodel.ProductViewModel
import br.edu.scl.ifsp.sdm.easyinventory.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding?= null
    private val binding get() = _binding!!
    lateinit var productAdapter: ProductAdapter
    lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentProductListBinding.inflate(inflater,container,false)

        binding.addProductBt.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_registerFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost= requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchView = menu.findItem(R.id.searchIt).actionView as SearchView

                44
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        productAdapter.filter.filter(p0)
                        return true
                    }
                })
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun configureRecyclerView()
    {

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        viewModel.allProducts.observe(viewLifecycleOwner) { list ->
            list?.let {
                productAdapter.updateList(list)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter()
        recyclerView.adapter = productAdapter

        val listener = object : ProductAdapter.ProductListener {
            override fun onItemClick(pos: Int) {
                val c = productAdapter.productFilterList[pos]

                val bundle = Bundle()
                bundle.putInt("productCode", c.productCode)

                findNavController().navigate(
                    R.id.action_productListFragment_to_productDetailsFragment,
                    bundle
                )

            }
        }
        productAdapter.setClickListener(listener)
    }

}