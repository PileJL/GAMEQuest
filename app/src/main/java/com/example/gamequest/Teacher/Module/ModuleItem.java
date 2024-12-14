package com.example.gamequest.Teacher.Module;

public class ModuleItem {
    String moduleNumber, lessonTitle;

    public ModuleItem(String moduleNumber, String lessonTitle) {
        this.moduleNumber = moduleNumber;
        this.lessonTitle = lessonTitle;
    }

    public String getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(String moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }
}
