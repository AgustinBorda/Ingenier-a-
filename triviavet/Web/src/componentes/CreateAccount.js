import React,{Component} from "react";
import { Link } from 'react-router-dom';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {AsyncStorage} from "AsyncStorage";
import ReactDOM from "react-dom";
import Menu from './Menu';

class CreateAccount extends Component{
	constructor(props) {
        super(props);
        this.state = {username: '', password: '', email:''};
    
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }
    
      handleChange(event) {
        this.setState({username: event.target.username, password: event.target.password, 
        	email: event.target.email});
      }
    
      handleSubmit(event) {
        alert('A user was submitted: ' + this.state.username);
        event.preventDefault();
      }
      
      componentDidMount(){
    
          fetch(process.env.API_HOST+"/login",{
              method: 'POST', 
              body: JSON.stringify({username: this.state.username, password: this.state.password,
               email: this.state.email}), 
              mode: "no-cors"
            }) 
            .then(response => {
              console.log(response);
                AsyncStorage.setItem('userToken', response.config.headers.Authorization);
                ReactDOM.render(
                   <Menu/>,
                    document.getElementById('root')
                )
              })
              .catch(error => {
                console.log(error)
              });
                   
      }

  
  render () {
    return (
          <form onSubmit={this.handleSubmit}>
           <h1> CreateAccount</h1>
            <Form>
              <Form.Group controlId="formBasicEmail">
              <Form.Label>Username</Form.Label>
              <Form.Control type="Username" placeholder="Enter Username" bsSize="large" 
              username={this.state.username} onChange={this.handleChange}/>
              <Form.Text className="text-muted">
       
              </Form.Text>
              </Form.Group>

              <Form.Group controlId="formBasicPassword" >
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password"  
                password={this.state.password} onChange={this.handleChange}/>
              </Form.Group>

               <Form.Group controlId="formBasicEmail" >
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="Email"  
                password={this.state.email} onChange={this.handleChange}/>
              </Form.Group>

		   <Button variant="primary" type="submit">
                Crear Cuenta
           </Button>
           <Link to="/login" className="Login">
            <Button variant="primary" type="submit">
                 Atras
            </Button>
           </Link>
            </Form>
 		 </form>
    );
  }
}


export default CreateAccount;