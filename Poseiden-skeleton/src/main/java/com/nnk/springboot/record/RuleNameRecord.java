package com.nnk.springboot.record;

import com.nnk.springboot.domain.RuleName;
import jakarta.validation.constraints.Size;

public interface RuleNameRecord {

    interface Vm {

        record RuleNameVm(
                String name,
                String description,
                String json,
                String template,
                String sqlStr,
                String sqlPart
        ){
            public RuleNameVm(RuleName ruleName){
                this(
                        ruleName.getName(),
                        ruleName.getDescription(),
                        ruleName.getJson(),
                        ruleName.getTemplate(),
                        ruleName.getSqlStr(),
                        ruleName.getSqlPart()
                );
            }
        }
    }
}
