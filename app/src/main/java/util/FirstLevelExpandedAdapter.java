package util;


import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.database.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class FirstLevelExpandedAdapter extends BaseExpandableListAdapter {

    private List<? extends Map<String, ?>> mGroupData;
    private int mExpandedGroupLayout;
    private int mCollapsedGroupLayout;
    private String[] mGroupFrom;
    private int[] mGroupTo;

    private List<? extends List<? extends Map<String, ?>>> mChildData;
    private int mChildLayout;
    private int mLastChildLayout;
    private String[] mChildFrom;
    private int[] mChildTo;

    private LayoutInflater mInflater;
    private Context context;




    public FirstLevelExpandedAdapter(Context context,
                                     List<? extends Map<String, ?>> groupData,
                                     int groupLayout,
                                     String[] groupFrom,
                                     int[] groupTo,
                                     List<? extends List<? extends Map<String, ?>>> childData,
                                     int childLayout,
                                     String[] childFrom,
                                     int[] childTo) {
        this.context=context;
        mGroupData = groupData;
        mExpandedGroupLayout = groupLayout;
        mCollapsedGroupLayout = groupLayout;
        mGroupFrom = groupFrom;
        mGroupTo = groupTo;

        mChildData = childData;
        mChildLayout = childLayout;
        mLastChildLayout = childLayout;
        mChildFrom = childFrom;
        mChildTo = childTo;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return mGroupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newGroupView(isExpanded, parent);
        } else {
            v = convertView;
        }
        bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
        return v;
    }

    public View newGroupView(boolean isExpanded, ViewGroup parent) {
        return mInflater.inflate((isExpanded) ? mExpandedGroupLayout : mCollapsedGroupLayout,
                parent, false);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExpandableListView view;
        view= new ExpandableListView(context);
       /* if(convertView==null){
            view= new ExpandableListView(context);
        } else {
            view=(ExpandableListView) convertView;
        }*/
        bindViewList(view);

        return view;
    }



    private void bindViewList(ExpandableListView view){

        view.setAdapter(new SimpleExpandableListAdapter(context
                ,mGroupData
                ,R.layout.row_second_month
                ,mGroupFrom
                ,mGroupTo
                ,mChildData
                ,R.layout.row_third_day
                ,mChildFrom
                ,mChildTo));
        view.setGroupIndicator(null);

    }

    private void bindView(View view, Map<String, ?> data, String[] from, int[] to) {
        int len= to.length;
        for(int i=0;i<len;i++){
            TextView textView=view.findViewById(to[i]);
            if(textView!=null){
               textView.setText(String.valueOf(data.get(from[i])));
            }
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
