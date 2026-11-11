package com.example.rolin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDnDPal.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_EMAIL + " TEXT UNIQUE NOT NULL," +
                    UserContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL)";

    private static final String SQL_CREATE_SESSIONS =
            "CREATE TABLE " + UserContract.SessionEntry.TABLE_NAME + " (" +
                    UserContract.SessionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.SessionEntry.COLUMN_CODE + " TEXT UNIQUE NOT NULL," +
                    UserContract.SessionEntry.COLUMN_DM_EMAIL + " TEXT NOT NULL)";

    private static final String SQL_CREATE_CHARACTERS =
            "CREATE TABLE " + UserContract.CharacterEntry.TABLE_NAME + " (" +
                    UserContract.CharacterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.CharacterEntry.COLUMN_USER_EMAIL + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_SESSION_CODE + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_NOMBRE + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_RAZA + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_CLASE + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_NIVEL + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_DADOS + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_STR + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_INT + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_BLO + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_CON + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_DEX + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_SAB + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_VOL + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_SIG + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_INTI + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_PER + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_SUE + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_CHA + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_VIDA_ACT + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_VIDA_MAX + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_MANA_ACT + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_MANA_MAX + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_AGUANTE_ACT + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_AGUANTE_MAX + " TEXT," +
                    UserContract.CharacterEntry.COLUMN_NOTAS + " TEXT)";

    private static final String SQL_CREATE_INVENTORY =
            "CREATE TABLE " + UserContract.InventoryEntry.TABLE_NAME + " (" +
                    UserContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.InventoryEntry.COLUMN_ITEM_UUID + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_USER_EMAIL + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_SESSION_CODE + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_NOMBRE + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_CANTIDAD + " INTEGER)";

    private static final String SQL_CREATE_SKILLS =
            "CREATE TABLE " + UserContract.SkillEntry.TABLE_NAME + " (" +
                    UserContract.SkillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.SkillEntry.COLUMN_USER_EMAIL + " TEXT," +
                    UserContract.SkillEntry.COLUMN_SESSION_CODE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_NOMBRE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_COSTE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_DESCRIPCION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_SESSIONS);
        db.execSQL(SQL_CREATE_CHARACTERS);
        db.execSQL(SQL_CREATE_INVENTORY);
        db.execSQL(SQL_CREATE_SKILLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_PASSWORD, password);

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    public boolean checkUserLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { UserContract.UserEntry._ID };

        String selection = UserContract.UserEntry.COLUMN_EMAIL + " = ? AND " +
                UserContract.UserEntry.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }
    public boolean createSession(String code, String dmEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.SessionEntry.COLUMN_CODE, code);
        values.put(UserContract.SessionEntry.COLUMN_DM_EMAIL, dmEmail);

        long newRowId = db.insert(UserContract.SessionEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    public boolean checkSessionExists(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { UserContract.SessionEntry._ID };
        String selection = UserContract.SessionEntry.COLUMN_CODE + " = ?";
        String[] selectionArgs = { code };

        Cursor cursor = db.query(
                UserContract.SessionEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null, null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}