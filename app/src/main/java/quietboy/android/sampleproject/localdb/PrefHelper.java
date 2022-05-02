package quietboy.android.sampleproject.localdb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefHelper {
    private SharedPreferences sharedPreferences;
    public PrefHelper(Context ct){
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ct);
    }

    public void saveQuery(String query){
        SharedPreferences.Editor ee = this.sharedPreferences.edit();
        ee.putString("qstr",query);

        ee.commit();
    }

    public String getLastQuery(){
        return this.sharedPreferences.getString("qstr","");
    }
}
