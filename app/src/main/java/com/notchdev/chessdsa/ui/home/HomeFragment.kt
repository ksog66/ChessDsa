package com.notchdev.chessdsa.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.notchdev.chessdsa.R
import com.notchdev.chessdsa.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG="HomeFragment"
class HomeFragment : Fragment(),View.OnClickListener {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var safeToMove:Boolean  =false
    private lateinit var boardBtn:Array<Array<Button>>
    private val visited =Array(5) {Array(5) {false} }
    private var prevX :Int =0
    private var prevY :Int =0
    private var noOfMoves:Int =0
    private var xDir= arrayOf(-2,-2,-1,-1,2,2,1,1)
    private var yDir =arrayOf(1,-1,2,-2,-1,1,-2,2)
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apply {
            boardBtn= arrayOf(
                arrayOf(button1,button2,button3,button4,button5),
                arrayOf(button6,button7,button8,button9,button10),
                arrayOf(button11,button12,button13,button14,button15),
                arrayOf(button16,button17,button18,button19,button20),
                arrayOf(button21,button22,button23,button24,button25)
            )
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        boardBtn.forEach { btnLoop->
            btnLoop.forEach { btn->
                btn.setOnClickListener(this)
            }
        }
        initializeBoard()
    }

    private fun initializeBoard() {
        for (i in 0..4){
            for (j in 0..4){
                boardBtn[i][j].isEnabled = true
            }
        }
        boardBtn[0][0].isEnabled=false
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.button2 -> {
                getSafeOrNot(0, 1)
            }
            R.id.button3 ->{
                getSafeOrNot(0,2)
            }
            R.id.button4 ->{
                getSafeOrNot(0,3)
            }
            R.id.button5 ->{
                getSafeOrNot(0,4)
            }
            R.id.button6 ->{
                getSafeOrNot(1,0)
            }
            R.id.button7 ->{
                getSafeOrNot(1,1)
            }
            R.id.button8 ->{
                getSafeOrNot(1,2)
            }
            R.id.button9 ->{
                getSafeOrNot(1,3)
            }
            R.id.button10 ->{
                getSafeOrNot(1,4)
            }
            R.id.button11 ->{
                getSafeOrNot(2,0)
            }
            R.id.button12 ->{
                getSafeOrNot(2,1)
            }
            R.id.button13 ->{
                getSafeOrNot(2,2)
            }
            R.id.button14 ->{
                getSafeOrNot(2,3)
            }
            R.id.button15 ->{
                getSafeOrNot(2,4)
            }
            R.id.button16 ->{
                getSafeOrNot(3,0)
            }
            R.id.button17 ->{
                getSafeOrNot(3,1)
            }
            R.id.button18 ->{
                getSafeOrNot(3,2)
            }
            R.id.button19 ->{
                getSafeOrNot(3,3)
            }
            R.id.button20 ->{
                getSafeOrNot(3,4)
            }
            R.id.button21 ->{
                getSafeOrNot(4,0)
            }
            R.id.button22 ->{
                getSafeOrNot(4,1)
            }
            R.id.button23 ->{
                getSafeOrNot(4,2)
            }
            R.id.button24 ->{
                getSafeOrNot(4,3)
            }
            R.id.button25 ->{
                getSafeOrNot(4,4)
            }
        }
        if(!safeToMove) {
            Log.d(TAG, "InvalidMove")
            Toast.makeText(requireContext(), "InvalidMove", Toast.LENGTH_SHORT).show()
        }
//        checkIfMovesPossible()
    }

    private fun getSafeOrNot(x: Int, y: Int) {
        Log.d(TAG, "{$x,$y}")
        viewModel.isThisMoveSafe(x,y)
        viewModel.isSafe.observe({lifecycle}) {
            it?.let{
                safeToMove = it
            }
        }
        viewModel.count.observe({lifecycle}) {
            it?.let {
                noOfMoves = it
            }
        }
        if(safeToMove)
            boardBtn[x][y].isEnabled = false
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}