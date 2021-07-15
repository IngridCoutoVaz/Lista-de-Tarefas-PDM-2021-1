package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.pdm.listadetarefaspdm.AutenticacaoFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityLoginBinding.root)
    }

    fun onClick(view: View) {
        when (view) {
            activityLoginBinding.cadastrarBt -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
            }
            activityLoginBinding.entrarBt -> {
                val email: String
                val senha: String
                with(activityLoginBinding) {
                    email = emailEt.text.toString()
                    senha = senhaEt.text.toString()
                }
                AutenticacaoFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }.addOnFailureListener {
                    Toast.makeText(this, "Usuário ou senha invalidos", Toast.LENGTH_SHORT).show()
                }

            }
            activityLoginBinding.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (AutenticacaoFirebase.firebaseAuth.currentUser != null) {
            Toast.makeText(this, "Usuário já autenticado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}