import logo from './logo.svg';
import './App.css';
import CustomButton from './component/CustomButton';
import { useState } from 'react';
import IncButton from './component/IncButton';

// 데이터 생성시 보통 const
const ironMan = {
  name: 'Tony Stark',
  heroName: 'Iron-man',
  imgUrl: 'https://cdn.pixabay.com/photo/2020/11/28/03/19/iron-man-5783522_640.png',
  imgSize: 100,
}

const weapons = [
  {title: 'Replusor Beam', idx: 1 },
  {title: 'Unibeam Blaster', idx: 2 },
  {title: 'Smart missile', idx: 3 },
];

const listWeapons = weapons.map(weapon => 
  <li key={weapon.idx}>
    {weapon.title}
  </li>
)

function App() {
  const [count, setCount] = useState(0);

  function handleClick() {
    setCount(count + 1);
  }

  return (
    <div className="App">
      <header className="App-header">
        <h1>{ironMan.heroName}</h1>
        <img className='profile'
             src={ironMan.imgUrl}
             alt={ironMan.name + '변신사진'}
             style={{
              width: ironMan.imgSize,
              height: ironMan.imgSize,
             }}
        />
        <IncButton count={count} onClick={handleClick} />
        <IncButton count={count} onClick={handleClick}></IncButton>
        {/* <ul>{listWeapons}</ul> */}
        {/* <CustomButton data={ironMan} /> */}
      </header>
    </div>
  );
}

export default App;
