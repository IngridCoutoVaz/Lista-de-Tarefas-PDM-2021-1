import TarefasFirebase.Constantes.TAREFAS_DATABASE
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.TarefasDAO
import br.edu.ifsp.scl.pdm.listadetarefaspdm.view.MainActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TarefasFirebase(mainActivity: MainActivity): TarefasDAO {

    object Constantes {
        val TAREFAS_DATABASE = "listaTarefa"
    }

    private val tarefasListaRtDb = Firebase.database.getReference(TAREFAS_DATABASE)
    private val tarefasList: MutableList<Tarefas> = mutableListOf()

    init {
        tarefasListaRtDb.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTarefa: Tarefas = snapshot.getValue<Tarefas>() ?: Tarefas()
                if (tarefasList.indexOfFirst { it.titulo.equals(novaTarefa.titulo) } == -1) {
                    tarefasList.add(novaTarefa)
                    mainActivity.atualizaAdapter()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefaEditada: Tarefas = snapshot.getValue<Tarefas>() ?: Tarefas()
                val indice = tarefasList.indexOfFirst { it.titulo.equals(tarefaEditada.titulo) }
                tarefasList[indice] = tarefaEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefaRemovida: Tarefas = snapshot.getValue<Tarefas>() ?: Tarefas()
                tarefasList.remove(tarefaRemovida)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }


    override fun createTarefa(tarefa: Tarefas) = createOrUpdadeTarefa(tarefa)

    override fun readTarefa(nome: String): Tarefas = tarefasList[tarefasList.indexOfFirst { it.titulo.equals(nome) }]

    override fun readTarefas(): MutableList<Tarefas> = tarefasList

    override fun updateTarefa(tarefa: Tarefas) = createOrUpdadeTarefa(tarefa)

    override fun deleteTarefa(nome: String) {
        tarefasListaRtDb.child(nome).removeValue()
    }

    private fun createOrUpdadeTarefa(tarefa: Tarefas) {
        tarefasListaRtDb.child(tarefa.titulo).setValue(tarefa)
    }

}