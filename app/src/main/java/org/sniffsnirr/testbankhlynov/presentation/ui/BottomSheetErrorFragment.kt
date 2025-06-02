package org.sniffsnirr.testbankhlynov.presentation.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.sniffsnirr.testbankhlynov.databinding.FragmentErrorBinding

@AndroidEntryPoint
class BottomSheetErrorFragment  : BottomSheetDialogFragment() {

    private var _binding: FragmentErrorBinding?=null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentErrorBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = arguments?.getString(ERROR_MESS)
        with(binding) {
            errorMessage.text = text
            closeButton.setOnClickListener { dismiss() }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    companion object{
        const val ERROR_MESS="ERROR_MESS"
    }
}