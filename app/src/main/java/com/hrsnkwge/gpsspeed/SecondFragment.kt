package com.hrsnkwge.gpsspeed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrsnkwge.gpsspeed.databinding.FragmentSecondBinding
import java.io.File
import java.util.*

class SecondFragment : Fragment() {



    private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.filelist
        val viewManager = LinearLayoutManager(requireContext())
        val path = requireContext().getExternalFilesDir(null)
        val folder = File(path,"")
        var files = ArrayList(Arrays.asList(*folder.list()))
        if(Constants.isrecording)files.removeAt(files.size-1)
        val viewAdapter = FileListAdapter(files,requireActivity())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation)
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.divider)!!)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}