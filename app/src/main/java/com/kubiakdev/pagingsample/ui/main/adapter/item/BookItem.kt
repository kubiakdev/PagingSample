package com.kubiakdev.pagingsample.ui.main.adapter.item

interface BookItem {
    val id: String
    val title: String
    val author: String
    val description: String
    val imageInBase64: String
}