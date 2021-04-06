package com.notchdev.chessdsa.ui.nqueen

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.notchdev.chessdsa.R
import com.notchdev.chessdsa.databinding.FragmentNqueenBinding

class NqueenFragment : Fragment() {

    private lateinit var viewModel: NqueenViewModel
    private lateinit var boardBtn: Array<Array<ImageView>>
    private var _binding: FragmentNqueenBinding? = null
    private var countQueen: Int = 0
    private var safeQueen: Int = 0
    private var coordinates = mutableListOf<Pair<Int, Int>>()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.game_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            initializeBoard()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNqueenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NqueenViewModel::class.java)

        binding.apply {
            boardBtn = arrayOf(
                arrayOf(block1, block2, block3, block4, block5, block6),
                arrayOf(block7, block8, block9, block10, block11, block12),
                arrayOf(block13, block14, block15, block16, block17, block18),
                arrayOf(block19, block20, block21, block22, block23, block24),
                arrayOf(block25, block26, block27, block28, block29, block30),
                arrayOf(block31, block32, block33, block34, block35, block36)
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..5) {
            for (j in 0..5) {
                boardBtn[i][j].setOnClickListener {
                    if (it.tag != 0) {
                        ++countQueen
                        coordinates.remove(Pair(i, j))
                        unvisitCurrentPosition(i, j)
                        updateBoard(i, j)
                    } else {
                        if (countQueen > 0) {
                            --countQueen
                            coordinates.add(Pair(i, j))
                            visitCurrentPosition(i,j)
                        } else {
                            Toast.makeText(requireContext(), "No Queen left", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    binding.queenCount?.text = countQueen.toString()
                    updateGameStatus()
                }
            }
        }
        initializeBoard()
    }

    private fun updateGameStatus() {
        var greenQueen =0
        for (i in 0 until coordinates.size) {
            val x = coordinates[i].first
            val y = coordinates[i].second
            if (viewModel.previousUpdation(x, y)) {
                boardBtn[x][y].apply {
                    setBackgroundResource(R.drawable.queen_item_safe)
                    tag = 2
                    greenQueen++
                }

            } else {
                boardBtn[x][y].apply {
                    setBackgroundResource(R.drawable.queen_item_danger)
                    tag = 1
                }
            }
        }
        safeQueen = greenQueen
        checkGameStatus()
    }

    private fun unvisitCurrentPosition(i: Int, j: Int) {
        viewModel.setNotVisited(i, j)
        boardBtn[i][j].tag = 0
    }
    private fun visitCurrentPosition(i:Int,j:Int) {
        viewModel.setVisited(i,j)
    }

    private fun initializeBoard() {
        for (i in 0..5) {
            for (j in 0..5) {
                boardBtn[i][j].apply {
                    tag = 0
                    updateBoard(i, j)
                }
            }
        }
        coordinates.clear()
        viewModel.initializeGame()
        countQueen = 6
        safeQueen = 0
        binding.queenCount.text = countQueen.toString()
    }

    private fun updateBoard(i: Int, j: Int) {
        if ((i + j) % 2 == 0)
            boardBtn[i][j].setBackgroundColor(Color.parseColor("#ebecf0"))
        else
            boardBtn[i][j].setBackgroundColor(Color.BLACK)
    }



    private fun checkGameStatus() {
        Log.d("Queen", "SafeQueen:$safeQueen")
        if (safeQueen == 6) {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Congratulation")
                setMessage("Would You Like To Play Again")
                setPositiveButton("Yes") { _, _ ->
                    initializeBoard()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                    findNavController().popBackStack()
                }
                setCancelable(false)
                create()
                show()
            }
        }
    }
}