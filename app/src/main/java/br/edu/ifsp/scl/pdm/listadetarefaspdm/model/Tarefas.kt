package br.edu.ifsp.scl.pdm.listadetarefaspdm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarefas(
    val usuario: String = "",
    var dataCriacao: String = "",
    var titulo: String = "",
    var descricao: String = " ",
    var dataTermino: String = "",
    var tarefaConcluida: Boolean = false
): Parcelable {

}

