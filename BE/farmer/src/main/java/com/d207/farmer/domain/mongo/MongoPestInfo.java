package com.d207.farmer.domain.mongo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pest")
public class MongoPestInfo {
    @Id
    private String id;
    private String name;
}
