package com.islavdroid.daterecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SECTION_VIEW = 0;
    public static final int CONTENT_VIEW = 1;

    ArrayList<ItemInterface> mUsersAndSectionList;

    WeakReference<Context> mContextWeakReference;

    public MyAdapter(ArrayList<ItemInterface> usersAndSectionList, Context context) {

        this.mUsersAndSectionList = usersAndSectionList;

        this.mContextWeakReference = new WeakReference<Context>(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = mContextWeakReference.get();
        if (viewType == SECTION_VIEW) {
            return new SectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_group_title, parent, false));
        }
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_item, parent, false), context);
    }

    @Override
    public int getItemViewType(int position) {
        if (mUsersAndSectionList.get(position).isSection()) {
            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Context context = mContextWeakReference.get();

        if (context == null) {
            return;
        }

        if (SECTION_VIEW == getItemViewType(position)) {

            SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
            GroupTitleModel sectionItem = ((GroupTitleModel) mUsersAndSectionList.get(position));

            sectionViewHolder.title.setText(sectionItem.title);
            return;
        }

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        UserModel currentUser = ((UserModel) mUsersAndSectionList.get(position));

        myViewHolder.TvName.setText(currentUser.name);
        myViewHolder.TvPhone.setText(currentUser.phone);

    }


    @Override
    public int getItemCount() {
        return mUsersAndSectionList.size();
    }

    //holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView TvName, TvPhone;
        public LinearLayout ll;

        public MyViewHolder(View itemView, final Context context) {

            super(itemView);
            TvName = (TextView) itemView.findViewById(R.id.tv_name);
            TvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_layout);

            ll.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((MainActivity) context).userItemClick(getAdapterPosition());
                }
            });
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public SectionViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_group_title);
        }
    }
}


