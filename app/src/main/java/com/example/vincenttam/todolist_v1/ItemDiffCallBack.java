package com.example.vincenttam.todolist_v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Created by VincentTam on 16/12/20.
 */

public class ItemDiffCallBack extends DiffUtil.Callback {
    private List<ToDoItem> mOldList;
    private List<ToDoItem> mNewList;
    private final String TAG = getClass().getSimpleName();
    public ItemDiffCallBack(List<ToDoItem> oldList, List<ToDoItem> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }
    @Override
    public int getOldListSize() {
        return mOldList == null ? 0 : mOldList.size();
    }
    @Override
    public int getNewListSize() {
        return mNewList == null ? 0 : mNewList.size();
    }
    //这个是用来判断是否是一个对象的
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).itemID == mNewList.get(newItemPosition).itemID;
    }
    //这个是用来判断相同对象的内容是否相同
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        String oldContent = mOldList.get(oldItemPosition).Title;
        String newContent = mNewList.get(newItemPosition).Title;
        Log.i(TAG, "oldContent:" + oldContent + " newContent:" + newContent);
        return TextUtils.equals(oldContent ,newContent );
    }
    //找出其中的不同
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        ToDoItem oldItem = mOldList.get(oldItemPosition);
        ToDoItem newItem = mNewList.get(newItemPosition);
        Bundle diffBundle = new Bundle();
        if (!TextUtils.equals(oldItem.Title, newItem.Title)) {
            diffBundle.putString("content", newItem.Title);
        }
        return diffBundle;
    }
}
