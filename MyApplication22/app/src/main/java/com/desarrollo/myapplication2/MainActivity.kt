package com.desarrollo.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var user: AppCompatEditText
    private lateinit var psw: AppCompatEditText
    private lateinit var btnLogin:AppCompatButton
    private lateinit var btnRegister:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //definicion de las variables
        auth = FirebaseAuth.getInstance() //objeto autenticación
        user = findViewById<AppCompatEditText>(R.id.textUser)
        psw = findViewById<AppCompatEditText>(R.id.textPassword)
        btnLogin = findViewById<AppCompatButton>(R.id.bttValidar)
        btnRegister = findViewById<AppCompatButton>(R.id.btnRegister)

        btnLogin.setOnClickListener{
            val correo = user.text.toString().trim()
            val contra = psw.text.toString().trim()
            if(correo.isNotEmpty() && contra.isNotEmpty()){
                login(correo,contra)
            }else{
                Toast.makeText(this,"Ingresa las credenciales",Toast.LENGTH_SHORT).show()
                }
            }

        btnRegister.setOnClickListener{
            val correo = user.text.toString().trim()
            val contra = psw.text.toString().trim()
            if(correo.isNotEmpty() && contra.isNotEmpty()){
                registrar(correo,contra)
            }else{
                Toast.makeText(this,"Ingresa las credenciales",Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun login(correo: String, contra: String) {
        auth.signInWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                    val intento = Intent(this, PantallaPrincipal::class.java)
                    intento.putExtra("email", correo)
                    startActivity(intento)
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun registrar(correo: String, contra: String) {
        auth.createUserWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}