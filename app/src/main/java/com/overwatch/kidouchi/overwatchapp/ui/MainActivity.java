package com.overwatch.kidouchi.overwatchapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.overwatch.kidouchi.overwatchapp.OverwatchApp;
import com.overwatch.kidouchi.overwatchapp.R;
import com.overwatch.kidouchi.overwatchapp.bus.SearchEvent;
import com.overwatch.kidouchi.overwatchapp.bus.RxEventBus;
import com.overwatch.kidouchi.overwatchapp.repo.ProfileRepository;
import com.overwatch.kidouchi.overwatchapp.viewmodel.ProfilesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.profile_list)
    RecyclerView profileListRV;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.delete_all_button)
    Button deleteAllButton;

    private ProfilesViewModel profilesViewModel;

    @Nullable
    private ProfileListAdapter profileListAdapter;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        profilesViewModel = new ProfilesViewModel(new ProfileRepository(getApplication()));

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(profilesViewModel.getAllProfiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileEntities -> {
                    profileListAdapter = new ProfileListAdapter(profileEntities);
                    profileListRV.setLayoutManager(new LinearLayoutManager(this));
                    profileListRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                    profileListRV.setAdapter(profileListAdapter);
                }, throwable -> {
                    Toast.makeText(this, "Couldn't load profiles", Toast.LENGTH_LONG).show();
                    Log.d(TAG, throwable.getMessage());
                })
        );

        compositeDisposable.add(RxEventBus.getInstance().getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event instanceof SearchEvent && profileListAdapter != null) {
                        updateList(profileListAdapter);
                    }
                }, throwable -> {
                    Toast.makeText(this, R.string.event_retrieval_err_msg, Toast.LENGTH_LONG).show();
                    Log.e(TAG + "-EventBus", throwable.getMessage());
                })
        );
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();

        super.onDestroy();
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        new ProfileSearchDialog().show(getSupportFragmentManager(), "profile_search");
    }

    @OnClick(R.id.delete_all_button)
    void onDeleteAll() {
        profilesViewModel.clearProfileList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(numRows -> Toast.makeText(this, "Success! " + numRows + "deleted!", Toast.LENGTH_LONG).show(),
                        throwable -> Toast.makeText(this, "Wasn't able to clear all the profiles", Toast.LENGTH_LONG).show());
    }

    private void updateList(@NonNull ProfileListAdapter adapter) {
        compositeDisposable.add(profilesViewModel.getAllProfiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileEntities -> {
                    adapter.updateList(profileEntities);
                }, throwable -> {
                    Toast.makeText(this, R.string.update_profiles_err_msg, Toast.LENGTH_LONG).show();
                    Log.e(TAG, throwable.getMessage());
                })
        );
    }
}
