package com.example.xvso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.xvso.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.combine

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding

    var check = 2
    var howMuchXwin = 0
    var howMuchOwin = 0
    val x = "X"
    val o = "O"
    var boardState = arrayOfNulls<String>(9)

    val winCombinations = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(3, 6, 9),
        intArrayOf(1, 5, 9),
        intArrayOf(3, 5, 7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    private fun messageCouse(winner: String){
        val message = if (winner == x) getString(R.string.chackWinX) else getString(R.string.chackWinO)
        bind.textEnter.text = message
    }

    private fun updateWinCounters() {
        bind.chackWinX.text = "$howMuchXwin" // Оновлюємо кількість перемог X
        bind.chackWinO.text = "$howMuchOwin" // Оновлюємо кількість перемог O
    }

    private fun announceWinner(winner: String) {
        val message = if (winner == x) getString(R.string.chackWinX) else getString(R.string.chackWinO)
        bind.textEnter.text = message
        if (winner == x) howMuchXwin += 1 else howMuchOwin += 1
        updateWinCounters() // Оновлюємо UI з лічильниками перемог
        resetBoard() // Скидуємо дошку для нової гри
    }


    private fun togglePlayer() {
        check = if (check == 1) 2 else 1
        val message = if (check == 1) getString(R.string.nowX) else getString(R.string.nowO)
        bind.textEnter.text = message
    }
    private fun resetBoard() {
        boardState.fill(null) // Очистити стан дошки
        // Очистити текст на всіх кнопках
        listOf(bind.but1, bind.but2, bind.but3, bind.but4, bind.but5, bind.but6, bind.but7, bind.but8, bind.but9).forEach { it.text = "" }
        // Скинути гравця до початкового стану, якщо потрібно
        check = 2
        togglePlayer()
    }


    //виграш команди
    private fun checkWin(): Boolean {
        for (player in listOf(x, o)) {
            if (winCombinations.any { combination ->
                    combination.all { index -> boardState[index - 1] == player }
                }) {
                announceWinner(player)
                return true
            }
        }
        return false
    }
    // Припустимо, ви маєте функцію для обробки натискання кнопки:
    fun onButtonClick(button: View) {
        val index = button.tag.toString().toInt() - 1 // Використовуйте tag кнопок, щоб визначити індекс
        if (boardState[index] == null && !checkWin()) { // Додано перевірку на перемогу, щоб запобігти зміні стану після перемоги
            boardState[index] = if (check == 1) x else o
            button as Button
            button.text = boardState[index]
            if (checkWin()) { // Перевіряємо, чи виграв хтось після останнього ходу
                // Переможця оголошено в методі checkWin
            } else if (boardState.all { it != null }) {
                // Логіка для оголошення нічиєї, якщо дошка повністю заповнена
                bind.textEnter.text = getString(R.string.tie)
                resetBoard()
            } else {
                togglePlayer() // Зміна гравця, якщо гра продовжується
            }
        }
    }
}
