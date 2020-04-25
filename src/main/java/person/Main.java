package person;
import com.github.javafaker.Faker;
import legoset.model.LegoSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");


    private static void CreatePeopleSet(int darab) throws ParseException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i=0;i<darab;i++) {
                em.persist(RandomPerson());
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static List<Person> getPeople() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Person l ORDER BY l.id",Person.class).getResultList();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        CreatePeopleSet(100);

        getPeople().forEach(System.out::println);

        emf.close();
    }

    public static Person RandomPerson() throws ParseException {
        Faker faker = new Faker();
        Person person = new Person();

        Date date;
        date = faker.date().birthday();
        java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        person.setName(faker.name().name());
        person.setDob( localDate);
        person.setGender (faker.options().option(Person.Gender.values()));
        Address address = new Address();
        address.city = faker.address().city();
        address.country = faker.address().country();
        address.state = faker.address().state();
        address.streetAddress = faker.address().streetAddress();
        address.zip = faker.address().zipCode();
        person.setAddress(address);
        person.setEmail(faker.internet().emailAddress());
        person.setProfession(faker.company().profession());

        return person;
    }
}