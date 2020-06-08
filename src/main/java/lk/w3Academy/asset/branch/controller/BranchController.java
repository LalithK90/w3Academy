package lk.w3Academy.asset.branch.controller;

import lk.w3Academy.asset.branch.entity.Branch;
import lk.w3Academy.asset.branch.entity.Enum.BranchStatus;
import lk.w3Academy.asset.branch.service.BranchService;
import lk.w3Academy.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/branch")
public class BranchController implements AbstractController<Branch, Long> {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    private String commonThing(Model model, Branch branch) {
        model.addAttribute("addStatus", true);
        model.addAttribute("branch", branch);
        model.addAttribute("branchStatuse", BranchStatus.values());
        return "branch/branch";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("branches", branchService.findAll());
        return commonThing(model, new Branch());
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("branch", new Branch());
        model.addAttribute("branchStatuse", BranchStatus.values());
        return "branch/branch";
    }


    @GetMapping("/view/{id}")
    public String findById(@PathVariable Long id, Model model) {
        commonThing(model, new Branch());
        model.addAttribute("branches", branchService.findAll());
        model.addAttribute("branchDetail", branchService.findById(id));
        return "branch/branch";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("addStatus", false);
        Branch branch = branchService.findById(id);
        List<Branch> branches = branchService.findAll();
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i) == branch) {
                branches.remove(i);
                break;
            }
        }
        model.addAttribute("branches", branches);
        model.addAttribute("branch", branch);
        model.addAttribute("branchStatuse", BranchStatus.values());
        return "branch/branch";
    }

    @PostMapping(value = {"/add", "/update"})
    public String persist(@Valid @ModelAttribute Branch branch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, branch);
        }
        redirectAttributes.addFlashAttribute("branchDetailShow", true);
        redirectAttributes.addFlashAttribute("branchDetail", branchService.persist(branch));
        return "redirect:/branch";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        branchService.delete(id);
        model.addAttribute("message", "Successfully deleted");
        return "redirect:/branch";
    }


/*
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("name") String name,RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("name","name");
    return "redirect:/";
    }
     @RequestMapping(value = "/", method = RequestMethod.GET)
     public String homeMethod(@ModelAttribue("name") String name){
     return "name";
     }
 */
}

