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

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static void createPeopleSet() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
           for(int i=0; i<5; i++){
               var random = RandomPerson();
               em.persist(random);
               System.out.println(random);
           }
            em.getTransaction().commit();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws ParseException {
        createPeopleSet();
        emf.close();
    }


    public static Date between(Date from, Date to) throws IllegalArgumentException {
        Faker faker = new Faker();
        if (to.before(from)) {
            throw new IllegalArgumentException("Invalid date range, the upper bound date is before the lower bound.");
        }
        if (from.equals(to)) {
            return from;
        }
        long offsetMillis = faker.random().nextLong(to.getTime() - from.getTime());
        return new Date(from.getTime() + offsetMillis);
    }

    public static Person RandomPerson() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date from = format.parse("1900-01-01");
        java.util.Date to = format.parse("2020-12-31");
        java.util.Date date = between(from, to);
        java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Faker faker = new Faker();
        Person person = new Person();

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