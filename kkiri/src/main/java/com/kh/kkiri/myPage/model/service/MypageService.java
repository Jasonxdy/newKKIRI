package com.kh.kkiri.myPage.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.kkiri.common.vo.PageInfo;
import com.kh.kkiri.event.model.vo.Event;
import com.kh.kkiri.member.model.vo.Member;
import com.kh.kkiri.myPage.model.vo.Ticket;
import com.kh.kkiri.payment.model.vo.Payment;

public interface MypageService {

	/** 계좌정보 변경을 위한 Service
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	int updateAccount(Member loginMember)throws Exception;

	/**
	 * @param loginMember
	 * @param changePassword
	 * @return result
	 * @throws Exception
	 */
	int updatePassword(Member loginMember, String changePassword)throws Exception;

	/**
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	int deleteMember(Member loginMember)throws Exception;

	/** 마이페이지 정보 수정을 위한 Service
	 * @param loginMember
	 * @param member
	 * @param profile
	 * @return result 
	 * @throws Exception
	 */
	int updateMember(Member loginMember, Member member, MultipartFile profile, String savepath)throws Exception;

	/**
	 * @param ticket
	 * @param pinf 
	 * @return ticketList
	 * @throws Exception
	 */
	List<Ticket> ticketLog(Ticket ticket, PageInfo pinf)throws Exception;

	/** pagination maxnum을 찾기 위한 서비스
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	int getListCount(Ticket ticket)throws Exception;

	/** 마이페이지 이벤트 내역을 보는 Service
	 * @param memberNo
	 * @param pInf 
	 * @return eList
	 * @throws Exception
	 */
	List<Event> moveEvent(int memberNo, PageInfo pInf)throws Exception;

	int costTicket(Ticket ticket, Member loginMember)throws Exception;

	/** 내가 참가한 이벤트 보는 Service
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	List<Event> moveEvent2(int memberNo ,PageInfo pInf)throws Exception;

	int listEventCount(int memberNo)throws Exception;

	int listEventCount2(int memberNo)throws Exception;

	
}
