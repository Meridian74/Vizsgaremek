package com.codecool.vizsgaremek.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "ATTENDANCES")
public class Attendance {

   private LocalDateTime entryTime;
   private LocalDateTime exitTime;

   @Embedded
   @AttributeOverride(name = "id", column = @Column(name = "SHIFT_ID"))
   private Shift currentDailyShift;

}
