### Gridview实现多个menu模块，可添加可删除
<p>此案例主要讲的是Android控件Gridview（九宫格）完美实现仿支付宝首页，包含添加和删除功能；Fragment底部按钮切换的效果，包含四个模块，登录页面圆形头像等，一个小项目的初始布局。</p> 
<p>效果图：</p> 
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img alt="" src="https://static.oschina.net/uploads/space/2016/1221/105317_msaS_2945455.gif"></p> 
<p>代码：</p> 
<p>自定义GridView</p> 
<pre><code class="language-html">&lt;menu.menudemo.ui.view.ZQScrollGridView
    android:id="@+id/home_gridview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#E4E4E4"
    android:cacheColorHint="#00000000"
    android:fadingEdge="none"
    android:horizontalSpacing="1dp"
    android:listSelector="#00000000"
    android:numColumns="3"
    android:scrollbars="none"
    android:verticalSpacing="1dp"
    &gt;&lt;/menu.menudemo.ui.view.ZQScrollGridView&gt;</code></pre> 
<pre><code class="language-java">/**
 * 不能滚动的GridView，用于嵌套在如ScrollView之类的可滚动的控件中
 */
public class ZQScrollGridView extends GridView {
	  
	public ZQScrollGridView(Context context){
        this(context, null);  
   } 
	
	public ZQScrollGridView(Context context, AttributeSet attrs){
         super(context, attrs);  
    }  
 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE &gt;&gt; 2, MeasureSpec.AT_MOST);
         super.onMeasure(widthMeasureSpec, mExpandSpec);  
    } 
} </code></pre> 
<p>首页代码</p> 
<pre><code class="language-java">/**
 * 首页
 */
public class HomeFragment extends Fragment implements  View.OnClickListener {

    private View view;
    private TextView tv_sign;
    private Intent intent;
    private ZQScrollGridView gridView;

    private static HomeAdapter adapter;
    public static List&lt;Integer&gt; listPosition;
    public static List&lt;UserBean&gt; list;
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
        public void onItemClick(AdapterView&lt;?&gt; parent, View view, int position, long id) {
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
        public boolean onItemLongClick(AdapterView&lt;?&gt; parent, View view, int position, long id) {

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
        list = new ArrayList&lt;UserBean&gt;();
        for (int i = 0; i &lt; 34; i++) {
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
        for (int i = 0; i &lt; listPosition.size() - 1; i++) {
            UserBean userBean = list.get(listPosition.get(i));
            if (!userBean.isCheck()) {
                home += listPosition.get(i) + ",";
            }
        }
        aCache.remove("home");
        listPosition.clear();
        try {
            UtilFileDB.ADDFile(aCache, "home", home.substring(0, (home.length() - 1)));
            if (listPosition == null || listPosition.size() &lt;= 1) {
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
        for (int i = 0; i &lt; 34; i++) {
            UserBean user = new UserBean(HomeData.IMG[i], HomeData.TITLE[i], false, isvisibility);
            list.add(user);
        }
        HomeFragment.list.get(33).setIsvisibility(false);
        notifyData();
    }
}</code></pre> 
<span id="OSC_h3_1"></span>
<h3><span style="color:#B22222"><strong>由于代码太多未完全给出，源码直接下载即可（点击下载）</strong></span></h3> 
