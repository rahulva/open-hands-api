package com.project.open_hands.resource.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostResponse extends PostRequest {
    private long id;
}
