package com.example.guessing.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.input.key.Key.Companion.Break
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun updateGuess(currentGuess: String) {
        _uiState.update {
            it.copy(
                currentGuess = currentGuess
            )
        }
    }

    private fun validateNo(): Int{
        val validGuess = try {
            _uiState.value.currentGuess.toInt()
        }catch (e: Exception){
            0
        }
        return validGuess
    }

    fun addGuess(context: Context) {

        if(validateNo() !in 1..100){
            Toast.makeText(
                context,
                "Please enter a number between 1 and 100",
                Toast.LENGTH_SHORT
            ).show()
            _uiState.update {
                it.copy(
                    currentGuess = ""
                )
            }
            return
        }
        changeHint()
        if(_uiState.value.remainingAttempts > 0){
            _uiState.update {
                it.copy(
                    listOfGuessedNumbers = it.listOfGuessedNumbers.plus(validateNo()),
                    remainingAttempts = it.remainingAttempts - 1,
                    currentGuess = "",
                )
            }
        }


        val gameStatus = when{
            validateNo() == _uiState.value.mysteryNumber -> GameStatus.WON
            _uiState.value.remainingAttempts == 0 -> GameStatus.LOST
            else -> GameStatus.IN_PROGRESS
        }

        _uiState.update {
            it.copy(
                gameStatus = gameStatus
            )
        }
    }

    private fun changeHint() {
        val validGuess = validateNo()
        if (validGuess in 1..100) {
            if (validGuess > _uiState.value.mysteryNumber) {
                _uiState.update {
                    it.copy(hint = "Hint: It's lower")
                }
            } else if (validGuess < _uiState.value.mysteryNumber) {
                _uiState.update {
                    it.copy(hint = "Hint: It's higher")
                }
            } else {
                _uiState.update {
                    it.copy(hint = "You got it!")
                }

            }
        }
        viewModelScope.launch {
            if(validGuess == _uiState.value.mysteryNumber){
                delay(2000)
                resetGame()
            }
        }
    }


    fun resetGame(){
        _uiState.update {
            it.copy(
                currentGuess = "",
                mysteryNumber = (1..100).random(),
                remainingAttempts = 5,
                listOfGuessedNumbers = emptyList(),
                hint = "Guess\n the mystery number between\n 1 and 100",
                gameStatus = GameStatus.IN_PROGRESS
            )
        }
    }
}

enum class GameStatus{
    IN_PROGRESS,
    WON,
    LOST
}
data class GameUiState(
    val currentGuess: String = "",
    val mysteryNumber: Int = (1..100).random(),
    val remainingAttempts: Int = 5,
    val listOfGuessedNumbers: List<Int> = emptyList(),
    val hint: String = "Guess\n the mystery number between\n 1 and 100",
    val gameStatus: GameStatus = GameStatus.IN_PROGRESS
)
