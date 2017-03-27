package androidproject.com.test.androidservertestproject;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 3/26/2017.
 */
public class BackgroundTask2 extends AsyncTask<String,Void,String> {

    UserDBHelper userDBHelper;
    SQLiteDatabase sqLiteDatabase;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Context ctx;
    public AsyncResponse delegate;

    public BackgroundTask2(Context ctx){

        this.ctx=ctx;
    }

    @Override
    protected void onPreExecute() {
      //  delegate= (AsyncResponse) this;
    }

    @Override
    protected String doInBackground(String... params) {

        String JSON_result=params[0];
        int count=0;
        String userId,id,title,body;


        try {
            jsonArray= new JSONArray(JSON_result);
            userDBHelper = new UserDBHelper(ctx);
            sqLiteDatabase = userDBHelper.getWritableDatabase();
            userDBHelper.delprevInformations(sqLiteDatabase);
           // userDBHelper.close();

            Log.i("", "Array Size:" + jsonArray.length());

            while(count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                userId=JO.getString("userId");
                id=JO.getString("id");
                title=JO.getString("title");
                body=JO.getString("body");




                count++;

                userDBHelper.addPostsInfo(userId, id, title, body, sqLiteDatabase);


            }
            userDBHelper.close();
            return "Operation Successful";

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }


}
