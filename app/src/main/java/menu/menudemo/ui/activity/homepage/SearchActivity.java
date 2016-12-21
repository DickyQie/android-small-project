package menu.menudemo.ui.activity.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import menu.menudemo.R;
import menu.menudemo.data.HomeData;
import menu.menudemo.data.UserBean;
import menu.menudemo.file.ACache;
import menu.menudemo.file.UtilFileDB;
import menu.menudemo.home.SearchAdapter;
import menu.menudemo.ui.view.LinearLayoutForListView;


/**
 * Created by Administrator on 2016/10/20.
 * <p>
 * 选择栏目列表
 */

public class SearchActivity extends Activity implements  View.OnClickListener {

    private LinearLayoutForListView mListview;

    public static List<UserBean> list;
    public static List<Integer> listPosition;
    private Context context;
    private static SearchAdapter adapter;

    private static String key = "1";
    private static ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listPosition = (List<Integer>) getIntent().getSerializableExtra("list");
        aCache = ACache.get(this);
        context = this;
        initView();
    }

    public void initView() {
        mListview = (LinearLayoutForListView) findViewById(R.id.search_listview);
        findViewById(R.id.search_close).setOnClickListener(this);
        onLoad();
    }

    public void onLoad() {
        adapter = new SearchAdapter(context);
        mListview.setAdapter(adapter);
        showData();
    }

    private void showData() {
        list = new ArrayList<UserBean>();
        for (int i = 0; i < 33; i++) {
            UserBean user = new UserBean(HomeData.IMGSEARCH[i], HomeData.TITLE[i], false, false);
            list.add(user);
        }
        for (int j = 0; j < (listPosition.size() - 1); j++) {
            list.get(listPosition.get(j)).setIsvisibility(true);
        }
        notifyData();
    }


    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                key = "2";
                int postion = Integer.valueOf(msg.obj.toString());
                String dataHome= UtilFileDB.SELETEFile(aCache, "home");
                String home="";
                if(dataHome==null){
                    home=postion+"";
                }else
                {
                    home= dataHome+ "," + postion;
                }
                UtilFileDB.DELETEFile(aCache, "home");
                UtilFileDB.ADDFile(aCache, "home", home);
                list.get(postion).setIsvisibility(true);
                notifyData();
            }
        }
    };


    /*****
     * 刷新数据
     */
    private void notifyData() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_close:
                Intent intent = new Intent();
                intent.putExtra("key", key);
                setResult(1, intent);
               finish();
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                Intent intent = new Intent();
                intent.putExtra("key", key);
                setResult(1, intent);
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void onError(Object error) {
        Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_LONG).show();

    }


}
