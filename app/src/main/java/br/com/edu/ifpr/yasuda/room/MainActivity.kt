package br.com.edu.ifpr.yasuda.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.com.edu.ifpr.yasuda.room.db.AppDatabase
import br.com.edu.ifpr.yasuda.room.db.dao.PersonDao
import br.com.edu.ifpr.yasuda.room.entities.Person
import br.com.edu.ifpr.yasuda.room.ui.PeopleAdapter
import br.com.edu.ifpr.yasuda.room.ui.PeopleAdapterListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PeopleAdapterListener {

    lateinit var personDao: PersonDao
    lateinit var adapter: PeopleAdapter
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

/*        lv_people.setOnItemClickListener{ _, _, position, _ ->
            editPerson(getPersonFromList(position))
        }

        lv_people.setOnItemLongClickListener { _, _ ,position, _ ->
            removePerson(getPersonFromList(position))
            loadData()
            true
        }*/

        loadData()
    }

    private fun removePerson(person: Person){
        personDao.remove(person)
        loadData()
    }

/*
    private fun getPersonFromList(position : Int) = adapter.getItem(position) as Person
*/

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

                adapter.updatePerson(person)
            }
        }
        else{
            var person = Person(firstName, lastName, title)
            val id = personDao.insert(person).toInt()
            person = personDao.findById(id)!!

            val position = adapter.addPerson(person)
            list_people.scrollToPosition(position)
        }
    }

    private fun loadData(){
        val people = personDao.getAll()
        adapter = PeopleAdapter(people.toMutableList(), this)
        list_people.adapter = adapter

        list_people.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        cleanFields()
    }

    private fun cleanFields(){
        tf_firstName.text.clear()
        tf_lastName.text.clear()
        tf_title.text.clear()
        tf_firstName.requestFocus()

    }

    override fun personRemoved(person: Person) {
        personDao.remove(person)
    }

    override fun personClicked(person: Person) {
        editPerson(person)
    }
}
