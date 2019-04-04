package br.com.edu.ifpr.yasuda.room.db.dao

import androidx.room.*
import br.com.edu.ifpr.yasuda.room.entities.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM people")
    fun getAll(): List<Person>

    @Query("SELECT * FROM people WHERE id = :id LIMIT 1")
    fun findById(id: Int): Person?

    @Query("SELECT * FROM people WHERE first_name LIKE :firstName AND last_name LIKE :lastName")
    fun findByName(firstName: String, lastName: String): List<Person>

    @Insert
    fun  insert(person: Person): Long

    @Update
    fun  update(person: Person)

    @Delete
    fun remove(person: Person)


}