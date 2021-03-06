package com.kh.kkiri.myPage.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.kkiri.common.Pagination;
import com.kh.kkiri.common.Pagination2;
import com.kh.kkiri.common.vo.PageInfo;
import com.kh.kkiri.event.model.vo.Event;
import com.kh.kkiri.member.model.service.MemberService;
import com.kh.kkiri.member.model.vo.Member;
import com.kh.kkiri.myPage.model.service.MypageService;
import com.kh.kkiri.myPage.model.vo.Ticket;

@RequestMapping("/mypage/*")
@Controller
@SessionAttributes({"loginMember", "msg"})
public class MypageController {

	@Autowired
	private MypageService mypageService;

	@Autowired
	private MemberService memberService;

	@RequestMapping("in")
	public String mypageIn(Model model, HttpSession session, 
			RedirectAttributes rdAttr) {
		Member loginMember  = (Member)session.getAttribute("loginMember");
		
		// 새로운 로그인 갱신 
		try {
			
		loginMember = mypageService.loginAgain(loginMember.getMemberNo());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "마이페이지 이동중 오류가 발생하였습니다.");
			return "common/errorPage";
		}
		if(loginMember.getMemberCategory().equals("설정해주세요") || 
				loginMember.getMemberPlace().equals("설정해주세요")) {
			String msg = "관심 지역과 관심 카테고리를 수정해주세요";
			session.setAttribute("msg", msg);
		}
		model.addAttribute("loginMember", loginMember);
		return "myPage/my_profile";
	}


	@RequestMapping("updateAccount")
	public String updateAccount(Model model,String bankName, String bankOrner, String bankNumber , RedirectAttributes rdAttr ) {

		Member loginMember = (Member)model.getAttribute("loginMember");
		String Account = bankName+","+bankNumber+","+bankOrner;
		loginMember.setMemberAccount(Account);
		String msg = null;
		try {

			int result = mypageService.updateAccount(loginMember);

			if(result>0) {
				msg = "회원정보 수정 완료";
				rdAttr.addFlashAttribute("msg", msg);
			}
			return "redirect:/mypage/in";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "계좌정보 수정중 오류가 발생하였습니다.");
			return "common/errorPage";
		}
	}
	@RequestMapping("updatePassword")
	public String updatePassword (Model model, String originPassword, String changePassword, RedirectAttributes rdAttr) {

		Member loginMember = (Member)model.getAttribute("loginMember");

		loginMember.setMemberPwd(originPassword);
		String msg = "";
		// 비밀번호 확인 작업
		try {

			int result = mypageService.updatePassword(loginMember ,changePassword);

			if(result==0)  msg="기존 비밀번호가 틀렸습니다.";
			else if(result ==1) msg="비밀번호 변경에 성공하였습니다.";
			else msg="비밀번호 변경에 실패하였습니다.";
			rdAttr.addFlashAttribute("msg", msg);

		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "비밀번호 변경 과정중 오류가 발생했습니다.");
			return "common/errorPage";
		}


		return "redirect:/mypage/in";
	}
	@RequestMapping("movedeleteMember")
	public String movedeleteMember() {
		return "myPage/deleteMember";
	}

	@RequestMapping("deleteMember")
	public String deleteMember (Model model , String password, SessionStatus status , RedirectAttributes arAttr) {

		Member loginMember = (Member)model.getAttribute("loginMember");
		int result =0;
		loginMember.setMemberPwd(password);
		String msg = "";
		try {

			result = mypageService.deleteMember(loginMember);

			if(result ==-1) {
				msg="회원탈퇴에 실패하였습니다.";
				arAttr.addFlashAttribute("msg", msg);
				return "redirect:/mypage/movedeleteMember";
			}
			else if(result==0) {
				msg="비밀번호가 틀렸습니다.";
				arAttr.addFlashAttribute("msg", msg);
				return "redirect:/mypage/movedeleteMember"; // 삭제페이지로 이동
			}else {
				// session 만료 
				status.setComplete();
				msg="회원탈퇴에 성공하였습니다.";
				return "redirect:/";
			}

		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "회원탈퇴 과정에서 오류가 발생했습니다.");
			return "common/errorPage";
		}

	}
	@RequestMapping("main")
	public String main () {
		return "redirect:/";	
	}

	@RequestMapping("updateMyPage")
	public String updateMyPage (Model model) {
		
		Member loginMember = (Member)model.getAttribute("loginMember");
		if(loginMember.getMemberIntroduce()!=null) {
		loginMember.setMemberIntroduce(loginMember.getMemberIntroduce().replace("<br>", "\r\n"));
		}
		loginMember.setMemberPhone(loginMember.getMemberPhone().trim());
		model.addAttribute("loginMember", loginMember);
		return "myPage/update_profile";
	}
	@RequestMapping("updateMember")
	public String updateMember (Member member,String [] interest ,  Model model, RedirectAttributes rdAttr,@RequestParam(value = "profile" , required = false) MultipartFile profile, HttpServletRequest request ) {
		Member loginMember = (Member)model.getAttribute("loginMember");
		String memberInterest = "";
		for(int i =0 ; i<interest.length; i++) {
			memberInterest+=interest[i];
			if(i!=interest.length-1) {

				memberInterest += ",";
			}
		}
		
		member.setMemberCategory(memberInterest);

		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "/upProfileImage";

		File folder = new File(savePath);

		if(!folder.exists()) {
			folder.mkdir();
		}
		int result = 0;
		String msg = "";

		try {

			result = mypageService.updateMember(loginMember, member, profile,savePath);


			if(result >0) {
				// 업데이트 성공
				msg="회원정보 수정에 성공했습니다.";
				rdAttr.addFlashAttribute("msg", msg);
				member.setMemberId(loginMember.getMemberId());
				if(member.getMemberProfile()==null) {
					member.setMemberProfile(loginMember.getMemberProfile());
				}
				loginMember = member;
				model.addAttribute("loginMember", loginMember);


			}else if(result ==0) {
				// 업데이트 실패
				msg = "회원정보 수정에 실패했습니다.";
				rdAttr.addFlashAttribute("msg", msg);
				member.setMemberId(loginMember.getMemberId());
			}

		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "회원정보 수정 과정에서 오류가 발생했습니다.");
			return "common/errorPage";
		}

		return "redirect:/mypage/in";
	}
	@RequestMapping(value="ticketLog" ,method=RequestMethod.GET)
	public String ticketLog(Model model, 
			@RequestParam(value="currentPage" , required = false) Integer currentPage,
			@RequestParam(value="ticketsort" ,required=false)String ticketSort) {
		Member loginMember  = (Member)model.getAttribute("loginMember");
		Ticket ticket = new Ticket();
		int memberNo = ((Member)model.getAttribute("loginMember")).getMemberNo();
		ticket.setMemberNo(memberNo);
		if(ticketSort ==null) {
			ticketSort = "all";
		}
		ticket.setPaymentType(ticketSort);

		// 페이징 처리
		if(currentPage == null) {
			currentPage = 1;
		}

		try {
			int listCount = mypageService.getListCount(ticket);
			PageInfo Pinf = Pagination.getPageInfo(10, 5, currentPage, listCount);
			List<Ticket> ticketList = mypageService.ticketLog(ticket,Pinf);
			
			loginMember = mypageService.loginAgain(loginMember.getMemberNo());
			model.addAttribute("loginMember", loginMember);
			model.addAttribute("ticketList", ticketList);
			model.addAttribute("pInf", Pinf);
			model.addAttribute("ticketSort", ticketSort);
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "티켓페이지 이동중 에러가 발생했습니다.");
			return "common/errorPage";
		}
		return "myPage/ticket_history";
	}
	@RequestMapping("moveEvent")
	public String moveEvent(Model model,
			@RequestParam(value="currentPage" , required = false) Integer currentPage,
			@RequestParam(value="flag" , required = false) Integer flag
			) {

		Member loginMember = (Member)model.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();

		if(currentPage == null) {
			currentPage = 1;
		}
		
		if(flag == null) {
			flag = 1;
		}

		try {
			int listCount = mypageService.listEventCount(memberNo);
			int listCount2 = mypageService.listEventCount2(memberNo,flag);
				
			
			PageInfo pInf = Pagination.getPageInfo(3, 5, currentPage, listCount);
			PageInfo pInf2 = Pagination2.getPageInfo(3, 5, currentPage, listCount2);
			
			// 내가 주최자인 이벤트
			List<Event> eList = mypageService.moveEvent(memberNo,pInf);
			// 내가 참가한 이벤트 
			List<Event> ejList = mypageService.moveEvent2(memberNo,pInf2,flag);
			
			// 대기
			List<Event> ejList2 = mypageService.moveEvent3(memberNo, pInf2,flag);
			
			
			
			loginMember = mypageService.loginAgain(loginMember.getMemberNo());
			model.addAttribute("loginMember", loginMember);
			model.addAttribute("ejList2", ejList2);
			model.addAttribute("eList", eList);
			model.addAttribute("ejList", ejList);
			model.addAttribute("pInf", pInf);
			model.addAttribute("pInf2", pInf2);
			model.addAttribute("flag", flag);
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "마이 이벤트 페이지 이동중 에러가 발생했습니다.");
			return "common/errorPage";
		}

		return "myPage/my_event";

	}
	@RequestMapping("moveRefund")
	public String moveRefund(Model model) {

		return "myPage/ticket_refund";
	}

	@RequestMapping("costTicket")
	public String costTicket(Integer refundTicket, Model model,
			RedirectAttributes rdattr,
			String memberPassword) {
		Member loginMember = (Member)model.getAttribute("loginMember");
		loginMember.setMemberPwd(memberPassword);
		Ticket ticket = new Ticket();
		ticket.setMemberNo(loginMember.getMemberNo());
		ticket.setPaymentTicket(-refundTicket);
		ticket.setPaymentType("R");
		String msg = null;

		try {

			int result = mypageService.costTicket(ticket,loginMember);
			// 0 = 비밀번호 불일치
			// 1 = payment만 넣음
			// -1 = 둘 다 넣음
			// -2 = payment를 못넣음

			if(result==-1) {
				msg="환급신청이 완료 되었습니다.";
				loginMember.setMemberTicket((loginMember.getMemberTicket()-refundTicket));
			}
			else if(result==0) msg="비밀번호가 일치하지 않습니다.";
			else if (result ==-2) msg="환급 신청작업중 오류가 발생하였습니다.";
			else if (result==1)msg="환급 신청 과정중 문제가 발생하였습니다.";


			loginMember.setMemberPwd("");
			model.addAttribute("loginMember", loginMember);
			rdattr.addFlashAttribute("msg", msg);
			return "redirect:/mypage/moveRefund";
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "환급신청 작업중 에러가 발생했습니다.");
			return "common/errorPage";
		}
	}
	@RequestMapping("recharge")
	public String recharge() {

		return "myPage/ticket_recharge";

	}

	@RequestMapping(value = "ticketRecharge")
	@ResponseBody
	public String ticketRecharge(@RequestParam(value = "recharge-amount")Integer rechargeAmount,
			@RequestParam(value="memberNo")Integer memberNo, 
			@RequestParam(value="memberId")String memberId,
			@RequestParam(value="recharge-way")String rechargeWay
			) {

		Ticket ticket = new Ticket();

		ticket.setMemberNo(memberNo);
		ticket.setPaymentTicket((rechargeAmount/1000));
		ticket.setPaymentType("C");


		String merchantUid = null;

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");

		Calendar time = Calendar.getInstance();

		merchantUid = date.format(time.getTime())+memberId.substring(0,4)+memberNo;

		ticket.seteventName(merchantUid);

		int result = 0;
		try {

			result = mypageService.ticketRecharge(ticket);
			// 0 = 입력 실패 / 1 = 입력 성공

			if(result >0) {
			}

			else {
				merchantUid = null;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return merchantUid;
	}

	@RequestMapping("successRecharge")
	public String successRecharge (@RequestParam(value="recharge")Integer recharge, Model model) {
		
		// 멤버의 데이터 전달 및 로그인 중인 멤버의 값 변경
		Member loginMember = (Member)model.getAttribute("loginMember");
		int result = 0;
		try{
			result = mypageService.successRecharge(loginMember,(recharge/1000));
			loginMember.setMemberTicket((loginMember.getMemberTicket()+(recharge/1000)));
			model.addAttribute("loginMember", loginMember);
		
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "티켓충전 중 에러가 발생했습니다. 관리자에게 문의하시기 바랍니다.");
			return "common/errorPage";
		}


		return "redirect:/mypage/ticketLog";
	}

	@RequestMapping("deleteTicket")
	public String deleteTicket(@RequestParam(value="merchant_uid")String merchant_uid) {
		
	String checker = null;
	int result = 0;
	try {
		
	result = mypageService.deleteTicket(merchant_uid);
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
	
	
	return checker;
	
	}
	@RequestMapping("moveFavorite")
	public String moveFavorite(Model model,
			@RequestParam(value="currentPage", required=false) Integer currentPage) {
		Member loginMember = (Member)model.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		try {
			int count = mypageService.getFavoriteCount(memberNo);
			if(currentPage == null) currentPage = 1;

			PageInfo pInf = Pagination.getPageInfo(9, 10, currentPage, count);

			List<Member> fList = mypageService.moveFavorite(memberNo, pInf);
			loginMember = mypageService.loginAgain(loginMember.getMemberNo());
			model.addAttribute("loginMember", loginMember);
			model.addAttribute("pInf", pInf);
			model.addAttribute("fList", fList);
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "즐겨찾기 불러오기 중 에러가 발생했습니다.");
			return "common/errorPage";
		}

		return "myPage/my_favorite";
	}

	@RequestMapping("changeMemo")
	public String changeMemo(Model model, Member member,
			RedirectAttributes rdattr) {
		Member loginMember = (Member)model.getAttribute("loginMember");
		member.setMemberNo(loginMember.getMemberNo());
		//		String msg = null;
		try {
			int result = mypageService.changeMemo(member);
			//			if(result>0) msg = "변경되었습니다.";
			//			else msg = "변경 실패";
			//			rdattr.addFlashAttribute("msg", msg);
			rdattr.addFlashAttribute("updateResult",result);
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "즐겨찾기 불러오기 중 에러가 발생했습니다.");
			return "common/errorPage";
		}

		return "redirect:/mypage/moveFavorite";
	}

	@RequestMapping("deleteFavorite")
	public String deleteFavorite(Model model, Member member,
			RedirectAttributes rdattr) {
		Member loginMember = (Member)model.getAttribute("loginMember");
		member.setMemberNo(loginMember.getMemberNo());
		//		String msg = null;
		try {
			int result = mypageService.deleteFavorite(member);
			//			if(result>0) msg = "삭제되었습니다.";
			//			else msg = "삭제 실패";
			//			rdattr.addFlashAttribute("msg", msg);
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "즐겨찾기 삭제 중 에러가 발생했습니다.");
			return "common/errorPage";
		}

		return "redirect:/mypage/moveFavorite";
	}
}
