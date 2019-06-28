package com.example.permisos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button agregar;
    ArrayList<String> nombres=new ArrayList<>(),id=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregar=findViewById(R.id.agregarUsuario);

        //dato=new datosD("llll",15,192.25);
        cargar(this);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),InsertarUsuario.class);
                startActivity(in);
            }
        });
    }


    private void cargar(final Context contex) {
        nombres.clear();
        id.clear();
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://192.168.43.220:8000/api/v1.0/user",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject re=response.getJSONObject(i);
                        nombres.add(re.getString("nombre"));
                        id.add(re.getString("_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayout llBotonera = (LinearLayout) findViewById(R.id.botonera);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT );
                for (int i=0; i<nombres.size(); i++) {
                    Button button = new Button(contex);
                    button.setId(i);
                    button.setOnClickListener((View.OnClickListener) contex);
                    button.setLayoutParams(lp);
                    button.setText("nombre :" + nombres.get(i));
                    llBotonera.addView(button);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        for(int i=0;i<nombres.size();i++){
            if(v.getId()==i){
                Intent in=new Intent(getApplicationContext(),ActualizarDeletear.class);
                in.putExtra("id",id.get(i));
                startActivity(in);
            }
        }
    }
}
