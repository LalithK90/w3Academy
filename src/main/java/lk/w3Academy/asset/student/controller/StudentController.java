package lk.w3Academy.asset.student.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.w3Academy.asset.commonAsset.service.CommonService;
import lk.w3Academy.asset.student.entity.Student;
import lk.w3Academy.asset.student.entity.StudentFiles;
import lk.w3Academy.asset.student.service.StudentFilesService;
import lk.w3Academy.asset.student.service.StudentService;
import lk.w3Academy.asset.userManagement.service.UserService;
import lk.w3Academy.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("/student")
@Controller
public class StudentController {
    private final StudentService studentService;
    private final StudentFilesService studentFilesService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CommonService commonService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, StudentFilesService studentFilesService,
                             DateTimeAgeService dateTimeAgeService, CommonService commonService,
                             UserService userService) {
        this.studentService = studentService;
        this.studentFilesService = studentFilesService;

        this.dateTimeAgeService = dateTimeAgeService;
        this.commonService = commonService;
        this.userService = userService;
    }
//----> Student details management - start <----//

    // Common things for an student add and update
    private String commonThings(Model model) {
        commonService.commonEmployeeAndStudent(model);
        return "student/addStudent";
    }

    //When scr called file will send to
    @GetMapping("/file/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("filename") String filename) {
        StudentFiles file = studentFilesService.findByNewID(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    //Send all student data
    @RequestMapping
    public String studentPage(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/student";
    }

    //Send on student details
    @GetMapping(value = "/{id}")
    public String studentView(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("studentDetail", student);
        model.addAttribute("addStatus", false);
        model.addAttribute("files", studentFilesService.studentFileDownloadLinks(student));
        return "student/student-detail";
    }

    //Send student data edit
    @GetMapping(value = "/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("newStudent", student.getBitIndexNumber());
        model.addAttribute("addStatus", false);
        model.addAttribute("files", studentFilesService.studentFileDownloadLinks(student));
        return commonThings(model);
    }

    //Send an student add form
    @GetMapping(value = {"/add"})
    public String studentAddForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("student", new Student());
        return commonThings(model);
    }

    //Student add and update
    @PostMapping(value = {"/add", "/update"})
    public String addStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model
    ) {

        if (result.hasErrors()) {
            model.addAttribute("addStatus", true);
            model.addAttribute("student", student);
            return commonThings(model);
        }
        try {
            student.setMobileOne(commonService.commonMobileNumberLengthValidator(student.getMobileOne()));
            student.setLand(commonService.commonMobileNumberLengthValidator(student.getLand()));
            //after save student files and save student
            studentService.persist(student);

            //save student images file

                if (student.getFile().getOriginalFilename() != null) {
                    StudentFiles studentFiles = studentFilesService.findByName(student.getFile().getOriginalFilename());
                    if (studentFiles != null) {
                        // update new contents
                        studentFiles.setPic(student.getFile().getBytes());
                        // Save all to database
                    } else {
                        studentFiles = new StudentFiles(student.getFile().getOriginalFilename(),
                                student.getFile().getContentType(),
                                student.getFile().getBytes(),
                                student.getNic().concat("-" + LocalDateTime.now()),
                                UUID.randomUUID().toString().concat("student"));
                        studentFiles.setStudent(student);
                    }
                    studentFilesService.persist(studentFiles);
                }
            return "redirect:/student";

        } catch (Exception e) {
            ObjectError error = new ObjectError("student",
                    "There is already in the system. <br>System message -->" + e.toString());
            result.addError(error);
            model.addAttribute("addStatus", true);
            model.addAttribute("student", student);
            return commonThings(model);
        }
    }

    //If need to student {but not applicable for this }
    @GetMapping(value = "/remove/{id}")
    public String removeStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/student";
    }

    //To search student any giving student parameter
    @GetMapping(value = "/search")
    public String search(Model model, Student student) {
        model.addAttribute("studentDetail", studentService.search(student));
        return "student/student-detail";
    }

//----> Student details management - end <----//
    //````````````````````````````````````````````````````````````````````````````//
//----> StudentWorkingPlace - details management - start <----//

    //Send form to add working place before find student
    @GetMapping(value = "/workingPlace")
    public String addStudentWorkingPlaceForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("studentDetailShow", false);
        return "studentWorkingPlace/addStudentWorkingPlace";
    }

    @GetMapping(value = "/getStudent")
    @ResponseBody
    public MappingJacksonValue getStudent(@RequestParam("designation") String designation,
                                          @RequestParam("id") Long id) {
        Student student = new Student();

        //MappingJacksonValue
        List<Student> students = studentService.search(student);
        //studentService.findByWorkingPlace(workingPlaceService.findById(id));

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(students);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "payRoleNumber", "designation");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("Student", simpleBeanPropertyFilter);
        //Student :- need to annotate relevant class with JsonFilter  {@JsonFilter("Student") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
    //Send a searched student to add working place
/*
    @PostMapping( value = "/workingPlace" )
    public String addWorkingPlaceStudentDetails(@ModelAttribute( "student" ) Student student, Model model) {

        List< Student > students = studentService.search(student);
        if ( students.size() == 1 ) {
            model.addAttribute("studentDetailShow", true);
            model.addAttribute("studentNotFoundShow", false);
            model.addAttribute("studentDetail", students.get(0));
            model.addAttribute("files", studentFilesService.studentFileDownloadLinks(student).get(0));
            model.addAttribute("studentWorkingPlaceHistoryObject", new StudentWorkingPlaceHistory());
            model.addAttribute("workingPlaceChangeReason", WorkingPlaceChangeReason.values());
            model.addAttribute("province", Province.values());
            model.addAttribute("districtUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                    .build()
                    .toString());
            model.addAttribute("stationUrl", MvcUriComponentsBuilder
                    .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                    .build()
                    .toString());
            return "studentWorkingPlace/addStudentWorkingPlace";
        }
        model.addAttribute("student", new Student());
        model.addAttribute("studentDetailShow", false);
        model.addAttribute("studentNotFoundShow", true);
        model.addAttribute("studentNotFound", "There is not student in the system according to the provided details" +
                " \n Could you please search again !!");

        return "studentWorkingPlace/addStudentWorkingPlace";
    }

    @PostMapping( value = "/workingPlace/add" )
    public String addWorkingPlaceStudent(@ModelAttribute( "studentWorkingPlaceHistory" ) StudentWorkingPlaceHistory studentWorkingPlaceHistory, Model model) {
        System.out.println(studentWorkingPlaceHistory.toString());
        // -> need to write validation before the save working place
        //before saving set student current working palace
        WorkingPlace workingPlace = studentWorkingPlaceHistory.getWorkingPlace();

        studentWorkingPlaceHistory.setWorkingPlace(studentWorkingPlaceHistory.getStudent().getWorkingPlace());
        studentWorkingPlaceHistory.getStudent().setWorkingPlace(workingPlace);

        studentWorkingPlaceHistory.setWorkingDuration(dateTimeAgeService.dateDifference(studentWorkingPlaceHistory.getFrom_place(), studentWorkingPlaceHistory.getTo_place()));
        studentWorkingPlaceHistoryService.persist(studentWorkingPlaceHistory);
        return "redirect:/student";
    }
*/

//----> StudentWorkingPlace - details management - end <----//

}
/*
 try {
            List< FileModel > storedFile = new ArrayList< FileModel >();

            for ( MultipartFile file : files ) {
                FileModel fileModel = fileRepository.findByName(file.getOriginalFilename());
                if ( fileModel != null ) {
                    // update new contents
                    fileModel.setPic(file.getBytes());
                } else {
                    fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                }

                fileNames.add(file.getOriginalFilename());
                storedFile.add(fileModel);
            }

            // Save all Files to database
            fileRepository.saveAll(storedFile);

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch ( Exception e ) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }

* */

/*
 public String addStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model,
 RedirectAttributes redirectAttributes) {

        * String newStudentNumber = "";
        String input;
        if (studentService.lastStudent() != null) {
            input = studentService.lastStudent().getNumber();
            int studentNumber = Integer.valueOf(input.replaceAll("[^0-9]+", "")).intValue() + 1;

            if ((studentNumber < 10) && (studentNumber > 0)) {
                newStudentNumber = "KL000" + studentNumber;
            }
            if ((studentNumber < 100) && (studentNumber > 10)) {
                newStudentNumber = "KL00" + studentNumber;
            }
            if ((studentNumber < 1000) && (studentNumber > 100)) {
                newStudentNumber = "KL0" + studentNumber;
            }
            if (studentNumber > 10000) {
                newStudentNumber = "KL" + studentNumber;
            }
        } else {
            newStudentNumber = "KL0001";
            input = "KL0000";
        }


        if (dateTimeAgeService.getAge(student.getDateOfBirth()) < 18) {
            ObjectError error = new ObjectError("dateOfBirth", "Student must be 18 old ");
            result.addError(error);
        }
        if (result.hasErrors()) {
                System.out.println("i m here");
                model.addAttribute("addStatus", true);
            if (studentService.lastStudent() != null) {
                model.addAttribute("lastStudent", studentService.lastStudent().getPayRoleNumber());
            }


                model.addAttribute("addStatus", true);
                CommonThings(model);
                redirectAttributes.addFlashAttribute("student", student);
                redirectAttributes.addFlashAttribute("files", student.getFiles());
                return "student/addStudent";
                }

      if (studentService.isStudentPresent(student)) {
            System.out.println("already on ");
            User user = userService.findById(userService.findByStudentId(student.getId()));
            if (student.getStudentStatus() != StudentStatus.WORKING) {
                user.setEnabled(false);
                studentService.persist(student);
            }
            System.out.println("update working");
            user.setEnabled(true);
            studentService.persist(student);
            return "redirect:/student";
        }
        if (student.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Successfully Add but Email was not sent.");
            redirectAttributes.addFlashAttribute("alertStatus", false);

            studentService.persist(student);
        }


        System.out.println("save no id");
                System.out.println("Student come "+student.toString());
                //studentService.persist(student);
                return "redirect:/student";
                }

 */