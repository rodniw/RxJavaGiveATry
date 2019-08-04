package dev.rodni.ru.contactmanager;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.rodni.ru.contactmanager.adapter.ContactsAdapter;
import dev.rodni.ru.contactmanager.db.ContactsAppDatabase;
import dev.rodni.ru.contactmanager.db.entity.Contact;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private Contact contact;
    private RecyclerView recyclerView;
    private ContactsAppDatabase contactsAppDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts Manager");

        recyclerView = findViewById(R.id.recycler_view_contacts);
        contactsAppDatabase= Room.databaseBuilder(getApplicationContext(),ContactsAppDatabase.class,"ContactDB").allowMainThreadQueries().build();

        contactsAdapter = new ContactsAdapter(this, contactArrayList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        compositeDisposable.add(
                contactsAppDatabase.getContactDAO().getContacts()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(contacts -> {
                            contactArrayList.clear();
                            contactArrayList.addAll(contacts);
                            contactsAdapter.notifyDataSetChanged();
                        }, throwable -> {

                        })
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addAndEditContacts(false, null, -1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAndEditContacts(final boolean isUpdate, final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdate ? "Add New Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", (dialogBox, id) -> {

                })
                .setNegativeButton("Delete",
                        (dialogBox, id) -> {
                            if (isUpdate) {
                                deleteContact(contact, position);
                            } else {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (TextUtils.isEmpty(newContact.getText().toString())) {
                Toast.makeText(MainActivity.this, "Enter contact name!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                alertDialog.dismiss();
            }

            if (isUpdate && contact != null) {
                updateContact(newContact.getText().toString(), contactEmail.getText().toString(), position);
            } else {
                createContact(newContact.getText().toString(), contactEmail.getText().toString());
            }
        });
    }

    private void deleteContact(Contact contact, int position) {
        contactsAppDatabase.getContactDAO().deleteContact(contact);
    }

    private void updateContact(String name, String email, int position) {
        contact = contactArrayList.get(position);

        contact.setName(name);
        contact.setEmail(email);

        contactsAppDatabase.getContactDAO().updateContact(contact);
    }


    private void createContact(String name, String email) {
        //this long id return number of rows
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

//after using room with rx we can delete all notifies method from crud operations
//because we always listening to all the changes already
