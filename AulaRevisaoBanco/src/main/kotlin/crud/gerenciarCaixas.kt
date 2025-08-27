package org.example.crud

import org.example.entidades.CaixaDAgua
import org.example.enumeradores.Material

fun cadastrarCaixa(){

    println("Escolha o material do qual a caixa é composta:")
    println("1 - Plástico")
    println("2 - PVC")
    println("3 - Metal")
    println("4 - Argamassa")
    val opcao = readln().toInt()
    var material : Material
    when(opcao){
        1-> material = Material.PLASTICO
        2-> material = Material.PVC
        3-> material = Material.METAL
        4-> material = Material.ARGAMASSA
        else -> material = Material.PLASTICO
    }

    println("Capacidade da Caixa em Litros: ")
    val litros = readln().toDouble()

    println("Altura da caixa: ")
    val alt = readln().toDouble()

    println("Largura da Caixa: ")
    val larg = readln().toDouble()

    println("Profundidade da Caixa: ")
    val prof = readln().toDouble()

    println("Blabla blabla bla? ")
    val bla = readln()

    //Salvar as variáveis agora dentro da classe
    //Conecte o atributo da classe a variável que o usuário digitou
    CaixaDAgua(
        material = material,
        blablablabla = bla,
        profundida = prof,
        largura = larg,
        altura = alt,
        capacidade = litros
    )
}

fun editarCaixa(){
}

fun listarCaixas(){
}

fun excluirCaixa(){

}