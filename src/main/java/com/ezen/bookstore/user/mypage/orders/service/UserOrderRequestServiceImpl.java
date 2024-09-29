package com.ezen.bookstore.user.mypage.orders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.dto.UserProductRequestDTO;
import com.ezen.bookstore.user.mypage.orders.repository.UserOrderRequestRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
public class UserOrderRequestServiceImpl implements UserOrderRequestService {

	private final UserOrderRequestRepository orderRequestRepository;

	@Override
	public List<UserCustomerOrderWithDetailsDTO> getOrderList() {
		String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
		return orderRequestRepository.getOrderList(memberId);
	}

	@Override
    public Map<String, Integer> getStatusCounts() {
        String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
        
        Map<String, Integer> counts = new HashMap<>();
        counts.put("취소요청", orderRequestRepository.countByOrderStatus(memberId, "02"));
        counts.put("취소완료", orderRequestRepository.countByOrderStatus(memberId, "05"));

        counts.put("배송전", orderRequestRepository.countByDeliveryStatus(memberId, "01"));
        counts.put("배송중", orderRequestRepository.countByDeliveryStatus(memberId, "02"));
        counts.put("배송완료", orderRequestRepository.countByDeliveryStatus(memberId, "03"));

        return counts;
    }

	@Override
	public UserCustomerOrderWithDetailsDTO getOrderDetail(Integer orderNum) {
		return orderRequestRepository.getOrderDetail(orderNum);
	}
	
	@Override
	public List<UserCustomerOrderWithDetailsDTO> getDetailItem(Integer orderNum) {
		return orderRequestRepository.getDetailItem(orderNum);
	}

	public List<UserProductRequestDTO> getOrderCancleList(Integer orderNum) {
		try {
			return orderRequestRepository.getOrderCancleList(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public List<UserProductRequestDTO> getOrderReturnList(Integer orderNum) {
		try {
			return orderRequestRepository.getOrderReturnList(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderRequestList(Integer orderNum, String memberId) {
		try {
			return orderRequestRepository.getOrderRequestList(orderNum, memberId);
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
			for (UserCustomerOrderWithDetailsDTO dto : list) {
				result += orderRequestRepository.orderCancle(dto);
				dto.setOrder_detail_status("02");
				
				orderRequestRepository.productRequestInsert(dto);
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
	
	@Override
	public UserCustomerOrderWithDetailsDTO getReturnRefundInfo(Integer orderNum) {
		try {
			return orderRequestRepository.getReturnRefundInfo(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Transactional
	public int returnRequest(Map<String, Object> data) {
	    int result = 0;
	    List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("list");
	    Map<String, String> info = (Map<String, String>) data.get("dto");

	    try {
	        for (Map<String, Object> map : list) {
	            result += orderRequestRepository.returnRequest(map);
	            Integer orderRequestQty = Integer.parseInt((String) map.get("order_request_qty"));
	            Integer orderDetailNum = Integer.parseInt((String) map.get("order_detail_num"));
	            
	            orderRequestRepository.productRequestInsert("04", orderRequestQty, orderDetailNum);
	        }

	        orderRequestRepository.returnRequestInfoUpdate(info);
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        // 적절한 에러 처리를 추가하세요.
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}

}
