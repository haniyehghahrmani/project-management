package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Person;
import com.example.projectManagement.repository.PersonRepository;
import com.example.projectManagement.service.PersonService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    public PersonServiceImpl(PersonRepository repository){
        this.repository = repository;
    }

    @Override
    public Person save(@Valid Person person) {
        return repository.save(person);
    }

    @Override
    public Person update(Person person) throws NoContentException {
        Person existingPerson = repository.findById(person.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Person Was Found with id " + person.getId() + " To Update!")
                );

        existingPerson.setName(person.getName());
        existingPerson.setLastname(person.getLastname());
        existingPerson.setNationalId(person.getNationalId());
        existingPerson.setBirthdate(person.getBirthdate());
        existingPerson.setAge(person.getAge());
        existingPerson.setGender(person.getGender());
        existingPerson.setUser(person.getUser());

        return repository.saveAndFlush(existingPerson);
    }


    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findPersonByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Person Was Found with id " + id  +" To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) throws NoContentException {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Person Was Found with id : " + id );
        }
    }

    @Override
    public Long getPersonsCount() {
        return repository.count();
    }

    @Override
    public Person logicalRemoveWithReturn(Long id) throws NoContentException {
        Person person = repository.findPersonByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Person Was Found with id  " + id  +" To Remove !")
        );

        person.setDeleted(true);
        return repository.save(person);

    }

    @Override
    public List<Person> findPersonByDeletedFalse() {
        return repository.findPersonByDeletedFalse();
    }

    @Override
    public Optional<Person> findPersonByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Person> optional = repository.findPersonByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Person Was Found with id : " + id );
        }
    }

    @Override
    public List<Person> findPersonByNameAndLastnameAndDeletedFalse(String name, String lastName) {
        return repository.findPersonByNameAndLastnameAndDeletedFalse(name , lastName);
    }

    @Override
    public Optional<Person> findPersonByNationalIDAndDeletedFalse(String nationalId) throws NoContentException {
        Optional<Person> optional = repository.findPersonByNationalIdAndDeletedFalse(nationalId);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Person Was Found with National ID : " + nationalId );
        }
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

}
