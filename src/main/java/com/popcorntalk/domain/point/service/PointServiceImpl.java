package com.popcorntalk.domain.point.service;

import com.popcorntalk.domain.point.entity.Point;
import com.popcorntalk.domain.point.repository.PointRepository;
import com.popcorntalk.global.annotation.DistributedLock;
import com.popcorntalk.global.exception.ErrorCode;
import com.popcorntalk.global.exception.customException.InsufficientPointException;
import com.popcorntalk.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointRecordService pointRecordService;
    private final int SIGNUP_REWARD = 1000;
    private final int INITIAL_POINT = 0;

    @Override
    @Transactional
//    @DistributedLock(lockName = "point", identifier = "userId")
    public void deductPointForPurchase(Long userId, int price) throws InterruptedException {


//        Thread.sleep(1000000000L);
        Point userPoint = getPoint(userId);
        int previousPoint = userPoint.getPoint();
        int newPointBalance = userPoint.getPoint() - price;
        userPoint.update(newPointBalance);

        pointRecordService.createPointRecord(
            userPoint.getId(), previousPoint, -price, userPoint.getPoint()
        );
    }

    @Override
//    @DistributedLock(lockName = "point", identifier = "userId")
    public void checkUserPoint(Long userId, int price) {

        Point userPoint = getPoint(userId);

        if (userPoint.getPoint() < price) {
            throw new InsufficientPointException(ErrorCode.INSUFFICIENT_POINT);
        }
    }

    @Override
    @Transactional
//    @DistributedLock(lockName = "point", identifier = "userId")
    public void rewardPointForSignUp(Long userId) {

        Point signupPoint = Point.createOf(userId, SIGNUP_REWARD);
        pointRepository.save(signupPoint);

        pointRecordService.createPointRecord(
            signupPoint.getId(), INITIAL_POINT, +SIGNUP_REWARD, SIGNUP_REWARD
        );
    }

    @Override
    @Transactional
//    @DistributedLock(lockName = "point", identifier = "userId")
    public void earnPoint(Long userId, int point) {

        Point userPoint = getPoint(userId);
        int previousPoint = userPoint.getPoint();
        int newPointBalance = userPoint.getPoint() + point;
        userPoint.update(newPointBalance);

        pointRecordService.createPointRecord(
            userPoint.getId(), previousPoint, +point, userPoint.getPoint()
        );
    }

    @Override
//    @DistributedLock(lockName = "point", identifier = "userId")
    public Point getPoint(Long userId) {
        return pointRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.POINT_NOT_FOUND));
    }
}
