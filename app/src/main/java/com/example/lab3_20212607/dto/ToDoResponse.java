package com.example.lab3_20212607.dto;

import java.util.ArrayList;

public class ToDoResponse {
    private ArrayList<ToDo> todos;

    public ArrayList<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<ToDo> todos) {
        this.todos = todos;
    }
}
