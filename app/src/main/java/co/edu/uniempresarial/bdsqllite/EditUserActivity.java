package co.edu.uniempresarial.bdsqllite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.uniempresarial.bdsqllite.classes.User;
import co.edu.uniempresarial.bdsqllite.model.UserDAO;

public class EditUserActivity extends AppCompatActivity {

    private EditText etNombres;
    private EditText etApellidos;
    private EditText etUsuario;
    private EditText etContra;
    private Button btnSave;
    private Button btnDelete;
    private Button btnBackToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        begin();

        // Recupera los datos del usuario que se va a editar
        int userDocument = getIntent().getIntExtra("userDocument", -1);
        if (userDocument != -1) {
            UserDAO userDAO = new UserDAO(this, null);
            User user = userDAO.getUserByDocument(userDocument);
            if (user != null) {
                etNombres.setText(user.getNames());
                etApellidos.setText(user.getLastNames());
                etUsuario.setText(user.getUser());
                etContra.setText(user.getPass());
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    UserDAO userDAO = new UserDAO(EditUserActivity.this, null);
                    User updatedUser = new User();
                    updatedUser.setDocument(userDocument);
                    updatedUser.setNames(etNombres.getText().toString());
                    updatedUser.setLastNames(etApellidos.getText().toString());
                    updatedUser.setUser(etUsuario.getText().toString());
                    updatedUser.setPass(etContra.getText().toString());
                    updatedUser.setStatus(1);

                    userDAO.updateUser(updatedUser);

                    Toast.makeText(EditUserActivity.this, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = new UserDAO(EditUserActivity.this, null);
                User deleteUser = userDAO.getUserByDocument(userDocument);

                if (deleteUser != null) {
                    userDAO.deleteUser(deleteUser);

                    Toast.makeText(EditUserActivity.this, "El usuario ahora esta inactivo", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBackToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void begin() {
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etUsuario = findViewById(R.id.etUsuario);
        etContra = findViewById(R.id.etContra);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnBackToList = findViewById(R.id.btnBackToList);
    }


    private boolean validateFields() {
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String contra = etContra.getText().toString().trim();

        if (nombres.isEmpty()) {
            etNombres.setError("Por favor, ingrese el nombre");
            return false;
        }
        if (apellidos.isEmpty()) {
            etApellidos.setError("Por favor, ingrese el apellido");
            return false;
        }
        if (usuario.isEmpty()) {
            etUsuario.setError("Por favor, ingrese el usuario");
            return false;
        }
        if (contra.isEmpty()) {
            etContra.setError("Por favor, ingrese la contraseña");
            return false;
        }

        return true;
    }


}