package com.example.githubuser.helper

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateHelperTest {

    private val dateNow = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())

    @Test
    fun getDate() {
        val dateAssign = DateHelper.getCurrentDate()
        assertEquals(dateNow,dateAssign)
    }
}