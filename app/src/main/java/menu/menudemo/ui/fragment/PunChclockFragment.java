package menu.menudemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import menu.menudemo.R;


/**
 * Created by Administrator on 2016/10/19.
 * <p>
 * 打卡
 */

public class PunChclockFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_punch, container, false);
    }


}
