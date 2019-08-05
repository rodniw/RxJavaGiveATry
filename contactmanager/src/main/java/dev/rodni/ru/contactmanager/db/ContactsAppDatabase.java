package dev.rodni.ru.contactmanager.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import dev.rodni.ru.contactmanager.db.entity.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {
    public abstract ContactsDAO getContactDAO();
}
