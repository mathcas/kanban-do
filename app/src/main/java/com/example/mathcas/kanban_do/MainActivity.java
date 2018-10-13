package com.example.mathcas.kanban_do;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathcas.kanban_do.DataBase.CreateDB;
import com.example.mathcas.kanban_do.DataBase.DBController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private FloatingActionButton floatingActionButton1;
    private FloatingActionButton floatingActionButton2;
    private FloatingActionButton floatingActionButton3;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private Button buttonDialog;
    private int cont;
    private ListView lista1;
    private ListView lista2;
    private ListView lista3;
    private GridLayout gridCard;
    private SimpleCursorAdapter adaptador;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restart);

        linearLayout1 = findViewById(R.id.linear1);
        linearLayout2 = findViewById(R.id.linear2);
        linearLayout3 = findViewById(R.id.linear3);
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);
        lista1 = (ListView)findViewById(R.id.listview1);
        lista2 = (ListView)findViewById(R.id.listview2);
        lista3 = (ListView)findViewById(R.id.listview3);

        carregaCards(1);
        carregaCards(2);
        carregaCards(3);

        this.setListener();
    }

    private void setListener() {
        lista1.setOnItemClickListener(this);
        lista2.setOnItemClickListener(this);
        lista3.setOnItemClickListener(this);
        floatingActionButton1.setOnClickListener(this);
        floatingActionButton2.setOnClickListener(this);
        floatingActionButton3.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.listview1:
                cont = 1;
                createDialogWithData(String.valueOf(id));
                break;
            case R.id.listview2:
                cont = 2;
                createDialogWithData(String.valueOf(id));
                break;
            case R.id.listview3:
                cont = 3;
                createDialogWithData(String.valueOf(id));
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.floatingActionButton1:
                cont = 1;
                createDialogCustom();
                break;
            case R.id.floatingActionButton2:
                cont = 2;
                createDialogCustom();
                break;
            case R.id.floatingActionButton3:
                cont = 3;
                createDialogCustom();
                break;
            case R.id.grid_card:
                if (v instanceof GridLayout) {
                    if (((GridLayout) v).getChildAt(0) instanceof TextView) {
                        createDialogWithData(((TextView) ((GridLayout) v).getChildAt(0)).getText().toString());
                    }
                }
                break;
        }
    }

    private void createDialogWithData(final String id) {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
        dialog.setContentView(R.layout.dialog_card_full);

        final EditText title = dialog.findViewById(R.id.titulo_card);
        final EditText description = dialog.findViewById(R.id.descricao_card);

        final DBController crud = new DBController(getBaseContext());
        final Cursor cursor = crud.carregaDadoById(Integer.parseInt(id));
        title.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDB.TITULO_CARDS)));
        description.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDB.DESCRIPTION)));

        buttonDialog = dialog.findViewById(R.id.confirm_card);
        if (buttonDialog != null) buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterCard(Integer.valueOf(id), cont, dialog);
            }
        });

        Button deleteCard = dialog.findViewById(R.id.delete_card);
        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.deletaRegistro(Integer.valueOf(id));
                carregaCards(cont);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void createDialogCustom() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
        dialog.setContentView(R.layout.dialog_card);
        buttonDialog = dialog.findViewById(R.id.confirm_card);
        if (buttonDialog != null) buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCard(cont, dialog);
            }
        });
        dialog.show();
    }

    private void insertCard(int coluna, Dialog dialog) {
        DBController crud = new DBController(getBaseContext());
        EditText titulo = (EditText)dialog.findViewById(R.id.titulo_card);
        EditText description = (EditText)dialog.findViewById((R.id.descricao_card));
        String tituloString = titulo.getText().toString();
        String descriptionString = description.getText().toString();
        String resultado;
        String id = "";
        resultado = crud.insereCard(tituloString,descriptionString, coluna, 1);

        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
        carregaCards(coluna);
        dialog.dismiss();
    }

    private void alterCard(int id, int coluna, Dialog dialog) {
        DBController crud = new DBController(getBaseContext());
        EditText titulo = (EditText)dialog.findViewById(R.id.titulo_card);
        EditText description = (EditText)dialog.findViewById((R.id.descricao_card));
        String tituloString = titulo.getText().toString();
        String descriptionString = description.getText().toString();
        CheckBox checkBox = dialog.findViewById(R.id.alarm);
        if (checkBox.isChecked()) scheduleNotification(getNotification(tituloString), 1000);
        int column = coluna;
        column = verifyRadioButtonIsChecked(column, dialog);

        crud.alteraRegistro(id, tituloString, descriptionString, column);

        if (column != coluna) carregaCards(column);
        carregaCards(coluna);
        dialog.dismiss();
    }

    private int verifyRadioButtonIsChecked(int column, Dialog dialog) {
        RadioButton radioButton1 = dialog.findViewById(R.id.radio_button1);
        RadioButton radioButton2 = dialog.findViewById(R.id.radio_button2);
        RadioButton radioButton3 = dialog.findViewById(R.id.radio_button3);
        if (radioButton1.isChecked()) column = Integer.valueOf(radioButton1.getText().toString());
        if (radioButton2.isChecked()) column = Integer.valueOf(radioButton2.getText().toString());
        if (radioButton3.isChecked()) column = Integer.valueOf(radioButton3.getText().toString());
        return column;
    }

    private void carregaCards(int coluna) {
        DBController crud = new DBController(getBaseContext());
        Cursor cursor = crud.carregaDados(coluna);

        String[] nomeCampos = new String[] { CreateDB.ID_CARDS, CreateDB.TITULO_CARDS};
        int[] idViews = new int[] {R.id.id_text, R.id.card_text_view};

        adaptador = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.card_text_recycler,cursor,nomeCampos,idViews, 0);

        if (coluna == 1) {
            lista1.setAdapter(adaptador);
        }
        else if (coluna == 2) {
            lista2.setAdapter(adaptador);
        }
        else if (coluna == 3) {
            lista3.setAdapter(adaptador);
        }

    }

    private void initGridCards() {
        gridCard = new GridLayout(getBaseContext());
        gridCard = findViewById(R.id.grid_card);
        gridCard.setLayoutMode(R.layout.card_text_recycler);
        gridCard.setPadding(0,16,0,16);
        gridCard.setClickable(true);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Kanban-do");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.fragment_contraint_radius);
        return builder.build();
    }

}
