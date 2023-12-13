package com.example.personalpasswordmanager

class Password {

    private var descricao : String;
    private lateinit var senha: String;
    private val listaMinusculas : CharRange = ('a'..'z')
    private val listaMaiusculas : CharRange = ('A'..'Z')
    private val listaNumeros : CharRange = ('0'..'9')
    private val listaEspeciais : CharArray = "!@#$%*-+=(){}[]".toCharArray()

    private val isMaiusculas:Boolean;
    private val isEspecial:Boolean;
    private val isNumero:Boolean;
    private val quant:Int;

    constructor(descricao:String?,temLM:Boolean? = false,temCS:Boolean?,temN:Boolean?,tamanho: Int?){
        this.descricao = if (descricao!=null) descricao else "";
        this.isMaiusculas = if (temLM!=null) temLM else false;
        this.isEspecial = if (temCS!=null) temCS else false;
        this.isNumero = if (temN!=null) temN else false;
        this.quant = if (tamanho!=null) tamanho else 4;
        gerarSenha()
    }

    fun gerarSenha(){
        var listaDeCaracteres = mutableListOf<Char>()

        for(caractere in listaMinusculas){
            listaDeCaracteres.add(caractere)
        }

        if(this.isMaiusculas){
            for(caractere in listaMaiusculas){
                listaDeCaracteres.add(caractere)
            }
        }

        if (this.isNumero){
            for(caractere in listaNumeros){
                listaDeCaracteres.add(caractere)
            }
        }

        if(this.isEspecial){
            for(caractere in listaEspeciais){
                listaDeCaracteres.add(caractere)
            }
        }

        this.senha = List(quant) { listaDeCaracteres.random() }.joinToString("")
    }

    fun getDescricao():String{
        return this.descricao
    }

    fun setDescricao(novaDescricao:String){
        this.descricao = novaDescricao
    }

    fun getSenha():String{
        return this.senha;
    }

    fun getQuant():Int{
        return this.quant
    }

    fun getTemLM():Boolean{
        return this.isMaiusculas
    };

    fun getTemCS():Boolean{
        return this.isEspecial
    };

    fun getTemN():Boolean{
        return this.isNumero
    };

}