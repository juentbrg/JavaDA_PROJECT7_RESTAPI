package com.nnk.springboot.record;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface CurvePointRecord {

    interface Vm {

        record CurvePointVm (
                Integer curveId,
                LocalDateTime asOfDate,
                Double term,
                Double value,
                LocalDateTime creationDate
        ){
            public CurvePointVm(CurvePoint curvePoint){
                this(
                        curvePoint.getCurveId(),
                        curvePoint.getAsOfDate(),
                        curvePoint.getTerm(),
                        curvePoint.getValue(),
                        curvePoint.getCreationDate()
                );
            }
        }
    }

    interface Api {

        record CurvePointRequest (
                Integer curveId,
                LocalDateTime asOfDate,
                Double term,
                Double value
        ){}
    }
}
