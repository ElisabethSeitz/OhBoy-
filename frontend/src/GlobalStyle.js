import { createGlobalStyle } from 'styled-components';
import backgroundImage from './commons/backgroundImage.png';

export default createGlobalStyle`
  :root {
   --green-main: #A8CE00;
   /*--blue-75: #7589A2;
   --blue-50: #E0E4E8;
   --blue-25: #F8F8F8;*/

   --blue-main: #69A3B0;
   --red-main: #F10058;
   --beige-main: #fcfcfa;
   /*--orange-75: #FF9C86;
   --orange-50: #FFBDAF;
   --orange-25: #FFDED7;*/
   
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
   --red-border: 1px solid var(--red-main);
   
   //--blue-background: #E0E4E866;
  
  
  }

  * {
    box-sizing: border-box;
  }
  
  html, body {
    margin: 0;
    font-family: 'Roboto Light', sans-serif;
  }
   
  input, textarea {
    font-size: 1em;
    font-family: inherit;
    //background: var(--blue-background);
  }
  
  body{
    background-image: url(${backgroundImage});
  }
 
`;
