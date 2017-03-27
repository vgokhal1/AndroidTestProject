package androidproject.com.test.androidservertestproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Post_Details extends ActionBarActivity implements AsyncResponse2{

    String PostID=null,Titlecontent,Bodycontent,Commentscontent,user,usercomment;
    TextView Title,Body,Comments;
    JSONArray jsonArray;
    Toolbar toolbar;
    StringBuilder stringBuilder=new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__details);
        PostID=getIntent().getStringExtra("id");
        Title=(TextView)findViewById(R.id.PostTitle);
        Body=(TextView)findViewById(R.id.PostBody);
        Comments=(TextView)findViewById(R.id.PostComment);

        BackgroundTask3 backgroundTask3=new BackgroundTask3(this);
        backgroundTask3.delegate=this;
        backgroundTask3.execute(PostID);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Details");

    }


    @Override
    public void processFinish2(String status,String output) {

        if(status.contains("Success-Fetching Post Details")){
           // Log.i("","Output:"+output);
            int count=0;

            try {
                jsonArray= new JSONArray(output);


              //  Log.i("", "Array Size:" + jsonArray.length());

                while(count<jsonArray.length())
                {
                    JSONObject JO=jsonArray.getJSONObject(count);

                    Titlecontent=JO.getString("title");
                    Bodycontent=JO.getString("body");

                    Thread.sleep(5);

                    count++;



                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Log.i("","Titlecontent:"+Titlecontent);
           // Log.i("", "Bodycontent:" + Bodycontent);


            BackgroundTask4 backgroundTask4=new BackgroundTask4(this);
            backgroundTask4.delegate=this;
            backgroundTask4.execute(PostID);
        }

        else if(status.contains("Success-Fetching Post Comments")){
           // Log.i("","Output:"+output);

            int count=0;

            try {
                jsonArray= new JSONArray(output);


                //  Log.i("", "Array Size:" + jsonArray.length());

                while(count<jsonArray.length())
                {
                    JSONObject JO=jsonArray.getJSONObject(count);


                    user=JO.getString("email");
                    usercomment=JO.getString("body");

                    stringBuilder.append("\n"+user+" commented: \n\n");
                    stringBuilder.append(usercomment+"\n\n");


                            Thread.sleep(5);

                    count++;



                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Commentscontent=stringBuilder.toString().trim();
            Title.setText(Titlecontent);
            Body.setText(Bodycontent);
            Comments.setText(Commentscontent);

        }




    }
}
