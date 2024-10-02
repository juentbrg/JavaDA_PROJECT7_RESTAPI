package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.record.CurvePointRecord;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Transactional(readOnly = true)
    public List<CurvePointRecord.Vm.CurvePointVm> getAllCurvePoints() {
        List<CurvePoint> curvePointList = curvePointRepository.findAll();

        if (curvePointList.isEmpty())
            return null;

        return curvePointList.stream()
                .map(CurvePointRecord.Vm.CurvePointVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CurvePointRecord.Vm.CurvePointVm getCurvePointById(int id) {
        Optional<CurvePoint> curvePointOpt = curvePointRepository.findById(id);

        if (curvePointOpt.isPresent())
            return new CurvePointRecord.Vm.CurvePointVm(curvePointOpt.get());

        return null;
    }

    @Transactional
    public void createCurvePoint(CurvePoint curvePoint) {
        try {
            curvePointRepository.save(curvePoint);
        } catch (Exception e) {
            throw new RuntimeException("failed to create curve point", e);
        }
    }

    @Transactional
    public CurvePointRecord.Vm.CurvePointVm updateCurvePoint(int id, CurvePoint curvePointRequest) {
        Optional<CurvePoint> curvePointOpt = curvePointRepository.findById(id);
        if (curvePointOpt.isPresent()) {
            CurvePoint curvePoint = curvePointOpt.get();

            BeanUtils.copyProperties(curvePointRequest, curvePoint, getNullPropertyNames(curvePointRequest));
            curvePointRepository.save(curvePoint);
            return new CurvePointRecord.Vm.CurvePointVm(curvePoint);
        }
        return null;
    }

    @Transactional
    public void deleteCurvePoint(int id) {
        try {
            curvePointRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("failed to delete curve point", e);
        }
    }
}
