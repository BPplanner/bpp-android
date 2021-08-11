package com.bpplanner.bpp.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bpplanner.bpp.databinding.DialogDatepickerBinding
import com.bpplanner.bpp.ui.common.base.BaseDialogFragment


class DatePickerDialog : BaseDialogFragment<DialogDatepickerBinding>() {
    private var onConfirmClickListener: ((String) -> Unit)? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogDatepickerBinding {
        return DialogDatepickerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.datePicker.minDate = System.currentTimeMillis() + 24 * 60 * 60 * 1000

            b.btnCancel.setOnClickListener {
                dismiss()
            }

            b.btnConfirm.setOnClickListener {
                val date = "${b.datePicker.year}-${b.datePicker.month+1}-${b.datePicker.dayOfMonth}"
                onConfirmClickListener?.invoke(date)
                dismiss()
            }
        }
    }

    fun setOnConfirmClickListener(listener: ((String) -> Unit)) : DatePickerDialog{
        onConfirmClickListener = listener
        return this
    }


}