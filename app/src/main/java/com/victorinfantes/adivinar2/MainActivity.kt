package com.victorinfantes.adivinar2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.victorinfantes.adivinar2.ui.theme.Adivinar2Theme
import kotlin.random.Random.Default.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Adivinar2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun MyApp(modifier: Modifier = Modifier) {
    var num: MutableState<String> = remember { mutableStateOf("") }
    var numSecreto = SecretNumber()// Se mete el objeto entero


    Log.i(
        "SECRETO",
        "Numero actual: ${numSecreto.secretNumber}"
    ) // Muestra en la terminal el numero secreto

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            TextoExplcacion()
            EntradaDatos(num)
            BotonComprobar(num, numSecreto, LocalContext.current)
        }
    }
}

@Composable
fun TextoExplcacion() {
    Column(Modifier.padding(125.dp, 5.dp)) {
        Text(text = "Adivina el número")
    }
    Column(Modifier.padding(20.dp, 5.dp)) {
        Text(text = "Introduce un numero del 1 al 10 y haz click en el botón Comprobar para ver si has acertado.")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradaDatos(num: MutableState<String>) {
    OutlinedTextField(
        value = num.value,
        onValueChange = { num.value = it },
        Modifier.padding(50.dp, 5.dp)
    )
}

@Composable
fun BotonComprobar(num: MutableState<String>, numSecreto: SecretNumber, context: Context) {

    var numeroAcertado = remember { mutableStateOf(false) }

    Button(
        onClick = { comporbar(num, numSecreto, context, numeroAcertado) },
        Modifier.padding(90.dp, 5.dp)
    ) {
        Text(if (numeroAcertado.value == false) "Comprobar numero" else "Jugar otra vez")
    }
}

fun comporbar(
    num: MutableState<String>,
    numSecreto: SecretNumber,
    context: Context,
    numeroAcertado: MutableState<Boolean>,
) {
    if (numeroAcertado.value == false) {
        try {
            if (num.value.toInt() == numSecreto.secretNumber) {
                Toast.makeText(context, "Ese era el numero. Muy bien", Toast.LENGTH_SHORT).show()
                numSecreto.generateNewNumber()
                numeroAcertado.value = true
            } else {
                Toast.makeText(context, "Prueba con otro", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Esto no parece ser un numero valido", Toast.LENGTH_SHORT)
                .show()
        }
    }
    else {
        num.value = ""
        numSecreto.generateNewNumber()
        numeroAcertado.value = false
        Log.i("Secreto","Nuevo numero_ ${numSecreto.secretNumber}")
    }
}

fun generarNumeroSecreto(): Int {
    return nextInt(1, 11)
}
