import { useState } from 'react';
import { useParams } from 'react-router-dom';

function BoardDetail() {
  // const [bid, setBid] = useState(0);

  const params = useParams();
  console.log(params.bno);
  // setBid(params.bno);
  
  return (
    <div className='container main'>
      <h1>boardDetail {params.bid} </h1>
    </div>
  );
}

export default BoardDetail;