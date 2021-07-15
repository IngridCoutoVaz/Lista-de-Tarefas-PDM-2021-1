package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.pdm.listadetarefaspdm.AutenticacaoFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {

    private lateinit var activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecuperarSenhaBinding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityRecuperarSenhaBinding.root)
    }

    fun onClick(view: View) {
        if (view == activityRecuperarSenhaBinding.emailRecuperacaoSenhaEt) {
            val email = activityRecuperarSenhaBinding.enviarEmailBt.text.toString()
            AutenticacaoFirebase.firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                Toast.makeText(this, "E-mail de recuperação enviado para $email", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Falha no envio de e-mail de recuperação", Toast.LENGTH_SHORT).show()
            }
        }
    }
}