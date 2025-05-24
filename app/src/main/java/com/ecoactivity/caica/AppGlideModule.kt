package com.ecoactivity.caica

// Importa a anotação GlideModule que indica ao Glide para gerar classes auxiliares
import com.bumptech.glide.annotation.GlideModule

// Importa a classe base que será estendida para configurar o Glide
import com.bumptech.glide.module.AppGlideModule

/**
 * Classe de configuração personalizada do Glide.
 *
 * Essa classe informa ao Glide que você deseja usar um módulo customizado.
 * Com isso, o Glide gera automaticamente classes como GlideApp, GlideRequest etc.
 *
 * Essa configuração permite:
 * - Aplicar opções globais de cache, decodificação, transformação de imagem
 * - Habilitar integração com bibliotecas (Ex: FirebaseImageLoader)
 * - Usar GlideApp.with(...) em vez de Glide.with(...)
 *
 * Essa classe precisa estar presente para que o Glide gere as classes
 * complementares automaticamente após o build.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean = false
// para otimização
}
