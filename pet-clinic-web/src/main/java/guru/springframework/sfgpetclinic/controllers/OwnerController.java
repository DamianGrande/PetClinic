package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"", "/index", "/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", this.ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView getOwnerDetails(@PathVariable Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", this.ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/list")
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        if (owner.getLastName() == null)
            owner.setLastName("");
        List<Owner> owners = this.ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");
            return "owners/findOwners";
        }
        model.addAttribute("owners", owners);
        if (owners.size() == 1)
            return "redirect:/owners/" + owners.iterator().next().getId();
        return "owners/ownersList";
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreationForm(Owner owner, BindingResult result) {
        if (result.hasErrors())
            return "owners/createOrUpdateOwnerForm";
        Owner savedOwner = this.ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateOwnerForm(@PathVariable Long id, Model model) {
        model.addAttribute("owner", this.ownerService.findById(id));
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{id}/edit")
    public String processUpdateOwnerForm(@PathVariable Long id, Owner owner, BindingResult result) {
        if (result.hasErrors())
            return "owners/createOrUpdateOwnerForm";
        owner.setId(id);
        Owner savedOwner = this.ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }
}
