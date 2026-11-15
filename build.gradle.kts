plugins {

    // Evita cargar los plugins varias veces
    // en los distintos módulos.

    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    // Kotlin Serialization disponible para los módulos
    alias(libs.plugins.kotlinSerialization) apply false
}