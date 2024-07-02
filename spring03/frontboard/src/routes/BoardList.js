import axios from 'axios';  // REST API 호출 핵심!!

// Hook함수 사용
import React, { useState, useEffect } from 'react';

// Navigation
import { Link } from 'react-router-dom';

function BoardList() {    // 객체를 만드는 함수
  // 변수 선언
  const [boardList, setBoardList] = useState([]); // 배열값을 받아서 상태를 저장하기 때문에 []

  // 함수선언
  // 제일 중요!!
  const getBoardList = async () => {
    var pageString = 'page=0';
    const resp = (await axios.get("//localhost:8080/api/board/list/free?" + pageString)).data;
    setBoardList(resp);  // boardList에 데이터가 들어감
    console.log(resp);
  }

  useEffect(() => {
    getBoardList();
  }, []); // 값이 없을때는 빈 화면

  return (
    <div className='container'>
      <table className='table'>
        <thead className='table-dark'>
          <tr className='text-center'>
            <th>번호</th>
            <th style={{width: '50%'}}>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {/* 반복으로 들어갈 부분 */}
          {boardList.map((board) => (
          <tr className='text-center' key={board.bno}>
            <td>{board.bno}</td>
            <td className='text-start'>{board.title}</td>
            <td>{board.writer}</td>
            <td>{board.hit}</td>
            <td>{board.createDate}</td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
}

export default BoardList;