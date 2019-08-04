package dev.rodni.ru.contactmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import dev.rodni.ru.contactmanager.db.entity.Contact;
import io.reactivex.Flowable;

@Dao
public interface ContactDAO {
    @Insert
    long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("select * from contacts")
    Flowable<List<Contact>> getContacts();

    @Query("select * from contacts where contact_id == :contactId")
    Contact getContact(long contactId);
}
