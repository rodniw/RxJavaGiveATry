package dev.rodni.ru.rxjavagiveatry.data;

import java.util.ArrayList;
import java.util.List;

import dev.rodni.ru.rxjavagiveatry.entity.Task;

public class DataSource {

    //Static method for generating some dummy Tasks:
    public static List<Task> createTasksList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));
        return tasks;
    }
}
