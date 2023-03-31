package com.aistgroup.cinemarestfullapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "halls")
public class Hall {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer maxSeats;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cinema_id", nullable = false)
  private Cinema cinema;

  @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
  private List<Session> sessions;

  @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
  private List<Seat> seats;

}
