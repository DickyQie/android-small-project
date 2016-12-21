package menu.menudemo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import menu.menudemo.R;
import menu.menudemo.data.HomeData;
import menu.menudemo.data.UserBean;
import menu.menudemo.file.ACache;
import menu.menudemo.file.UtilFileDB;
import menu.menudemo.home.HomeAdapter;
import menu.menudemo.ui.activity.LoginFragmentActivity;
import menu.menudemo.ui.activity.homepage.SearchActivity;
import menu.menudemo.ui.view.ZQScrollGridView;


/**
 * 首页
 */
public class HomeFragment extends Fragment implements  View.OnClickListener {

    private View view;
    private TextView tv_sign;
    private Intent intent;
    private ZQScrollGridView gridView;

    private static HomeAdapter adapter;
    public static List<Integer> listPosition;
    public static List<UserBean> list;
    private static ACache aCache;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        view = getView();
        aCache = ACache.get(getActivity());
        context = getActivity();
        initView();
    }

    public void initView() {
        tv_sign = (TextView) view.findViewById(R.id.home_sign);
        tv_sign.setOnClickListener(this);
        gridView = (ZQScrollGridView) view.findViewById(R.id.home_gridview);
        onLoad();
    }


    public void onLoad() {
        adapter = new HomeAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onItemClickListener);
        gridView.setOnItemLongClickListener(onItemLongClickListener);
        showData();
    }

    /*****
     * 刷新数据
     */
    private void notifyData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                showLongClick(false);
            } else if (msg.what == 2)//删除
            {
                showDelete();
            }
        }
    };


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == (listPosition.size() - 1)) {
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("list", (Serializable) listPosition);
                startActivityForResult(intent, 1);
            }
            else {

            }

        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == (listPosition.size() - 1)) {
                return false;
            }
            showLongClick(true);
            Message msg = new Message();
            msg.what = 1;// 删除
            msg.obj = 1;
            LoginFragmentActivity.handler.sendMessage(msg);
            return false;
        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                if (data.getStringExtra("key").equals("2")) {
                    listPosition.clear();
                    listPosition = HomeData.POSITION(aCache);
                    notifyData();
                }
            }
        }
    }

    private void showData() {
        listPosition = HomeData.POSITION(aCache);
        list = new ArrayList<UserBean>();
        for (int i = 0; i < 34; i++) {
            UserBean user = new UserBean(HomeData.IMG[i], HomeData.TITLE[i], false, false);
            list.add(user);
        }
        notifyData();
    }

    /***
     * 删除
     */
    private void showDelete() {
        //删除缓存
        String home = "";
        for (int i = 0; i < listPosition.size() - 1; i++) {
            UserBean userBean = list.get(listPosition.get(i));
            if (!userBean.isCheck()) {
                home += listPosition.get(i) + ",";
            }
        }
        aCache.remove("home");
        listPosition.clear();
        try {
            UtilFileDB.ADDFile(aCache, "home", home.substring(0, (home.length() - 1)));
            if (listPosition == null || listPosition.size() <= 1) {
                listPosition = HomeData.POSITION(aCache);
            }
        } catch (Exception e) {
            listPosition.add((list.size()-1));//只留加号
        }
        showLongClick(false);
    }

    /****
     * 重新刷新数据
     *
     * @param isvisibility
     */
    private void showLongClick(boolean isvisibility) {
        list.clear();
        for (int i = 0; i < 34; i++) {
            UserBean user = new UserBean(HomeData.IMG[i], HomeData.TITLE[i], false, isvisibility);
            list.add(user);
        }
        HomeFragment.list.get(33).setIsvisibility(false);
        notifyData();
    }
}
