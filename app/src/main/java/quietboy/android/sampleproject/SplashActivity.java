package quietboy.android.sampleproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tvSplashStatus)
    TextView tvStat;

    private Handler h;
    private final OkHttpClient htc = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        h = new Handler(this.getMainLooper());

        initPrereqs();
    }

    private void initPrereqs(){

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvStat.setText("Testing connection...");
                testConnection();
            }
        },1200);

    }

    private void testConnection(){
        try{
            runNetworkTestAPI();
        }
        catch (Exception ex){
            Log.e("NETWORK_TEST","Error running network connection.");
        }
    }

    private void runNetworkTestAPI() throws Exception {

        Request rq = new Request.Builder()
                .url("https://itunes.apple.com/search?term=star" +
                        "&amp;country=au&amp;media=movie&amp;all")
                .build();

        htc.newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                Snackbar.make(tvStat,"Test failed. Will be closing in two seconds.",Snackbar.LENGTH_LONG).show();

                String errmsg = e.getMessage();

                if (errmsg != null){

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            tvStat.setText("Network load error.");
                        }
                    });

                    Log.e("NET_TEST",e.getMessage());
                }

                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                SplashActivity.this.finish();
                            }
                        });
                    }
                },2000);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response != null){
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            tvStat.setText("Network test successful.");
                        }
                    });

                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            finish();
                        }
                    },500);
                }
                else{
                    throw new IOException("There was an error handling response.");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}