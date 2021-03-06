
import React, {Component} from "react";
import {AsyncStorage} from "AsyncStorage";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Link } from 'react-router-dom';
import Menu from './Menu';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

class AllStatistics extends Component {

  constructor(props){
    super(props);
    this.state = {
      statistics: []
    }
  }

  componentDidMount(){
    this._getStadistics();
  }

   async _getStadistics() {
    const isAdmin = await AsyncStorage.getItem("isAdmin")
    const token =  await AsyncStorage.getItem('userToken');
    fetch(process.env.REACT_APP_API_HOST + "/admin/usersstatistic", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token,
        'IsAdmin' : isAdmin,
        },
      method: 'GET',
      mode: "cors",
      })
    .then(async response => {
      const resp = await response.json();
      this.setState({ statistics: resp});
    })
    .catch(error => {
      console.log(error);
    });
  }

  render () {
        return (
       	  <div>
          <Navbar className="bg-light justify-content-between" fixed="top">
      		  <Navbar.Brand>Todas Las Estadisticas</Navbar.Brand>
      		  </Navbar>
      		  <Col>
      	      <Navbar variant="dark" bg="dark"className="justify-content-between">
      	      <Navbar.Brand>Estadisticas</Navbar.Brand>
      	      </Navbar>

                  {this.state.statistics.map(statistic =>
                    <li key={statistic.id.toString()}>
                      <div style={{padding:10}}>
                       <Card id={statistic.id.toString()} border="primary">
                         <Card.Header>Usuario : {statistic.user.toString()} | Categoria : {statistic.nombre.toString()}  | Respuestas Corretas : {statistic.correct_answer.toString()} | Respuestas Incorretas : {statistic.incorrect_answer.toString()}
                         </Card.Header>
                       </Card>
                       </div>
                    </li>
                  )}
              	</Col>
        	
            <Link to="/Stadistics" className="Stadistics">
            <Button variant="primary" type="submit">
                Atras
            </Button>
           </Link>
          </div>
        );
  	}

}
export default AllStatistics;
