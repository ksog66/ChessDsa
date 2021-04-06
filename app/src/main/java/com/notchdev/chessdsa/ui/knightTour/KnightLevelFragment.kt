package com.notchdev.chessdsa.ui.knightTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.notchdev.chessdsa.R
import com.notchdev.chessdsa.databinding.FragmentKnighttourLevelBinding

class KnightLevelFragment: Fragment() {

    private var _binding:FragmentKnighttourLevelBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKnighttourLevelBinding.inflate(inflater,container,false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            easyKnightCv.setOnClickListener {
                findNavController().navigate(R.id.action_to_knighttour)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}