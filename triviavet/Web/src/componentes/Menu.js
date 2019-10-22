import React,{Component} from "react";
import { Link } from 'react-router-dom';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

class Menu extends Component{
  
  render () {
    return (
        <div>
           <h1> MENU  </h1>
           <Link to="/newQuestion" className="NewQuestion">
           <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
        </div>
         <p></p>
         <p></p>
         <Link to="/menu" className="Menu">
            <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
    );
  }
}


export default Menu;