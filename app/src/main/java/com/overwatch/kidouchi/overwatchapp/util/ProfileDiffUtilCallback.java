package com.overwatch.kidouchi.overwatchapp.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;

import java.util.List;

public class ProfileDiffUtilCallback extends DiffUtil.Callback {

    private List<ProfileEntity> oldList;
    private List<ProfileEntity> newList;

    public ProfileDiffUtilCallback(final List<ProfileEntity> oldList, final List<ProfileEntity> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldList.get(i).getId() == newList.get(i1).getId();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
