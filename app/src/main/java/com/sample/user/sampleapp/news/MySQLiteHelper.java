package com.sample.user.sampleapp.news;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "NewsDB";

    // MyNews table name
    private static final String TABLE_NEWS = "news_table";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create news table
        String CREATE_NEW_TABLE =
                "CREATE TABLE "+ TABLE_NEWS + " ( " +
                "id TEXT UNIQUE, " +
                "url TEXT, "+
                "title TEXT )";
 
        // create newss table
        db.execSQL(CREATE_NEW_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older newss table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
 
        // create fresh newss table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
    /**
     * CRUD operations (create "add", read "get", update, delete) news + get all newss + delete all newss
     */

 
    // Newss Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url";
 
    private static final String[] COLUMNS = {KEY_ID,KEY_URL,KEY_TITLE};
 
    public void addNews(News news){
        Log.d("addNews", news.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        String url = news.getURL();
        values.put(KEY_ID, news.getId()); // get title
        values.put(KEY_URL, news.getURL()); // get author
        values.put(KEY_TITLE, news.getTitle()); // get title

        // 3. insert
        db.insert(TABLE_NEWS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public News getNews(String id){
 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_NEWS, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { id }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() == 0) return null;

        Log.d("getNews --> ", "Data: "
                + cursor.getString(0) + " - "
                + cursor.getString(1) + " - "
                + cursor.getString(2) + " - ");
        // 4. build news object
        News news = new News();
        news.setId(cursor.getString(0));
        news.setURL(cursor.getString(1));
        news.setTitle(cursor.getString(2));

        Log.d("getNews(" + id + ")", news.toString());
 
        // 5. return news
        return news;
    }
 
    // Get All Newss
    public List<News> getAllNews() {
        List<News> newsList = new LinkedList<News>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NEWS;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build news and add it to list
        News news = null;
        if (cursor.moveToFirst()) {
            do {
                news = new News();
                news.setId(cursor.getString(0));
                news.setTitle(cursor.getString(1));
                news.setURL(cursor.getString(2));
 
                // Add news to newss
                newsList.add(news);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllNews()", newsList.toString());
 
        // return newss
        return newsList;
    }
 
     // Updating single news
    public int updateNews(News news) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", news.getTitle()); // get title
        values.put("url", news.getURL()); // get author
 
        // 3. updating row
        int i = db.update(TABLE_NEWS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { news.getId() }); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single news
    public void deleteNews(News news) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_NEWS,
                KEY_ID+" = ?",
                new String[] { news.getId() });
 
        // 3. close
        db.close();
 
        Log.d("deleteNews", news.toString());
 
    }
}