package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        this.owner = Owner.builder().id(1L).build();
        this.petTypes = new HashSet<PetType>();
        this.petTypes.add(PetType.builder().id(1L).name("Dog").build());
        this.petTypes.add(PetType.builder().id(2L).name("Cat").build());
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.petController).build();
    }

    @Test
    void initCreationForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        this.mockMvc.perform(get("/owners/1/pets/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(model().attributeExists("pet")).andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processCreationForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        this.mockMvc.perform(post("/owners/1/pets/new")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/1"));
        verify(this.petService).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());
        this.mockMvc.perform(get("/owners/1/pets/1/edit")).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(model().attributeExists("pet")).andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(this.owner);
        when(this.petTypeService.findAll()).thenReturn(this.petTypes);
        this.mockMvc.perform(post("/owners/1/pets/1/edit")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/1"));
        verify(this.petService).save(any());
    }
}