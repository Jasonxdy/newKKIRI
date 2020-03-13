package com.kh.kkiri.home.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.kkiri.member.model.vo.Member;

@Repository
public class HomeDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/**
	 * 전체 회원 수 조회
	 * @return memberCount
	 * @throws Exception
	 */
	public int selectMemberCount() throws Exception{
		return sqlSession.selectOne("homeMapper.selectMemberCount");
	}

	
	/**
	 * 1주간 가장 높은 평점을 가진 회원 조회
	 * @return list
	 * @throws Exception
	 */
	public List<Member> selectMemberList(int memberCount) throws Exception{
		return sqlSession.selectList("homeMapper.selectMemberList", memberCount);
	}


}