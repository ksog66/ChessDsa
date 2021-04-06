package com.notchdev.chessdsa.ui.knightTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import com.notchdev.chessdsa.databinding.FragmentKnighttourBinding

class KnightHardTourFragment: Fragment() {

    private lateinit var viewModel :KnightTourViewModel
    private var _binding:FragmentKnighttourBinding ? = null
    private var safeToMove: Boolean = false
    private lateinit var boardBtn: Array<Array<ShapeableImageView>>
    private var prevX: Int = 0
    private var prevY: Int = 0
    private var noOfMoves: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKnighttourBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}