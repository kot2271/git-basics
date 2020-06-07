package com.todolist.ToDoList.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title, description, full_text;

    public int getId() {
        return id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFull_text() {
        return full_text;
    }

    public Task setFull_text(String full_text) {
        this.full_text = full_text;
        return this;
    }

    public Task() {
    }

    public Task(String title, String description, String full_text) {
        this.title = title;
        this.description = description;
        this.full_text = full_text;
    }
}
