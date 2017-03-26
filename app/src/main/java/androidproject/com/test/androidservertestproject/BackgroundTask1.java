package androidproject.com.test.androidservertestproject;

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
 * Created by admin on 3/24/2017.
 */
public class BackgroundTask1 extends AsyncTask<String, Void, String> {

    Context ctx;

    BackgroundTask1(Context ctx)
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
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response="",line="";

            while((line=bufferedReader.readLine())!=null)
            {
                response+=line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
       // super.onPostExecute(result);
        Log.i("", "Retrived Data:" + result);
    }
}
