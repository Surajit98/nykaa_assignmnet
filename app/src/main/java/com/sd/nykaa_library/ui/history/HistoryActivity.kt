package com.sd.nykaa_library.ui.history

import androidx.databinding.DataBindingUtil
import com.sd.nykaa_library.R
import com.sd.nykaa_library.databinding.ActivityHistoryBinding
import com.sd.nykaa_library.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity() {

    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var binding: ActivityHistoryBinding
    private var adapter: HistoryListAdapter? = null


    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        binding.apply {
            lifecycleOwner=this@HistoryActivity
        }
        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun init() {
        setList()
        addObservers()
    }



    private fun addObservers() {
        viewModel.getAllSessions().observe(this, {
            adapter?.setData(it)
        })
    }

    private fun setList() {
        adapter = HistoryListAdapter()
        binding.list.adapter = adapter
    }
}