package com.notchdev.chessdsa.ui.knightTour

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.notchdev.chessdsa.R
import com.notchdev.chessdsa.databinding.FragmentKnighttourBinding

private const val TAG = "HomeFragment"

class KnightTourFragment : Fragment() {

    companion object {
        const val PREF_GAME = "prefs_game"
        const val HIGH_SCORE = "prefs_highScore"
    }

    private lateinit var viewModel: KnightTourViewModel
    private var _binding: FragmentKnighttourBinding? = null
    private var safeToMove: Boolean = false
    private lateinit var boardBtn: Array<Array<ShapeableImageView>>
    private var prevX: Int = 0
    private var prevY: Int = 0
    private var noOfMoves: Int = 0
    private var highScore: Int = 0
    private lateinit var sharedPreferences: SharedPreferences


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = activity?.getSharedPreferences(PREF_GAME, Context.MODE_PRIVATE)!!
        _binding = FragmentKnighttourBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this).get(KnightTourViewModel::class.java)
        val root: View = binding.root
        binding.apply {
            boardBtn = arrayOf(
                arrayOf(button1, button2, button3, button4, button5),
                arrayOf(button6, button7, button8, button9, button10),
                arrayOf(button11, button12, button13, button14, button15),
                arrayOf(button16, button17, button18, button19, button20),
                arrayOf(button21, button22, button23, button24, button25)
            )
        }
        sharedPreferences.getInt(HIGH_SCORE, 0).let {
            highScore = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in 0..4) {
            for (j in 0..4) {
                boardBtn[i][j].setOnClickListener {
                    getSafeOrNot(i, j)
                    if (!safeToMove) {
                        Log.d(TAG, "InvalidMove")
                        Toast.makeText(
                            requireContext(),
                            "Hihihihih Can't Go there",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    checkIfMovesPossible()
                    checkHighScore()
                }
            }
        }
        initializeBoard()
    }

    private fun initializeBoard() {
        for (i in 0..4) {
            for (j in 0..4) {
                boardBtn[i][j].apply {
                    isEnabled = true
                    setImageResource(R.drawable.grass)
                }
            }
        }
        boardBtn[0][0].apply {
            isEnabled = false
            setImageResource(R.drawable.knight_on_grass)
        }
        binding.currScore.text = "0"
        binding.highScore.text = highScore.toString()
        viewModel.initializeGame()
    }

    private fun checkIfMovesPossible() {
        val isItPossible = viewModel.checkMovePossible()
        if (!isItPossible) {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setMessage("Game Over")
                setPositiveButton("Play Again") { _, _ ->
                    initializeBoard()
                }
                setNegativeButton("Home") { _, _ ->
                    findNavController().popBackStack()
                }
                setCancelable(false)
                create()
                show()
            }
        }
    }

    private fun checkHighScore() {
        if (noOfMoves >= highScore) {
            sharedPreferences.edit {
                putInt(HIGH_SCORE, noOfMoves)
            }
        }
        sharedPreferences.getInt(HIGH_SCORE,highScore).let {
            highScore = it
        }
    }

    private fun getSafeOrNot(x: Int, y: Int) {
        Log.d(TAG, "{$x,$y}")
        viewModel.isThisMoveSafe(x, y)
        viewModel.isSafe.observe({ lifecycle }) {
            it?.let {
                safeToMove = it
            }
        }
        viewModel.count.observe({ lifecycle }) {
            it?.let {
                noOfMoves = it
            }
        }
        if (safeToMove) {
            binding.currScore.text = noOfMoves.toString()
            boardBtn[x][y].apply {
                isEnabled = false
                setImageResource(R.drawable.knight_on_grass)
            }
            updatePreviousMoveImage()

        }
    }

    private fun updatePreviousMoveImage() {
        viewModel.currX.observe({ lifecycle }) {
            it?.let {
                prevX = it
            }
        }
        viewModel.currY.observe({ lifecycle }) {
            it?.let {
                prevY = it
            }
        }
        boardBtn[prevX][prevY].setImageResource(R.drawable.grass_dry)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}