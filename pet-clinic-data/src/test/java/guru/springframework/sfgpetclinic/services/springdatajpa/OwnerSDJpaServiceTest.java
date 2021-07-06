package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class OwnerSDJpaServiceTest {
    private final String LAST_NAME = "Smith";
    private Owner returnOwner;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.returnOwner = Owner.builder().id(1L).lastName(this.LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = new HashSet<Owner>();
        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());
        when(this.ownerRepository.findAll()).thenReturn(returnOwnerSet);
        Set<Owner> owners = this.service.findAll();
        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(this.ownerRepository.findById(anyLong())).thenReturn(Optional.of(this.returnOwner));
        Owner owner = this.service.findById(1L);
        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(this.ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = this.service.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(this.ownerRepository.save(any())).thenReturn(this.returnOwner);
        Owner savedOwner = this.service.save(ownerToSave);
        assertNotNull(savedOwner);
        verify(this.ownerRepository).save(any());
    }

    @Test
    void delete() {
        this.service.delete(this.returnOwner);
        verify(this.ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        this.service.deleteById(1L);
        verify(this.ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(this.ownerRepository.findByLastName(any())).thenReturn(this.returnOwner);
        Owner smith = this.service.findByLastName(this.LAST_NAME);
        assertEquals(this.LAST_NAME, smith.getLastName());
        verify(this.ownerRepository).findByLastName(any());
    }
}