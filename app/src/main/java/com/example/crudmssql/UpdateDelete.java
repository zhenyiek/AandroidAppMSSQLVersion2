package com.example.crudmssql;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.crudmssql.model.Employee;
import com.example.crudmssql.util.ConnectionHelper;

public class UpdateDelete extends AppCompatActivity {

    TextView editId, editName, editPhoneNum, editAge;
    Button btnDone, btnCancel;

    ConnectionHelper Connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Connection = new ConnectionHelper();

        editId = (TextView) findViewById(R.id.txtId);
        editName = (TextView) findViewById(R.id.txtName);
        editPhoneNum = (TextView) findViewById(R.id.txtPhone);
        editAge = (TextView) findViewById(R.id.txtAge);
        btnDone = (Button) findViewById(R.id.btn_Done);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            int id = (int) bundle.get("ID");
            recoverEmployee(id);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                java.sql.Connection conn = Connection.Conn();
                String resultado = "";

                if(conn == null) {
                    Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();
                // Update an existing record
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(editId.getText().toString()));
                employee.setName(editName.getText().toString());
                employee.setPhone("Done");
                employee.setAge(Integer.parseInt(editAge.getText().toString()));

                resultado = updateDone(conn, employee);

                Toast.makeText(UpdateDelete.this, resultado, Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                java.sql.Connection conn = Connection.Conn();
                String resultado = "";

                if(conn == null) {
                    Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();
                // Update an existing record
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(editId.getText().toString()));
                employee.setName(editName.getText().toString());
                employee.setPhone("Cancel");
                employee.setAge(Integer.parseInt(editAge.getText().toString()));

                resultado = updateCancel(conn, employee);

                Toast.makeText(UpdateDelete.this, resultado, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void recoverEmployee(int id) {

        Connection conn = Connection.Conn();
        String resultado = "";

        if(conn == null) {
            Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String QUERY = "SELECT * FROM employee WHERE id = " + id;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next()) {
                editId.setText(String.valueOf(id));
                editName.setText(resultSet.getString("name"));
                editPhoneNum.setText(resultSet.getString("phone"));
                editAge.setText(String.valueOf(resultSet.getInt("age")));
            }

        } catch (SQLException erro) {
            Toast.makeText(UpdateDelete.this, "An error has occurred: " + erro, Toast.LENGTH_LONG).show();
        }

    }

    public String updateDone(Connection conn, Employee employee) {
        try {
            String QUERY_UPDATE = "UPDATE employee SET name = ?, phone = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPhone());
            preparedStatement.setInt(3, employee.getId());
            preparedStatement.setInt(4, employee.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Updated successfully";
        } catch (SQLException erro) {
            return "Error updating:" + erro.getMessage();
        }
    }

    public String updateCancel(Connection conn, Employee employee) {
        try {
            String QUERY_UPDATE = "UPDATE employee SET name = ?, phone = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPhone());
            preparedStatement.setInt(3, employee.getId());
            preparedStatement.setInt(4, employee.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Cancel successfully";
        } catch (SQLException erro) {
            return "Error updating:" + erro.getMessage();
        }
    }

}