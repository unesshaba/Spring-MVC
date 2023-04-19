package com.example.patientsmvc.web;

import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
//    @Autowired
    private PatientRepository patientRepository;
//    public PatientController(PatientController patientController) {
//        this.patientController = patientController;
//   l'annotation autowired n'est pas recommander pour cela on ajoute un constructor  avec parametre  }
    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name="page",defaultValue="0") int page,
                           @RequestParam(name="size",defaultValue="5") int size,
                           @RequestParam(name="keyword",defaultValue="") String keyword){
       Page<Patient> pagepatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
//        Page<Patient> pagepatients=patientRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listPatients",pagepatients.getContent());
        model.addAttribute("pages", new int[pagepatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";

    }
    @GetMapping("/delete")
    public String delete(Long id, String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }
    @GetMapping("/")
    public String home(){

        return "redirect:/index";

}
@GetMapping("/patients")
@ResponseBody
public List<Patient> listPatient( ){
        return  patientRepository.findAll();
    }
    @GetMapping(path = "/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

   @PostMapping(path = "/save")
////    //une fois tu cree un patient tu fait la validation
   public String Save(Model model,
                      @Valid Patient patient, BindingResult bindingResult,
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors())  return "formPatients";
       patientRepository.save(patient);
       return "redirect:/index?page=" +page+"&keyword="+keyword;

}
    @GetMapping (path = "/edit")
    public String Edit(Model model, Long Id,int page,String keyword){
        Patient patient =  patientRepository.findById(Id).orElse(null);
        if(patient == null)throw new RuntimeException("Patient Introuvable");

        model.addAttribute("patient", patient);

        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "editPatients";
    }
}
