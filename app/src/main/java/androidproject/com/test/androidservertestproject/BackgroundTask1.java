package androidproject.com.test.androidservertestproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by admin on 3/24/2017.
 */
public class BackgroundTask1 extends AsyncTask<String, Void, String> {

    Context ctx;
    String response,line="";
    ProgressDialog progressDialog;
    public AsyncResponse delegate;

    public BackgroundTask1(Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String data_url="https://jsonplaceholder.typicode.com/posts";
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

            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            response=stringBuilder.toString().trim();
            return response;

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error";
    }


    @Override
    protected void onPostExecute(String result) {
       // super.onPostExecute(result);
        //Log.i("", "Retrived Data:" + result);
        //Log.i("", "Data Size:" + result.length());

       // BackgroundTask2 backgroundTask2=new BackgroundTask2(ctx);
       // backgroundTask2.execute(result);
        delegate.processFinish(result);
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(ctx);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please wait!");
        progressDialog.setMessage("Fetching Information from server....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
