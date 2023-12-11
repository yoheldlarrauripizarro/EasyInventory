package br.edu.scl.ifsp.sdm.easyinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import br.edu.scl.ifsp.sdm.easyinventory.R
import br.edu.scl.ifsp.sdm.easyinventory.data.Product
import br.edu.scl.ifsp.sdm.easyinventory.viewmodel.ProductViewModel
import br.edu.scl.ifsp.sdm.easyinventory.databinding.FragmentProductDetailsBinding
import com.google.android.material.snackbar.Snackbar

class ProductDetailsFragment: Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var product: Product
    lateinit var productNameText: EditText
    lateinit var descriptionText: EditText
    lateinit var quantityText: EditText
    lateinit var providerContactText: EditText
    lateinit var viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        33
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productNameText = binding.commonLayout.productNameEt
        descriptionText = binding.commonLayout.descriptionEt
        quantityText = binding.commonLayout.quantityEt
        providerContactText = binding.commonLayout.providerContactEt
        val productCode = requireArguments().getInt("productCode")
        viewModel.getProductById(productCode)
        viewModel.product.observe(viewLifecycleOwner) { result ->
            result?.let {
                product = result
                productNameText.setText(product.productName)
                descriptionText.setText(product.description)
                quantityText.setText(product.quantity.toString())
                providerContactText.setText(product.providerContact)
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.details_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.saveProductDetailsBt -> {
                        product.productName=productNameText.text.toString()
                        product.description=descriptionText.text.toString()
                        product.quantity=quantityText.text.toString().toInt()
                        product.providerContact=providerContactText.text.toString()
                        viewModel.update(product)
                        Snackbar.make(binding.root, "Product Updated", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    R.id.deleteProductDetailsBt->{
                        viewModel.delete(product)
                        Snackbar.make(binding.root, "Product Deleted", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
}
