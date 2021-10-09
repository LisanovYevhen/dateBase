package util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class LastAdapter extends BaseExpandableListAdapter {

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

    public LastAdapter(Context context,
                                      List<? extends Map<String, ?>> groupData,
                                      int groupLayout,
                                      String[] groupFrom,
                                      int[] groupTo,
                                      List<? extends List<? extends Map<String, ?>>> childData,
                                      int childLayout,
                                      String[] childFrom,
                                      int[] childTo) {
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
        View view;
        if(convertView==null){
            view = newChildView(isLastChild, parent);
        } else {
            view=convertView;
        }
        bindView(view, mChildData.get(groupPosition).get(childPosition), mChildFrom, mChildTo);

        return view;
    }

    public View newChildView(boolean isLastChild, ViewGroup parent){


        return mInflater.inflate((isLastChild) ? mLastChildLayout : mChildLayout, parent, false);
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
