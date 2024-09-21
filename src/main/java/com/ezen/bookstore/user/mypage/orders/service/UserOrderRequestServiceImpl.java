package com.ezen.bookstore.user.mypage.orders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.repository.UserOrderRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderRequestServiceImpl implements UserOrderRequestService {
	
	private final UserOrderRequestRepository orderRequestRepository;
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderList(String memberId) {
		return orderRequestRepository.getOrderList(memberId);
	}
	
    @Override
    public Map<String, Integer> getOrderStatusCounts(String memberId) {
        Map<String, Integer> counts = new HashMap<>();
     // 주문 상태 카운트
        counts.put("취소요청", orderRequestRepository.countByOrderStatus(memberId, "02")); // 취소 요청
        counts.put("반품요청", orderRequestRepository.countByOrderStatus(memberId, "04")); // 반품 요청
        counts.put("취소완료", orderRequestRepository.countByOrderStatus(memberId, "05")); // 취소 완료
        counts.put("교환완료", orderRequestRepository.countByOrderStatus(memberId, "06")); // 교환 완료
        counts.put("반품완료", orderRequestRepository.countByOrderStatus(memberId, "07")); // 반품 완료
        return counts;
    }

    @Override
    public Map<String, Integer> getDeliveryStatusCounts(String memberId) {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("배송전", orderRequestRepository.countByDeliveryStatus(memberId, "01"));
        counts.put("배송중", orderRequestRepository.countByDeliveryStatus(memberId, "02"));
        counts.put("배송완료", orderRequestRepository.countByDeliveryStatus(memberId, "03"));
        return counts;
    }
	
    @Override
    public UserCustomerOrderWithDetailsDTO getOrderDetail(Integer orderNum) {
		try {
			return orderRequestRepository.getOrderDetail(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
    
    public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum, String memberId) {
		try {
			return orderRequestRepository.getOrderCancleList(orderNum, memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

    @Override
	@Transactional
	public int orderCancle(List<UserCustomerOrderWithDetailsDTO> list) {
		int result = 0;
		
		try {
			for(UserCustomerOrderWithDetailsDTO dto : list) {
				result += orderRequestRepository.orderCancle(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

    @Override
	public UserCustomerOrderWithDetailsDTO getRefundInfo(Integer orderNum) {
		try {
			return orderRequestRepository.getRefundInfo(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
