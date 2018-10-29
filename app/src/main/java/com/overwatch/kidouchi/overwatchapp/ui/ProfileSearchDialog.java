package com.overwatch.kidouchi.overwatchapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.overwatch.kidouchi.overwatchapp.R;
import com.overwatch.kidouchi.overwatchapp.bus.RxEventBus;
import com.overwatch.kidouchi.overwatchapp.model.Profile;
import com.overwatch.kidouchi.overwatchapp.viewmodel.ProfilesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileSearchDialog extends DialogFragment {

    private ProfilesViewModel profilesViewModel;

    @BindView(R.id.profile_search_field)
    EditText profileSearchField;

    @BindView(R.id.platform_spinner)
    Spinner platformSpinner;

    @BindView(R.id.region_spinner)
    Spinner regionSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.profile_search_dialog, null);

        ButterKnife.bind(this, dialogView);

        RxEventBus.getInstance();

        profilesViewModel = new ProfilesViewModel(getContext());
        setupPlatformSpinner();
        setupRegionSpinner();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setPositiveButton("Search", (dialog, which) -> {
                    final Profile.Platform platform = Profile.Platform.valueOf(platformSpinner.getSelectedItem().toString());
                    final Profile.Region region = Profile.Region.valueOf(regionSpinner.getSelectedItem().toString());
                    final String username = profileSearchField.getText().toString();
                    profilesViewModel.getProfile(username, platform, region)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(profileEntity -> profilesViewModel.sendAddProfileEvent(
                                                                    profileEntity.getUsername(), platform, region),
                                    throwable -> {
                                        Toast.makeText(dialogView.getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    });

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);
        }
    }

    private void setupPlatformSpinner() {
        final ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Profile.Platform.values());

        platformSpinner.setAdapter(adapter);
        platformSpinner.setSelection(0);
    }

    private void setupRegionSpinner() {
        final ArrayAdapter<String> adapter = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Profile.Region.values());

        regionSpinner.setAdapter(adapter);
        regionSpinner.setSelection(0);
    }
}
