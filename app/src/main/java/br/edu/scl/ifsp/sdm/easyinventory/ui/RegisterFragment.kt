package br.edu.scl.ifsp.sdm.easyinventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.scl.ifsp.sdm.easyinventory.R
import br.edu.scl.ifsp.sdm.easyinventory.data.Product
import br.edu.scl.ifsp.sdm.easyinventory.viewmodel.ProductViewModel
import br.edu.scl.ifsp.sdm.easyinventory.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private var _binding:FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this) .get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.register_menu,menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean{
                return when(menuItem.itemId){
                    R.id.saveProductBt -> {
                        val productName = binding.commonLayout.productNameEt.text.toString()
                        val quantity = binding.commonLayout.quantityEt.text.toString().toIntOrNull()?:0
                        val description = binding.commonLayout.descriptionEt.text.toString()
                        val providerContact = binding.commonLayout.providerContactEt.text.toString()
                        val product = Product(0, productName,description,quantity,providerContact)
                        viewModel.insert(product)
                        Snackbar.make(binding.root, "Product Added", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()

                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }
}