package com.nnk.springboot.record;

import com.nnk.springboot.domain.CurvePoint;

public interface CurvePointRecord {

    interface Vm {

        record CurvePointVm (
                Integer curveId,
                Double term,
                Double value
        ){
            public CurvePointVm(CurvePoint curvePoint){
                this(
                        curvePoint.getCurveId(),
                        curvePoint.getTerm(),
                        curvePoint.getValue()
                );
            }
        }
    }
}
