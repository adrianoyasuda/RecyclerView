package br.com.edu.ifpr.yasuda.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.edu.ifpr.yasuda.room.db.dao.PersonDao
import br.com.edu.ifpr.yasuda.room.entities.Person

@Database(entities = arrayOf(Person::class),version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun personDao(): PersonDao
}