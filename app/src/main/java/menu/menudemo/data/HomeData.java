package menu.menudemo.data;


import java.util.ArrayList;
import java.util.List;

import menu.menudemo.R;
import menu.menudemo.file.ACache;
import menu.menudemo.file.UtilFileDB;


/**
 * Created by Administrator on 2016/10/23.
 */

public class HomeData {

    public static String[] TITLE = {"党建工作", "组织工作", "主要经济工作", "安全生产", "信访工作", "“三农”工作",
            "政法工作", "四创工作", "人口计生卫生工作", "城镇化建设", "财政税收", "人大工作",
            "政法工作", "组织工作", "城镇建设", "安全生产", "财政税收", "人大工作",
            "政协工作", "武装工作", "宣传思想工作", "“三变”工作", "统战工作", "食品安全工作",
            "就业社保工作", "“两遇”管控工作", "交通建设工作", "教育工作", "小康社会建设", "环保工作",
            "便民服务", "文化工作", "民政工作","添加内容"};

    public static int[] IMG = {R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4, R.mipmap.g5, R.mipmap.g6, R.mipmap.g7, R.mipmap.g8, R.mipmap.g9,
            R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4, R.mipmap.g5, R.mipmap.g6, R.mipmap.g7, R.mipmap.g8, R.mipmap.g9,
            R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4, R.mipmap.g5, R.mipmap.g6, R.mipmap.g7, R.mipmap.g8, R.mipmap.g9,
            R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4, R.mipmap.g5, R.mipmap.g6,R.mipmap.home_add
    };

    public static int[] IMGSEARCH = {R.mipmap.list1, R.mipmap.list2, R.mipmap.list3, R.mipmap.list4, R.mipmap.list5, R.mipmap.list6, R.mipmap.list7, R.mipmap.list8, R.mipmap.list9,
            R.mipmap.list10, R.mipmap.list11, R.mipmap.list12, R.mipmap.list13, R.mipmap.list14, R.mipmap.list15, R.mipmap.list16, R.mipmap.list17, R.mipmap.list18,
            R.mipmap.list19, R.mipmap.list20, R.mipmap.list21, R.mipmap.list22, R.mipmap.list23, R.mipmap.list24, R.mipmap.list25, R.mipmap.list26, R.mipmap.list27,
            R.mipmap.list28, R.mipmap.list29, R.mipmap.list30, R.mipmap.list31, R.mipmap.list32, R.mipmap.list33
    };

    /****
     *
     * 获取缓存数据
     * @param aCache
     * @return
     */
    public static final List<Integer> POSITION(ACache aCache)
    {
        String string = UtilFileDB.SELETEFile(aCache,"home");
        List<Integer> list=new ArrayList<Integer>();
        if (string==null)//默认
        {
            for (int i=0;i<14;i++) {
                list.add(i);
            }
            list.add(33);
            UtilFileDB.ADDFile(aCache,"home","0,1,2,3,4,5,6,7,8,9,10,11,12,13");
            return list;
        }
        else
        {
            String[] stringArr= string.split(",");
            for (int i=0;i<stringArr.length;i++)
            {
                list.add(Integer.valueOf(stringArr[i]));
            }
            list.add(33);
            return list;
        }

    }


}
