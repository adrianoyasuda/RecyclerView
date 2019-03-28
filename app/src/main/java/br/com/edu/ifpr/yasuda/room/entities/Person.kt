package br.com.edu.ifpr.yasuda.room.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class Person(
    @ColumnInfo(name = "first_name")
    var firstname: String,
    @ColumnInfo(name = "last_name")
    var lastname: String,
    var title: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val fullName get() = "${firstname} ${lastname}"
    val fullNameWithTitle get() = "${title} ${fullName}"

    override fun toString() = fullNameWithTitle
}