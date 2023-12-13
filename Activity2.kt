package com.example.personalpasswordmanager


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.CheckBox
import android.content.Intent
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.slider.Slider
import androidx.core.view.isVisible

class Activity2 : AppCompatActivity() {

    private lateinit var password: Password;
    private var possuiPassword = false

    private lateinit var title: TextView;
    private lateinit var plataforma: EditText;
    private lateinit var deslizador: Slider;
    private lateinit var seekbar: SeekBar;
    private lateinit var progresso:TextView
    private lateinit var letrasMaiusculas: CheckBox;
    private lateinit var numeros:CheckBox;
    private lateinit var caracteresEspeciais:CheckBox;
    private lateinit var submitButton: Button;
    private lateinit var cancelButton:Button;
    private lateinit var deleteButton:Button;
    private var senhaPlataforma: String? = "";
    private var maiusculasBoolean:Boolean? = false;
    private var numerosBoolean:Boolean? = false;
    private var especiaisBoolean:Boolean? = false;
    private var quant:Int? = 4;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        if(intent.hasExtra("descricao")){
            this.senhaPlataforma = intent.getStringExtra("descricao")
            this.possuiPassword = true;
        }
        if(intent.hasExtra("tamanho")){
            this.quant = intent.getIntExtra("tamanho",4);
        }
        if(intent.hasExtra("temLM")){
            this.maiusculasBoolean = intent.getBooleanExtra("temLM",false);
        }
        if(intent.hasExtra("temCS")){
            this.especiaisBoolean = intent.getBooleanExtra("temCS",false);
        }
        if(intent.hasExtra("temN")){
            this.numerosBoolean = intent.getBooleanExtra("temN",false);
        }


        this.password = Password(
            this.senhaPlataforma,
            this.maiusculasBoolean,
            this.especiaisBoolean,
            this.numerosBoolean,
            this.quant
        )



        this.title = findViewById(R.id.titulo);
        if(this.possuiPassword){
            this.title.setText("Editar Senha")
        }

        this.plataforma = findViewById(R.id.descricao);
        this.plataforma.setText(this.password.getDescricao())

        //Configuracoes do Slider
        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progresso.text = progress.toString()
                quant = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Implemente a lógica desejada quando o usuário inicia o toque na SeekBar.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Implemente a lógica desejada quando o usuário interrompe o toque na SeekBar.
            }
        })




        this.letrasMaiusculas = findViewById(R.id.letrasMaiusculas);
        this.letrasMaiusculas.isChecked = this.password.getTemLM()
        this.numeros = findViewById(R.id.numeros);
        this.numeros.isChecked = this.password.getTemN()
        this.caracteresEspeciais = findViewById(R.id.caracteresEspeciais);
        this.caracteresEspeciais.isChecked = this.password.getTemCS()
        this.submitButton = findViewById(R.id.botaoConfirmar);
        this.submitButton.setOnClickListener{submit()}
        this.cancelButton = findViewById(R.id.botaoCancelar);
        this.cancelButton.setOnClickListener{cancel()}
        this.deleteButton = findViewById(R.id.botaoExcluir);
        if(!this.possuiPassword){
            this.deleteButton.isVisible = false
        }
        this.deleteButton.setOnClickListener{ delete() }

    }

    fun cancel(){
        finish()
    }

    fun delete(){
        val intent = Intent().apply {
            putExtra("excluir",true)
        }
        setResult(RESULT_OK,intent)
        finish()
    }

    fun submit(){
        val descricao = this.plataforma.text.toString()
        val temLM = this.letrasMaiusculas.isChecked()
        val temN = this.numeros.isChecked()
        val temCS = this.caracteresEspeciais.isChecked()
        val tamanho = this.deslizador.value.toInt()

        val intent = Intent().apply {
            putExtra("descricao",descricao)
            putExtra("temLM",temLM)
            putExtra("temN",temN)
            putExtra("temCS",temCS)
            putExtra("tamanho",tamanho)
        }

        if(this.possuiPassword){
            intent.apply {
                putExtra("temSenha",true)
            }
        }
        setResult(RESULT_OK,intent)
        finish()
    }


}