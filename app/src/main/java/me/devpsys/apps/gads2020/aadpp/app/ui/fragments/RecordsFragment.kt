package me.devpsys.apps.gads2020.aadpp.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import me.devpsys.apps.gads2020.aadpp.app.adatpters.RecordsAdapter
import me.devpsys.apps.gads2020.aadpp.app.databinding.FragmentRecordsBinding
import me.devpsys.apps.gads2020.aadpp.app.vmodels.RecordsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RecordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordsFragment : Fragment() {

    private lateinit var mBinding: FragmentRecordsBinding
    private val viewModel: RecordsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRecordsBinding.inflate(inflater)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.lifecycleOwner = this
        mBinding.model = viewModel
        mBinding.rvRecords.adapter = RecordsAdapter()

        viewModel.loadRecords()
        viewModel.records().observe(viewLifecycleOwner, Observer {
            it?.let { records ->
                (mBinding.rvRecords.adapter as RecordsAdapter).records(records)
            }
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecordsFragment.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int) = RecordsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, sectionNumber)
            }
        }
    }
}