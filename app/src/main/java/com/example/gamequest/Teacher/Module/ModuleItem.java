package com.example.gamequest.Teacher.Module;

public class ModuleItem {
    String lessonTitle, lessonDescription, lessonId;

    public ModuleItem(String lessonTitle, String lessonDescription, String lessonId) {
        this.lessonDescription = lessonDescription;
        this.lessonTitle = lessonTitle;
        this.lessonId = lessonId;
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

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
}
