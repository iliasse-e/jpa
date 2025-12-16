package fr.spoonless.javaee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Calendar;

import fr.spoonless.javaee.entities.Individu;

public class App {
  public static void main(String[] args) {

    // Va intérroger persistence.xml <persistence-unit name="individu-unit"> pour créer une connexion à la db
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("individu-unit");

    EntityManager entityManager = emf.createEntityManager();

    Individu individu = new Individu();
    individu.setPrenom("Mohand");
    individu.setNom("Tazerout");
    individu.setAge(32);

    individu.setDateCreation(Calendar.getInstance());
    
    Calendar creationDate = Calendar.getInstance();
    creationDate.set(1972, 11, 12);
    individu.setDateDeNaissance(creationDate);

    try {
      entityManager.getTransaction().begin();
      entityManager.persist(individu);
      entityManager.getTransaction().commit();
    }
    finally {
      entityManager.close();
      emf.close();
    }
        
  }
}
