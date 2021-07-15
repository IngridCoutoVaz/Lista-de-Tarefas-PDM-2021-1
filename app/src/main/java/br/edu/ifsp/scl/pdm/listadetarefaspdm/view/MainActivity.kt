package br.edu.ifsp.scl.pdm.listadetarefaspdm.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.pdm.listadetarefaspdm.*
import br.edu.ifsp.scl.pdm.listadetarefaspdm.adapter.OnTarefaClickListener
import br.edu.ifsp.scl.pdm.listadetarefaspdm.adapter.TarefaAdapter
import br.edu.ifsp.scl.pdm.listadetarefaspdm.controller.TarefaController
import br.edu.ifsp.scl.pdm.listadetarefaspdm.databinding.ActivityMainBinding
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas

class MainActivity : AppCompatActivity(), OnTarefaClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var tarefasList: MutableList<Tarefas>
    private lateinit var tarefasAdapter: TarefaAdapter
    private lateinit var tarefasLayoutManager: LinearLayoutManager
    private  lateinit var novaTarefaLauncher: ActivityResultLauncher<Intent>
    private  lateinit var editaTarefaLauncher: ActivityResultLauncher<Intent>

    private lateinit var tarefaController: TarefaController
    private lateinit var tarefa: Tarefas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        tarefaController = TarefaController(this)
        tarefasList = mutableListOf()



        tarefasList=tarefaController.buscaTarefas()
        tarefasLayoutManager = LinearLayoutManager(this)
        tarefasAdapter = TarefaAdapter(tarefasList, this, menuInflater)
        activityMainBinding.tarefaRv.adapter = tarefasAdapter
        activityMainBinding.tarefaRv.layoutManager = tarefasLayoutManager

        novaTarefaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val tarefa: Tarefas? = activityResult.data?.getParcelableExtra<Tarefas>(Intent.EXTRA_USER)
                if (tarefa != null) {
                    atualizaTaskList(tarefa)
                    tarefaController.insereTarefa(tarefa)

                }
            }
        }

        editaTarefaLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val tarefa: Tarefas? = activityResult.data?.getParcelableExtra<Tarefas>(Intent.EXTRA_USER)
                if (tarefa != null) {
                    tarefasAdapter.getPosicao()
                    tarefasList.set(tarefasAdapter.getPosicao(), tarefa)
                    tarefasAdapter.notifyDataSetChanged()
                    tarefaController.atualizaTarefa(tarefa)


                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        tarefasList = tarefaController.buscaTarefas()
        tarefasAdapter.notifyDataSetChanged()
    }
    override fun onTarefaClick(posicao: Int) {
        val tarefa: Tarefas = tarefasList[posicao]
        Toast.makeText(this, tarefa.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tarefas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.novaTarefaMi -> {
            val novaTarefaIntent = Intent(this, TarefaActivity::class.java)
            novaTarefaLauncher.launch(novaTarefaIntent)
            true
        }
        R.id.sairMi -> {
            AutenticacaoFirebase.firebaseAuth.signOut()
            true
        }
        else -> {
            false
        }
    }


    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        tarefa = tarefasList.get(tarefasAdapter.getPosicao())
        when (item.itemId){
            R.id.removerTarefaMi -> {
                tarefasList.remove(tarefa)
                tarefasAdapter.notifyDataSetChanged()
                tarefaController.removeTarefa(tarefa.titulo)
                true
            }
            R.id.editarTarefaMi -> {
                //if(tarefa.concluid.istrue
                val intent = Intent(this,EditarTarefaActivity::class.java)
                intent.putExtra(Intent.EXTRA_USER, tarefa)
                editaTarefaLauncher.launch(intent)
                return true
            }
            R.id.concluirTarefaMi -> {
                tarefa.tarefaConcluida = true
                tarefaController.atualizaTarefa(tarefa)
                atualizaAdapter()
            }
        }
        return false
    }

    override fun onCreateContextMenu(menu: ContextMenu?,v: View?,menuInfo: ContextMenu.ContextMenuInfo?) {

        menuInflater.inflate(R.menu.context_menu_tarefa, menu)
    }

    fun atualizaAdapter (){
        tarefasAdapter.notifyDataSetChanged()
    }
    fun atualizaTaskList(task: Tarefas) {
        if(task.titulo.isNotEmpty()){
            tarefasList.add(task)
            tarefasAdapter.notifyDataSetChanged()
        }
    }
}
