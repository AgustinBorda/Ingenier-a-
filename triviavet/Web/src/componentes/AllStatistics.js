
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
      estadisticas: []
    }
  }

  componentDidMount(){
    this._getStadistics();
  }

   async _getStadistics() {
    const token =  await AsyncStorage.getItem('userToken');
    fetch(process.env.REACT_APP_API_HOST + "/admin/usersstatistics", {
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
		      		<Navbar.Brand>Todas Las Estadisticas</Navbar.Brand>
		      	
		      	</Navbar>
		      	<Row style={{paddingTop: 60}} noGutters="true">
		        	<Col>
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
			
            <p></p>
            <p></p>   
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

