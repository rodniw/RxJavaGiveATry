package dev.rodni.ru.contactmanager.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;

import java.util.List;

import dev.rodni.ru.contactmanager.db.ContactsAppDatabase;
import dev.rodni.ru.contactmanager.db.entity.Contact;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ContactsRepository {

    private Application mApplication;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();

    public ContactsRepository(Application application) {
        this.mApplication = application;
        contactsAppDatabase = Room.databaseBuilder(
                mApplication.getApplicationContext(),
                ContactsAppDatabase.class,
                "ContactDB")
                .build();
        readContact();
    }

    public void createContact(String name, String email) {
        compositeDisposable.add(
                Completable.fromAction(() -> {
                    long id = contactsAppDatabase.getContactDAO().addContact(new Contact(0,name, email));
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                            }
                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }

    public void readContact() {
        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContacts()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                contacts -> contactsLiveData.postValue(contacts),
                                throwable -> {
                        })
        );
    }

    public void updateContact(Contact contact) {

        compositeDisposable.add(
                Completable.fromAction(() -> contactsAppDatabase.getContactDAO().updateContact(contact))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                            }
                            @Override
                            public void onError(Throwable e) {
                            }
                        })
        );
    }

    public void deleteContact(Contact contact) {
        compositeDisposable.add(
                Completable.fromAction(() -> contactsAppDatabase.getContactDAO().deleteContact(contact))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                            }
                            @Override
                            public void onError(Throwable e) {
                            }
                        })
        );
    }

    public MutableLiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

    public void clear() {
        compositeDisposable.clear();
    }
}
