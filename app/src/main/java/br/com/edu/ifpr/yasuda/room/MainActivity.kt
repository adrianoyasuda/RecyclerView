package br.com.edu.ifpr.yasuda.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.room.Room
import br.com.edu.ifpr.yasuda.room.db.AppDatabase
import br.com.edu.ifpr.yasuda.room.db.dao.PersonDao
import br.com.edu.ifpr.yasuda.room.entities.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var personDao: PersonDao
    lateinit var adapter: ArrayAdapter<Person>
    var personEditing: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db =
            Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "person.db")
            .allowMainThreadQueries()
            .build()
        personDao = db.personDao()
        personDao.getAll()

        bt_save.setOnClickListener { savePerson() }

        lv_people.setOnItemClickListener{ _, _, position, _ ->
            /*val person =adapter.getItem(position) as Person
            editPerson(person)*/
            editPerson(getPersonFromList(position))
        }

        lv_people.setOnItemLongClickListener { _, _ ,position, _ ->
            /*val person = adapter.getItem(position) as Person
            personDao.remove(person)*/
            removePerson(getPersonFromList(position))
            loadData()
            true
        }

        loadData()
    }

    private fun removePerson(person: Person){
        personDao.remove(person)
        loadData()
    }

    private fun getPersonFromList(position : Int) = adapter.getItem(position) as Person

    private fun editPerson(person: Person) {
        tf_firstName.setText(person.firstname)
        tf_lastName.setText(person.lastname)
        tf_title.setText(person.title)

        tf_firstName.requestFocus()

        personEditing = person
    }

    private fun savePerson(){
        val firstName = tf_firstName.text.toString()
        val lastName = tf_lastName.text.toString()
        val title = tf_title.text.toString()


        if (personEditing != null){
            personEditing?.let { person ->
                person.firstname = firstName
                person.lastname = lastName
                person.title = title
                personDao.update(person)
            }
        }
        else{
            val person = Person(firstName, lastName, title)
            personDao.insert(person)
        }
        loadData()
    }

    private fun loadData(){
        val people = personDao.getAll()
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, people)
        lv_people.adapter = adapter
        cleanFields()
    }

    private fun cleanFields(){
        tf_firstName.text.clear()
        tf_lastName.text.clear()
        tf_title.text.clear()
        tf_firstName.requestFocus()

    }
}
