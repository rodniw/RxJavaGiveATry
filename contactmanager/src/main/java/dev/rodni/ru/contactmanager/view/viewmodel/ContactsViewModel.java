package dev.rodni.ru.contactmanager.view.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dev.rodni.ru.contactmanager.db.entity.Contact;
import dev.rodni.ru.contactmanager.repository.ContactsRepository;

public class ContactsViewModel extends AndroidViewModel {
    private ContactsRepository repository;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
    }

    public void create(String name, String email) {
        repository.createContact(name, email);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return repository.getContactsLiveData();
    }

    public void update(Contact contact) {
        repository.updateContact(contact);
    }

    public void delete(Contact contact) {
        repository.deleteContact(contact);
    }

    public void clear() {
        repository.clear();
    }
}
