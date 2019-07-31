package dev.rodni.ru.todoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import dev.rodni.ru.todoapp.data.ToDoListItem;
import dev.rodni.ru.todoapp.data.ToDoDataManager;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UpdateToDoFragment extends DialogFragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageButton closeImageButton;
    private ImageButton saveImageButton;

    public static Fragment parentFragment;
    public static int fragmentTypeOfParent;
    private static ToDoListItem goal;

    private MaterialEditText materialEditText;
    private static Object objectCame;

    private ToDoDataManager dbManager;

    public UpdateToDoFragment() {
    }

    public static UpdateToDoFragment newInstance(String param1, int fragemtType, Fragment fragmentToDeal, Object object) {
        UpdateToDoFragment fragment = new UpdateToDoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);

        parentFragment = fragmentToDeal;
        fragmentTypeOfParent = fragemtType;

        objectCame = object;

        goal = (ToDoListItem) object;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_tasks, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        materialEditText = (MaterialEditText) view.findViewById(R.id.etTask);
        materialEditText.setText(mParam1);

        dbManager = new ToDoDataManager(getContext());
        dbManager.open();

        closeImageButton = (ImageButton) view.findViewById(R.id.imageButtonClose);

        closeImageButton.setOnClickListener(v -> getDialog().dismiss());

        saveImageButton = (ImageButton) view.findViewById(R.id.imageButtonSave);

        saveImageButton.setOnClickListener(v -> {
            String newGoal = materialEditText.getText().toString();
            if ((materialEditText.getText().toString()).equals("")) {
            } else {
                goal.setToDoListItemName(newGoal);
                Toast.makeText(getActivity(), "Updated successfully", Toast.LENGTH_LONG).show();
            }

            dbManager.updateToDoListItem(goal);

            TodolistFragment todolistFragment = (TodolistFragment) parentFragment;
            todolistFragment.setUserVisibleHint(true);
            getDialog().dismiss();
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
