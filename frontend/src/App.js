import React from 'react';
import { setup } from './service/MonsterService';

function App() {
  setup().then(
    (monsters) =>
      (document.getElementById('spanText').innerText = monsters[0].name)
  );
  return (
    <div>
      <span id="spanText"></span>
    </div>
  );
}

export default App;
