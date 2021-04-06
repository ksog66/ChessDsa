package com.notchdev.chessdsa.ui.nqueen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NqueenViewModel : ViewModel() {


    private val visited = Array(6) { Array(6) { false } }

    fun setVisited(x: Int, y: Int) {
        visited[x][y] = !visited[x][y]
    }

    private fun prevMoveValidation(x: Int, y: Int): Boolean {

        if (x - 1 >= 0) {
            //upper column check
            for (r in x - 1 downTo 0) {
                if (visited[r][y]) return false
            }
        }
        if (x + 1 <= 5) {
            //down column check
            for (r in x + 1..5) {
                if (visited[r][y]) return false
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            //upper left diagonal
            for ((r, c) in (x - 1 downTo 0).zip(y - 1 downTo 0)) {
                if (visited[r][c]) return false
            }
        }
        if(x + 1 <=5 && y-1 >=0) {
            //down left diagonal
            for ((r, c) in (x + 1..5).zip(y - 1 downTo 0)) {
                if (visited[r][c]) return false
            }
        }
        if(x-1 >=0 && y+1 <=5) {
            //upper right diagonal
            for ((r, c) in (x - 1 downTo 0).zip(y + 1..5)) {
                if (visited[r][c]) return false
            }
        }
        if(x+1<=5 && y+1<=5) {
            //down right diagonal
            for ((r, c) in (x + 1..5).zip(y + 1..5)) {
                if (visited[r][c]) return false
            }
        }

        if (y - 1 >= 0) {
            //left row check
            for (c in y - 1 downTo 0) {
                if (visited[x][c]) return false
            }
        }
        if (y + 1 <= 5) {
            //right row check
            for (c in y + 1..5) {
                if (visited[x][c]) return false
            }
        }
        return true
    }

    fun setNotVisited(x: Int, y: Int) {
        visited[x][y] = false
    }

    fun previousUpdation(x: Int, y: Int): Boolean {
        Log.d("ViewModel", "${prevMoveValidation(x, y)} and ${visited[x][y]} for $x and $y")
        return prevMoveValidation(x, y) && visited[x][y]
    }

    fun initializeGame() {
        for(i in 0..5) {
            for(j in 0..5) {
                visited[i][j] = false
            }
        }
    }
}