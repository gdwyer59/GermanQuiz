package com.garethdwyer.germanquiz

import androidx.collection.ArraySet

data class LessonLibrary(var lessonLibrary: ArrayList<Lesson>) {
    fun listLessons(): Array<String> {
        //var lessonNames = ArrayList<String>()
        var lessonNames = ArraySet<String>()
        for(i in lessonLibrary) {
            lessonNames.add(i.lessonName)
        }
        return lessonNames.toTypedArray()
        //return unique set of names
    }
}
