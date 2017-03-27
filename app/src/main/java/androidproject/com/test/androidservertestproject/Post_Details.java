package androidproject.com.test.androidservertestproject;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Post_Details extends ActionBarActivity implements AsyncResponse2{

    String PostID=null,Titlecontent,Bodycontent,Commentscontent,user,usercomment;
    TextView Title,Body,Comments;
    JSONArray jsonArray;
    Toolbar toolbar;
    StringBuilder stringBuilder=new StringBuilder();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

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
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView= (NavigationView)findViewById(R.id.navigation_view);
        //Set the Navigation Bar Click Events
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplication(), HomeScreen.class);
                        startActivity(intent);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.setting:
                        Intent intent2 = new Intent(getApplication(), Settings.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        break;


                }


                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
    boolean twice;
    @Override
    public void onBackPressed() {
        if(twice==true){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        Toast.makeText(getBaseContext(), "Press again to Exit...", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
            }
        },3000);
        twice=true;
    }

}
