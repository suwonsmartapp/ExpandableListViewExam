# ExpandableListView 및 BaseExpandableListAdapter 사용 예제

# 1. 레이아웃 준비
이 예제는 레이아웃 3개를 필요로 합니다.

## activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.massivcode.expandablelistviewexam.MainActivity">

   <ExpandableListView
       android:id="@+id/expandableListView"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>

</LinearLayout>

```

## item_group.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/group_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp" />

</LinearLayout>
```

## item_child.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:padding="16dp"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/child_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```

# 2. Adapter
이 예제에서는 BaseExpandableListAdapter 를 이용합니다.

```java
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

```

# 3. Activity
액티비티에서는 데이터를 초기화하고 그것을 이용하여 어댑터를 생성한 뒤, 리스트 뷰에 세팅하는 메소드가 존재합니다.

```java
package com.massivcode.expandablelistviewexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView mExpandableListView;
    private CustomExpandableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 초기화 메소드
     */
    private void init() {
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        initData();
        mExpandableListView.setAdapter(mAdapter);
    }


    /**
     * 데이터 초기화 및 어댑터 생성 메소드
     */
    private void initData() {
        List<String> groupData = initGroupData();
        List<List<String>> childData = initChildData();

        mAdapter = new CustomExpandableAdapter(groupData, childData, getApplicationContext());

    }

    /**
     * 그룹 데이터 생성 메소드
     * @return 그룹 데이터 리스트
     */
    private ArrayList<String> initGroupData() {
        ArrayList<String> result = new ArrayList<>();

        result.add("테란");
        result.add("프로토스");
        result.add("저그");

        return result;
    }

    /**
     * 차일드 데이터 생성 메소드
     * @return 차일드 데이터 리스트
     */
    private ArrayList<List<String>> initChildData() {
        ArrayList<List<String>> result = new ArrayList<>();

        ArrayList<String> terranUnits = new ArrayList<>();
        terranUnits.add("SCV");
        terranUnits.add("마린");
        terranUnits.add("메딕");
        terranUnits.add("파이어벳");
        terranUnits.add("고스트");

        ArrayList<String> protossUnits = new ArrayList<>();
        protossUnits.add("프로브");
        protossUnits.add("질럿");
        protossUnits.add("드라군");
        protossUnits.add("다크템플러");
        protossUnits.add("하이템플러");
        protossUnits.add("아칸");

        ArrayList<String> zergUnits = new ArrayList<>();
        zergUnits.add("드론");
        zergUnits.add("저글링");
        zergUnits.add("히드라");
        zergUnits.add("럴커");

        result.add(terranUnits);
        result.add(protossUnits);
        result.add(zergUnits);

        return result;
    }
}

```
