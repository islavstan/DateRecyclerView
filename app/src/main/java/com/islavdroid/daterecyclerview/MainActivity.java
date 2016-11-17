package com.islavdroid.daterecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    protected LinearLayoutManager layoutManager;
    private EditText editText;
    private Button button;
    SimpleDateFormat sdf;
    RecyclerView recyclerView;
    ArrayList<UserModel> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_my_recycler_view);
         sdf = new SimpleDateFormat("yyyy-MM-dd");

        //arraylist со списком собщений

        editText=(EditText)findViewById(R.id.edittext);
        button=(Button)findViewById(R.id.button_send);

        try {
            usersList.add(new UserModel("Jos", "123546567", sdf.parse("2016-1-1")));
            usersList.add(new UserModel("Kiran", "456546456", sdf.parse("2016-1-1")));
            usersList.add(new UserModel("Manu", "5678", sdf.parse("2016-3-31")));
            usersList.add(new UserModel("Roy", "67443453", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Musthu", "456353", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Jaffer", "4644", sdf.parse("2016-1-31")));
            usersList.add(new UserModel("Jaffer", "4644", sdf.parse("2017-1-31")));
            usersList.add(new UserModel("Stas", "4644", sdf.parse("2017-1-31")));
            usersList.add(new UserModel("Stas", "fff", sdf.parse("2017-1-31")));
            usersList.add(new UserModel("Stas", "46fffff44", sdf.parse("2017-1-31")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mUsersAndSectionList = new ArrayList<>();
        getSectionalList(usersList);


        recyclerView.setHasFixedSize(true);

      //  final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    UserModel userModel =new UserModel("Stas", editText.getText().toString(), sdf.parse("2018-1-31"));
                  // usersList.add(userModel);
                    setOneItem(usersList,userModel);
                    mAdapter.notifyDataSetChanged();

                    editText.setText("");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

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

    private void setOneItem(ArrayList<UserModel> usersList,UserModel us2) {

       UserModel us1 = usersList.get(usersList.size()-1);

       String header = getSimpleDate(us1.jDate);
        String newHeader = getSimpleDate(us2.jDate);
        usersList.add(us2);
        if (!header.equals(newHeader)) {
            mUsersAndSectionList.add(new GroupTitleModel(newHeader));
        }
        mUsersAndSectionList.add(us2);}

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