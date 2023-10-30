package com.red.newsapp.categories;

public class CategoryRVModal {
    private String category;
    private boolean selected;

    public CategoryRVModal(String category, boolean selected) {
        this.category = category;
        this.selected = selected;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
