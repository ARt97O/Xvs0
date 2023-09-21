package com.example.xvso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.xvso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding

    // Зміна check використовується, щоб вивести X або 0, check = 1 це X, check = 2 це 0.
    // The check variable is used to output X or 0, check = 1 is X, check = 2 is 0.
    var check = 2
    var checkWinX = 0
    var checkWinO = 0
    val x = "X"
    val o = "O"
    val winX = "Виграв Х"
    val winO = "Виграв О"
    val nowX = "Ходить Х"
    val nowO = "Ходить O"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    // Виводить на екран хто ходить
    // Displays who is walking on the screen
    fun checkMove(){
        if (check == 1) {
            bind.textEnter.text = nowX
        }else if (check == 2) {
            bind.textEnter.text = nowO
        }
    }
    fun onClickBut(view: View) {
        if (check == 1) {
            bind.but.text = x
            check += 1
        } else if (check == 2) {
            bind.but.text = o
            check -= 1
        }
        checkMove()
        chakingWinX()
        chakingWinO()
        }

    fun onClickBut2(view: View) {
        if (bind.but2.text != x && bind.but2.text != o) {
            if (check == 1) {
                bind.but2.text = x
                check += 1
            } else if (check == 2) {
                bind.but2.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut3(view: View) {
        if (bind.but3.text != x && bind.but3.text != o) {
            if (check == 1) {
                bind.but3.text = x
                check += 1
            } else if (check == 2) {
                bind.but3.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut4(view: View) {
        if (bind.but4.text != x && bind.but4.text != o) {
            if (check == 1) {
                bind.but4.text = x
                check += 1
            } else if (check == 2) {
                bind.but4.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut5(view: View) {
        if (bind.but5.text != x && bind.but5.text != o) {
            if (check == 1) {
                bind.but5.text = x
                check += 1
            } else if (check == 2) {
                bind.but5.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut6(view: View) {
        if (bind.but6.text != x && bind.but6.text != o) {
            if (check == 1) {
                bind.but6.text = x
                check += 1
            } else if (check == 2) {
                bind.but6.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut7(view: View) {
        if (bind.but7.text != x && bind.but7.text != o) {
            if (check == 1) {
                bind.but7.text = x
                check += 1
            } else if (check == 2) {
                bind.but7.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut8(view: View) {
        if (bind.but8.text != x && bind.but8.text != o) {
            if (check == 1) {
                bind.but8.text = x
                check += 1
            } else if (check == 2) {
                bind.but8.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }

    fun onClickBut9(view: View) {
        if (bind.but9.text != x && bind.but9.text != o) {
            if (check == 1) {
                bind.but9.text = x
                check += 1
            } else if (check == 2) {
                bind.but9.text = o
                check -= 1
            }
            checkMove()
            chakingWinX()
            chakingWinO()
        }
    }
    // Чистить ігрове поле
    // Cleans the playing field
    fun reset() {
        bind.but.text = ""
        bind.but2.text = ""
        bind.but3.text = ""
        bind.but4.text = ""
        bind.but5.text = ""
        bind.but6.text = ""
        bind.but7.text = ""
        bind.but8.text = ""
        bind.but9.text = ""
        bind.but.text = ""
    }
        //Функція перевірки перемоги X
        // X win check feature
    fun chakingWinX() {
            while (true) {
                if (bind.but.text == x && bind.but4.text == x && bind.but7.text == x) {
                    bind.textEnter.text = winX
                    checkWinX += 1
                    reset()
                }
                if (bind.but2.text == x && bind.but5.text == x && bind.but8.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but3.text == x && bind.but6.text == x && bind.but9.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but.text == x && bind.but2.text == x && bind.but3.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but4.text == x && bind.but5.text == x && bind.but6.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but7.text == x && bind.but8.text == x && bind.but9.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but.text == x && bind.but5.text == x && bind.but9.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                if (bind.but3.text == x && bind.but5.text == x && bind.but7.text == x) {
                    bind.textEnter.text = winX
                    checkWinX +=1
                    reset()
                }
                bind.chackWinO.text = checkWinO.toString()
                bind.chackWinX.text = checkWinX.toString()
                resetDraw()
                break
            }
        }
    ////Функція перевірки перемоги O
    fun chakingWinO() {
        while (true) {
            if (bind.but.text == o && bind.but4.text == o && bind.but7.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but2.text == o && bind.but5.text == o && bind.but8.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but3.text == o && bind.but6.text == o && bind.but9.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but.text == o && bind.but2.text == o && bind.but3.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but4.text == o && bind.but5.text == o && bind.but6.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but7.text == o && bind.but8.text == o && bind.but9.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but.text == o && bind.but5.text == o && bind.but9.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            if (bind.but3.text == o && bind.but5.text == o && bind.but7.text == o) {
                bind.textEnter.text = winO
                checkWinO += 1
                reset()
            }
            bind.chackWinO.text = checkWinO.toString()
            bind.chackWinX.text = checkWinX.toString()
            resetDraw()
            break
        }
    }
    //Функція відповідає за нічию
    fun resetDraw() {
        if (bind.but.text != "" && bind.but2.text != "" && bind.but3.text != "" &&
            bind.but4.text != "" && bind.but5.text != "" && bind.but6.text != "" &&
            bind.but7.text != "" && bind.but8.text != "" && bind.but9.text != ""
        ) {
            reset()
        }
    }
}
