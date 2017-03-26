package androidproject.com.test.androidservertestproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 3/24/2017.
 */
public class container_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.container_fragment,container,false);

        BackgroundTask1 backgroundTask1=new BackgroundTask1(getActivity().getApplicationContext());
        backgroundTask1.execute();
                return view;
    }

}
