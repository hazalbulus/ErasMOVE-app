package com.erasmuarrem.ErasMove.controllers;

import com.erasmuarrem.ErasMove.helpers.HashingPasswordHelper;
import com.erasmuarrem.ErasMove.models.*;
import com.erasmuarrem.ErasMove.repositories.LanguageRepository;
import com.erasmuarrem.ErasMove.repositories.OutgoingStudentRepository;
import com.erasmuarrem.ErasMove.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/initialize")
public class InitializationController {

    private final CourseService courseService;
    private final DepartmentService departmentService;
    private final DepartmentCoordinatorService departmentCoordinatorService;
    private final ErasmusUniversityService erasmusUniversityService;
    private final ErasmusUniversityDepartmentService erasmusUniversityDepartmentService;
    private final AdministrativeStaffService administrativeStaffService;
    private final OutgoingStudentRepository outgoingStudentRepository;
    private final ExchangeUniversityService exchangeUniversityService;
    private final ExchangeUniversityDepartmentService exchangeUniversityDepartmentService;
    private final IncomingStudentService incomingStudentService;
    private  final CourseCoordinatorService courseCoordinatorService;
    private final LanguageRepository languageRepository;
    private final AdminService adminService;
    private final HostUniversityService hostUniversityService;
    private final HashingPasswordHelper hashingPasswordHelper = HashingPasswordHelper.getInstance();

    @Autowired
    public InitializationController(CourseService courseService, DepartmentService departmentService, DepartmentCoordinatorService departmentCoordinatorService,
                                    ErasmusUniversityService erasmusUniversityService, ErasmusUniversityDepartmentService erasmusUniversityDepartmentService,
                                    AdministrativeStaffService administrativeStaffService, OutgoingStudentRepository outgoingStudentRepository,
                                    ExchangeUniversityService exchangeUniversityService, ExchangeUniversityDepartmentService exchangeUniversityDepartmentService,
                                    IncomingStudentService incomingStudentService, CourseCoordinatorService courseCoordinatorService, LanguageRepository languageRepository,
                                    AdminService adminService, HostUniversityService hostUniversityService) {
        this.courseService = courseService;
        this.departmentService = departmentService;
        this.departmentCoordinatorService = departmentCoordinatorService;
        this.erasmusUniversityService = erasmusUniversityService;
        this.erasmusUniversityDepartmentService = erasmusUniversityDepartmentService;
        this.administrativeStaffService = administrativeStaffService;
        this.outgoingStudentRepository = outgoingStudentRepository;
        this.exchangeUniversityService = exchangeUniversityService;
        this.exchangeUniversityDepartmentService = exchangeUniversityDepartmentService;
        this.incomingStudentService = incomingStudentService;
        this.courseCoordinatorService = courseCoordinatorService;
        this.languageRepository = languageRepository;
        this.adminService = adminService;
        this.hostUniversityService = hostUniversityService;
    }

    @GetMapping
    public String initialize() {

        //Host University
        HostUniversity BilkentUniv = new HostUniversity();
        BilkentUniv.setWaitingQueue(new ArrayList<>());
        BilkentUniv.setUniversityName("Bilkent University");
        hostUniversityService.addHostUniversity(BilkentUniv);
        //Admin
        Admin admin = new Admin();
        admin.setEmail("korhan@gmail.com");
        hashingPasswordHelper.setPassword("admin");
        admin.setHashedPassword(hashingPasswordHelper.Hash());
        adminService.addAdmin(admin);

        //CS Courses
        Course course1 = new Course();
        course1.setCourseName("CS-101");
        course1.setDescription("Algorithms and Programming I");
        course1.setEcts(6.5);
        courseService.addNewCourse(course1);

        Course course2 = new Course();
        course2.setCourseName("CS-102");
        course2.setDescription("Algorithms and Programming II");
        course2.setEcts(6.5);
        courseService.addNewCourse(course2);

        Course course3 = new Course();
        course3.setCourseName("CS-201");
        course3.setDescription("Fundamental Structures of Computer Science I");
        course3.setEcts(5.0);
        courseService.addNewCourse(course3);

        Course course4 = new Course();
        course4.setCourseName("CS-202");
        course4.setDescription("Fundamental Structures of Computer Science II");
        course4.setEcts(5.0);
        courseService.addNewCourse(course4);

        Course course5 = new Course();
        course5.setCourseName("CS-319");
        course5.setDescription("Object-Oriented Software Engineering");
        course5.setEcts(6.5);
        courseService.addNewCourse(course5);

        Course course6 = new Course();
        course6.setCourseName("CS-353");
        course6.setDescription("Database Systems");
        course6.setEcts(5.0);
        courseService.addNewCourse(course6);

        Course course7 = new Course();
        course7.setCourseName("CS-342");
        course7.setDescription("Database Systems");
        course7.setEcts(6.5);
        courseService.addNewCourse(course7);

        // CS ELECTIVES
        Course course8 = new Course();
        course8.setCourseName("ADA-265");
        course8.setDescription("How Houses Build People");
        course8.setEcts(5.0);
        courseService.addNewCourse(course8);

        Course course9 = new Course();
        course9.setCourseName("PSYC-100");
        course9.setDescription("Introduction to Psychology");
        course9.setEcts(5.0);
        courseService.addNewCourse(course9);

        // ADD THE CS DEPARTMENT
        List<Course> csCourseList = new ArrayList<>();
        csCourseList.add(course1);
        csCourseList.add(course2);
        csCourseList.add(course3);
        csCourseList.add(course4);
        csCourseList.add(course5);
        csCourseList.add(course6);
        csCourseList.add(course7);
        List<Course> csElectiveCourseList = new ArrayList<>();
        csElectiveCourseList.add(course8);
        csElectiveCourseList.add(course9);
        Department cs = new Department();
        cs.setDepartmentName("CS");
        cs.setCourseList(csCourseList);
        cs.setElectiveCourseList(csElectiveCourseList);
        departmentService.addDepartment(cs);

        // ADD DEPARTMENT COORDINATOR FOR CS
        DepartmentCoordinator csDepartmentCoordinator = new DepartmentCoordinator();
        csDepartmentCoordinator.setName("Can Alkan");
        csDepartmentCoordinator.setEmail("calkan@cs.bilkent.edu.tr");
        hashingPasswordHelper.setPassword("123");
        csDepartmentCoordinator.setHashedPassword(hashingPasswordHelper.Hash());
        csDepartmentCoordinator.setDepartment(cs);
        departmentCoordinatorService.addDepartmentCoordinator(csDepartmentCoordinator);

        //MAN Courses
        Course man1 = new Course();
        man1.setCourseName("MAN-101");
        man1.setDescription("Introduction to Management");
        man1.setEcts(6.0);
        courseService.addNewCourse(man1);

        Course man2 = new Course();
        man2.setCourseName("MAN-231");
        man2.setDescription("Accounting Principles");
        man2.setEcts(6.0);
        courseService.addNewCourse(man2);

        Course man3 = new Course();
        man3.setCourseName("MAN-102");
        man3.setDescription("Fundamentals of Management");
        man3.setEcts(5.0);
        courseService.addNewCourse(man3);

        Course man4 = new Course();
        man4.setCourseName("MAN-211");
        man4.setDescription("Financial Management");
        man4.setEcts(4.0);
        courseService.addNewCourse(man4);

        Course man5 = new Course();
        man5.setCourseName("MAN-311");
        man5.setDescription("Assets and Profit");
        man5.setEcts(6.0);
        courseService.addNewCourse(man5);

        Course man6 = new Course();
        man6.setCourseName("MAN-401");
        man6.setDescription("Banks & Accounting");
        man6.setEcts(5.5);
        courseService.addNewCourse(man6);
        //MAN Department
        List<Course> manCourseList = new ArrayList<>();
        manCourseList.add(man1);
        manCourseList.add(man2);
        manCourseList.add(man3);
        manCourseList.add(man4);
        manCourseList.add(man5);
        manCourseList.add(man6);
        List<Course> manElectives = new ArrayList<>();
       // manElectives.add(course8);
       // manElectives.add(course9);

        Department man = new Department();
        man.setDepartmentName("MAN");
        man.setCourseList(manCourseList);
        man.setElectiveCourseList(manElectives);
        departmentService.addDepartment(man);

        //Department Coordinator of Man
        DepartmentCoordinator manDepartmentCoordinator = new DepartmentCoordinator();
        manDepartmentCoordinator.setName("Ceren Aydogmus");
        manDepartmentCoordinator.setEmail("caydogmus@bilkent.edu.tr ");
        hashingPasswordHelper.setPassword("123");
        manDepartmentCoordinator.setHashedPassword(hashingPasswordHelper.Hash());
        manDepartmentCoordinator.setDepartment(man);
        departmentCoordinatorService.addDepartmentCoordinator(manDepartmentCoordinator);
        // IE DEPARTMENT
        Course ie1 = new Course();
        ie1.setCourseName("IE-102");
        ie1.setDescription("A Process Outlook for Industrial Engineering");
        ie1.setEcts(5.0);
        courseService.addNewCourse(ie1);

        Course ie2 = new Course();
        ie1.setCourseName("IE-272");
        ie1.setDescription("Manufacturing Processes and Operations Analysis");
        ie1.setEcts(6.5);
        courseService.addNewCourse(ie2);

        Course ie3 = new Course();
        ie3.setCourseName("IE-202");
        ie3.setDescription("Introduction to Modeling and Optimization");
        ie3.setEcts(6.5);
        courseService.addNewCourse(ie3);

        Course ie4 = new Course();
        ie1.setCourseName("IE-342");
        ie1.setDescription("Engineering Economic Analysis");
        ie1.setEcts(5.0);
        courseService.addNewCourse(ie4);

        Course ie5 = new Course();
        ie5.setCourseName("IE-342");
        ie5.setDescription("Engineering Economic Analysis");
        ie5.setEcts(5.0);
        courseService.addNewCourse(ie5);

        Course ie6 = new Course();
        ie6.setCourseName("IE-303");
        ie6.setDescription("Modeling and Methods in Optimization");
        ie6.setEcts(5.0);
        courseService.addNewCourse(ie6);

        Course ie7 = new Course();
        ie7.setCourseName("IE-325");
        ie7.setDescription("Stochastic Models");
        ie7.setEcts(5.0);
        courseService.addNewCourse(ie7);

        // IE ELECTIVES
        Course ieEl1 = new Course();
        ieEl1.setCourseName("ADA-265");
        ieEl1.setDescription("How Houses Build People");
        ieEl1.setEcts(5.0);
        courseService.addNewCourse(ieEl1);

        Course ieEl2 = new Course();
        ieEl2.setCourseName("PSYC-100");
        ieEl2.setDescription("Introduction to Psychology");
        ieEl2.setEcts(5.0);
        courseService.addNewCourse(ieEl2);

        // add the ie courses
        List<Course> ieCourseList = new ArrayList<>();
        ieCourseList.add(ie1);
        ieCourseList.add(ie2);
        ieCourseList.add(ie3);
        ieCourseList.add(ie4);
        ieCourseList.add(ie5);
        ieCourseList.add(ie6);
        ieCourseList.add(ie7);
        List<Course> ieElectiveCourseList = new ArrayList<>();
        ieElectiveCourseList.add(ieEl1);
        ieElectiveCourseList.add(ieEl2);

        // add the ie department
        Department ie = new Department();
        ie.setDepartmentName("IE");
        ie.setCourseList(ieCourseList);
        ie.setElectiveCourseList(ieElectiveCourseList);
        departmentService.addDepartment(ie);

        // ADD DEPARTMENT COORDINATOR FOR IE
        DepartmentCoordinator ieDepartmentCoordinator = new DepartmentCoordinator();
        ieDepartmentCoordinator.setName("Emre Uzun");
        ieDepartmentCoordinator.setEmail("emreu@bilkent.edu.tr");
        hashingPasswordHelper.setPassword("123");
        ieDepartmentCoordinator.setHashedPassword(hashingPasswordHelper.Hash());
        ieDepartmentCoordinator.setDepartment(ie);
        departmentCoordinatorService.addDepartmentCoordinator(ieDepartmentCoordinator);


        // erasmus universities

        // EPFL
        Course erasmusCourse1 = new Course();
        erasmusCourse1.setCourseName("CS-307");
        erasmusCourse1.setDescription("Introduction to multiprocessor architecture");
        erasmusCourse1.setEcts(4.0);
        courseService.addNewCourse(erasmusCourse1);

        Course erasmusCourse2 = new Course();
        erasmusCourse2.setCourseName("CS-323");
        erasmusCourse2.setDescription("Introduction to operating systems");
        erasmusCourse2.setEcts(5.0);
        courseService.addNewCourse(erasmusCourse2);

        ErasmusUniversity epfl = new ErasmusUniversity();
        epfl.setUniversityName("EPFL");
        epfl.setCountry("Switzerland");
        List<Course> epflCourses = new ArrayList<>();
        epflCourses.add(erasmusCourse1);
        epflCourses.add(erasmusCourse2);
        epfl.setRejectedCourses(epflCourses);
        epfl.setAcceptedStudents(new ArrayList<>());
        erasmusUniversityService.addErasmusUniversity(epfl);

        // EPFL CS DEPARTMENT
        ErasmusUniversityDepartment epflCS = new ErasmusUniversityDepartment();
        epflCS.setDepartmentName("CS");
        epflCS.setQuota(2);
        epflCS.setMaxQuota(2);
        epflCS.setErasmusUniversity(epfl);
        // might add courses later on
        erasmusUniversityDepartmentService.addErasmusUniversityDepartment(epflCS);

        //ETH
        Course ethCS1  = new Course();
        ethCS1.setCourseName("CS-101");
        ethCS1.setDescription("Introduction to Programming");
        ethCS1.setEcts(6.0);
        courseService.addNewCourse(ethCS1);

        Course ethCS2  = new Course();
        ethCS2.setCourseName("CS-102");
        ethCS2.setDescription("Programming and Algorithms");
        ethCS2.setEcts(6.0);
        courseService.addNewCourse(ethCS2);


        Course ethCS3 = new Course();
        ethCS3.setCourseName("CS-461");
        ethCS3.setDescription("Computer Vision");
        ethCS3.setEcts(5.5);
        courseService.addNewCourse(ethCS3);

        ErasmusUniversity eth = new ErasmusUniversity();
        eth.setUniversityName("ETH Zurich");
        eth.setCountry("Switzerland");
        List<Course> ethCourses = new ArrayList<>();
        ethCourses.add(ethCS1);
        ethCourses.add(ethCS2);
        ethCourses.add(ethCS3);
        eth.setRejectedCourses(ethCourses);
        eth.setAcceptedStudents(new ArrayList<>());
        erasmusUniversityService.addErasmusUniversity(eth);

        ErasmusUniversityDepartment ethCS = new ErasmusUniversityDepartment();
        ethCS.setQuota(2);
        ethCS.setErasmusUniversity(eth);
        ethCS.setDepartmentName("CS");
        ethCS.setMaxQuota(2);
        erasmusUniversityDepartmentService.addErasmusUniversityDepartment(ethCS);

        Course ethCS4 = new Course();
        ethCS4.setCourseName("CS-331");
        ethCS4.setDescription("Computer Networks");
        ethCS4.setEcts(5.5);

        Course ethCS5 = new Course();
        ethCS5.setCourseName("CS-400");
        ethCS5.setDescription("Automata Theory and Turing Machines");
        ethCS5.setEcts(5.0);

        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( ethCS4,ethCS.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( ethCS5,ethCS.getID());

        //Vrije
        Course vrijeCS1  = new Course();
        vrijeCS1.setCourseName("CS-101");
        vrijeCS1.setDescription("Introduction to Programming");
        vrijeCS1.setEcts(6.0);

        Course vrijeCS2 = new Course();
        vrijeCS2.setCourseName("CS-102");
        vrijeCS2.setDescription("Algorithms");
        vrijeCS2.setEcts(6.0);
        courseService.addNewCourse(vrijeCS2);


        Course vrijeCS3 = new Course();
        vrijeCS3.setCourseName("CS-461");
        vrijeCS3.setDescription("Machine Learning");
        vrijeCS3.setEcts(5.5);
        courseService.addNewCourse(vrijeCS3);

        ErasmusUniversity vrije = new ErasmusUniversity();
        vrije.setUniversityName("Vrije University");
        vrije.setCountry("Holland");
        List<Course> vrijeCourses = new ArrayList<>();
        vrijeCourses.add(vrijeCS2);
        vrijeCourses.add(vrijeCS3);
        vrije.setRejectedCourses(vrijeCourses);
        eth.setAcceptedStudents(new ArrayList<>());
        erasmusUniversityService.addErasmusUniversity(vrije);

        ErasmusUniversityDepartment vrijeCS = new ErasmusUniversityDepartment();
        vrijeCS.setQuota(3);
        vrijeCS.setErasmusUniversity(vrije);
        vrijeCS.setDepartmentName("CS");
        vrijeCS.setMaxQuota(3);
        erasmusUniversityDepartmentService.addErasmusUniversityDepartment(vrijeCS);

        Course vrijeCS4 = new Course();
        vrijeCS4.setCourseName("CS-331");
        vrijeCS4.setDescription("Computer Architecture");
        vrijeCS4.setEcts(5.5);

        Course vrijeCS5 = new Course();
        vrijeCS5.setCourseName("CS-400");
        vrijeCS5.setDescription("Data Structures");
        vrijeCS5.setEcts(5.0);

        Course vrijeCS6 = new Course();
        vrijeCS6.setCourseName("CS-105");
        vrijeCS6.setDescription("Python for Beginners");
        vrijeCS6.setEcts(5.0);

        Course vrijeCS7 = new Course();
        vrijeCS7.setCourseName("CS-400");
        vrijeCS7.setDescription("Data Engineering for Social Sciences");
        vrijeCS7.setEcts(3.5);

        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID(vrijeCS1, vrijeCS.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( vrijeCS4,vrijeCS.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( vrijeCS5,vrijeCS.getID());
        erasmusUniversityDepartmentService.addElectiveCourseByErasmusDepartmentID( vrijeCS7, vrijeCS.getID() );
        erasmusUniversityDepartmentService.addElectiveCourseByErasmusDepartmentID( vrijeCS6, vrijeCS.getID() );
        //Roskilde University
        //Vrije
        Course roskCS1  = new Course();
        roskCS1.setCourseName("CS-101");
        roskCS1.setDescription("How to Be a Programmer");
        roskCS1.setEcts(6.0);

        Course roskCS2 = new Course();
        roskCS2.setCourseName("CS-102");
        roskCS2.setDescription("Algorithms of Life");
        roskCS2.setEcts(6.0);
        courseService.addNewCourse(roskCS2);


        Course roskCS3 = new Course();
        roskCS3.setCourseName("CS-461");
        roskCS3.setDescription("Deep Learning");
        roskCS3.setEcts(5.5);
        courseService.addNewCourse(roskCS3);

        ErasmusUniversity roskilde = new ErasmusUniversity();
        vrije.setUniversityName("Roskilde University");
        roskilde.setCountry("Denmark");
        Language Danish = new Language();
        Danish.setLanguage("Danish");
        Danish.setLevel("B2");
        languageRepository.save(Danish);
        roskilde.setLanguageRequirement(Danish);
        List<Course> roskildeCourses = new ArrayList<>();
        roskildeCourses.add(roskCS2);
        roskildeCourses.add(roskCS3);
        roskilde.setRejectedCourses(roskildeCourses);
        roskilde.setAcceptedStudents(new ArrayList<>());
        erasmusUniversityService.addErasmusUniversity(roskilde);
        //Roskilde CS
        ErasmusUniversityDepartment roskCS = new ErasmusUniversityDepartment();
        roskCS.setQuota(4);
        roskCS.setErasmusUniversity(roskilde);
        roskCS.setDepartmentName("CS");
        roskCS.setMaxQuota(4);
        erasmusUniversityDepartmentService.addErasmusUniversityDepartment(roskCS);

        Course roskCS4 = new Course();
        roskCS4.setCourseName("CS-331");
        roskCS4.setDescription("Computer Architecture");
        roskCS4.setEcts(5.5);

        Course roskCS5 = new Course();
        roskCS5.setCourseName("CS-400");
        roskCS5.setDescription("Data Structures");
        roskCS5.setEcts(5.0);

        Course roskCS6 = new Course();
        roskCS6.setCourseName("CS-105");
        roskCS6.setDescription("Python for Beginners");
        roskCS6.setEcts(5.0);

        Course roskCS7 = new Course();
        roskCS7.setCourseName("CS-400");
        roskCS7.setDescription("Data Engineering for Social Sciences");
        roskCS7.setEcts(3.5);

        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID(roskCS1, roskCS.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( roskCS4,roskCS.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( roskCS5,roskCS.getID());
        erasmusUniversityDepartmentService.addElectiveCourseByErasmusDepartmentID( roskCS7, roskCS.getID() );
        erasmusUniversityDepartmentService.addElectiveCourseByErasmusDepartmentID( roskCS6, roskCS.getID() );

        //Roskilde IE
        ErasmusUniversityDepartment roskIE = new ErasmusUniversityDepartment();
        roskIE.setQuota(4);
        roskIE.setErasmusUniversity(roskilde);
        roskIE.setDepartmentName("IE");
        roskIE.setMaxQuota(4);
        erasmusUniversityDepartmentService.addErasmusUniversityDepartment(roskIE);

        Course roskIE1 = new Course();
        roskIE1.setCourseName("IE-331");
        roskIE1.setDescription("Industrial Architecture");
        roskIE1.setEcts(5.5);

        Course roskIE2 = new Course();
        roskIE2.setCourseName("IE-400");
        roskIE2.setDescription("Data Optimization");
        roskIE2.setEcts(5.0);

        Course roskIE3 = new Course();
        roskIE3.setCourseName("IE-105");
        roskIE3.setDescription("Excel for Beginners");
        roskIE3.setEcts(5.0);

        Course roskIE4 = new Course();
        roskIE4.setCourseName("IE-440");
        roskIE4.setDescription("Data Science");
        roskIE4.setEcts(3.5);

        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID(roskIE3, roskIE.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( roskIE4,roskIE.getID());
        erasmusUniversityDepartmentService.addCourseByErasmusDepartmentID( roskIE1,roskIE.getID());
        erasmusUniversityDepartmentService.addElectiveCourseByErasmusDepartmentID( roskIE2, roskIE.getID() );



        //Exchange University
        //Queen's Univ
        ExchangeUniversity queens = new ExchangeUniversity();
        queens.setCountry("Canada");
        queens.setUniversityName("Queen's University, Kingston");
        queens.setMaxUniversityQuota(4);
        queens.setUniversityQuota(4);
        queens.setAcceptedStudents(new ArrayList<>() );

        Course rejectQueen1 = new Course();
        rejectQueen1.setEcts(5.0);
        rejectQueen1.setCourseName("IE-202");
        rejectQueen1.setDescription("Optimization");
        courseService.addNewCourse(rejectQueen1);


        Course rejectQueen2 = new Course();
        rejectQueen2.setEcts(3.0);
        rejectQueen2.setCourseName("IE-303");
        rejectQueen2.setDescription("Stochastic Models");
        courseService.addNewCourse(rejectQueen2);

        List<Course> rejectedQueen = new ArrayList<>();
        rejectedQueen.add(rejectQueen1);
        rejectedQueen.add(rejectQueen2);

        queens.setRejectedCourses(rejectedQueen);
        exchangeUniversityService.addExchangeUniversity(queens);

        ExchangeUniversityDepartment queensIE = new ExchangeUniversityDepartment();
        queensIE.setDepartmentName("IE");
        queensIE.setExchangeUniversity(queens);
        exchangeUniversityDepartmentService.addExchangeUniversityDepartment(queensIE);

        Course acceptedQueen1 = new Course();
        acceptedQueen1.setEcts(4.5);
        acceptedQueen1.setCourseName("IE-410");
        acceptedQueen1.setDescription("Game Theory");

        Course acceptedQueen2 = new Course();
        acceptedQueen2.setEcts(5.0);
        acceptedQueen2.setCourseName("IE-341");
        acceptedQueen2.setDescription("Industrial Revolution");


        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(acceptedQueen1,queensIE.getID());
        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(acceptedQueen2,queensIE.getID());

        //Seoul University
        ExchangeUniversity seoul = new ExchangeUniversity();
        seoul.setCountry("South Korea");
        seoul.setUniversityName("Seoul National University");
        seoul.setUniversityQuota(7);
        seoul.setMaxUniversityQuota(7);
        seoul.setAcceptedStudents(new ArrayList<>());
        Language Korean = new Language();

        Korean.setLanguage("Korean");
        Korean.setLevel("B1");
        languageRepository.save(Korean);

        seoul.setLanguageRequirement(Korean);

        Course rejectSeoul1 = new Course();
        rejectSeoul1.setEcts(6.0);
        rejectSeoul1.setCourseName("ME-101");
        rejectSeoul1.setDescription("Introduction to Machines");
        courseService.addNewCourse(rejectSeoul1);

        Course rejectSeoul2 = new Course();
        rejectSeoul2.setEcts(6.0);
        rejectSeoul2.setCourseName("ME-311");
        rejectSeoul2.setDescription("Fluid Things");
        courseService.addNewCourse(rejectSeoul2);

        List<Course> rejectedSeoul = new ArrayList<>();
        rejectedSeoul.add(rejectSeoul1);
        rejectedSeoul.add(rejectSeoul2);
        seoul.setRejectedCourses(rejectedSeoul);


        exchangeUniversityService.addExchangeUniversity(seoul);
        exchangeUniversityService.addLanguageRequirementToExchangeUniversityByExchangeUniversityID(seoul.getID(), Korean);

        ExchangeUniversityDepartment seoulME = new ExchangeUniversityDepartment();
        seoulME.setDepartmentName("ME");
        seoulME.setExchangeUniversity(seoul);
        exchangeUniversityDepartmentService.addExchangeUniversityDepartment(seoulME);

        ExchangeUniversityDepartment seoulEEE = new ExchangeUniversityDepartment();
        seoulEEE.setDepartmentName("EEE");
        seoulEEE.setExchangeUniversity(seoul);
        exchangeUniversityDepartmentService.addExchangeUniversityDepartment(seoulEEE);


        Course seoulME1 = new Course();
        seoulME1.setEcts(4.5);
        seoulME1.setCourseName("ME-303");
        seoulME1.setDescription("Robotics");

        Course seoulME2 = new Course();
        seoulME2.setEcts(5.5);
        seoulME2.setCourseName("ME-444");
        seoulME2.setDescription("Humandroids");

        Course seoulEEE1 = new Course();
        seoulEEE1.setEcts(4.5);
        seoulEEE1.setCourseName("EEE-102");
        seoulEEE1.setDescription("Analog Circuits");

        Course seoulEEE2 = new Course();
        seoulEEE2.setEcts(5.0);
        seoulEEE2.setCourseName("EEE-312");
        seoulEEE2.setDescription("Signals and Systems");


        Course seoulEEEE = new Course();
        seoulEEEE.setEcts(5.0);
        seoulEEE2.setCourseName("EEE-391");
        seoulEEE2.setDescription("Signals and Systems for Computer Scientists");


        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(seoulME1,seoulME.getID());
        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(seoulME2,seoulME.getID());
        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(seoulEEE1,seoulEEE.getID());
        exchangeUniversityDepartmentService.addCourseByExchangeDepartmentID(seoulEEE2,seoulEEE.getID());
        exchangeUniversityDepartmentService.addElectiveCourseByExchangeDepartmentID(seoulEEEE, seoulEEE.getID());

        // applications
        ApplicationWrapper awp1 = new ApplicationWrapper();
        awp1.setCgpa(3.80);
        awp1.setFirstName("Alperen");
        awp1.setLastName("Gozeten");
        awp1.setSelectedSemester("fall");
        awp1.setStudentId(21902464L);
        awp1.setTotalPoint(99);
        List<String> unis1 = new ArrayList<>();
        unis1.add("EPFL");
        unis1.add("ETH Zurich");
        awp1.setSelectedUniversities(unis1);

        ApplicationWrapper awp2 = new ApplicationWrapper();
        awp2.setCgpa(3.90);
        awp2.setFirstName("Kursad");
        awp2.setLastName("Guzelkaya");
        awp2.setSelectedSemester("fall");
        awp2.setStudentId(21902464L);
        awp2.setTotalPoint(98);
        List<String> unis2 = new ArrayList<>();
        unis2.add("ETH Zurich");
        awp2.setSelectedUniversities(unis2);

        // Erasmus CS
        List<ApplicationWrapper> applications = new ArrayList<>();
        applications.add(awp1);
        applications.add(awp2);
        administrativeStaffService.addStudents(true, 1L, applications); // CS Applications

        OutgoingStudent student1 = outgoingStudentRepository.findById(3L).get();
        OutgoingStudent student2 = outgoingStudentRepository.findById(4L).get();

        hashingPasswordHelper.setPassword("123");
        student1.setHashedPassword(hashingPasswordHelper.Hash()); // make passwords 123

        hashingPasswordHelper.setPassword("123");
        student2.setHashedPassword(hashingPasswordHelper.Hash());

        //Administrative Staff
        AdministrativeStaff yelda  = new AdministrativeStaff();
        yelda.setEmail("yeldaates@bilkent.edu.tr");
        yelda.setName("Yelda Irem Ates");
        hashingPasswordHelper.setPassword("123");
        List<Department> departmentsOfYelda = new ArrayList<>();
        departmentsOfYelda.add(cs);
        departmentsOfYelda.add(man);
        yelda.setHashedPassword(hashingPasswordHelper.Hash());
        yelda.setDepartments(departmentsOfYelda);
        administrativeStaffService.addAdministrativeStaff(yelda);

        //Course Coordinator
        CourseCoordinator eray = new CourseCoordinator();
        eray.setName("Eray Tuzun");
        eray.setDepartment(cs);
        List<Course> erayCourses = new ArrayList<>();
        erayCourses.add(course5);
        eray.setCourseList(erayCourses);
        eray.setEmail("eraytuzun@cs.bilkent.edu.tr");
        hashingPasswordHelper.setPassword("123");
        eray.setHashedPassword(hashingPasswordHelper.Hash());
        courseCoordinatorService.addCourseCoordinator(eray);

        CourseCoordinator selim = new CourseCoordinator();
        selim.setName("Selim Aksoy");
        selim.setDepartment(cs);
        List<Course> selimCourses = new ArrayList<>();
        selimCourses.add(course3);
        selimCourses.add(course4);
        selim.setCourseList(selimCourses);
        selim.setEmail("saksoy@cs.bilkent.edu.tr");
        hashingPasswordHelper.setPassword("123");
        selim.setHashedPassword(hashingPasswordHelper.Hash());
        courseCoordinatorService.addCourseCoordinator(selim);

        CourseCoordinator orsan = new CourseCoordinator();
        orsan.setName("Orsan Orge");
        orsan.setDepartment(man);
        List<Course> orsanCourses = new ArrayList<>();
        orsanCourses.add(man1);
        orsanCourses.add(man4);
        orsanCourses.add(man6);
        orsan.setCourseList(orsanCourses);
        orsan.setEmail("orsan@bilkent.edu.tr");
        hashingPasswordHelper.setPassword("123");
        orsan.setHashedPassword(hashingPasswordHelper.Hash());
        courseCoordinatorService.addCourseCoordinator(orsan);

       //Incoming Students
        IncomingStudent bond = new IncomingStudent();
        bond.setName("James Bond");
        bond.setStudentId(22101456L);

        bond.setEmail("james@mail.com");
        hashingPasswordHelper.setPassword("123");
        bond.setHashedPassword(hashingPasswordHelper.Hash());

        bond.setContractedUniversity(epfl);
        bond.setSemester("Spring");
        bond.setDepartment(cs);

        List<Course> prefOfBond = new ArrayList<>();
        prefOfBond.add(course1);
        prefOfBond.add(course3);
        prefOfBond.add(course5);
        prefOfBond.add(course8);
        bond.setPreferredCourses(prefOfBond);
        incomingStudentService.addIncomingStudent(bond);
        //Kendall
        IncomingStudent kendall = new IncomingStudent();
        kendall.setName("Kendall Jenner");
        kendall.setStudentId(22103357L);

        kendall.setEmail("kendal@mail.com");

        hashingPasswordHelper.setPassword("123");
        kendall.setHashedPassword(hashingPasswordHelper.Hash());

        kendall.setContractedUniversity(queens);
        kendall.setSemester("Fall");
        kendall.setDepartment(man);

        List<Course> prefOfKendall = new ArrayList<>();
        prefOfKendall.add(man1);
        prefOfKendall.add(man2);
        prefOfKendall.add(man3);
        prefOfKendall.add(man6);
        prefOfKendall.add(course9);
        kendall.setPreferredCourses(prefOfKendall);
        incomingStudentService.addIncomingStudent(kendall);

        return "Initialized Successfully!";
    }
}
