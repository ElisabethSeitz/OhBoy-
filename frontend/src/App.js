import React from 'react';
import { setup } from './service/MonsterService';

function App() {
  setup().then(
    (text) => (document.getElementById('spanText').innerText = text)
  );
  return (
    <div>
      <span id="spanText"></span>
    </div>
  );
}

export default App;
