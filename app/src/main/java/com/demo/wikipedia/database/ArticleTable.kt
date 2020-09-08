package com.demo.wikipedia.database

import com.demo.wikipedia.article.ArticleResponseModel.NoPages

data class ArticleTable(var id:Int,var detail:String) {
    companion object{
        val TABLE_NAME = "article_details"
        val COLUMN_ID = "id"
        val COLUMN_DETAIL = "article_detail"
        private var id = 0
        private var article_detail: String? = null
        //CREATE TABLE
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DETAIL + " TEXT "
                + ")")
    }
}
