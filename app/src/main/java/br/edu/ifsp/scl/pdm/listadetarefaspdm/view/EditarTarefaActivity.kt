package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.pdm.listadetarefaspdm.AutenticacaoFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityEditarTarefaBinding
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityTarefaBinding
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas

class EditarTarefaActivity : AppCompatActivity() {

    private lateinit var activityEditarTarefaBinding: ActivityEditarTarefaBinding
    private  var tarefas: Tarefas? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditarTarefaBinding = ActivityEditarTarefaBinding.inflate(layoutInflater)
        setContentView(activityEditarTarefaBinding.root)
        tarefas = intent.getParcelableExtra(Intent.EXTRA_USER) as Tarefas?


        activityEditarTarefaBinding.dataCriacaoEditarEt.setText(tarefas?.dataCriacao)
        activityEditarTarefaBinding.dataCumprimentoEditarEt.setText(tarefas?.dataTermino)
        activityEditarTarefaBinding.descricaoEditarEt.setText(tarefas?.descricao)
        activityEditarTarefaBinding.tituloEditarEt.setText(tarefas?.titulo)

    }

    fun onClick(view: View) {
        tarefas?.dataTermino = activityEditarTarefaBinding.dataCumprimentoEditarEt.text.toString()
        tarefas?.descricao = activityEditarTarefaBinding.descricaoEditarEt.text.toString()

        val retorno = Intent()
        retorno.putExtra(Intent.EXTRA_USER, tarefas)
        setResult(RESULT_OK,retorno)
        finish()

    }

}