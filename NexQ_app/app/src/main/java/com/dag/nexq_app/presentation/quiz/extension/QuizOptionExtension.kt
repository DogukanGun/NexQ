package com.dag.nexq_app.presentation.quiz.extension

fun Int.getOption():String{
    return if (this in 1..26) {
        (this + 64).toChar().toString()
    } else {
        "Invalid index, please enter a value between 1 and 26."
    }
}

fun Int.selectOption(option:Int):Int{
    return if(this == -1){
        option
    } else {
        -1;
    }
}