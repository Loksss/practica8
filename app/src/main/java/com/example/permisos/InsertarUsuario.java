package com.example.permisos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class InsertarUsuario extends AppCompatActivity {

    Button enviar;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_usuario);


        ed=findViewById(R.id.nombre);
        enviar=findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),MainActivity.class);
                cargar();
                startActivity(in);
            }
        });
    }

    private void cargar(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams p=new RequestParams();
        p.put("nombre",ed.getText().toString());
        client.post("http://192.168.43.220:8000/api/v1.0/user",p,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String respuesta=response.getString("message");
                    if(respuesta!=null){
                        Toast.makeText(getApplicationContext(),respuesta,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"no hay respuesta",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
