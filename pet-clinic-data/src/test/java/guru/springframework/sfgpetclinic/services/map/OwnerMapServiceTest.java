package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        this.ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        this.ownerMapService.save(Owner.builder().id(this.ownerId).lastName(this.lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = this.ownerMapService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = this.ownerMapService.findById(this.ownerId);
        assertEquals(this.ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();
        Owner savedOwner = this.ownerMapService.save(owner2);
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = this.ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        this.ownerMapService.delete(this.ownerMapService.findById(this.ownerId));
        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        this.ownerMapService.deleteById(this.ownerId);
        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner smith = this.ownerMapService.findByLastName(this.lastName);
        assertNotNull(smith);
        assertEquals(this.ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = this.ownerMapService.findByLastName("foo");
        assertNull(smith);
    }
}