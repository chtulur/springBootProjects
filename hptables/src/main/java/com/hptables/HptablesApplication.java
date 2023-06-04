package com.hptables;

// import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;

// import com.hptables.model.HogwartsClass;
// import com.hptables.model.Student;
// import com.hptables.repos.HogwartsRepo;
// import com.hptables.repos.StudentRepo;

@SpringBootApplication
public class HptablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HptablesApplication.class, args);
	}

  // @Bean
  // CommandLineRunner run(HogwartsRepo hogwartsRepo, StudentRepo studentRepo) {
  //   return args -> {
  //       List<HogwartsClass> classes = List.of(
  //               new HogwartsClass(null, "Potions", "Severus Snape"),
  //               new HogwartsClass(null, "Transfiguration", "Minerva McGonagall"),
  //               new HogwartsClass(null, "Defense Against the Dark Arts", "Alastor \"Mad-Eye\" Moody"),
  //               new HogwartsClass(null, "Astronomy", "Aurora Sinistra"),
  //               new HogwartsClass(null, "Muggle Studies", "Charity Burbage"),
  //               new HogwartsClass(null, "History of Magic", "Cuthbert Binns"),
  //               new HogwartsClass(null, "Charms", "Filius Flitwick"),
  //               new HogwartsClass(null, "Divination", "Sybill Trelawney"),
  //               new HogwartsClass(null, "Care of Magical Creatures", "Rubeus Hagrid"),
  //               new HogwartsClass(null, "Herbology", "Pomona Sprout")
  //       );

  //       hogwartsRepo.saveAll(classes);


  //       List<Student> students = List.of(
  //         new Student(null, "Harry Potter", "Gryffindor"),
  //         new Student(null, "Hermione Granger", "Gryffindor"),
  //         new Student(null, "Susan Bones", "Hufflepuff"),
  //         new Student(null, "Hannah Abbott", "Hufflepuff"),
  //         new Student(null, "Justin Finch-Fletchley", "Hufflepuff"),
  //         new Student(null, "Cedric Diggory", "Hufflepuff"),
  //         new Student(null, "Colin Creevey", "Gryffindor"),
  //         new Student(null, "Lavender Brown", "Gryffindor"),
  //         new Student(null, "Dennis Creevey", "Gryffindor"),
  //         new Student(null, "Seamus Finnigan", "Gryffindor"),
  //         new Student(null, "Percy Weasley", "Gryffindor"),
  //         new Student(null, "Ron Weasley", "Gryffindor"),
  //         new Student(null, "Ginny Weasley", "Gryffindor"),
  //         new Student(null, "Neville Longbottom", "Gryffindor"),
  //         new Student(null, "Terry Boot", "Ravenclaw"),
  //         new Student(null, "Marietta Edgecombe", "Ravenclaw"),
  //         new Student(null, "Vincent Crabbe", "Ravenclaw"),
  //         new Student(null, "Luna Lovegood", "Ravenclaw"),
  //         new Student(null, "Draco Malfoy", "Slytherin"),
  //         new Student(null, "Millicent Bulstrode", "Slytherin"),
  //         new Student(null, "Gregory Goyle", "Slytherin"),
  //         new Student(null, "Pansy Parkinson", "Slytherin"),
  //         new Student(null, "Ernie Macmillan", "Hufflepuff"),
  //         new Student(null, "Blaise Zabini", "Slytherin"),
  //         new Student(null, "Dean Thomas", "Gryffindor"),
  //         new Student(null, "Padma Patil", "Ravenclaw"),
  //         new Student(null, "Parvati Patil", "Gryffindor"),
  //         new Student(null, "George Weasley", "Gryffindor"),
  //         new Student(null, "Fred Weasley", "Gryffindor"),
  //         new Student(null, "Cho Chang", "Ravenclaw")
  //       );

  //       studentRepo.saveAll(students);
  //   };
  // }
}
