package com.akmj.myapplication

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello broooooooo, ${platform.name}!"
    }
}