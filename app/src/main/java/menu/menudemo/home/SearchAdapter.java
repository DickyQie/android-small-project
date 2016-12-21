package menu.menudemo.home;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import menu.menudemo.R;
import menu.menudemo.data.HomeData;
import menu.menudemo.data.UserBean;
import menu.menudemo.ui.activity.homepage.SearchActivity;


/**
 * Created by Administrator on 2016/10/20.
 * <p>
 * Alt+Insert --->Imple...   --->快速导入方法
 */

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    public SearchAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return SearchActivity.list == null ? 0 : (SearchActivity.list.size() - 1);
    }

    @Override
    public Object getItem(int position) {
        return SearchActivity.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_search_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UserBean userBean = (UserBean) getItem(position);
        holder.iv_img.setImageResource(userBean.getImg());
        holder.tv_name.setText(HomeData.TITLE[position]);
        if (userBean.isvisibility()) {
            holder.tv_add.setImageResource(R.mipmap.search_add_no);
        } else {
            holder.tv_add.setImageResource(R.mipmap.search_add_yes);
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userBean.isvisibility())//添加
                {
                    Message msg2 = new Message();
                    msg2.what = 1;
                    msg2.obj=position;
                    new SearchActivity().handler.sendMessage(msg2);
                }

            }
        });
        return convertView;
    }

    class ViewHolder {

        private ImageView iv_img;
        private TextView tv_name;
        private ImageView tv_add;

        public ViewHolder(View view) {
            iv_img = (ImageView) view.findViewById(R.id.item_search_img);
            tv_name = (TextView) view.findViewById(R.id.item_search_text);
            tv_add = (ImageView) view.findViewById(R.id.item_search_add);
        }
    }
}
