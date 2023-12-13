package com.example.personalpasswordmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PasswordAdapter (var contexto : Context, var listaDeSenhas: MutableList<Password>): BaseAdapter() {
    override fun getCount(): Int {
        return this.listaDeSenhas.size
    }

    override fun getItem(posicao: Int): Any {
        return this.listaDeSenhas.get(posicao)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(posicao: Int, view: View?, viewGroup: ViewGroup?): View {
        var novaView = if (view == null){
            LayoutInflater.from(this.contexto).inflate(R.layout.password_list, viewGroup, false)
        }else{
            view
        }

        val textView = novaView.findViewById<TextView>(R.id.senhaList)
        val senha = this.listaDeSenhas[posicao]
        val texto = "${senha.getDescricao()} (${senha.getQuant()})"
        textView.text = texto

        return novaView
    }

    fun adicionar(novaSenha: Password){
        this.listaDeSenhas.add(novaSenha)
        notifyDataSetChanged()
    }

    fun alterarSenha(novaSenha:Password, posicao:Int){
        this.listaDeSenhas[posicao] = novaSenha
        notifyDataSetChanged()
    }

    fun excluirSenha(posicao:Int){
        this.listaDeSenhas.removeAt(posicao)
        notifyDataSetChanged()
    }

}