package br.edu.ifsp.scl.pdm.listadetarefaspdm.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.pdm.listadetarefaspdm.R
import br.edu.ifsp.scl.pdm.listadetarefaspdm.model.Tarefas

class TarefaAdapter(
    private val tarefaList: MutableList<Tarefas>,
    private val onTarefaClickListener: OnTarefaClickListener,
    private val menuInflater: MenuInflater
): RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(){

    inner class TarefaViewHolder(viewTarefa: View): RecyclerView.ViewHolder(viewTarefa), View.OnCreateContextMenuListener{
        val tituloTarefa : TextView = viewTarefa.findViewById(R.id.tituloTarefaTv)
        val usuario : TextView = viewTarefa.findViewById(R.id.usuarioTv)
        val dataCriacao : TextView = viewTarefa.findViewById(R.id.dataCriacaoTv)
        val descricao: TextView = viewTarefa.findViewById(R.id.descricaoTv)
        val dataTermino: TextView = viewTarefa.findViewById(R.id.dataTerminoTv)
        val concluida: TextView = viewTarefa.findViewById(R.id.concluirTv)
        init {
            viewTarefa.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ) {
            menuInflater.inflate(R.menu.context_menu_tarefa, menu)
        }
    }

    private var idx: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val viewTarefa: View = LayoutInflater.from(parent.context).inflate(R.layout.view_tarefas, parent, false)
        return TarefaViewHolder(viewTarefa)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefaList[position]

        holder.tituloTarefa.text = tarefa.titulo
        holder.usuario.text = tarefa.usuario
        holder.dataCriacao.text = tarefa.dataCriacao
        holder.descricao.text = tarefa.descricao
        holder.dataTermino.text = tarefa.dataTermino
        holder.concluida.text  = tarefa.tarefaConcluida.toString()
        holder.itemView.setOnClickListener {
            onTarefaClickListener.onTarefaClick(position)
        }

        holder.itemView.setOnLongClickListener { v ->
            idx = position
            false
        }
    }

    override fun getItemCount(): Int = tarefaList.size

    fun getPosicao(): Int {
        return idx
    }
}