package person;
import lombok.*;
import javax.persistence.*;
import java.time.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Person {

    public static enum  Gender {
        FEMALE, MALE;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private Gender gender;

    @Embedded
    private Address address;

    private String email;

    private String profession;

    public void setName(String name){
        this.name = name;
    }

    public void setDob(LocalDate date){
        this.dob = date;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setProfession(String profession){
        this.profession = profession;
    }
}