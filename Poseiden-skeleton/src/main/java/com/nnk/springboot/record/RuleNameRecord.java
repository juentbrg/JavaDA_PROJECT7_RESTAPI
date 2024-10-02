package com.nnk.springboot.record;

import com.nnk.springboot.domain.RuleName;

public interface RuleNameRecord {

    interface Vm {

        record RuleNameVm(
                Integer id,
                String name,
                String description,
                String json,
                String template,
                String sqlStr,
                String sqlPart
        ){
            public RuleNameVm(RuleName ruleName){
                this(
                        ruleName.getId(),
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
