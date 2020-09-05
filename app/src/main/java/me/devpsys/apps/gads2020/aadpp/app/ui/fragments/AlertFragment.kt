package me.devpsys.apps.gads2020.aadpp.app.ui.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import me.devpsys.apps.gads2020.aadpp.app.R
import me.devpsys.apps.gads2020.aadpp.app.databinding.FragmentAlertBinding

private const val ARG_PARAM_TYPE = "alert_type"

/**
 * A simple [Fragment] subclass.
 * Use the [AlertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlertFragment : DialogFragment() {

    private var mAlertType: String? = null
    private lateinit var mBinding: FragmentAlertBinding

    private lateinit var listener: OnRequestConfirmationListener

    interface OnRequestConfirmationListener {
        fun onConfirmed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mAlertType = it.getString(ARG_PARAM_TYPE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_alert,
            null,
            false
        )

        mBinding.type = mAlertType

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            mBinding.ibCancel.setOnClickListener {
                dismiss()
            }
            mBinding.btnYes.setOnClickListener {
                listener.onConfirmed()
                dismiss()
            }

            builder.setView(mBinding.root)
            builder.setCancelable(mAlertType != "confirmation")
            builder.create()
        } ?: throw IllegalStateException("Parent cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = activity as OnRequestConfirmationListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement OnRequestConfirmationListener")
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param alertType Parameter 1.
         * @return A new instance of fragment AlertFragment.
         */
        @JvmStatic
        fun newInstance(alertType: String) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_TYPE, alertType)
                }
            }
    }
}