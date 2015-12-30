package com.massivcode.expandablelistviewexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright 2015 Pureum Choe
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * <p/>
 * Created by Ray Choe on 2015-12-30.
 */
public class CustomExpandableAdapter extends BaseExpandableListAdapter {


    /**
     * BaseAdapter 과는 달리 ExpandableAdapter 는 데이터를 2개 보유합니다.
     * 부모 데이터와 자식 데이터를 보유하는데, 자식 데이터의 경우에는 리스트 안에 리스트가 들어있는 구조입니다.
     */
    private List<String> mGroupList;
    private List<List<String>> mChildList;
    private LayoutInflater mInflater;

    public CustomExpandableAdapter(List<String> mGroupList, List<List<String>> mChildList, Context context) {
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 주로 그룹리스트의 사이즈를 리턴합니다.
     * @return
     */
    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    /**
     * 해당 위치의 그룹아이템을 리턴합니다.
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    /**
     * 해당 위치의 그룹아이템의 id를 리턴하는데, 주로 위치값을 리턴합니다.
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_group, parent, false);
            viewHolder.groupTextView = (TextView) convertView.findViewById(R.id.group_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String groupString = (String) getGroup(groupPosition);
        viewHolder.groupTextView.setText(groupString);

        return convertView;
    }


    /**
     * getGroupCount와는 살짝 다른 모습을 보입니다.
     * 차일드 데이터의 경우 리스트 안에 리스트가 들어있는 구조이기 때문에
     * 그룹포지션의 위치하는 데이터의 개수를 리턴합니다.
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    /**
     * 그룹 데이터의 경우에는 하나의 위치에 데이터가 하나씩 매칭되지만,
     * 차일드 데이터의 경우에는 하나의 위치에 데이터가 여러개 들어있는 리스트가 매칭됩니다.
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    /**
     * getId 메소드들은 주로 포지션을 그대로 리턴하도록 구현합니다.
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_child, parent, false);
            viewHolder.childTextView = (TextView) convertView.findViewById(R.id.child_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String childString = (String) getChild(groupPosition, childPosition);
        viewHolder.childTextView.setText(childString);

        return convertView;
    }


    /**
     * 특정 위치의 차일드 아이템이 클릭 가능한지를 결정하는 메소드인데
     * 대부분 true 를 리턴하도록 구현합니다.
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 이것도 true 를 리턴하도록 구현합니다.
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class ViewHolder {
        TextView groupTextView;
        TextView childTextView;
    }

}
