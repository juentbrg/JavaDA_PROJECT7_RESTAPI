package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.record.CurvePointRecord;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nnk.springboot.utils.CRUDUtils.getNullPropertyNames;

@Service
public class CurvePointService {

    private static final Logger logger = LoggerFactory.getLogger(CurvePointService.class);

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Transactional(readOnly = true)
    public List<CurvePointRecord.Vm.CurvePointVm> getAllCurvePoints() {
        logger.info("Fetching all curve points");
        List<CurvePoint> curvePointList = curvePointRepository.findAll();
        logger.debug("Found {} curve points", curvePointList.size());

        if (curvePointList.isEmpty()) {
            logger.warn("No curve points found");
            return null;
        }

        return curvePointList.stream()
                .map(CurvePointRecord.Vm.CurvePointVm::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CurvePointRecord.Vm.CurvePointVm getCurvePointById(int id) {
        logger.info("Fetching curve point with id: {}", id);
        Optional<CurvePoint> curvePointOpt = curvePointRepository.findById(id);

        if (curvePointOpt.isPresent()) {
            logger.debug("Curve point found with id: {}", id);
            return new CurvePointRecord.Vm.CurvePointVm(curvePointOpt.get());
        }

        logger.warn("No curve point found with id: {}", id);
        return null;
    }

    @Transactional
    public void createCurvePoint(CurvePoint curvePoint) {
        try {
            logger.info("Creating new curve point");
            curvePointRepository.save(curvePoint);
            logger.debug("Curve point created with id: {}", curvePoint.getId());
        } catch (Exception e) {
            logger.error("Failed to create curve point", e);
            throw new RuntimeException("Failed to create curve point", e);
        }
    }

    @Transactional
    public CurvePointRecord.Vm.CurvePointVm updateCurvePoint(int id, CurvePoint curvePointRequest) {
        logger.info("Updating curve point with id: {}", id);
        Optional<CurvePoint> curvePointOpt = curvePointRepository.findById(id);
        if (curvePointOpt.isPresent()) {
            CurvePoint curvePoint = curvePointOpt.get();
            BeanUtils.copyProperties(curvePointRequest, curvePoint, getNullPropertyNames(curvePointRequest));
            curvePointRepository.save(curvePoint);
            logger.debug("Curve point updated with id: {}", id);
            return new CurvePointRecord.Vm.CurvePointVm(curvePoint);
        }

        logger.warn("No curve point found with id: {} for update", id);
        return null;
    }

    @Transactional
    public void deleteCurvePoint(int id) {
        try {
            logger.info("Deleting curve point with id: {}", id);
            curvePointRepository.deleteById(id);
            logger.debug("Curve point deleted with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete curve point with id: {}", id, e);
            throw new RuntimeException("Failed to delete curve point", e);
        }
    }
}
