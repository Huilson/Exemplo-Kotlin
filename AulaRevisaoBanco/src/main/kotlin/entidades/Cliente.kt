package org.example.entidades

import org.example.enumeradores.Sexo

class Cliente(
    nome: String,
    idade: Int,
    sexo: Sexo,
    cpf: String,
    val endereco: String,
    val situacao: String
) : Pessoa(
    nome = nome,
    idade = idade,
    sexo = sexo,
    cpf = cpf
){

}