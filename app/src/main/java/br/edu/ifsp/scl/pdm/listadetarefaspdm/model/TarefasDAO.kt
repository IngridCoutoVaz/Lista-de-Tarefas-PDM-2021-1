package br.edu.ifsp.scl.pdm.listadetarefaspdm.model

interface TarefasDAO {
    fun createTarefa(tarefa : Tarefas)
    fun readTarefa(nome : String) : Tarefas;
    fun readTarefas() : MutableList<Tarefas>
    fun updateTarefa(tarefa : Tarefas)
    fun deleteTarefa(nome : String)
}