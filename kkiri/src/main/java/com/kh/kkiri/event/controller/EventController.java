package com.kh.kkiri.event.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.kkiri.event.model.service.EventService;
import com.kh.kkiri.event.model.vo.Event;
import com.kh.kkiri.member.model.vo.Member;

@Controller
@SessionAttributes({ "loginMember", "msg" })
@RequestMapping("/event/*")
public class EventController {

	@Autowired
	private EventService eventService;

	// 이벤트 상세 페이지 이동
	@RequestMapping("detail")
	public String eventDetail(@RequestParam(value = "no", required = false) Integer no, Model model,
			RedirectAttributes rdAttr) {

		int eventNo = 0;

//		테스트용으로 삭제 예정
		if (no != null) {
			eventNo = no;
		} else {
			eventNo = 99;
		}

		String msg = null;
		String url = null;

		try {

			// 이벤트 + 주최자 정보 얻어오기
			Event event = eventService.selectEvent(eventNo);

			// 이벤트 참석자 정보 얻어오기
			List<Member> partyList = eventService.selectPartyList(eventNo);

			if (event != null) {

				model.addAttribute("event", event);
				
				if(partyList != null && !partyList.isEmpty()) {
					model.addAttribute("partyList", partyList);
					url = "event/eventDetail";
					
				} else {
					msg = "이벤트 상세 페이지 조회 실패";
					rdAttr.addFlashAttribute("msg", msg);
					url = "redirect:/";
				}

			} else {
				msg = "이벤트 상세 페이지 조회 실패";
				rdAttr.addFlashAttribute("msg", msg);
				url = "redirect:/";
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "이벤트 상세 페이지 조회 과정 중 오류 발생");
			return "common/errorPage.jsp";
		}
		return url;
	}
	
	
	@RequestMapping("selectParticipant")
	public String selectParticipant(@RequestParam(value = "no", required = false) Integer no, Model model,
			RedirectAttributes rdAttr) {
		
		int eventNo = 0;

//		테스트용으로 삭제 예정
		if (no != null) {
			eventNo = no;
		} else {
			eventNo = 99;
		}

		String msg = null;
		String url = null;

		try {

			// 이벤트 + 주최자 정보 얻어오기
			Event event = eventService.selectEvent(eventNo);

			// 이벤트 참석자 정보 얻어오기
			List<Member> partyList = eventService.selectPartyList(eventNo);

			if (event != null) {

				model.addAttribute("event", event);
				
				if(partyList != null && !partyList.isEmpty()) {
					model.addAttribute("partyList", partyList);
					url = "event/eventParticipant";
					
				} else {
					msg = "이벤트 상세 페이지 조회 실패";
					rdAttr.addFlashAttribute("msg", msg);
					url = "redirect:/";
				}

			} else {
				msg = "이벤트 상세 페이지 조회 실패";
				rdAttr.addFlashAttribute("msg", msg);
				url = "redirect:/";
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "이벤트 상세 페이지 조회 과정 중 오류 발생");
			return "common/errorPage.jsp";
		}
		return url;
	}
	
	
	
	
	/*
	 * // 이벤트 참가회원 조회
	 * 
	 * @RequestMapping("selectParty") public String partyList(@RequestParam(value =
	 * "")) {
	 * 
	 * }
	 */

}
