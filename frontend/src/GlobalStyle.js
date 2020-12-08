import { createGlobalStyle } from 'styled-components';
import backgroundImage from './commons/backgroundImage.png';

export default createGlobalStyle`

  :root {
   --green-main: #A8CE00;
   --blue-main: #69A3B0;
   --orange-main: #D13016;
   --beige-main: #fcfcfa;
   
   
   --black-main: #000;
   --grey-font: #AFA9A9;
   --grey-background: #F5F3F3;
   --grey-medium: #DDD9D9;
   
  
   --size-xs: 4px;
   --size-s: 8px;
   --size-m: 12px;
   --size-l: 16px;
   --size-xl: 24px;
   --size-xxl: 32px;
   
   --grey-shadow: 5px 5px 5px var(--grey-medium);
   --grey-border: 1px solid var(--grey-font);
   --black-border: 1px solid var(--black-main);
   --blue-border: 1px solid var(--blue-main);
   --green-border: 1px solid var(--green-main);
   --orange-border: 1px solid var(--orange-main);
   
  
  }

  * {
    box-sizing: border-box;
  }
  
  html, body {
    margin: 0;
    font-family: sans-serif;
    font-weight: 400;
  }
   
  input, textarea {
    font-size: 1em;
    font-family: inherit;
  }
  
  body{
    background-image: url(${backgroundImage});
  }
 
`;
