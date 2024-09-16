import { useEffect} from 'react';

function DisableBackButton() {

  useEffect(()=>{
    window.history.pushState(null, '', window.location.href);
    window.onpopstate = () => {
    window.history.pushState(null, '', window.location.href);
    }
    });
  }
export default DisableBackButton;