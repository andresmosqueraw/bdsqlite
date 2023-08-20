package co.edu.uniempresarial.bdsqllite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import co.edu.uniempresarial.bdsqllite.classes.User;

public class UserDAO {
    private ManageDB manageDB;
    Context context;
    View view;
    User user;

    public UserDAO(Context context, View view) {
        this.context = context;
        this.view = view;
        manageDB = new ManageDB(context);
    }

    //Crear método para insertar datos en la base de datos
    public void insertUser(User myUser){
        try {
            SQLiteDatabase db = manageDB.getWritableDatabase();
            if (db != null){
                ContentValues values = new ContentValues();
                values.put("use_document", myUser.getDocument());
                values.put("use_user", myUser.getUser());
                values.put("use_names", myUser.getNames());
                values.put("use_lastnames", myUser.getLastNames());
                values.put("use_pass", myUser.getPass());
                values.put("use_status", myUser.getStatus());
                long cod = db.insert("users", null, values);
                Snackbar.make(this.view, "El usuario fue registrado con exito: " + cod, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(this.view, "El usuario no se pudo registrar. ERROR", Snackbar.LENGTH_LONG).show();
            }
        } catch (SQLException e){
            Log.i("Error", e.getMessage());
        }
    }

    public ArrayList<User> getUserList(){
        SQLiteDatabase db = manageDB.getReadableDatabase();
        String query ="select * from users";
        ArrayList<User> userList = new ArrayList<User>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                user = new User ();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastNames(cursor.getString(3));
                user.setPass(cursor.getString(4));
                user.setStatus(cursor.getInt(5));
                userList.add(user);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public User getUserByDocument(int userId) {
        SQLiteDatabase db = manageDB.getReadableDatabase();
        String query = "select * from users where use_document = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User ();
            user.setDocument(cursor.getInt(0));
            user.setUser(cursor.getString(1));
            user.setNames(cursor.getString(2));
            user.setLastNames(cursor.getString(3));
            user.setPass(cursor.getString(4));
            user.setStatus(cursor.getInt(5));
            cursor.close();
        }

        db.close();
        return user;
    }

    public void updateUser(User updatedUser) {
        SQLiteDatabase db = manageDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("use_user", updatedUser.getUser());
        values.put("use_names", updatedUser.getNames());
        values.put("use_lastnames", updatedUser.getLastNames());
        values.put("use_pass", updatedUser.getPass());
        values.put("use_status", updatedUser.getStatus());

        String whereClause = "use_document = ?";
        String[] whereArgs = { String.valueOf(updatedUser.getDocument()) };

        db.update("users", values, whereClause, whereArgs);
        db.close();
    }

    public void deleteUser(User userToBeDeleted) {
        SQLiteDatabase db = manageDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("use_status", 0); // Set status to 0

        String whereClause = "use_document = ?";
        String[] whereArgs = { String.valueOf(userToBeDeleted.getDocument()) };

        db.update("users", values, whereClause, whereArgs);
        db.close();
    }

    public boolean checkDocumentExists(int documentId) {
        boolean exists = false;
        SQLiteDatabase db = manageDB.getReadableDatabase();
        String query = "select * from users where use_document = ?";
        String[] selectionArgs = { String.valueOf(documentId) };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            exists = true;
            cursor.close();
        }

        db.close();
        return exists;
    }
}