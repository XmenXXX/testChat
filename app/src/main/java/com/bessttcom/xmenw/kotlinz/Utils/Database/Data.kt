package com.bessttcom.xmenw.kotlinz.Utils.Database

data class Message (
        val uerId: String = "",
        val message: String = "",
        val timestamp: String = "")

data class UserInfo(
        var birthday: String = "",
        var email: String = "",
        var name: String = "",
        var online: Boolean = false,
        var phone: String = "",
        var photo: String = "",
        var uid: String = "")

data class Chats(
        var id: String = "id",
        var lastMessage: String = "",
        var lastMessageTimeStamp: String = "",
        var lastMessageUserId: String = "",
        var timestamp: String = "",
        var title: String = "")