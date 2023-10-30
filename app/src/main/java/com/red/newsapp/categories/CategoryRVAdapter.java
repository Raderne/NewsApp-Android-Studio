package com.red.newsapp.categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.red.newsapp.R;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModal> categoryRVModals;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModals = categoryRVModals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_rv_item, parent, false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryRVModal categoryRVModal = categoryRVModals.get(position);
        holder.categoryBtn.setText(categoryRVModal.getCategory());

        if (categoryRVModal.isSelected()) {
            holder.categoryBtn.setBackgroundResource(R.drawable.category_button_selected);
        } else {
            holder.categoryBtn.setBackgroundResource(R.drawable.category_button);
        }

        holder.categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loop through all items in the list and set the isSelected property of each item to false
                for (CategoryRVModal categoryRVModal1 : categoryRVModals) {
                    categoryRVModal1.setSelected(false);
                }
                categoryRVModal.setSelected(true);

                categoryClickInterface.onCategoryClick(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModals.size();
    }

    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button categoryBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBtn = itemView.findViewById(R.id.btnCategory);
        }
    }
}
