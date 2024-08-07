package com.learnspigot.bot

import io.github.cdimascio.dotenv.Dotenv

object Environment {
    private val dotenv = Dotenv.configure()
        .systemProperties()
        .load()

    fun get(variable: String): String {
        return dotenv.get(variable)
    }
}