package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.pdm.listadetarefaspdm.AutenticacaoFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.R
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityLoginBinding.root)

        AutenticacaoFirebase.googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        AutenticacaoFirebase.googleSignInClient = GoogleSignIn.getClient(this, AutenticacaoFirebase.googleSignInOptions!!)
        AutenticacaoFirebase.googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

        if( AutenticacaoFirebase.googleSignInAccount != null ){
            Toast.makeText(this, "Usuário autenticado com sucesso!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        activityLoginBinding.googleLoginBt.setOnClickListener {
            val googleSignInIntent = AutenticacaoFirebase.googleSignInClient?.signInIntent
            googleSignInLauncher.launch(googleSignInIntent)
        }

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                AutenticacaoFirebase.googleSignInAccount = task.getResult(ApiException::class.java)

                val credencial: AuthCredential =
                    GoogleAuthProvider.getCredential(AutenticacaoFirebase.googleSignInAccount?.idToken, null)

                AutenticacaoFirebase.firebaseAuth.signInWithCredential(credencial).addOnSuccessListener {
                    Toast.makeText(this, "Usuário ${AutenticacaoFirebase.googleSignInAccount?.email} com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_SHORT).show()
                }
            }catch (e: ApiException) {
                Toast.makeText(this, "Falha na autenticação", Toast.LENGTH_SHORT).show()
            }
        }
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

    fun onClickGoogle(view: View) {

    }
}