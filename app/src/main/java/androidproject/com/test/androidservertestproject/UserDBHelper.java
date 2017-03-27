package androidproject.com.test.androidservertestproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 3/26/2017.
 */
public class UserDBHelper extends SQLiteOpenHelper {

    int cnt=0;

    //DB Name
    private static final String DATABASE_NAME="POSTSINFO.DB";
    //DB Versions
    private static final int DATABASE_VERSION=1;
    //Query for creating table
    String UserAccDBName;
    private static final String CREATE_TABLE_POSTS="CREATE TABLE POSTS(userId TEXT,id TEXT,title TEXT,body TEXT);";

    public UserDBHelper(Context context)
    {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
      //  Log.e("DATABASE_OPERATIONS", "Databse created/opened...");


    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_POSTS);
       // Log.e("DB Operations", "POSTS Table Created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void delprevInformations(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM POSTS;");

    }

    public void addPostsInfo(String userId,String id,String title,String body,SQLiteDatabase db)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("userId",userId);
        contentValues.put("id", id);
        contentValues.put("title",title);
        contentValues.put("body", body);
        UserAccDBName="POSTS";
        db.insert(UserAccDBName, null, contentValues);
        cnt++;


       // Log.e("DB Operations", "1 row Inserted in..." + UserAccDBName + " Count:" + cnt);
    }

    public Cursor getInformations(SQLiteDatabase db)
    {
        Cursor cursor;
        UserAccDBName="POSTS";
        String[] Projections= {"userId","id","title","body"};
        cursor=db.query(UserAccDBName,Projections,null,null,null,null,null);
        return cursor;

    }
}
