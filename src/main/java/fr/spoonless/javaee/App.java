package fr.spoonless.javaee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import fr.spoonless.javaee.entities.Individu;

public class App {
  public static void main(String[] args) {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("individu-unit"); // Va intérroger persistence.xml <persistence-unit name="individu-unit">

    EntityManager entityManager = emf.createEntityManager();

    Individu individu = new Individu();
    individu.setPrenom("Mohand");
    individu.setNom("Tazerout");

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
