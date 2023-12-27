package com.unidac.breakfast.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "name")
@Entity(name = "tb_items")
public class BreakfastItem implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean missing;
    @ManyToOne
    @JoinColumn(name = "collaborator_id")
    private User collaborator;
    @ManyToOne
    @JoinColumn(name = "breakfast_id")
    private BreakfastDay breakfast;
}
