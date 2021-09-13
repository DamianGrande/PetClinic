package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OwnerControllerTest {
    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.owners = new HashSet<Owner>();
        this.owners.add(Owner.builder().id(1L).build());
        this.owners.add(Owner.builder().id(2L).build());
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Test
    void listOwners() throws Exception {
        when(this.ownerService.findAll()).thenReturn(this.owners);
        this.mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/index")).andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void listOwnersByIndex() throws Exception {
        when(this.ownerService.findAll()).thenReturn(this.owners);
        this.mockMvc.perform(get("/owners/index")).andExpect(status().isOk()).andExpect(view().name("owners/index")).andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        this.mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners")).andExpect(model().attributeExists("owner"));
        verifyZeroInteractions(this.ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(this.ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(this.owners));
        this.mockMvc.perform(get("/owners/list")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList")).andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        HashSet<Owner> owners = new HashSet<Owner>();
        owners.add(Owner.builder().id(1L).build());
        when(this.ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));
        this.mockMvc.perform(get("/owners/list")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/1")).andExpect(model().attribute("owners", hasSize(1)));
    }

    @Test
    void displayOwner() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        this.mockMvc.perform(get("/owners/123")).andExpect(status().isOk()).andExpect(view().name("owners/ownerDetails")).andExpect(model().attributeExists("owner"));
    }
}