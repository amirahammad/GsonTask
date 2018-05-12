package tasks.com.gsontask;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Object>listItemInRecyclerView=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(this,listItemInRecyclerView);
        recyclerView.setAdapter(adapter);

        try {
            addMenuItemsFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void initialize(){
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
    }
    private String readJsonDataFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String jsoData = null;
            inputStream = getResources().openRawResource(R.raw.json_file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((jsoData = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsoData);
            }
        }
        finally {
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return new String(stringBuilder);
    }

    private void addMenuItemsFromJson() throws IOException, JSONException {
        String jsonData=readJsonDataFromFile();
        JSONArray jsonArray=new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); ++i) {

            JSONObject menuItemObject = jsonArray.getJSONObject(i);

            String menuItemName = menuItemObject.getString("name");
            String menuItemDescription = menuItemObject.getString("description");
            String menuItemPrice = menuItemObject.getString("price");
            String menuItemCategory = menuItemObject.getString("category");
            String menuItemImageName = menuItemObject.getString("photo");
            Pojo pojo = new Pojo(menuItemName, menuItemDescription, menuItemPrice,
                    menuItemCategory, menuItemImageName);
            listItemInRecyclerView.add(pojo);
        }



    }
}
