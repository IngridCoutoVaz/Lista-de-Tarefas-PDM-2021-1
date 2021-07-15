package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.pdm.listadetarefaspdm.AutenticacaoFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityTarefaBinding
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas

class TarefaActivity: AppCompatActivity(){
        private lateinit var activityTarefaBinding: ActivityTarefaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityTarefaBinding = ActivityTarefaBinding.inflate(layoutInflater)
        setContentView(activityTarefaBinding.root)
    }


    fun onClickButton(view: View){
        val tarefa: Tarefas
        with(activityTarefaBinding) {
            tarefa = Tarefas(
                AutenticacaoFirebase.firebaseAuth.currentUser?.email.toString(),
                dataCriacaoEt.text.toString(),
                tituloEt.text.toString(),
                descricaoEt.text.toString(),
                dataTerminoEt.text.toString(),
                tarefaConcluida = false
            )
        }

        if (view == activityTarefaBinding.salvarBt) {
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, tarefa)
            setResult(AppCompatActivity.RESULT_OK, retornoIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }
}