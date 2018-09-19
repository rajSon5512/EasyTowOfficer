package com.knoxpo.rajivsonawala.easytow_officer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItem;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItemLab;

import com.knoxpo.rajivsonawala.easytow_officer.models.SideItemLab;

public class SidebarFragment extends Fragment {

    public interface CallBack{

        void onMenuClicked(long menuId);

    }

    private CallBack mCallBack;
    private RecyclerView mRecyclerView;
    private SideItemLab mSideItemLab=null;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mCallBack=(CallBack)getActivity();
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mCallBack=null;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSideItemLab=SideItemLab.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_recycler_view,container,false);

        init(v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new SideItemAdapter());

        return v;
    }

    private void init(View v) {

        mRecyclerView=v.findViewById(R.id.nav_recycler_view);
    }

    public class SideItemAdapter extends RecyclerView.Adapter<SideVH>{

        private  LayoutInflater mInflater;

        public SideItemAdapter(){

            mInflater=LayoutInflater.from(getActivity());

        }

        @NonNull
        @Override
        public SideVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=mInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new SideVH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SideVH holder, int position) {

            holder.bind(mSideItemLab.getSideItems().get(position));

        }

        @Override
        public int getItemCount() {
            return mSideItemLab.getSize();
        }
    }

    public class SideVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        private SideItem mItem;


        public SideVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(SideItem sideItem){

            mItem=sideItem;
            ((TextView)itemView).setText(mItem.getTitle());

        }

        @Override
        public void onClick(View v) {

            if(mItem!=null){

                mCallBack.onMenuClicked(mItem.getId());

            }

        }
    }

}



