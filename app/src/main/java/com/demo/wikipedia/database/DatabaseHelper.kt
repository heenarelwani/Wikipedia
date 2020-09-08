package com.demo.wikipedia.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "Wikipedia_db"

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //Create Tables
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ArticleTable.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db?.execSQL("DROP TABLE IF EXISTS " + ArticleTable.TABLE_NAME)
        //Create Table Again
        onCreate(db)
    }

    //insert new row
    fun insertArticleDetail(article_detail: String): Long {
        // get writable database as we want to write data
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ArticleTable.COLUMN_DETAIL, article_detail)
        // insert row
        val id = db.insert(ArticleTable.TABLE_NAME, null, contentValues)
        // return newly inserted row id
        return db.insert(ArticleTable.TABLE_NAME, null, contentValues)
    }

    //get all
    fun getAllDetails(): ArrayList<ArticleTable>? {
        val articleDetailsModels: ArrayList<ArticleTable> = ArrayList()
        // Select All Query
        val selectQuery = "SELECT  * FROM " + ArticleTable.TABLE_NAME
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        var articleId: Int
        var articledetail: String
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getCount() > 0) {
                  //  val articleDetails = ArticleTable()
                    articleId=cursor.getInt(cursor.getColumnIndex(ArticleTable.COLUMN_ID))
                    articledetail=cursor.getString(cursor.getColumnIndex(ArticleTable.COLUMN_DETAIL))
                    val article=ArticleTable(articleId,articledetail)
                    articleDetailsModels.add(article)
                } else {
                    println("In ELSE Condtion")
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return articleDetailsModels
    }

      fun delete(){
        val db = this.readableDatabase
        db.delete(ArticleTable.TABLE_NAME,null,null)
    }
}


