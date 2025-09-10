package org.example.crud

import org.example.entidades.CaixaDAgua
import org.example.enumeradores.Material
import java.sql.Connection
import java.sql.ResultSet

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

fun cadastrarCaixa(id : Int) {
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
    val banco = conectar.conectarComBanco()!!
    if(id == 0){
        val salvar = banco.prepareStatement(
            "INSERT INTO CaixaDAgua" +
                    " (material, capacidade, altura, " +
                    " largura, profundidade, blablablabla, preco)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"
        )
        salvar.setString(1, c.material.name)//Enum deve sempre usar .name no final
        salvar.setDouble(2, c.capacidade!!)//Atributos nulos devem ser seguidos de !!
        salvar.setDouble(3, c.altura)
        salvar.setDouble(4, c.largura)
        salvar.setDouble(5, c.profundida)
        salvar.setString(6, c.blablablabla)
        salvar.setString(7, c.preco.toString())//No banco salve o BigDecimal como String
        salvar.executeUpdate()//Isso fará um COMMIT no banco
        salvar.close()
    }else{
        val sql = "UPDATE CaixaDAgua SET " +
                " material = ?," +
                " capacidade = ?," +
                " altura = ?," +
                " largura = ?," +
                " profundidade = ?," +
                " blablablabla = ?," +
                " preco = ?" +
                " WHERE id = ?"
        val editar = banco.prepareStatement(sql)
        editar.setInt(8, id)
        editar.setString(1, c.material.name)//Enum deve sempre usar .name no final
        editar.setDouble(2, c.capacidade!!)//Atributos nulos devem ser seguidos de !!
        editar.setDouble(3, c.altura)
        editar.setDouble(4, c.largura)
        editar.setDouble(5, c.profundida)
        editar.setString(6, c.blablablabla)
        editar.setString(7, c.preco.toString())//No banco salve o BigDecimal como String
        editar.executeUpdate()//Isso fará um COMMIT no banco
        editar.close()
    }
    banco.close()//Fecha a transação e a conexão com o banco
}

fun editarCaixa() {
    println("Digite o ID que deseja editar")
    var id = readln().toInt()

    val banco = conectar.conectarComBanco()
    val sqlBusca = "SELECT * FROM CaixaDAgua WHERE id = ?"
    val resultados = banco!!.prepareStatement(sqlBusca)
    resultados.setInt(1, id)
    val retorno = resultados.executeQuery()

    while (retorno.next()) {
        println("---------------------------------------------")
        println("Id: ${retorno.getString("id")}")
        id = retorno.getString("id").toInt()//ID da caixa que será editada

        println("Material: ${retorno.getString("material")}")
        println("Capacidade: ${retorno.getString("capacidade")}")
        println("Altura: ${retorno.getString("altura")}")
        println("Largura: ${retorno.getString("largura")}")
        println("Profundidade: ${retorno.getString("profundidade")}")
        println("Blablablabla: ${retorno.getString("blablablabla")}")
        println("Preço: ${retorno.getString("preco")}")
    }

    println("Faça suas alterações: ")
    cadastrarCaixa(id)
    banco.close()
}

fun listarCaixas() {
    val banco = conectar.conectarComBanco()
    val sql = "SELECT * FROM CaixaDAgua"
    //Após consultar o banco usando um SQL junto da função executeQuery
    //A consulta, se assertiva, retorna um array de respostas
    val resultados: ResultSet = banco!!.createStatement().executeQuery(sql)

    while (resultados.next()) {
        //Para cada consulta, use o nome que está no BANCO!
        println("---------------------------------------------")
        println("Id: ${resultados.getString("id")}")
        println("Material: ${resultados.getString("material")}")
        println("Capacidade: ${resultados.getString("capacidade")}")
        println("Altura: ${resultados.getString("altura")}")
        println("Largura: ${resultados.getString("largura")}")
        println("Profundidade: ${resultados.getString("profundidade")}")
        println("Blablablabla: ${resultados.getString("blablablabla")}")
        println("Preço: ${resultados.getString("preco")}")
    }
    resultados.close()
    banco.close()
}

fun excluirCaixa() {
    println("Digite o ID que deseja excluir")
    val id = readln().toInt()

    val banco = conectar.conectarComBanco()
    val sqlBusca = "SELECT * FROM CaixaDAgua WHERE id = ?"
    val resultados = banco!!.prepareStatement(sqlBusca)
    resultados.setInt(1, id)
    val retorno = resultados.executeQuery()

    while (retorno.next()) {
        println("---------------------------------------------")
        println("Id: ${retorno.getString("id")}")
        println("Material: ${retorno.getString("material")}")
        println("Capacidade: ${retorno.getString("capacidade")}")
        println("Altura: ${retorno.getString("altura")}")
        println("Largura: ${retorno.getString("largura")}")
        println("Profundidade: ${retorno.getString("profundidade")}")
        println("Blablablabla: ${retorno.getString("blablablabla")}")
        println("Preço: ${retorno.getString("preco")}")
    }
    retorno.close()

    println("Tem certeza que deseja excluir esse registro?")
    val resposta = readln().lowercase()
    when (resposta) {
        "sim" -> {
            val deletar = banco.prepareStatement("DELETE FROM CaixaDAgua WHERE id = ?")
            deletar.setInt(1, id)//Diz qual é o valor do 1º ponto de interrogação (?)
            deletar.executeUpdate()//Manda a instrução para ser executada no banco
            deletar.close()
        }
        else -> {
            println("Operação cancelada!")
        }
    }
    banco.close()
}