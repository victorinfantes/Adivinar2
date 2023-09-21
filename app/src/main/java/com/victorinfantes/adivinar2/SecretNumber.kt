package com.victorinfantes.adivinar2

class SecretNumber {
    val rango = 1..11
    var secretNumber:Int = rango.random()

    fun generateNewNumber() {
        var tmp = 0
        do {
            tmp = rango.random()
        } while (tmp == secretNumber)
        secretNumber = tmp
    }
}