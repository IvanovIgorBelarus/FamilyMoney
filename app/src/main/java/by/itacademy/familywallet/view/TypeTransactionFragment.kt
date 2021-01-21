package by.itacademy.familywallet.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.itacademy.familywallet.R
import by.itacademy.familywallet.databinding.FragmentTypeTransactionBinding
import by.itacademy.familywallet.presentation.ItemClickListener
import by.itacademy.familywallet.presentation.TypeTransactionAdapter
import org.koin.android.ext.android.inject

class TypeTransactionFragment : Fragment(), ItemClickListener {
    private lateinit var fragmentType: String
    private lateinit var binding: FragmentTypeTransactionBinding
    private val typeTransactionAdapter: TypeTransactionAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_type_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTypeTransactionBinding.bind(view)
        binding.typeAdapter.apply {
            layoutManager = LinearLayoutManager(this@TypeTransactionFragment.context)
            adapter = typeTransactionAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        typeTransactionAdapter.update(fragmentType)
    }
    companion object {
        fun newInstance(type: String) = TypeTransactionFragment().apply { fragmentType = type }
    }

    override fun onClick() {
        startActivity(Intent(this.context, TransactionActivity::class.java))
    }
}