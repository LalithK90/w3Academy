package w3Campus.asset.student.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import w3Campus.asset.commonAsset.service.CommonService;
import w3Campus.asset.student.entity.Enum.*;
import w3Campus.asset.student.entity.Student;
import w3Campus.asset.student.entity.StudentFiles;
import w3Campus.asset.student.entity.StudentResult;
import w3Campus.asset.student.service.StudentFilesService;
import w3Campus.asset.student.service.StudentService;
import w3Campus.asset.userManagement.service.UserService;
import w3Campus.util.service.DateTimeAgeService;
import w3Campus.util.service.MakeAutoGenerateNumberService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final StudentFilesService studentFilesService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CommonService commonService;
    private final UserService userService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    @Autowired
    public StudentController(StudentService studentService, StudentFilesService studentFilesService,
                             DateTimeAgeService dateTimeAgeService, CommonService commonService,
                             UserService userService, MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.studentService = studentService;
        this.studentFilesService = studentFilesService;

        this.dateTimeAgeService = dateTimeAgeService;
        this.commonService = commonService;
        this.userService = userService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }
//----> Student details management - start <----//

    // Common things for an student add and update
    private String commonThings(Model model) {
        model.addAttribute("attempts", Attempt.values());
        model.addAttribute("streamLevels", StreamLevel.values());
        model.addAttribute("compulsoryOLSubjects", CompulsoryOLSubject.values());
        model.addAttribute("subjectResults", SubjectResult.values());
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
        model.addAttribute("file", studentFilesService.studentFileDownloadLink(student));
        return "student/student-detail";
    }

    //Send student data edit
    @GetMapping(value = "/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("addStatus", false);
        model.addAttribute("studentStatuses", StudentStatus.values());
        model.addAttribute("file", studentFilesService.studentFileDownloadLink(student));
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
    public String addStudent(@Valid @ModelAttribute Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("addStatus", false);
            model.addAttribute("studentStatuses", StudentStatus.values());
            if (student.getId() != null) {
                model.addAttribute("file", studentFilesService.studentFileDownloadLink(student));
            }
            return commonThings(model);
        }
        //mobile number length set 10
        student.setMobileOne(commonService.commonMobileNumberLengthValidator(student.getMobileOne()));
        student.setLand(commonService.commonMobileNumberLengthValidator(student.getLand()));
        //need to generate student register number
        if (student.getId() == null) {
            student.setStudentStatus(StudentStatus.ACTIVE);
            Student lastStudent = studentService.lastStudent();
            if (lastStudent != null) {
                student.setCode(makeAutoGenerateNumberService.numberAutoGen(lastStudent.getCode(), "W3S"));
            } else {
                student.setCode(makeAutoGenerateNumberService.numberAutoGen(null, "W3S"));
            }
        }
        //make nic with 12 characters
        if (student.getNic().length() == 10) {
            student.setNic(makeAutoGenerateNumberService.makeNewNIC(student.getNic()));
        }
        //student result start
        List<StudentResult> studentResults = new ArrayList<>();
        AtomicBoolean olMath = new AtomicBoolean(false);
        AtomicBoolean olEnglish = new AtomicBoolean(false);
        AtomicBoolean alHas = new AtomicBoolean(false);
        AtomicInteger alSubjectCount = new AtomicInteger();
        for (StudentResult studentResult : student.getStudentResults()) {
            if (studentResult.getIndexNumber() != null) {
                if (studentResult.getStreamLevel().equals(StreamLevel.OL) &&
                        studentResult.getCompulsoryOLSubject().equals(CompulsoryOLSubject.MATH) &&
                        !studentResult.getSubjectResult().equals(SubjectResult.W)) {
                    olMath.set(true);
                }
                if (studentResult.getStreamLevel().equals(StreamLevel.OL) &&
                        studentResult.getCompulsoryOLSubject().equals(CompulsoryOLSubject.ENG) &&
                        !studentResult.getSubjectResult().equals(SubjectResult.W)) {
                    olEnglish.set(true);
                }
                if (studentResult.getCompulsoryOLSubject().equals(CompulsoryOLSubject.OTH) &&
                        studentResult.getMark() == null &&
                        studentResult.getStreamLevel().equals(StreamLevel.AL) &&
                        !studentResult.getSubjectResult().equals(SubjectResult.W))
                    alSubjectCount.addAndGet(1);
                studentResult.setStudent(student);
                studentResults.add(studentResult);
            }
        }
        if (alSubjectCount.get() == 3)
            alHas.set(true);
        //student set student result
        student.setStudentResults(studentResults);
        //after save student files and save student
        Student dbStudent = studentService.persist(student);
//todo -> need to create automatic email with according to his result
        var emailMessage = "";
        if (olEnglish.get() && olMath.get() && alHas.get()) {
            emailMessage = "Dear " + student.getCallingName() + "\n Your are eligible to do \n Bachelor of Information Technology \n\n\n your registration number is " + dbStudent.getCode() + "\n\n\n\tThanks\n\tWorld Wide Wisdome Campus";
        }
        if (olEnglish.get() && olMath.get() && !alHas.get()) {
            emailMessage = "Dear " + student.getCallingName() + "\n Your are eligible to do \nFoundation of Information Technology \n\n\n your registration number is " + dbStudent.getCode() + "\n\n\n\tThanks\n\tWorld Wide Wisdome Campus";
        }
        if (!olEnglish.get() && !olMath.get() && !alHas.get()) {
            emailMessage = "Dear " + student.getCallingName() + "\n Your are not eligible to do \n Bachelor of Information Technology. \n But you can try again \n\t\tdo not give up \n\n\n your registration number is " + dbStudent.getCode() + "\n\n\n\tThanks\n\tWorld Wide Wisdome Campus";
        }
        System.out.println("email message  " + emailMessage);
        //save student images file
        try {
            if (student.getFile().getOriginalFilename() != null) {
                //to file size reduce
/*
                File convFile = new File( student.getFile().getOriginalFilename() );
                File input = new File("/tmp/duke.jpg");
                BufferedImage image = ImageIO.read(input);

                File output = new File("/tmp/duke-compressed-005.jpg");
                OutputStream out = new FileOutputStream(output);

                ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(out);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()){
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.05f);
                }

                writer.write(null, new IIOImage(image, null, null), param);

                out.close();
                ios.close();
                writer.dispose();*/

                StudentFiles studentFiles = studentFilesService.findByStudent(dbStudent);
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
                    "There is already in the system. \n System message --> \n" + e.getLocalizedMessage());
            result.addError(error);
            model.addAttribute("addStatus", true);
            model.addAttribute("student", student);
            return commonThings(model);
        }
    }

    //If need to student {but not applicable for this }
    @GetMapping(value = "/remove/{id}")
    public String removeStudent(@PathVariable Long id) {
        studentFilesService.deleteByStudent(studentService.findById(id));
        studentService.delete(id);
        return "redirect:/student";
    }

    //To search student any giving student parameter
    @GetMapping(value = "/search")
    public String search(Model model, Student student) {
        model.addAttribute("studentDetail", studentService.search(student));
        return "student/student-detail";
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
}
