// import { useState } from 'react';

function IncButton({ count, onClick }) {    // 넘어오는 props와 handler 함수 전달 받음
  // const [count, setCount] = useState(0);    // count = 변수, setCount = 변수 값을 조정할 함수, useState(0) = 최초의 상태가 0으로 초기화

  // function upClick() {
  //   setCount(count + 1);
  // }

  return (
    <button onClick={onClick}>
      {count}번 증가
    </button>
  )
}

export default IncButton;    // 외부에서 사용하려면 필수!!