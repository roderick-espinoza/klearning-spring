package com.cibertec.klearning.config;

import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.repository.PersonaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataTestRunner {
    @Bean
    CommandLineRunner testPersona(PersonaRepository personaRepository) {
        // ESTE METODO ES TE PRUEBA PARA PODER USAR EL FLUSH Y VERIFICAR QUE SE GUARDO EL REGISTRO EN LA BASE DE DATOS
        // LUEGO SE CAMBIARA POR LA ENTIDAD USUARIOS
        return args -> {

            System.out.println("======== ANTES ========");

            personaRepository.findAll()
                    .forEach(System.out::println);


            Persona persona = new Persona();

            persona.setIdPersona("PER00012");
            persona.setApellidos("Espinoza");
            persona.setNombres("Roderick");
            persona.setDniCe("12345678");
            persona.setSexo("M");
            persona.setFechaNacimiento(LocalDate.of(1998, 1, 10));
            persona.setEstadoCivil("S");
            persona.setNacionalidad("Peruana");
            persona.setCelular("999999999");
            persona.setEmail("test@test.com");
            persona.setFormacionAcademica("Ingenieria");
            persona.setFechaIngreso(LocalDate.now());
            persona.setModalidadTrabajo("Remoto");
            persona.setSkillPrincipal("Java");


            persona.setEstado("1");
            persona.setCreateUser("SYSTEM");
            persona.setCreateDate(LocalDateTime.now());


            System.out.println("======== GUARDANDO ========");

            personaRepository.save(persona);


            System.out.println("======== FLUSH ========");

            personaRepository.flush();


            System.out.println("======== DESPUES DEL FLUSH ========");

            personaRepository.findAll()
                    .forEach(System.out::println);

        };
    }
}
