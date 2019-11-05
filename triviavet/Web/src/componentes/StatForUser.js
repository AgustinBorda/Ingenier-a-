
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

class StatForUser extends Component {

  constructor(props){
    super(props);
    this.state = {
      statistics: [],
      username:''
    }
    
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this._getStadisticsForUsers = this._getStadisticsForUsers.bind(this);
  }

  componentDidMount(){
    this._getStadisticsForUsers();
  }
 handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    });
  }
  handleSubmit(event) {
    event.preventDefault();
  }


   async _getStadisticsForUsers() {
    const token =  await AsyncStorage.getItem('userToken');
    const isAdmin = await AsyncStorage.getItem("isAdmin")
    fetch(process.env.REACT_APP_API_HOST + "/admin/userstatistic", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token,
        'IsAdmin' : isAdmin,
      },
      method: 'POST',
       body: JSON.stringify({
        username: this.state.username
       }),
      mode: "cors",

      })
    .then(async response => {
      const resp = await response.json();
      this.setState({ statistics: resp});
      if(this.state.statistics.length===0){
        alert("No existen estadisticas para el usuario ingresado,\n puede que este no exista")
      }
      console.log(this.state);
    })
    .catch(error => {
      console.log(error);
    });
  }

  render () {
        return (
        	     <div>
          <Navbar className="bg-light justify-content-between" fixed="top">
            <Navbar.Brand>Estadisticas Por Usuario</Navbar.Brand>
            </Navbar>

            <Col>
              <Navbar variant="dark" bg="dark"className="justify-content-between">
              <Navbar.Brand>Estadisticas</Navbar.Brand>
              </Navbar>

               <Form onSubmit={this.handleSubmit}>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Ingrese el Username</Form.Label>
                <Form.Control name="username" type="Username" placeholder="Username" 
                bsSize="large" username={this.state.username} onChange={this.handleChange}/>

                <Form.Text className="text-muted"></Form.Text>
              </Form.Group>
              </Form>
              <Button variant="primary" type="submit" 
              onClick={this._getStadisticsForUsers}>
                 Buscar
              </Button>
               <p></p>
               <p></p>
                <Link to="/Stadistics" className="Stadistics">
                    <Button variant="primary" type="submit">
                         Atras
                    </Button>
                   </Link>
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
           
          </div>
        );
  	}

}
export default StatForUser;
