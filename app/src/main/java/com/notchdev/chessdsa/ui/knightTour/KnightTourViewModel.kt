package com.notchdev.chessdsa.ui.knightTour

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KnightTourViewModel : ViewModel() {


    private var _isSafe = MutableLiveData<Boolean>()
    val isSafe: LiveData<Boolean> = _isSafe

    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count
    private val visited = Array(5) { Array(5) { false } }
    private var _currX = MutableLiveData(0)
    val currX: LiveData<Int> = _currX
    private var _currY = MutableLiveData(0)
    val currY: LiveData<Int> = _currY

    private var noOfMoves: Int = 0
    private var prevX = 0
    private var prevY = 0
    private var xDir = arrayOf(-2, -2, -1, -1, 2, 2, 1, 1)
    private var yDir = arrayOf(1, -1, 2, -2, -1, 1, -2, 2)

    fun isThisMoveSafe(x: Int, y: Int) {
//        if(noOfMoves!=0){
//            val validMoveAccToPrevMove = prevMoveValidation(x,y)
//            val validCoordinateMoves = validCoordinateMove(x,y)
//            if (validMoveAccToPrevMove and validCoordinateMoves){
//                prevX=xTODO("Not yet implemented")
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
        val validCoordinateMoves = validCoordinateMove(x, y)
        val validMoveAccToPrevMove = prevMoveValidation(x, y)
        Log.d("viewModel", "prevMoveValidation: $validMoveAccToPrevMove")
        Log.d("viewModel", "validMove: $validCoordinateMoves")
        if (validCoordinateMoves and validMoveAccToPrevMove) {
            _currX.value = prevX
            _currY.value = prevY
            prevX = x
            prevY = y
            visited[x][y] = true
            _isSafe.value = true
            ++noOfMoves
            _count.setValue(noOfMoves)

        } else {
            _isSafe.setValue(false)
        }
    }


    private fun validCoordinateMove(x: Int, y: Int): Boolean {
        //will remove this program driven code and make it event driven code
        //no need to check for x and y they will be greater and lesser than 0 and 5 respectively
        return x >= 0 && y >= 0 && x < 5 && y < 5 && !visited[x][y]
    }

    private fun prevMoveValidation(x: Int, y: Int): Boolean {
        for (i in 0..7) {
            if (prevX + xDir[i] == x && y == prevY + yDir[i])
                return true
        }
        return false
    }

    fun checkMovePossible(): Boolean {
        for (i in 0..7) {
            val X = prevX + xDir[i]
            val Y = prevY + yDir[i]
            if(X >=0 && Y >= 0 && X < 5 && Y <5 && !visited[X][Y])
                return true
            else
                continue
        }
        return false
    }
    fun initializeGame() {
        for (i in 0..4) {
            for (j in 0..4) {
                visited[i][j] = false
            }
        }
        prevX = 0
        prevY = 0
        noOfMoves = 0
        _currX.value = 0
        _currY.value = 0
        _count.value = 0
    }
}