package com.codecool.vizsgaremek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "EMP_ID")
   private long id;

   @Column(name = "NAME")
   private String name;

   @Column(name = "BIRTH_DATE")
   private LocalDate birthDate;

   // note: further properties can be specified here in the future

   @ElementCollection
   @CollectionTable(name = "DAILY_ATTENDANCE", joinColumns = @JoinColumn(name = "EMP_ID"))
   @MapKeyColumn(name = "DATE")
   @AttributeOverride(name = "entryTime", column = @Column(name = "ENTRY_TIME"))
   @AttributeOverride(name = "exitTime", column = @Column(name = "EXIT_TIME"))
   private Map<LocalDate, Attendance> dailyAttendances = new HashMap<>();

}
