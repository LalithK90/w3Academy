package lk.w3Academy.asset.student.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.w3Academy.asset.student.entity.Student;
import lk.w3Academy.asset.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/student" )
public class StudentRestController {
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping( value = "/getStudent" )
    public MappingJacksonValue getStudent(@RequestParam( "designation" ) String designation,
                                                         @RequestParam( "id" ) Long id) {
        Student student = new Student();

        //MappingJacksonValue
        List< Student > students = studentService.search(student);
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
