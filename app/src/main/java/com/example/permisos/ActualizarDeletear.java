package com.example.permisos;

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

import cz.msebera.android.httpclient.Header;
public class ActualizarDeletear extends AppCompatActivity implements View.OnClickListener{

    Button actualizar,deletear;
    EditText ed;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_deletear);
        actualizar=findViewById(R.id.actualizar);
        actualizar.setOnClickListener(this);

        deletear=findViewById(R.id.eliminar);
        deletear.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getString("id");

        ed=findViewById(R.id.nombreActualizar);
        cargar();
    }

    private void cargar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://192.168.43.220:8000/api/v1.0/user/",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject re=response.getJSONObject(i);
                        if(id.equals(re.getString("_id"))){
                            ed.setText(re.getString("nombre"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    };
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.actualizar){
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            enviar();
            startActivity(in);
        }
        if(v.getId()==R.id.eliminar){
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            eliminar();
            startActivity(in);
        }
    }

    private void eliminar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.delete("http://192.168.43.220:8000/api/v1.0/user/"+id,null,new JsonHttpResponseHandler(){
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

    private void enviar() {
        //complete
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams p=new RequestParams();
        p.put("nombre",ed.getText().toString());
        client.patch("http://192.168.43.220:8000/api/v1.0/user/"+id,p,new JsonHttpResponseHandler(){
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
