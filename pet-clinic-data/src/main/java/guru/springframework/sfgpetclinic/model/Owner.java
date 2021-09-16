package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<Pet>();

    @Builder
    public Owner(String firstName, String lastName, Long id, String address, String city, String telephone, Set<Pet> pets) {
        super(firstName, lastName, id);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets != null)
            this.pets = pets;
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : this.pets)
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name))
                    return pet;
            }
        return null;
    }
}
