import { createGlobalStyle } from 'styled-components';

export default createGlobalStyle`
  :root {
   --green-main: #a0ca36;
   /*--blue-75: #7589A2;
   --blue-50: #E0E4E8;
   --blue-25: #F8F8F8;*/

   --blue-main: #5dbcd2;
   /*--orange-75: #FF9C86;
   --orange-50: #FFBDAF;
   --orange-25: #FFDED7;*/
   
   --black-main: #000;
   --grey-font: #AFA9A9;
   --grey-background: #F5F3F3;
   
  
   --size-xs: 4px;
   --size-s: 8px;
   --size-m: 12px;
   --size-l: 16px;
   --size-xl: 24px;
   --size-xxl: 32px;
   
   --black-shadow: 2px 2px 2px var(--black-main);
   --black-border: 1px solid var(--black-main);
   
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
`;
