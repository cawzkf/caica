package com.ecoactivity.caica

data class Planta(
    val id: Int,
    val nome_cientifico: String,
    val nome_popular: String,
    val descricao_botanica: String?,
    val habitat: String?,
    val propriedades_medicinais: String?,
    val uso_tradicional: String?,
    val metodo_preparo: String?,
    val indicacoes_terapeuticas: String?,
    val compostos_ativos: String?,
    val estudos_cientificos: String?,
    val sustentabilidade: String?,
    val impacto_social: String?,
    val data_descoberta: String?,
    val observacoes: String?,
    val uso_medicinal: String
)
