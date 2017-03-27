package androidproject.com.test.androidservertestproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 3/27/2017.
 */
public class BackgroundTask3 extends AsyncTask <String,Void,String> {

    String data_url="https://jsonplaceholder.typicode.com/posts/";
    String comment_url=null;
    Context ctx;
    String PostID;
    String response="[",line="";
    public AsyncResponse2 delegate;
    ProgressDialog progressDialog;


    BackgroundTask3(Context ctx){
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        PostID=params[0];
        data_url=data_url+PostID;
       // Log.i("", "Final URL:" +data_url);



        try {
            URL url=new URL(data_url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder=new StringBuilder();

            while((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line+"\n");
                Thread.sleep(500);
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            response+=stringBuilder.toString().trim();
            response+="]";

            return "Success-Fetching Post Details";

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    @Override
    protected void onPostExecute(String s) {


        delegate.processFinish2(s,response);
        progressDialog.dismiss();

    }

    @Override
    protected void onPreExecute() {

        progressDialog=new ProgressDialog(ctx);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Fetching updated copy of the post....");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }
}
