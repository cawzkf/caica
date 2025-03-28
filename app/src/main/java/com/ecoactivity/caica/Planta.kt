package com.ecoactivity.caica

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Planta(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("NOME_CIENTIFICO")
    val NOME_CIENTIFICO: String,

    @SerializedName("NOME_POPULAR")
    val NOME_POPULAR: String,

    @SerializedName("DESCRICAO_BOTANICA")
    val DESCRICAO_BOTANICA: String? = null,

    @SerializedName("HABITAT")
    val HABITAT: String? = null,

    @SerializedName("PROPRIEDADES_MEDICINAIS")
    val PROPRIEDADES_MEDICINAIS: String? = null,

    @SerializedName("USO_TRADICIONAL")
    val USO_TRADICIONAL: String? = null,

    @SerializedName("METODO_PREPARO")
    val METODO_PREPARO: String? = null,

    @SerializedName("INDICACOES_TERAPEUTICAS")
    val INDICACOES_TERAPEUTICAS: String? = null,

    @SerializedName("COMPOSTOS_ATIVOS")
    val COMPOSTOS_ATIVOS: String? = null,

    @SerializedName("ESTUDOS_CIENTIFICOS")
    val ESTUDOS_CIENTIFICOS: String? = null,

    @SerializedName("SUSTENTABILIDADE")
    val SUSTENTABILIDADE: String? = null,

    @SerializedName("IMPACTO_SOCIAL")
    val IMPACTO_SOCIAL: String? = null,

    @SerializedName("DATA_DESCOBERTA")
    val DATA_DESCOBERTA: String? = null,

    @SerializedName("OBSERVACOES")
    val OBSERVACOES: String? = null,

    @SerializedName("USO_MEDICINAL")
    val USO_MEDICINAL: String? = null,

    @SerializedName("IMAGEM")
    val IMAGEM: String? = null
) : Parcelable {
    // Funções de conveniência para evitar nulls
    fun getNomePopular() = NOME_POPULAR.ifEmpty { "Nome desconhecido" }
    fun getNomeCientifico() = NOME_CIENTIFICO.ifEmpty { "Nome científico desconhecido" }
    fun getImagemUrl() = IMAGEM?.replace("localhost", "192.168.0.100") ?: ""
}
