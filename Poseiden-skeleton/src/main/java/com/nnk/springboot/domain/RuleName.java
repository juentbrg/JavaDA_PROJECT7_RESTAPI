package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "RuleName")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 125)
    private String name;

    @Size(max = 125)
    private String description;

    @Size(max = 125)
    private String json;

    @Size(max = 512)
    private String template;

    @Size(max = 125)
    private String sqlStr;

    @Size(max = 125)
    private String sqlPart;

    public RuleName(String ruleName, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = ruleName;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
