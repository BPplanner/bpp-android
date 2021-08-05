package com.bpplanner.bpp.ui.setting

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity

class LicenceActivity : BaseActivity(){

    private lateinit var binding: RecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = LicenceAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

}