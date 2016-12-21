package menu.menudemo.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import menu.menudemo.R;
import menu.menudemo.data.UserBean;
import menu.menudemo.ui.fragment.HomeFragment;


/**
 * Created by Administrator on 2016/10/20.
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public HomeAdapter(Context context)
    {
        this.context=context;
        this.inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return HomeFragment.listPosition==null ? 0 : HomeFragment.listPosition.size();
    }

    @Override
    public Object getItem(int position) {
        return HomeFragment.list.get(HomeFragment.listPosition.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder  holder;
        if(null==convertView)
        {
            convertView=inflater.inflate(R.layout.item_home_gridview,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        final UserBean userBean=(UserBean) getItem(position);
        holder.iv_img.setImageResource(userBean.getImg());
        holder.tv_name.setText(userBean.getTitle());
        holder.checkBox.setChecked(userBean.isCheck());
        if(userBean.isvisibility())
        {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userBean.setCheck(holder.checkBox.isChecked());
            }
        });
        return convertView;
    }

    private void onClick(final View view, final UserBean userBean)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userBean.setCheck(view.isClickable());
            }
        });
    }

    public class ViewHolder{

        private ImageView iv_img;
        private TextView tv_name;
        private CheckBox checkBox;
        public ViewHolder(View view)
        {
            iv_img=(ImageView)view.findViewById(R.id.item_home_img);
            tv_name=(TextView)view.findViewById(R.id.item_home_txt);
            checkBox=(CheckBox)view.findViewById(R.id.item_home_chekbox);


        }
    }
}
