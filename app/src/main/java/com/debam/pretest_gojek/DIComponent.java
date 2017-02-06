package com.debam.pretest_gojek;

import com.debam.pretest_gojek.activity.AddContactActivity;
import com.debam.pretest_gojek.activity.ContactDetailsActivity;
import com.debam.pretest_gojek.activity.ContactsActivity;
import com.debam.pretest_gojek.models.Contact;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by Debam on 2/5/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface DIComponent {

    void inject(ContactsActivity act);
    void inject(AddContactActivity act);
    void inject(ContactDetailsActivity act);
}
