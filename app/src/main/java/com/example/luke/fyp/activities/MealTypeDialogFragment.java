package com.example.luke.fyp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luke.fyp.R;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     MealTypeDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link MealTypeDialogFragment.Listener}.</p>
 */
public class MealTypeDialogFragment extends BottomSheetDialogFragment {

    // TODO: change extra string to reflect package when app name changed
    public static final String EXTRA_MEAL_TYPE = "com.example.luke.fyp.MEAL_TYPE";
    public static final String EXTRA_MEAL_YEAR = "com.example.luke.fyp.MEAL_YEAR";
    public static final String EXTRA_MEAL_MONTH = "com.example.luke.fyp.MEAL_MONTH";
    public static final String EXTRA_MEAL_DAY = "com.example.luke.fyp.MEAL_DAY";
    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;

    public static MealTypeDialogFragment newInstance() {
        int itemCount = 4;
        final MealTypeDialogFragment fragment = new MealTypeDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_type_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_meal_type_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(getArguments().getInt(ARG_ITEM_COUNT)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onItemClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;
        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_meal_type_dialog_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClicked(getAdapterPosition());
                        String mealType = text.toString().substring(0, 1);
                        int year = ((DailyViewActivity)getActivity()).getCurrentYear();
                        int month = ((DailyViewActivity)getActivity()).getCurrentMonth();
                        int day = ((DailyViewActivity)getActivity()).getCurrentDay();
                        Intent intent = new Intent(getActivity(), MealBuilderActivity.class);
                        intent.putExtra(EXTRA_MEAL_TYPE, mealType);
                        intent.putExtra(EXTRA_MEAL_YEAR, year);
                        intent.putExtra(EXTRA_MEAL_MONTH, month);
                        intent.putExtra(EXTRA_MEAL_DAY, day);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        ItemAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position == 0){
                holder.text.setText(R.string.meal_breakfast);
            }
            if(position == 1){
                holder.text.setText(R.string.meal_lunch);
            }
            if(position == 2){
                holder.text.setText(R.string.meal_dinner);
            }
            if(position == 3){
                holder.text.setText(R.string.meal_snacks);
            }
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}