package com.khvedelidze.chatapp

data class Chat(var sender: String? = null,
                var receiver: String? = null,
                var message: String? = null,
                var isseen: Boolean? = null)