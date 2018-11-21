package com.kanbandos.mathcas.kanban_do.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBController {

    private SQLiteDatabase db;
    private CreateDB banco;

    public DBController (Context context) {
        banco = new CreateDB(context);
    }

    public String insereBoard(String titulo, String coluna1, String coluna2, String coluna3){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDB.TITULO, titulo);
        valores.put(CreateDB.COLUNA_1, coluna1);
        valores.put(CreateDB.COLUNA_2, coluna2);
        valores.put(CreateDB.COLUNA_3, coluna3);

        resultado = db.insert(CreateDB.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public String insereCard(String titulo, String description, int coluna, int board){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDB.TITULO_CARDS, titulo);
        valores.put(CreateDB.DESCRIPTION, description);
        valores.put(CreateDB.COLUNA, coluna);
        valores.put(CreateDB.BOARD_ID, board);

        resultado = db.insert(CreateDB.TABELA_CARDS, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(int coluna){
        Cursor cursor;
        String[] campos =  {banco.ID_CARDS, banco.TITULO_CARDS};
        String where = CreateDB.COLUNA + "=" + String.valueOf(coluna);
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_CARDS, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {banco.ID_CARDS,banco.TITULO_CARDS,banco.DESCRIPTION,banco.COLUNA};
        String where = CreateDB.ID_CARDS + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CreateDB.TABELA_CARDS,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, String titulo, String description, int coluna){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CreateDB.ID_CARDS + "=" + id;

        valores = new ContentValues();
        valores.put(CreateDB.TITULO, titulo);
        valores.put(CreateDB.DESCRIPTION, description);
        valores.put(CreateDB.COLUNA, coluna);

        db.update(CreateDB.TABELA_CARDS,valores,where,null);
        db.close();
    }

    public void deletaRegistro(int id){
        String where = CreateDB.ID_CARDS + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CreateDB.TABELA_CARDS,where,null);
        db.close();
    }
}
