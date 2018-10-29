package com.overwatch.kidouchi.overwatchapp.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.overwatch.kidouchi.overwatchapp.R;
import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder> {

    private List<ProfileEntity> profiles;

    public ProfileListAdapter(final List<ProfileEntity> profiles) {
        this.profiles = profiles;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        View profileView = inflater.inflate(R.layout.profile_item, parent, false);

        return new ProfileViewHolder(profileView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int position) {
        profileViewHolder.bind(profiles.get(position));

//        Animation animation = AnimationUtils.loadAnimation(profileViewHolder.itemView.getContext(),
//                R.anim.item_fall_down);
//        profileViewHolder.itemView.
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public void updateList(final List<ProfileEntity> newProfiles) {
        // DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProfileDiffUtilCallback(profiles, newProfiles));
        // diffResult.dispatchUpdatesTo(this);
        profiles = newProfiles;
        notifyItemInserted(newProfiles.size() - 1);
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        final SimpleDraweeView avatar;
        final TextView username;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.profile_avatar);
            username = itemView.findViewById(R.id.profile_name);
        }

        public void bind(final ProfileEntity profile) {
            avatar.setImageURI(Uri.parse(profile.getPortraitUrl()));
            username.setText(profile.getUsername());
        }
    }
}
