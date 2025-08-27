package org.example.entidades

import java.math.BigDecimal

class Servico (
    val preco : BigDecimal,
    val condicao : String,
    val profissional : Profissional
)