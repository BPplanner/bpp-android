package com.bpplanner.bpp.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bpplanner.bpp.databinding.DialogDatepickerBinding
import com.bpplanner.bpp.databinding.DialogReservationOkBinding
import com.bpplanner.bpp.ui.common.base.BaseDialogFragment

class OkDialog : BaseDialogFragment<DialogReservationOkBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogReservationOkBinding {
        return DialogReservationOkBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.ok.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
        )
    }
}