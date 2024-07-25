package com.studymavernspringboot.mybatisapp.category;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto implements ICategory {
    private Long id;
    private String name;
}