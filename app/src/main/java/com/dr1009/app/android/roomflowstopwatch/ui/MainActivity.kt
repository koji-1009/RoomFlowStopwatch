package com.dr1009.app.android.roomflowstopwatch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.dr1009.app.android.roomflowstopwatch.R
import com.dr1009.app.android.roomflowstopwatch.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>
    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.time.observe(this) {
            Snackbar.make(binding.root, "sec: $it", Snackbar.LENGTH_LONG).show()
        }
    }
}
