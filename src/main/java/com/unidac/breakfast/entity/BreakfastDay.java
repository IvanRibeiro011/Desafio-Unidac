package com.unidac.breakfast.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_breakfast")
public class BreakfastDay implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ManyToMany
    @JoinTable(name = "tb_breakfast_collaborator",
            joinColumns = @JoinColumn(name = "breakfast_id"),
            inverseJoinColumns = @JoinColumn(name = "collaborator_id"))
    private Set<User> collaborators = new HashSet<>();
    @OneToMany(mappedBy = "breakfast", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BreakfastItem> items = new ArrayList<>();
}
