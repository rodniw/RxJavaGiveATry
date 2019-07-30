package dev.rodni.ru.todoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dev.rodni.ru.todoapp.adapter.ToDoListAdapter;
import dev.rodni.ru.todoapp.adapter.RecyclerTouchListener;
import dev.rodni.ru.todoapp.data.ToDoListItem;
import dev.rodni.ru.todoapp.data.ToDoDataManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class TodolistFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerViewAchievingGoals;
    FloatingActionButton fabAddNewToDo;
    private String taskStatus = "";
    private String dateFront = "";
    private ToDoDataManager toDoDataManager;

    private List<ToDoListItem> goalsList = new ArrayList<>();
    private ToDoListAdapter goalAdapter;

    private View view;
    private TextView searchEditText;

    public TodolistFragment() {
    }

    public static TodolistFragment newInstance(String param1, String param2) {
        TodolistFragment fragment = new TodolistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        toDoDataManager = new ToDoDataManager(getContext());
        toDoDataManager.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_todolist, container, false);
        fabAddNewToDo = view.findViewById(R.id.fab_addNewToDo);
        searchEditText = view.findViewById(R.id.todo_search);

        recyclerViewAchievingGoals = view.findViewById(R.id.recycler_view_achieving_goals);

        if (Integer.parseInt(mParam1) == 0) {
            taskStatus = "Achieving";
            dateFront = "Will be achieved by";
        } else {
            taskStatus = "Achieved";
            dateFront = "Successfully achieved by";
        }

        //set fab
        fabAddNewToDo.setImageResource(R.drawable.ic_add_white);
        fabAddNewToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(mParam1) == 0) {
                    Intent add_new_intent = new Intent(getContext(), AddToDoActivity.class);
                    add_new_intent.putExtra("status", "Achieving");
                    startActivity(add_new_intent);
                } else {
                    Intent add_new_intent = new Intent(getContext(), AddToDoActivity.class);
                    add_new_intent.putExtra("status", "Achieved");
                    startActivity(add_new_intent);
                }
            }
        });
        setRecyclerView();
        loadData();

        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setRecyclerView() {

        goalAdapter = new ToDoListAdapter(goalsList, 0, TodolistFragment.this);

        recyclerViewAchievingGoals.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAchievingGoals.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAchievingGoals.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerViewAchievingGoals.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewAchievingGoals, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToDoListItem goal = goalsList.get(position);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("goal_related_data", MODE_PRIVATE).edit();
                editor.putString("goal_id", Integer.toString(goal.getToDoListItemId()));
                editor.putString("status", "0");
                editor.commit();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        recyclerViewAchievingGoals.setAdapter(goalAdapter);
    }

    private void loadData() {
    }

    public void markAsAchieved(int position) {

        if (Integer.parseInt(mParam1) == 0) {
            ToDoListItem goal = goalsList.get(position);
            goal.setToDoListItemStatus("Achieved");
            toDoDataManager.updateToDoListItem(goal);
            goalsList.remove(position);

            goalAdapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), "Marked as achieved", Toast.LENGTH_LONG).show();
        } else {
            ToDoListItem goal = goalsList.get(position);
            goal.setToDoListItemStatus("Achieving");
            toDoDataManager.updateToDoListItem(goal);
            goalsList.remove(position);

            goalAdapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), "Marked as not achieved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (recyclerViewAchievingGoals != null) {
                goalsList.clear();
                setRecyclerView();
                loadData();
            }
        }
    }

    public void viewListWithButtons(int position) {
        ToDoListItem goal = goalsList.get(position);
        goal.setToDoListItemBackgroundColor("1");
        goalAdapter.notifyItemChanged(position);
    }

    public void closeButtonsRow(int position) {
        ToDoListItem goal = goalsList.get(position);
        goal.setToDoListItemBackgroundColor("0");
        goalAdapter.notifyItemChanged(position);
    }

    public void deleteRow(int position) {
        final int selctedPosition = position;

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("");

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialog_cancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialog_ok);
        dialogButtonOk.setText("Delete!");

        TextView dialogDesc = (TextView) dialog.findViewById(R.id.dialog_message);

        dialogDesc.setText("Are you sure?");

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoListItem goal = goalsList.get(selctedPosition);
                toDoDataManager.deleteToDoListItem(goal);
                goalsList.remove(selctedPosition);
                dialog.dismiss();
                goalAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    public void viewUpdateFragment(int position) {
        ToDoListItem goal = goalsList.get(position);
        String content = goal.getToDoListItemName();

        FragmentManager oFragmentManager = getActivity().getSupportFragmentManager();
        UpdateToDoFragment updateTasksFragment = UpdateToDoFragment.newInstance(content, 0, TodolistFragment.this, goal);
        updateTasksFragment.show(oFragmentManager, "Sample Fragment");
    }
}
