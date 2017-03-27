package androidproject.com.test.androidservertestproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by admin on 3/23/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    ArrayList<Posts> arrayList=new ArrayList<>();
    Context ctx;


    public RecyclerAdapter(ArrayList<Posts> arrayList,Context ctx)
    {

        this.arrayList=arrayList;
        this.ctx=ctx;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler_view, parent, false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view,ctx);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Posts posts=arrayList.get(position);

        holder.userId=posts.getUserId();
        holder.id=posts.getId();
        holder.title.setText(posts.getTitle());
        holder.body.setText(posts.getBody());

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title,body;
        String userId,id;
        Context ctx;

        RecyclerViewHolder(View view,Context ctx)
        {
            super(view);
            this.ctx=ctx;
            view.setOnClickListener(this);
            title=(TextView)view.findViewById(R.id.title);
            body=(TextView)view.findViewById(R.id.body);

        }

        @Override
        public void onClick(View v) {


            //Log.i("","Id of the clicked item:"+id);
            Intent intent = new Intent(this.ctx,Post_Details.class);
            intent.putExtra("id",id);
            this.ctx.startActivity(intent);


        }
    }
}
