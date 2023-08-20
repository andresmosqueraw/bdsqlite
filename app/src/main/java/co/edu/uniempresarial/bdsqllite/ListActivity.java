package co.edu.uniempresarial.bdsqllite;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.edu.uniempresarial.bdsqllite.classes.User;
import co.edu.uniempresarial.bdsqllite.model.UserDAO;

public class ListActivity extends AppCompatActivity {

    private ListView listUsers;
    private EditText etFilter;
    private Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        begin();
        userList();

        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userList();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void begin() {
        etFilter = findViewById(R.id.etFilter);
        listUsers = findViewById(R.id.lvLista);
        btnBackToMain = findViewById(R.id.btnBackToMain);
    }

    private void userList() {
        UserDAO userDAO = new UserDAO(this, listUsers);
        ArrayList<User> userArrayList = userDAO.getUserList();

        String filterText = etFilter.getText().toString().trim().toLowerCase();

        if (!filterText.isEmpty()) {
            ArrayList<User> filteredList = new ArrayList<>();
            for (User user : userArrayList) {
                if (user.getUser().toLowerCase().contains(filterText) || user.getNames().toLowerCase().contains(filterText)) {
                    filteredList.add(user);
                }
            }
            userArrayList = filteredList;
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userArrayList);
        listUsers.setAdapter(adapter);

        ArrayList<User> finalUserArrayList = userArrayList;
        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = finalUserArrayList.get(position);

                Intent intent = new Intent(ListActivity.this, EditUserActivity.class);
                intent.putExtra("userDocument", selectedUser.getDocument());
                startActivity(intent);
            }
        });
    }
}