package com.example.carrot;

import com.example.carrot.model.Member;
import com.example.carrot.model.Post;
import com.example.carrot.response.PostResponseDto;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(CarrotApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

//    List<Post> List = new ArrayList<>();
//    List<PostResponseDto> test = new ArrayList<>();
//    for (Post post : List) = new ArrayList<>();
//    for (Post post : memberList) {
//        String nickname = post.getNickname();
//    }


}
