package com.overwatch.kidouchi.overwatchapp;

import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;
import com.overwatch.kidouchi.overwatchapp.model.Profile;
import com.overwatch.kidouchi.overwatchapp.repo.ProfileRepository;
import com.overwatch.kidouchi.overwatchapp.viewmodel.ProfilesViewModel;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class ProfileViewModelTest {

    @Mock
    private ProfileRepository profileRepository;

    private ProfilesViewModel profilesViewModel;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        profilesViewModel = new ProfilesViewModel(profileRepository);
    }

    @Test
    public void requestProfile_emitsProfile() {
        final ProfileEntity profileEntity = new ProfileEntity("testuser", "https://fakeurl.com");
        Mockito.when(profileRepository.getProfile("testuser", Profile.Region.US, Profile.Platform.PSN))
                .thenReturn(Observable.just(profileEntity));

        profilesViewModel.getProfile("testuser", Profile.Platform.PSN, Profile.Region.US)
                .test()
                .assertValue(profileEntity);
    }
}
