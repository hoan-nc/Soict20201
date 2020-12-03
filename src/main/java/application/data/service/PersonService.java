package application.data.service;

import application.data.entity.Person;
import application.data.entity.User;
import application.data.repository.IPersonRepository;
import application.data.repository.IRoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends BaseService<IPersonRepository,Person> {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    private IPersonRepository iPersonRepository;

    public PersonService(IPersonRepository personRepository){
        setRepository(personRepository);
        this.iPersonRepository = personRepository;
    }
}
