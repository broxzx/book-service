package com.project.booksocialnetwork.feedback.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedbacks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feedback {

    private String id;

    private Short note;

    private String comment;

    private String ownerId;

}
