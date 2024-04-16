package org.example.config

import org.lighthousegames.logging.logging

private val logger = logging()

object config {
    var databaseUrl: String = "jdbc:sqlite:personajes.db"
        private set
    var databaseInitTables: Boolean = true
        private set
    var databaseInitData: Boolean = true
        private set
    var storageData: String = "data"
        private set
    var cacheSize: Int = 7
        private set

    init {
        try {
            logger.debug { "Cargando configuración de la base de datos" }
            val properties = Properties()
            properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
            databaseUrl = properties.getProperty("database.url", this.databaseUrl)
            databaseInitTables = properties.getProperty("database.init.tables", this.databaseInitTables.toString()).toBoolean()
            databaseInitData = properties.getProperty("database.init.data", this.databaseInitData.toString()).toBoolean()
            storageData = properties.getProperty("storage.data", this.storageData)
            cacheSize = properties.getProperty("cache.size", this.cacheSize.toString()).toInt()
            logger.debug { "Configuración cargada correctamente" }

            Files.createDirectories(Path(storageData))

        } catch (e: Exception) {
            logger.error { "Ha ocurrido un error al cargar la configuración: ${e.message}" }
            logger.error { "Usando valores del sistema por defecto" }
        }
    }
}