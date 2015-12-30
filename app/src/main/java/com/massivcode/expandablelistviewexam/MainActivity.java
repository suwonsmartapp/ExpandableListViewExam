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
