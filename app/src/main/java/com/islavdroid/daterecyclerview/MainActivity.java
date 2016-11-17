package com.islavdroid.daterecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MyMediatorInterface {

    private MyAdapter mAdapter;
    private ArrayList<ItemInterface> mUsersAndSectionList;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<UserModel> usersList = new ArrayList<>();


        try {
            usersList.add(new UserModel("Jos", "123546567", sdf.parse("2016-1-1")));
            usersList.add(new UserModel("Kiran", "456546456", sdf.parse("2016-1-1")));
            usersList.add(new UserModel("Manu", "5678", sdf.parse("2016-3-31")));
            usersList.add(new UserModel("Roy", "67443453", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Musthu", "456353", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Jaffer", "4644", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Jaffer", "4644", sdf.parse("2017-1-31")));
            usersList.add(new UserModel("Stas", "4644", sdf.parse("2017-1-31")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mUsersAndSectionList = new ArrayList<>();
        getSectionalList(usersList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_my_recycler_view);
        recyclerView.setHasFixedSize(true);

      //  final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        layoutManager=new LinearLayoutManager(this);

      /*  layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (MyAdapter.SECTION_VIEW == mAdapter.getItemViewType(position)) {
                    return 2;
                }
                return 1;
            }
        });*/
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(mUsersAndSectionList, this);
        recyclerView.setAdapter(mAdapter);

    }

    private void getSectionalList(ArrayList<UserModel> usersList) {

        Collections.sort(usersList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel user1, UserModel user2) {
                return user1.jDate.compareTo(user2.jDate) > 0 ? 1 : 0;
            }
        });

        String lastHeader = "";

        int size = usersList.size();

        for (int i = 0; i < size; i++) {

            UserModel user = usersList.get(i);
            String header = getSimpleDate(user.jDate);

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;

                mUsersAndSectionList.add(new GroupTitleModel(header));
            }

            mUsersAndSectionList.add(user);
        }
    }

    public static String getSimpleDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String stringdate = format.format(date);
        return stringdate;
    }

    @Override
    public void userItemClick(int pos) {
        Toast.makeText(MainActivity.this, "Clicked User : " + ((UserModel) mUsersAndSectionList.get(pos)).name, Toast.LENGTH_SHORT).show();
    }
}