import React, {Component} from "react";
import {AsyncStorage} from "AsyncStorage";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { Link } from 'react-router-dom';
import Menu from './Menu';
import exitlogo from './exit.png';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

class Stadistics extends Component {

  constructor(props){
    super(props);
    this.state = {
      estadisticas: []
    }
  }

  componentDidMount(){
    this._getStadistics();
  }

   async _getStadistics() {
    const token =  await AsyncStorage.getItem('userToken');
    fetch(process.env.REACT_APP_API_HOST + "/logged/statistics", {
      headers : {
        'Accept' : 'application/json',
        'content-type' : 'application/json',
        'Authorization' : token
      },method: 'GET',
      mode: "cors",
      })
    .then(async response => {
      const resp = await response.json();
      this.setState({ estadisticas: resp.estadisticas});
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
		      		<Navbar.Brand>Estadisticas</Navbar.Brand>
		      		<Nav>
		      			<Nav.Link href="/Menu"><img src={exitlogo} height={40} width={40}/>
		      			</Nav.Link>
		      		</Nav>
		      	</Navbar>
		      	<Row style={{paddingTop: 60}} noGutters="true">
		        	<Col>
		          		<Navbar variant="dark" bg="dark"className="justify-content-between">
		            		<Navbar.Brand>Stadistics</Navbar.Brand>
		        		</Navbar>
	            	 {this.state.estadisticas.map((message) =>
	             	 <div style={{padding:10}}>
	                	<Card id={message} border="secondary">
	                 	<Card.Header>{message}
	                  		<Button variant ="primary" type="submit">
	                    		  -
	                  		</Button>
	                  	</Card.Header>
	                	</Card>
	                 </div>
	            )}
	        		</Col>
	        	</Row>
	   
            <Link to="/menu" className="Menu">
            <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
          </div>
        );
  	}		

}
export default Stadistics;

