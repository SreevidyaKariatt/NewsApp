package robosoft.com.jsonparser.common.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import robosoft.com.jsonparser.favorite.FavoriteDb;

/**
 * Created by sree on 27/12/15.
 */
public class DatabaseHelper {


    ReminderHelper helper;
    Context mContext;

    public DatabaseHelper(Context context)
    {
        helper = new ReminderHelper(context);
        mContext = context;
    }

    public int deleteRow(String data)
    {
        String convertData = data.replace("'", "");
        SQLiteDatabase database = helper.getWritableDatabase();
        int count = database.delete(helper.TABLE_NAME, helper.DATA + " = '" + convertData + "'", null);
        return count;
    }
    public long insertData(int position, byte[] image,String type,String data,String description,String imageUrl,String url)
    {
        String convertDescription = null;
        String convertData = data.replace("'", "");
        //
        if(description != null)
        convertDescription = description.replace("'", "");
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReminderHelper.POSITION,position);
        contentValues.put(ReminderHelper.IMAGE, image);
        contentValues.put(ReminderHelper.TYPE, type);
        contentValues.put(ReminderHelper.DATA, convertData);
        contentValues.put(ReminderHelper.DESCRIPTION, convertDescription);
        contentValues.put(ReminderHelper.IMAGE_URL,imageUrl);
        contentValues.put(ReminderHelper.URL, url);
        //TODO make one statement
        long id = database.insertWithOnConflict(ReminderHelper.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return id;

    }

    public ArrayList<FavoriteDb> getAllRows()
    {
        String where = null;
        String[] columns = {helper.POSITION,helper.IMAGE,helper.TYPE,helper.DATA,helper.DESCRIPTION,helper.IMAGE_URL,helper.URL};
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor =database.query(helper.TABLE_NAME, columns, where, null, null, null, null);
        ArrayList<FavoriteDb> favoriteList = new ArrayList<FavoriteDb>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoriteDb favoriteObject = new FavoriteDb();
            favoriteObject.setType(cursor.getString(cursor.getColumnIndexOrThrow(helper.TYPE)));
            favoriteObject.setData(cursor.getString(cursor.getColumnIndexOrThrow(helper.DATA)));
            favoriteObject.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(helper.DESCRIPTION)));
            favoriteObject.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(helper.URL)));
            favoriteObject.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(helper.IMAGE_URL)));
            favoriteList.add(favoriteObject);
            cursor.moveToFirst();
        }
        return  favoriteList;
    }

    public boolean checkForPositionInDb(String data) {

        String convertData = data.replace("'", "");
        ArrayList<FavoriteDb> favoriteList = getAllRows();
        for(FavoriteDb favoriteObject : favoriteList) {
            if(favoriteObject.getData().equals(convertData)){
                return true;
            }
        }
        return false;
    }



    static class ReminderHelper extends SQLiteOpenHelper
    {
        private static  final String DATABASE_NAME = "News.db";
        private static  final String TABLE_NAME = "imagedetails";
        private static  final int DATABASE_VERSION = 2;
        private static final String TYPE = "type";
        private static final String POSITION = "position";
        private static final String IMAGE = "image";
        private static final String DATA = "data";
        private static final String DESCRIPTION = "description";
        private static final String URL = "url";
        private static final String IMAGE_URL = "imageurl";
        private static final String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+ "(" +POSITION+ " INTEGER," +IMAGE+ " blob," +TYPE+ " VARCHAR(255)," +DATA + " VARCHAR(255),"+DESCRIPTION+" VARCHAR(255),"+IMAGE_URL+" VARCHAR(255),"+URL+" VARCHAR(255),PRIMARY KEY ("+POSITION+","+TYPE+"));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;

        public ReminderHelper(Context context)
        {

            super(context,DATABASE_NAME,null,DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                db.execSQL(CREATE_TABLE);

            }
            catch (SQLException e)
            {

            }

        }

        //TODO handle catch
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //TODO use alter table statement
                db.execSQL(DROP_TABLE);

                onCreate(db);

            }
            catch (SQLException e)
            {

            }

        }

    }
}
