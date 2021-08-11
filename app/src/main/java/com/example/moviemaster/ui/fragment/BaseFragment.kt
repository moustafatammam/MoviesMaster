package com.example.moviemaster.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<viewBinding : ViewDataBinding>: Fragment() {

    var binding: ViewDataBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding
    abstract val viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(binding).root
    }

    //You have to call your view model variable in your layout "viewmodel"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (binding as viewBinding).setVariable(BR.viewmodel, viewModel)
    }

}