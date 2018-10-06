package com.example.mathcas.kanban_do.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "boards";
    private static final String ID = "_id";
    public static final String TITULO = "titulo";
    public static final String COLUNA_1 = "first_column";
    public static final String COLUNA_2 = "second_column";
    public static final String COLUNA_3 = "third_column";

    public static final String TABELA_CARDS = "cards";
    public static final String ID_CARDS = "_id";
    public static final String TITULO_CARDS = "titulo";
    public static final String DESCRIPTION = "description";
    public static final String COLUNA = "columnn";
    public static final String BOARD_ID = "id";
    private static final int VERSION = 1;

    public CreateDB(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + TITULO + " text,"
                + COLUNA_1 + " integer,"
                + COLUNA_2 + " integer,"
                + COLUNA_3 + " integer"
                + ")";
        db.execSQL(sql);

        String sqlCards = "CREATE TABLE " + TABELA_CARDS + "("
                + ID_CARDS + " integer primary key autoincrement,"
                + TITULO_CARDS + " text,"
                + DESCRIPTION + " text,"
                + COLUNA + " integer,"
                + BOARD_ID + " integer"
                + ")";
        db.execSQL(sqlCards);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CARDS);
        onCreate(db);
    }
}
