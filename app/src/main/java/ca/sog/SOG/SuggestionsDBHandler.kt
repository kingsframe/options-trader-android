package ca.sog.SOG

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File

val DATABASE_NAME = "suggestions.db"

class SuggestionsDBHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    val tableName: String = "suggestions"
    val columns: Array<String> = arrayOf("_id", "display1", "query", "date")
    override fun onCreate(p0: SQLiteDatabase?) {
        //DO NOTHING
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun checkDatabase(): Boolean {
        val databaseFile = File("/data/data/ca.sog.SOG/databases/suggestions.db")
        return databaseFile.exists()
    }
    fun getMostRecentSuggestion() :String? {
        val path = "/data/data/ca.sog.SOG/databases/suggestions.db"
        val sortOrder = "${col_date} DESC"
        val projection = arrayOf(col_query)
        var res: String? = null;
        val sugDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY)
        val cursor:  Cursor = sugDB.query(tableName, projection, null, null, null, null, sortOrder)
        if (cursor.count> 0) {
            Log.d("TICKERSEARCH", "DATA retrieved success")
            if (cursor.moveToFirst()) {
                res = cursor.getString((cursor.getColumnIndex(col_query)))
            }
            cursor.close()
        }
        return res
    }

    companion object{
        val tableName: String = "suggestions"
        val col_id = "_id"
        val col_display1 = "display1"
        val col_query = "query"
        val col_date = "date"
    }
}