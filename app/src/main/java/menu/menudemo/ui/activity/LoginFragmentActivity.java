package menu.menudemo.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import menu.menudemo.R;
import menu.menudemo.file.DensityUtil;
import menu.menudemo.file.DrawableUtil;
import menu.menudemo.ui.fragment.HomeFragment;
import menu.menudemo.ui.fragment.MyFragment;
import menu.menudemo.ui.fragment.NewsFragment;
import menu.menudemo.ui.fragment.PunChclockFragment;


/**
 * Created by Administrator on 2016/10/19.
 * <p>
 * 主页Fragment模块
 */

public class LoginFragmentActivity extends FragmentActivity implements View.OnClickListener {

    private static String[] stringArr;

    private static final int[] RADIO_IDS = new int[]{R.id.tab_life, R.id.tab_profit, R.id.tab_invitation, R.id.tab_personal};
    // 默认图片
    private static final int[] TAB_ICON_NORMAL_IDS = new int[]{R.mipmap.home_bottom_no1, R.mipmap.home_bottom_no2,
            R.mipmap.home_bottom_no3, R.mipmap.home_bottom_no4};
    // 点击图片
    private static final int[] TAB_ICON_ACTIVE_IDS = new int[]{
            R.mipmap.home_bottom1, R.mipmap.home_bottom2,
            R.mipmap.home_bottom3, R.mipmap.home_bottom4};
    private FragmentManager mFragmentManager;
    private HomeFragment f1;
    private NewsFragment f2;
    private PunChclockFragment f3;
    private MyFragment f4;
    private RadioGroup mRadioGroup;
    private int mBottomDrawableSize;
    private RadioButton btn1, btn2, btn3, btn4;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    static LinearLayout linearLayout;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        context = this;
        WindowManager wm = this.getWindowManager();
        WIDTH = wm.getDefaultDisplay().getWidth();
        HEIGHT = wm.getDefaultDisplay().getHeight();
        float scale = this.getResources().getDisplayMetrics().density;
        initView();

    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.bottom_tab_group);
        mFragmentManager = getSupportFragmentManager();
        mBottomDrawableSize = DensityUtil.dip2px(this, 30);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        btn1 = (RadioButton) findViewById(R.id.tab_life);
        btn2 = (RadioButton) findViewById(R.id.tab_profit);
        btn3 = (RadioButton) findViewById(R.id.tab_invitation);
        btn4 = (RadioButton) findViewById(R.id.tab_personal);
        linearLayout = (LinearLayout) findViewById(R.id.login_delete);
        findViewById(R.id.home_close).setOnClickListener(this);
        findViewById(R.id.home_delete).setOnClickListener(this);
        showFragment(0);
        setBottomTextColor(0);
    }

    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.tab_life:
                    showFragment(0);
                    setBottomTextColor(0);
                    break;
                case R.id.tab_profit:
                    showFragment(1);
                    setBottomTextColor(1);
                    break;
                case R.id.tab_invitation:
                    showFragment(2);
                    setBottomTextColor(2);
                    break;
                case R.id.tab_personal:
                    showFragment(3);
                    setBottomTextColor(3);
                    break;

                default:
                    break;
            }
        }
    };


    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            linearLayout.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_close:
                Message msg1 = new Message();
                msg1.what = 1;// qux
                new HomeFragment().handler.sendMessage(msg1);
                linearLayout.setVisibility(View.GONE);
                break;
            case R.id.home_delete:
                Message msg2 = new Message();
                msg2.what = 2;// 删除
                new HomeFragment().handler.sendMessage(msg2);
                linearLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void showFragment(int page) {
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        if (f1 != null)
            mFragmentTransaction.hide(f1);
        if (f2 != null)
            mFragmentTransaction.hide(f2);
        if (f3 != null)
            mFragmentTransaction.hide(f3);
        if (f4 != null)
            mFragmentTransaction.hide(f4);
        switch (page) {
            case 0:// 首页
                if (f1 == null) {
                    f1 = new HomeFragment();

                    mFragmentTransaction.add(R.id.main_content, f1);
                } else {
                    mFragmentTransaction.show(f1);
                }
                break;
            case 1://

                if (f2 != null) {
                    mFragmentTransaction.remove(f2);
                }
                f2 = new NewsFragment();
                mFragmentTransaction.add(R.id.main_content, f2);
                break;
            case 2://
                if (f3 == null) {
                    f3 = new PunChclockFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("aMap", stringArr);
                    f3.setArguments(bundle);
                    mFragmentTransaction.add(R.id.main_content, f3);
                } else {
                    mFragmentTransaction.show(f3);
                }
                break;
            case 3:// 我的
                if (f4 == null) {
                    f4 = new MyFragment();
                    mFragmentTransaction.add(R.id.main_content, f4);
                } else {
                    mFragmentTransaction.show(f4);
                }
                break;
            default:
                break;
        }
        mFragmentTransaction.commitAllowingStateLoss();

    }

    private void setBottomTextColor(int position) {
        btn1.setTextColor(getResources().getColor(R.color.colorGray));
        btn2.setTextColor(getResources().getColor(R.color.colorGray));
        btn3.setTextColor(getResources().getColor(R.color.colorGray));
        btn4.setTextColor(getResources().getColor(R.color.colorGray));
        Drawable d1 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                TAB_ICON_NORMAL_IDS[0]);

        d1.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
        btn1.setCompoundDrawables(null, d1, null, null);
        Drawable d2 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                TAB_ICON_NORMAL_IDS[1]);
        d2.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
        btn2.setCompoundDrawables(null, d2, null, null);
        Drawable d3 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                TAB_ICON_NORMAL_IDS[2]);
        d3.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
        btn3.setCompoundDrawables(null, d3, null, null);
        Drawable d4 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                TAB_ICON_NORMAL_IDS[3]);
        d4.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
        btn4.setCompoundDrawables(null, d4, null, null);
        switch (position) {
            case 0:
                Drawable dz = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_ACTIVE_IDS[0]);
                dz.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                btn1.setTextColor(getResources()
                        .getColor(R.color.colorRed));
                btn1.setCompoundDrawables(null, dz, null, null);
                break;
            case 1:
                Drawable dz1 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_ACTIVE_IDS[1]);
                dz1.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                btn2.setTextColor(getResources()
                        .getColor(R.color.colorRed));
                btn2.setCompoundDrawables(null, dz1, null, null);
                break;
            case 2:
                Drawable dz2 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_ACTIVE_IDS[2]);
                dz2.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                btn3.setTextColor(getResources()
                        .getColor(R.color.colorRed));
                btn3.setCompoundDrawables(null, dz2, null, null);
                break;
            case 3:
                Drawable dz3 = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_ACTIVE_IDS[3]);
                dz3.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                btn4.setTextColor(getResources().getColor(R.color.colorRed));
                btn4.setCompoundDrawables(null, dz3, null, null);
                break;

            default:
                break;
        }

    }

    private void setTabChecked(int position) {
        int count = RADIO_IDS.length;
        for (int index = 0; index < count; index++) {
            RadioButton radioButton = (RadioButton) findViewById(RADIO_IDS[index]);
            if (index == position) {
                radioButton.setTextColor(getResources().getColor(
                        R.color.colorRed));
                Drawable d = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_ACTIVE_IDS[index]);
                d.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                radioButton.setCompoundDrawables(null, d, null, null);
            } else {
                radioButton.setTextColor(LoginFragmentActivity.this.getResources()
                        .getColor(R.color.colorGray));
                Drawable d = DrawableUtil.getDrawable(LoginFragmentActivity.this,
                        TAB_ICON_NORMAL_IDS[index]);
                d.setBounds(0, 0, mBottomDrawableSize, mBottomDrawableSize);
                radioButton.setCompoundDrawables(null, d, null, null);
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
