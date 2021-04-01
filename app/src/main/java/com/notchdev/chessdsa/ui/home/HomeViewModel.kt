package com.notchdev.chessdsa.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {



    private var _isSafe = MutableLiveData<Boolean>()
    val isSafe: LiveData<Boolean> = _isSafe

    private var _count =MutableLiveData<Int>()
    val count :LiveData<Int> = _count
    private val visited =Array(5) {Array(5) {false} }
    private var prevX :Int =0
    private var prevY :Int =0
    private var noOfMoves:Int =0
    private var xDir= arrayOf(-2,-2,-1,-1,2,2,1,1)
    private var yDir =arrayOf(1,-1,2,-2,-1,1,-2,2)

    fun isThisMoveSafe(x:Int,y:Int) {
//        if(noOfMoves!=0){
//            val validMoveAccToPrevMove = prevMoveValidation(x,y)
//            val validCoordinateMoves = validCoordinateMove(x,y)
//            if (validMoveAccToPrevMove and validCoordinateMoves){
//                prevX=x
//                prevY=y
//                visited[x][y]=true
//                _isSafe.value = true
//            } else{
//                _isSafe.value = false
//            }
//        }
//        else{
//            val validCoordinateMoves = validCoordinateMove(x,y)
//            if (validCoordinateMoves){
//                prevX=x
//                prevY=y
//                visited[x][y]=1
//                _isSafe.postValue(true)
//            } else{
//                _isSafe.postValue(false)
//            }
//        }
        val validCoordinateMoves = validCoordinateMove(x,y)
        val validMoveAccToPrevMove = prevMoveValidation(x,y)
        Log.d("viewModel", "prevMoveValidation: $validMoveAccToPrevMove")
        Log.d("viewModel", "validMove: $validCoordinateMoves")
        if (validCoordinateMoves and validMoveAccToPrevMove){
                prevX=x
                prevY=y
                visited[x][y]=true
                _isSafe.postValue(true)
                ++noOfMoves
                _count.setValue(noOfMoves)

        }
        else{
            _isSafe.setValue(false)
        }
    }


    private fun validCoordinateMove(x: Int, y: Int): Boolean {
        return x>=0 && y>=0 && x<5  && y<5 && !visited[x][y]
    }

    private fun prevMoveValidation(x:Int,y:Int): Boolean {
        for(i in 0..7){
            if(prevX+xDir[i] == x && y==prevY+yDir[i])
                return true
        }
        return false
    }
}