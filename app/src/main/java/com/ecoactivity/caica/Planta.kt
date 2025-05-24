package com.ecoactivity.caica

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Classe que representa os dados de uma planta recebida da API
 * Utiliza @Parcelize para permitir envio do objeto entre Activities via Intent
 */
@Parcelize
data class Planta(

    @SerializedName("id")
    val ID: Int, // Identificador da planta no banco

    @SerializedName("nome_cientifico")
    val NOME_CIENTIFICO: String, // Nome científico oficial da planta

    @SerializedName("nome_popular")
    val NOME_POPULAR: String, // Nome popular conhecido da planta

    @SerializedName("descricao")
    val DESCRICAO: String? = null, // Descrição geral da planta

    @SerializedName("habitat")
    val HABITAT: String? = null, // Ambiente natural onde a planta vive

    @SerializedName("propriedades_medicinais")
    val PROPRIEDADES_MEDICINAIS: String? = null, // Benefícios medicinais associados

    @SerializedName("usos_tradicionais")
    val USOS_TRADICIONAIS: String? = null, // Formas tradicionais de uso

    @SerializedName("indicacoes_terapeuticas")
    val INDICACOES_TERAPEUTICAS: String? = null, // Indicações clínicas populares

    @SerializedName("contra_indicacoes")
    val CONTRA_INDICACOES: String? = null, // Contraindicações conhecidas

    @SerializedName("imagem_url")
    val IMAGEM_URL: String? = null, // URL da imagem da planta

    @SerializedName("origem_nome")
    val ORIGEM_NOME: String? = null, // Explicação sobre a origem do nome

    @SerializedName("parte_utilizada")
    val PARTE_UTILIZADA: String? = null, // Parte da planta mais usada (ex: folhas, raiz)

    @SerializedName("modo_preparo")
    val MODO_PREPARO: String? = null, // Como é feito o preparo (chá, infusão, etc)

    @SerializedName("tags")
    val TAGS: String? = null // Palavras-chave relacionadas
) : Parcelable {

    // Retorna o nome popular ou um fallback padrão
    fun getNomePopular() = NOME_POPULAR.ifEmpty { "Nome desconhecido" }

    // Retorna o nome científico ou um fallback padrão
    fun getNomeCientifico() = NOME_CIENTIFICO.ifEmpty { "Nome científico desconhecido" }

    // Retorna a URL da imagem ou string vazia se nula
    fun getImagemUrl() = IMAGEM_URL ?: ""
}
