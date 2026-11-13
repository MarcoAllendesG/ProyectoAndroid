package com.example.rolin.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Gestor de base de datos local encargado de todas las operaciones
 * CRUD (Para poder guardar, modificar o eliminar los registros del jugador)
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDnDPal.db";
    public static final int DATABASE_VERSION = 1;

    /**
     *Crea tabla con los usuarios registrados
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_EMAIL + " TEXT UNIQUE NOT NULL," +
                    UserContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL)";

    /**
     *Crea tabla de la sesión
     */
    private static final String SQL_CREATE_SESSIONS =
            "CREATE TABLE " + UserContract.SessionEntry.TABLE_NAME + " (" +
                    UserContract.SessionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.SessionEntry.COLUMN_CODE + " TEXT UNIQUE NOT NULL," +
                    UserContract.SessionEntry.COLUMN_DM_EMAIL + " TEXT NOT NULL)";

    /**
     *Crea tabla del personaje
     */
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

    /**
     *Crea tabla de inventario
     */
    private static final String SQL_CREATE_INVENTORY =
            "CREATE TABLE " + UserContract.InventoryEntry.TABLE_NAME + " (" +
                    UserContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.InventoryEntry.COLUMN_ITEM_UUID + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_USER_EMAIL + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_SESSION_CODE + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_NOMBRE + " TEXT," +
                    UserContract.InventoryEntry.COLUMN_CANTIDAD + " INTEGER)";

    /**
     *Crea tabla de habilidades
     */
    private static final String SQL_CREATE_SKILLS =
            "CREATE TABLE " + UserContract.SkillEntry.TABLE_NAME + " (" +
                    UserContract.SkillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.SkillEntry.COLUMN_USER_EMAIL + " TEXT," +
                    UserContract.SkillEntry.COLUMN_SESSION_CODE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_NOMBRE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_COSTE + " TEXT," +
                    UserContract.SkillEntry.COLUMN_DESCRIPCION + " TEXT)";

    /**
     *Evita duplicidad de tablas
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;
    /**
     *@param context Contexto de la aplicación necesario para abrir o crear la base de datos.
     */
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

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param email Correo electrónico único del usuario.
     * @param password Contraseña asociada a la cuenta.
     * @return `true` si el registro fue un éxito, `false` si ocurrió un error o el correo ya existe.
     */
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_PASSWORD, password);

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }

    /**
     * Verifica si las credenciales ingresadas coinciden con un usuario registrado.
     *
     * @param email Correo electrónico a validar.
     * @param password Contraseña a validar.
     * @return `true` si el usuario existe y las credenciales son válidas.
     */
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
    /**
     * Asocia el código de la sesión generado.
     *
     * @return el código que se genero asociado a la sesión del dungeon master.
     */
    public boolean createSession(String code, String dmEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.SessionEntry.COLUMN_CODE, code);
        values.put(UserContract.SessionEntry.COLUMN_DM_EMAIL, dmEmail);

        long newRowId = db.insert(UserContract.SessionEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }
    /**
     * Verifica si el código ingresado es correcto.
     *
     * @return `exists` si el código ingresado concuerda con uno ya creado.
     */
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