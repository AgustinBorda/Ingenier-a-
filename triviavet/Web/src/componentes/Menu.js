import React,{Component} from "react";
import { Link } from 'react-router-dom';

class Menu extends Component{
  
  render () {
    return (
        <div>
           <h1> MENU  </h1>
           <Link to="/newQuestion" className="NewQuestion">Cargar pregunta</Link>
        </div>
    );
  }
}


export default Menu;