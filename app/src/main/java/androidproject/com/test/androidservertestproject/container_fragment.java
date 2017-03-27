package androidproject.com.test.androidservertestproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 3/24/2017.
 */
public class container_fragment extends Fragment implements AsyncResponse {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Posts> arrayList=new ArrayList<>();

    SQLiteDatabase sqLiteDatabase;
    UserDBHelper userDBHelper;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.container_fragment,container,false);


        BackgroundTask1 backgroundTask1=new BackgroundTask1(getActivity());
        backgroundTask1.delegate=this;
        backgroundTask1.execute();


        recyclerView=(RecyclerView)view.findViewById(R.id.postlistrecyclerview);
       // adapter=new RecyclerAdapter(arrayList);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



                return view;
    }


    @Override
    public void processFinish(String output) {


        if(output.contains("Operation Successful")){
            Log.i("", "Retrived Data:" + output);





            userDBHelper=new UserDBHelper(getActivity());
            sqLiteDatabase=userDBHelper.getReadableDatabase();
            cursor=userDBHelper.getInformations(sqLiteDatabase);


            if (cursor.moveToFirst()) {
                do {

                    String userId, id, title,body;
                    userId = cursor.getString(0);
                    id = cursor.getString(1);
                    title = cursor.getString(2);
                    body = cursor.getString(3);

                   // Log.i("", "UserId:" + userId);
                   // Log.i("", "ID:" + id);
                   // Log.i("", "Title:" + title);
                   // Log.i("", "Body:" + body);

                    Posts posts=new Posts(userId,id,title,body);
                    arrayList.add(posts);

                    //Log.i("", "ArrayList Size:" + arrayList.size());



                } while (cursor.moveToNext());

            }
            userDBHelper.close();
            adapter=new RecyclerAdapter(arrayList);
            recyclerView.setAdapter(adapter);
        }
        else{
            BackgroundTask2 backgroundTask2=new BackgroundTask2(getActivity());
            backgroundTask2.delegate=this;
            backgroundTask2.execute(output);
        }


    }
}
