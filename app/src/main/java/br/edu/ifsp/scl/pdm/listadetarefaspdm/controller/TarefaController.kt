package br.edu.ifsp.scl.pdm.listadetarefaspdm.controller

import TarefasFirebase
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.TarefasDAO
import br.edu.ifsp.scl.pdm.listadetarefaspdm.view.MainActivity
import br.edu.ifsp.scl.pdm.listadetarefaspdm.view.TarefaActivity

class TarefaController (mainActivity: MainActivity) {
    val tarefaDao : TarefasDAO
    init {

        tarefaDao = TarefasFirebase(mainActivity)
    }

    fun insereTarefa(tarefa: Tarefas) = tarefaDao.createTarefa(tarefa)
    fun buscaTarefa(nome: String) = tarefaDao.readTarefa(nome)
    fun buscaTarefas() = tarefaDao.readTarefas()
    fun atualizaTarefa(tarefa: Tarefas) = tarefaDao.updateTarefa(tarefa)
    fun removeTarefa(nome: String) = tarefaDao.deleteTarefa(nome)
}