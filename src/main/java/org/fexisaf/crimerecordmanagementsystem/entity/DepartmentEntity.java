//package org.fexisaf.crimerecordmanagementsystem.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Inheritance(strategy = InheritanceType.JOINED)
//public abstract class DepartmentEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "department_name")
//    private String departmentName;
//
//    // Constructors, getters, and setters can be added as needed
//
////    @Builder // Ensure to have a builder in the superclass
////    public static abstract class DepartmentEntityBuilder<D extends DepartmentEntity, B extends DepartmentEntityBuilder<D, B>> {
////        // Define methods in the builder if needed
////    }
//}
//
