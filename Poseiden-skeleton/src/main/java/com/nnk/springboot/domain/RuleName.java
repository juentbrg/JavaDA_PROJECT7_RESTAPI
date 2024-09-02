package com.nnk.springboot.domain;

import com.nnk.springboot.record.RuleNameRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "RuleName")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public RuleName(RuleNameRecord.Api.RuleNameRequest ruleNameRequest) {
        this.name = ruleNameRequest.name();
        this.description = ruleNameRequest.description();
        this.json = ruleNameRequest.json();
        this.template = ruleNameRequest.template();
        this.sqlStr = ruleNameRequest.sqlStr();
        this.sqlPart = ruleNameRequest.sqlPart();
    }
}
