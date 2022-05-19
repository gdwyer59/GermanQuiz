package com.garethdwyer.germanquiz

data class LessonLibrary(var lessonLibrary: ArrayList<Lesson>) {
    fun listLessons(): ArrayList<String> {
        var lessonNames = ArrayList<String>()
        for(i in lessonLibrary) {
            lessonNames.add(i.lessonName)
        }
        return lessonNames

    }
}
