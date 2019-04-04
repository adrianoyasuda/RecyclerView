package br.com.edu.ifpr.yasuda.room.ui

import br.com.edu.ifpr.yasuda.room.entities.Person

interface PeopleAdapterListener {
    fun personRemoved(person: Person)
    fun personClicked(person: Person)
}