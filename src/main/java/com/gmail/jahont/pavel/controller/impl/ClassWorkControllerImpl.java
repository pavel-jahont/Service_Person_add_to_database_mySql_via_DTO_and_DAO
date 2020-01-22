package com.gmail.jahont.pavel.controller.impl;

import com.gmail.jahont.pavel.controller.ClassWorkController;
import com.gmail.jahont.pavel.service.PersonService;
import com.gmail.jahont.pavel.service.impl.PersonServiceImpl;
import com.gmail.jahont.pavel.service.model.AddPersonDTO;
import com.gmail.jahont.pavel.service.model.PersonDTO;

public class ClassWorkControllerImpl implements ClassWorkController {

    private PersonService personService = new PersonServiceImpl();

    @Override
    public void runFirstTask() {
        AddPersonDTO personDTO = new AddPersonDTO();
        PersonDTO dto = personService.add(personDTO);
    }

}
