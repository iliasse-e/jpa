package fr.spoonless.javaee.entities;

import java.util.Calendar;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "individu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Individu {

  @Id
  @Column(name = "individuId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Basic // Mapping simple (varchar pour une String)
  @Column(length = 40, nullable = false)
  private String nom;

  @Basic
  @Column(length = 40, nullable = false)
  private String prenom;

  @Transient // Indique qu’un attribut ne doit pas être persistant. Donc jamais pris en compte
  private Integer age;

  @Temporal(TemporalType.DATE)
  private Calendar dateDeNaissance;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Calendar dateCreation;

  @Lob // Indique que la colonne correspond à un Large Object 
  @Basic(fetch = FetchType.LAZY)
  private byte[] image;

}