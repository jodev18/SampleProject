package quietboy.android.sampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import quietboy.android.sampleproject.adapters.ItemAdapter;
import quietboy.android.sampleproject.objs.BaseJSONContent;
import quietboy.android.sampleproject.objs.ResultContent;
import quietboy.android.sampleproject.utils.StringUtils;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.rContent)
    RecyclerView lContent;
    @BindView(R.id.etSearchbox)
    EditText eSearch;
    @BindView(R.id.btnSearch)
    Button bSearch;
    @BindView(R.id.tvHintInstruction)
    TextView tInstruc;
    @BindView(R.id.prgLoadingItems)
    ProgressBar pLoad;

    private Handler h;
    private final OkHttpClient htc = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        h = new Handler(this.getMainLooper());
        ButterKnife.bind(this);

        bSearch.setOnClickListener(search);
    }

    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            String searchkey = eSearch.getText().toString();

            Log.d("SearchURL",searchkey);
            Log.d("SearchURL",Integer.valueOf(searchkey.length()).toString());

            if (searchkey.length() > 0){

                //Display progressbar, hide our recyclerview for the meantime
                pLoad.setVisibility(View.VISIBLE);
                lContent.setVisibility(View.INVISIBLE);

                Request rq = new Request.Builder()
                        .url(StringUtils.getSearchURL(searchkey))
                        .build();

                htc.newCall(rq).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        tInstruc.setText("There was a problem loading the search results.");
                        pLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if(response != null){
                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    tInstruc.setVisibility(View.GONE);
                                    pLoad.setVisibility(View.GONE);
                                    lContent.setVisibility(View.VISIBLE);
                                    try{
                                        String datacontent = response.body().string();
//                                Log.d("Data",datacontent);
                                        Gson gg = new Gson();
                                        BaseJSONContent bb = gg.fromJson(datacontent,BaseJSONContent.class);
                                        ResultContent[] rr = bb.results;

                                        // For debug only
//                                        for(int x=0;x<rr.length;x++){
//                                            if (rr[x].trackName != null){
//                                                Log.d("DataItem",rr[x].trackName);
//                                            }
//                                        }
                                        if (rr.length > 0){
                                            LinearLayoutManager llm = new LinearLayoutManager(SearchActivity.this);
                                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                                            lContent.setLayoutManager(llm);
                                            ItemAdapter itemAdapter = new ItemAdapter(SearchActivity.this, rr);
                                            lContent.setAdapter(itemAdapter);
                                        }
                                        else{
                                            tInstruc.setVisibility(View.VISIBLE);
                                            tInstruc.setText("There are no entries for that search term.");
                                        }

                                    }
                                    catch (IOException ioex){
                                        Log.e("Err data","Error loading data.");
                                    }
                                }
                            });
                        }
                        else{
                            throw new IOException("There was an error handling response.");
                        }
                    }
                });
            }
            else{
                Snackbar.make(eSearch,"Please enter a " +
                        "keyword in the textbox.",Snackbar.LENGTH_LONG).show();
            }
        }
    };
}