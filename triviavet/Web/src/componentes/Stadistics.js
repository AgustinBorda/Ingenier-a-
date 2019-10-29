import React, {Component} from "react";
import {AsyncStorage} from "AsyncStorage";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Link } from 'react-router-dom';
import Menu from './Menu';


class Stadistics extends Component {

  constructor(props){
    super(props);
    this.state = {
      estadisticas: []
    }
  }

  componentWillMount(){
    this._getStadistics();
  }

   async _getStadistics() {
    const token =  await AsyncStorage.getItem('userToken');
    fetch(process.env.REACT_APP_API_HOST + "/admin/usersstatistic", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token
      },method: 'GET',
      mode: "cors",
      })
    .then(({data}) => {
      this.setState({ estadisticas: data.estadisticas});
    })
    .catch(error => {
      console.log(error);
    });
  }

  render () {
        return (
       
	    	//<h1> Estadisticas</h1>        
            <Link to="/menu" className="Menu">
            <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
    
        );
  	}		

}
export default Stadistics;

