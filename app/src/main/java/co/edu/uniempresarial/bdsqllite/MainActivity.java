package co.edu.uniempresarial.bdsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.uniempresarial.bdsqllite.classes.User;
import co.edu.uniempresarial.bdsqllite.model.UserDAO;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Validate";
    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;
    private ListView listUsers;
    private Button btnLimpiar;
    private Button btnRegister;
    private Button btnListar;
    private int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;
    SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        begin();

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    UserDAO userDAO = new UserDAO(MainActivity.this, v);
                    if (userDAO.checkDocumentExists(documento)) {
                        Toast.makeText(MainActivity.this, "Un usuario con ese número de documento ya existe.", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User(documento, usuario, nombres, apellidos, contra);
                        userDAO.insertUser(user);
                        clearFields();
                    }
                }
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearFields() {
        etDocumento.setText("");
        etUsuario.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etContra.setText("");
    }

    private void begin(){
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etContra = findViewById(R.id.etContra);
        listUsers = findViewById(R.id.lvLista);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnRegister = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
    }

    private boolean checkFields() {
        String docStr = etDocumento.getText().toString().trim();
        usuario = etUsuario.getText().toString().trim();
        nombres = etNombres.getText().toString().trim();
        apellidos = etApellidos.getText().toString().trim();
        contra = etContra.getText().toString().trim();

        if (docStr.isEmpty()) {
            etDocumento.setError("Por favor, ingrese el documento");
            return false;
        }

        if (usuario.isEmpty()) {
            etUsuario.setError("Por favor, ingrese el usuario");
            return false;
        }

        if (nombres.isEmpty()) {
            etNombres.setError("Por favor, ingrese el nombre");
            return false;
        }

        if (apellidos.isEmpty()) {
            etApellidos.setError("Por favor, ingrese el apellido");
            return false;
        }

        if (contra.isEmpty()) {
            etContra.setError("Por favor, ingrese la contraseña");
            return false;
        }

        try {
            documento = Integer.parseInt(docStr);
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Por favor, introduzca un documento válido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.i(TAG, "checkFields: " + documento);
        return true;
    }
}