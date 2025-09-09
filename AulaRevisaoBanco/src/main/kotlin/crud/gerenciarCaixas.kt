package org.example.crud

import org.example.entidades.CaixaDAgua
import org.example.enumeradores.Material
import java.sql.Connection

val conectar = EntidadeJDBC(
    url = "jdbc:postgresql://localhost:5432/revisao",
    usuario = "postgres",
    senha = "5432"//a senha de vocês é essa -> postgres
)

fun criarTabelaCaixa() {
    //Coloque o nome da tabela o mesmo nome da entidade
    val sql = "CREATE TABLE IF NOT EXISTS CaixaDAgua " +
            " (id serial NOT NULL PRIMARY KEY," +
            " material varchar(255)," +//Enumeradores serão STRINGS no banco
            " capacidade float," +
            " altura float," +
            " largura float," +
            " profundidade float," +
            " blablablabla varchar(255)," +
            " preco varchar(255)" +//BigDecimal no banco será uma STRING
            ")"
//Cada coluna da tabela precisa ter o mesmo no dos atributos da Entidade
    val banco = conectar.conectarComBanco()
    val enviarParaBanco = banco!!.createStatement().execute(sql)

    println(enviarParaBanco)//Se retornar false ou 1, deu certo!

    banco.close()//Encerra a conexão com o banco
}

fun cadastrarCaixa() {
    println("Preço da Caixa:")
    val preco = readln().toBigDecimal()

    println("Escolha o material do qual a caixa é composta:")
    println("1 - Plástico")
    println("2 - PVC")
    println("3 - Metal")
    println("4 - Argamassa")
    val opcao = readln().toInt()
    var material: Material
    when (opcao) {
        1 -> material = Material.PLASTICO
        2 -> material = Material.PVC
        3 -> material = Material.METAL
        4 -> material = Material.ARGAMASSA
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

    val c = CaixaDAgua(
        material = material,
        blablablabla = bla,
        profundida = prof,
        largura = larg,
        altura = alt,
        capacidade = litros,
        preco = preco
    )
    val banco = conectar.conectarComBanco()!!.prepareStatement(
        "INSERT INTO CaixaDAgua" +
                " (material, capacidade, altura, " +
                " largura, profundidade, blablablabla, preco)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)"
    )
    banco.setString(1, c.material.name)//Enum deve sempre usar .name no final
    banco.setDouble(2, c.capacidade!!)//Atributos nulos devem ser seguidos de !!
    banco.setDouble(3, c.altura)
    banco.setDouble(4, c.largura)
    banco.setDouble(5, c.profundida)
    banco.setString(6, c.blablablabla)
    banco.setString(7, c.preco.toString())//No banco salve o BigDecimal como String

    banco.executeUpdate()//Isso fará um COMMIT no banco

    banco.close()//Fecha a transação e a conexão com o banco
}

fun editarCaixa() {
}

fun listarCaixas() {
}

fun excluirCaixa() {

}