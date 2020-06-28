package w3Campus.asset.classRoom.controller;


import w3Campus.asset.branch.service.BranchService;
import w3Campus.asset.classRoom.entity.ClassRoom;
import w3Campus.asset.classRoom.service.ClassRoomService;
import w3Campus.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/classRoom")
public class ClassRoomController implements AbstractController<ClassRoom, Long> {
    private final ClassRoomService classRoomService;
    private final BranchService branchService;

    @Autowired
    public ClassRoomController(ClassRoomService classRoomService, BranchService branchService) {
        this.classRoomService = classRoomService;
        this.branchService = branchService;
    }

    private String commonThing(Model model, ClassRoom classRoom) {
        model.addAttribute("addStatus", true);
        model.addAttribute("classRoom", classRoom);
        model.addAttribute("branches", branchService.findAll());
        return "classRoom/classRoom";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("classRooms", classRoomService.findAll());
        return commonThing(model, new ClassRoom());
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("classRoom", new ClassRoom());
        return "classRoom/classRoom";
    }


    @GetMapping("/view/{id}")
    public String findById(@PathVariable Long id, Model model) {
        commonThing(model, new ClassRoom());
        model.addAttribute("classRooms", classRoomService.findAll());
        model.addAttribute("classRoomDetail", classRoomService.findById(id));
        return "classRoom/classRoom";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("addStatus", false);
        ClassRoom classRoom = classRoomService.findById(id);
        List<ClassRoom> classRoomes = classRoomService.findAll();
        for (int i = 0; i < classRoomes.size(); i++) {
            if (classRoomes.get(i) == classRoom) {
                classRoomes.remove(i);
                break;
            }
        }
        model.addAttribute("classRooms", classRoomes);
        model.addAttribute("classRoom", classRoom);
        return "classRoom/classRoom";
    }

    @PostMapping(value = {"/add", "/update"})
    public String persist(@Valid @ModelAttribute ClassRoom classRoom, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, classRoom);
        }
        redirectAttributes.addFlashAttribute("classRoomDetailShow", true);
        redirectAttributes.addFlashAttribute("classRoomDetail", classRoomService.persist(classRoom));
        return "redirect:/classRoom";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        classRoomService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted");
        return "redirect:/classRoom";
    }

}

