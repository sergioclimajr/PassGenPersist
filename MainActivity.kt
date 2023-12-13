package com.example.personalpasswordmanager


import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var alteredPosition = 0;
    private lateinit var listView: ListView;
    private lateinit var plusButton: FloatingActionButton;
    private var passwords = mutableListOf<Password>()

    private val gerenciadorResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            if(it.data != null){
                val descricao = it.data?.getStringExtra("descricao")
                val temLM = it.data?.getBooleanExtra("temLM",false)
                val temN = it.data?.getBooleanExtra("temN",false)
                val temCS = it.data?.getBooleanExtra("temCS",false)
                val tamanho = it.data?.getIntExtra("tamanho",4)

                Log.i("VALORES","${temCS} - ${temLM} - ${temN}")
                val senha = Password(descricao,temLM,temCS,temN,tamanho)

//                this.senhas.add(senha)
                Log.i("SENHA",senha.getSenha())
                Log.i("TAMANHO","${this.passwords.size}")
                if(it.data?.getBooleanExtra("temSenha",false) == true){
                    altter(senha,this.alteredPosition)
                }else if(it.data?.getBooleanExtra("excluir",false)==true){
                    delete(this.alteredPosition)
                }else{
                    update(senha)
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.listView = findViewById(R.id.listinhaDeSenhas);
        this.plusButton = findViewById(R.id.botaoNovaSenha)

//        this.listinhaDeSenha.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.caracteres)
        this.listView.adapter = PasswordAdapter(this,this.passwords);
        this.listView.setOnItemLongClickListener(getPassword())
        this.listView.setOnItemClickListener (passwordEditor())

        this.plusButton.setOnClickListener({nextPage()})
    }

    fun altter(novaSenha:Password, posicao:Int){
        (this.listView.adapter as PasswordAdapter).alterarSenha(novaSenha,posicao)
    }

    fun delete(posicao:Int){
        (this.listView.adapter as PasswordAdapter).excluirSenha(posicao)
    }

    fun nextPage(){
        val intent = Intent(this, Activity2::class.java)
        this.gerenciadorResult.launch(intent)
    }

    fun update(senha:Password){
        (this.listView.adapter as PasswordAdapter).adicionar(senha)
    }

    inner class passwordEditor: AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val senha = this@MainActivity.passwords.get(p2)
            alteredPosition = p2
            editPassword(senha)
        }
    }



    fun editPassword(senha:Password){
        val descricao = senha.getDescricao()
        val temLM = senha.getTemLM()
        val temN = senha.getTemN()
        val temCS = senha.getTemCS()
        val tamanho = senha.getQuant()

        val intent = Intent(this,Activity2::class.java).apply {
            putExtra("descricao",descricao)
            putExtra("temLM",temLM)
            putExtra("temN",temN)
            putExtra("temCS",temCS)
            putExtra("tamanho",tamanho)
        }

        this.gerenciadorResult.launch(intent)
    }

    inner class getPassword: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long):Boolean{
            val senha = this@MainActivity.passwords.get(p2)
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", senha.getSenha())
            clipboard.setPrimaryClip(clip)
            return true
        }
    }

}