package com.example.gamequest.Teacher.Module;

public class ModuleItem {
    String lessonTitle, lessonDescription;;

    public ModuleItem(String lessonTitle, String lessonDescription) {
        this.lessonDescription = lessonDescription;
        this.lessonTitle = lessonTitle;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }
}
