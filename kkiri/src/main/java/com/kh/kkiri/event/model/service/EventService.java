package com.kh.kkiri.event.model.service;

import java.util.List;
import java.util.Map;

import com.kh.kkiri.event.model.vo.Event;
import com.kh.kkiri.member.model.vo.Member;

public interface EventService {

	/**
	 * 이벤트 상세 조회
	 * @param eventNo
	 * @return event
	 * @throws Exception
	 */
	Event selectEvent(int eventNo) throws Exception;

	/**
	 * 이벤트 참석자 조회
	 * @param eventNo
	 * @return partyList
	 * @throws Exception
	 */
	List<Member> selectPartyList(int eventNo) throws Exception;

	/** 관리자: 이벤트 수 조회 Service
	 * @param map
	 * @return eventCount
	 * @throws Exception
	 */
	int adminEventCount(Map<String, String> map) throws Exception;


	
	
}
