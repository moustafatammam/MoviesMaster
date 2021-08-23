package com.example.moviemaster.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR

import androidx.lifecycle.ViewModel

abstract class BaseActivity<viewBinding : ViewDataBinding> : AppCompatActivity() {

    var binding: ViewDataBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> viewBinding
    abstract val viewModel: ViewModel

    //You have to call your view model variable in your layout "viewmodel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(binding).root)
        (binding as viewBinding).setVariable(BR.viewmodel, viewModel)

    }

    abstract fun setLayoutResourceId(): Int
}