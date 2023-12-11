package br.edu.scl.ifsp.sdm.easyinventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.scl.ifsp.sdm.easyinventory.data.Product
import br.edu.scl.ifsp.sdm.easyinventory.databinding.ProductCellBinding

    abstract class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(),
    Filterable {

    var listener: ProductListener?=null

    var productList = ArrayList<Product>()
    var productFilterList = ArrayList<Product>()

    private lateinit var binding: ProductCellBinding

    fun updateList(newList: List<Product> ){
        productList = newList as ArrayList<Product>
        productFilterList = productList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ProductListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        binding = ProductCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productNameVh.text = productFilterList[position].productName
        holder.quantityVh.text = productFilterList[position].quantity.toString()
        holder.providerContactVh.text = productFilterList[position].providerContact
    }

    override fun getItemCount(): Int {
        return productFilterList.size
    }

    inner class ProductViewHolder(view: ProductCellBinding): RecyclerView.ViewHolder(view.root)
    {
        val productNameVh = view.productNameTv
        val quantityVh = view.quantityTv
        val providerContactVh = view.providerContactTv

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }
    interface ProductListener {
        fun onItemClick(pos: Int)
    }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(p0: CharSequence?): FilterResults {
                    if (p0.toString().isEmpty())
                        productFilterList = productList
                    else {
                        val resultList = ArrayList<Product>()
                        for (row in productList)
                            if (row.productName.lowercase().contains(p0.toString().lowercase()))
                                resultList.add(row)
                        productFilterList = resultList
                    }
                    val filterResults = FilterResults()
                    43
                    filterResults.values = productFilterList
                    return filterResults
                }

                override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                    productFilterList = p1?.values as ArrayList<Product>
                    notifyDataSetChanged()
                }

            }
        }
}