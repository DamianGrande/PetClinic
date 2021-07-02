package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetSDJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<Pet>();
        this.petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long aLong) {
        return this.petRepository.findById(aLong).orElse(null);
    }

    @Override
    public Pet save(Pet object) {
        return this.petRepository.save(object);
    }

    @Override
    public void delete(Pet object) {
        this.petRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        this.petRepository.deleteById(aLong);
    }
}
