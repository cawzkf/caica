package com.ecoactivity.caica

import com.google.gson.annotations.SerializedName

data class Planta(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("NOME_CIENTIFICO")
    val NOME_CIENTIFICO: String,

    @SerializedName("NOME_POPULAR")
    val NOME_POPULAR: String,

    @SerializedName("DESCRICAO_BOTANICA")
    val DESCRICAO_BOTANICA: String?,

    @SerializedName("HABITAT")
    val HABITAT: String?,

    @SerializedName("PROPRIEDADES_MEDICINAIS")
    val PROPRIEDADES_MEDICINAIS: String?,

    @SerializedName("USO_TRADICIONAL")
    val USO_TRADICIONAL: String?,

    @SerializedName("METODO_PREPARO")
    val METODO_PREPARO: String?,

    @SerializedName("INDICACOES_TERAPEUTICAS")
    val INDICACOES_TERAPEUTICAS: String?,

    @SerializedName("COMPOSTOS_ATIVOS")
    val COMPOSTOS_ATIVOS: String?,

    @SerializedName("ESTUDOS_CIENTIFICOS")
    val ESTUDOS_CIENTIFICOS: String?,

    @SerializedName("SUSTENTABILIDADE")
    val SUSTENTABILIDADE: String?,

    @SerializedName("IMPACTO_SOCIAL")
    val IMPACTO_SOCIAL: String?,

    @SerializedName("DATA_DESCOBERTA")
    val DATA_DESCOBERTA: String?,

    @SerializedName("OBSERVACOES")
    val OBSERVACOES: String?,

    @SerializedName("USO_MEDICINAL")
    val USO_MEDICINAL: String,

    @SerializedName("IMAGEM")
    val IMAGEM: String?
) {
    // Funções de conveniência para evitar nulls
    fun getNomePopular() = NOME_POPULAR.ifEmpty { "Nome desconhecido" }
    fun getNomeCientifico() = NOME_CIENTIFICO.ifEmpty { "Nome científico desconhecido" }
    fun getImagemUrl() = IMAGEM?.replace("localhost", "192.168.0.100") ?: ""
}