# Les ORM

Les ORM sont des frameworks qui, comme l’indique leur nom, permettent de créer une correspondance entre un modèle objet et un modèle relationnel de base de données. Un ORM fournit généralement les fonctionnalités suivantes :

  - génération à la volée des requêtes SQL les plus simples (CRUD)

  - prise en charge des dépendances entre objets pour la mise en jour en cascade de la base de données

  - support pour la construction de requêtes complexes par programmation

Java EE fournit une API standard pour l’utilisation d’un ORM : JPA (Java Persistence API) (la JSR-338). Il existe plusieurs implémentations open source qui respectent l’API JPA : EclipseLink (qui est aussi l’implémentation de référence), Hibernate (JBoss - Red Hat), OpenJPA (Apache).

Toutes ces implémentations sont bâties sur JDBC. Nous retrouverons donc les notions de pilote, de data source et d’URL de connexion lorsqu’il s’agira de configurer l’accès à la base de données.

## Les entités JPA

JPA permet de définir des entités (entities). Une entité est simplement une instance d’une classe qui sera persistante (que l’on pourra sauvegarder dans / charger depuis une base de données relationnelle). Une entité est signalée par l’annotation `@Entity` sur la classe. De plus, une entité JPA doit disposer d’un ou plusieurs attributs définissant un identifiant grâce à l’annotation `@Id` (correspond à la clé primaire dans la table associée).

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Individu {

    @Id
    // Permet de définir la statégie de génération
    // de la clé lors d'une insertion en base de données.
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

}
```

*Il existe un grand nombre d’annotations JPA servant à préciser comment la correspondance doit être faite entre le modèle objet et le modèle relationnel de base de données. Il est possible de déclarer cette correspondance à l’aide du fichier `orm.xml`. Cependant, la plupart de développeurs préfèrent utiliser des annotations.*

[Pour accéder à une liste d'annotations JPA](https://gayerie.dev/docs/jakartaee/javaee_orm/jpa_entites.html#id1)

## EntityManager

Dans JPA, l’interface centrale qui va exploiter ces annotations est l’interface EntityManager.
Sans cela, les annotations seraient inutiles.

### Obtenir un EntityManager

JPA est une spécification. Pour pouvoir l’utiliser, il faut avoir à sa disposition une implémentation compatible avec JPA. Dans le cadre de ce cours, nous utiliserons Hibernate.

```xml
<dependency>
  <groupId>org.hibernate</groupId>
  <artifactId>hibernate-core</artifactId>
  <version>5.4.9.Final</version>
</dependency>
```

Il faut fournir à l’implémentation de JPA un fichier XML de déploiement nommé `persistence.xml`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
             http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">
  <persistence-unit name="monUniteDePersistance">
    <!-- la liste des noms complets des classes représentant
         les entités gérées par cette unité de persistance  -->
    <class>ma.classe.Entite</class>
    <properties>
      <!-- une propriété de configuration propre à l'implémentation de JPA -->
      <property name="une propriété" value="une valeur" />
    </properties>
  </persistence-unit>
</persistence>
```

On va ajouter dans le fichier :
```xml
<class>fr.spoonless.javaee.entities.Individu</class>
```

*Le fichier `persistence.xml` doit se situer dans le répertoire META-INF et être disponible dans le classpath à l’exécution. Dans un projet Maven, il suffit de créer ce fichier dans le répertoire src/main/resources/META-INF du projet (créez les répertoires manquants si nécessaire).*

Pour obtenir une instance de `EntityManager`, il faut utiliser la classe `Persistence`. Grâce à cette classe, nous allons pouvoir créer une instance de `EntityManagerFactory`. Cette dernière, comme son nom l’indique, permet de fabriquer une instance de `EntityManager`.

```java
// on spécifie le nom de l'unité de persistence en paramètre
EntityManagerFactory emf = Persistence.createEntityManagerFactory("monUniteDePersistance");

EntityManager entityManager = emf.createEntityManager();
```

### Les méthodes de EntityManager

A partir de `EntityManager` on va pouvoir manipuler les instances (ajouter, modifier, supprimer, ...) via ces méthodes :

- `find`
- `persist`
- `remove`
- `refresh`
- `merge`
- `detach`

## Lancer l'application

```powershell
mvn clean package
```

Executer le JAR : 
```powershell
mvn exec:java "-Dexec.mainClass=fr.spoonless.javaee.App"
```

`Persistence.createEntityManagerFactory` va intérroger le fichier `persistence.xml` pour se connecter à la DB, ajouter la table si inexistante et executer le script de App.

